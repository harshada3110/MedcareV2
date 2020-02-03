package com.google.sites.medcare.Schemes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;
import com.google.sites.medcare.AboutUs;
import com.google.sites.medcare.R;

import java.util.List;


public class SchemesAdapter extends RecyclerView.Adapter<SchemesAdapter.SchemesViewHolder> {
    private Context mctx;
    private List<Schemes> schemeList;
    private String schemeName;
    private String schemeAbout;

    public SchemesAdapter(Context mctx, List<Schemes> schemeList) {
        this.mctx = mctx;
        this.schemeList =schemeList;
    }

    @NonNull
    @Override
    public SchemesAdapter.SchemesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mctx).inflate(R.layout.scheme_row,parent,false);
        return  new SchemesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemesAdapter.SchemesViewHolder holder, int position) {

        SharedPreferences userDetails = mctx.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editDetails = userDetails.edit();
        String lang1 = userDetails.getString("MyLang", "en");
        Log.d("Language1", lang1);

        int langCode = FirebaseTranslateLanguage.EN;


        String lang = lang1;

        Schemes schemes=schemeList.get(position);
        String name = schemes.getName();
        String about = schemes.getAbout();

        /*if(lang == "en"){
            holder.Name.setText(schemes.getName());
            holder.About.setText(schemes.getAbout());
            Log.d("Language2", "Eng");
        }
        else if(lang == "hi"){
            Log.d("Language2", "Hin");

            translateTextHi(langCode, name, about, holder);
        }
        else if(lang == "kn"){
            Log.d("Language2", "Kn");

            translateTextKn(langCode, name, about, holder);
        }
        else {
            Log.d("LanguageX", lang);
            holder.Name.setText(schemes.getName());
            holder.About.setText(schemes.getAbout());
            Log.d("Language3", "Eng1");
        }*/

        switch (lang){
            case "en":
                holder.Name.setText(schemes.getName());
                holder.About.setText(schemes.getAbout());
                Log.d("Language2", "Eng");
                break;
            case "hi":
                Log.d("Language2", "Hin");

                translateTextHi(langCode, name, about, holder);
                break;
            case "kn":
                Log.d("Language2", "Kn");

                translateTextKn(langCode, name, about, holder);
                break;
            default:
                Log.d("LanguageX", lang);
                holder.Name.setText(schemes.getName());
                holder.About.setText(schemes.getAbout());
                Log.d("Language3", "Eng1");
        }

        holder.imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+schemes.getHelpline()));
                mctx.startActivity(dialIntent);
            }
        });
        holder.imageViewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(schemes.getLink()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return schemeList.size();
    }

    class SchemesViewHolder extends RecyclerView.ViewHolder{

        TextView Name;
        TextView About;
        ImageView imageViewWeb;
        ImageView imageViewCall;

        public SchemesViewHolder(@NonNull View itemview){
            super(itemview);
            Name=itemview.findViewById(R.id.scheme_name);
            About=itemview.findViewById(R.id.scheme_about);
            imageViewWeb=itemView.findViewById(R.id.imageViewWebSc);
            imageViewCall=itemView.findViewById(R.id.imageViewCallSc);
        }
    }

    /*private void identifyLanguage() {
        sourceText = mSourcetext.getText().toString();

        FirebaseLanguageIdentification identifier = FirebaseNaturalLanguage.getInstance()
                .getLanguageIdentification();

        mSourceLang.setText("Detecting..");
        identifier.identifyLanguage(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s.equals("und")){
                    Toast.makeText(getApplicationContext(),"Language Not Identified",Toast.LENGTH_SHORT).show();

                }
                else {
                    getLanguageCode(s);
                }
            }
        });
    }*/

    private void translateTextKn(int langCode, String name, String about, SchemesAdapter.SchemesViewHolder holder) {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                //from language
                .setSourceLanguage(langCode)
                // to language
                .setTargetLanguage(FirebaseTranslateLanguage.KN)
                .build();

        final FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance()
                .getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .build();


        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                translator.translate(name).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        schemeName = s;
                        Log.d("Tag", schemeName);
                        holder.Name.setText(schemeName);
                    }
                });
                translator.translate(about).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String a) {
                        schemeAbout = a;
                        holder.About.setText(schemeAbout);
                    }
                });
            }
        });
    }

    private void translateTextHi(int langCode, String name, String about, SchemesAdapter.SchemesViewHolder holder) {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                //from language
                .setSourceLanguage(langCode)
                // to language
                .setTargetLanguage(FirebaseTranslateLanguage.HI)
                .build();

        final FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance()
                .getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .build();


        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                translator.translate(name).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        schemeName = s;
                        Log.d("Tag", schemeName);
                        holder.Name.setText(schemeName);
                    }
                });
                translator.translate(about).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String a) {
                        schemeAbout = a;
                        holder.About.setText(schemeAbout);
                    }
                });
            }
        });
    }
}
