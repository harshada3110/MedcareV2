package com.google.sites.medcare.Schemes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.sites.medcare.AboutUs;
import com.google.sites.medcare.R;

import java.util.List;


public class SchemesAdapter extends RecyclerView.Adapter<SchemesAdapter.SchemesViewHolder> {
    private Context mctx;
    private List<Schemes> schemeList;

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
        Schemes schemes=schemeList.get(position);
        holder.Name.setText(schemes.getName());
        holder.About.setText(schemes.getAbout());

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
}
