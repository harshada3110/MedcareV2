package com.google.sites.medcare.Hospital;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.sites.medcare.Hospital.HospitalMap.MapActivity;
import com.google.sites.medcare.R;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder> {

    private Context mCtx;
    private List<HospitalList> hospList;

    public HospitalAdapter(Context mCtx, List<HospitalList> hospList) {
        this.mCtx = mCtx;
        this.hospList = hospList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.hospital_row,parent,false);

        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HospitalViewHolder holder, int position) {
         final HospitalList hospitalList = hospList.get(position);
         holder.HospitalName.setText(hospitalList.getName());
         String name = hospitalList.getName();
         holder.Location.setText(hospitalList.getLocation());
         Glide.with(mCtx).load(hospitalList.getPhoto()).into(holder.imageView);
         holder.HospitalCard.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(mCtx,HospitalDetails.class);
                 intent.putExtra("HospName", hospitalList.getName());
                 intent.putExtra("HospImage", hospitalList.getPhoto());
                 intent.putExtra("HospAddress", hospitalList.getAddress());
                 intent.putExtra("HospLocation", hospitalList.getLocation());
                 intent.putExtra("HospAbout", hospitalList.getAbout());
                 intent.putExtra("HospContact", hospitalList.getContact());
                 intent.putExtra("HospEmail", hospitalList.getEmail());
                 intent.putExtra("HospFacilities", hospitalList.getFacilities());
                 intent.putExtra("HospLoc", hospitalList.getLoc());
                 intent.putExtra("HospWebsite", hospitalList.getWebsite());
                 intent.putExtra("HospLongitude", hospitalList.getLongi());
                 intent.putExtra("HospLatitude", hospitalList.getLat());
                 mCtx.startActivity(intent);
             }
         });
        holder.imageViewLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(hospitalList.getLoc()));
                mCtx.startActivity(intent);*/
                Intent intent=new Intent(mCtx, MapActivity.class);
                intent.putExtra("HospLongitude", hospitalList.getLongi());
                intent.putExtra("HospLatitude", hospitalList.getLat());
                mCtx.startActivity(intent);
            }
        });
        holder.imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+hospitalList.getContact()));
                mCtx.startActivity(dialIntent);
            }
        });
        holder.imageViewWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(hospitalList.getWebsite()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hospList.size();
    }

    class HospitalViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView HospitalName;
        TextView Location;
        CardView HospitalCard;
        ImageView imageViewWeb;
        ImageView imageViewCall;
        ImageView imageViewLoc;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);

            HospitalName = itemView.findViewById(R.id.hospital_name);
            Location = itemView.findViewById(R.id.hospital_location);
            imageView=itemView.findViewById(R.id.post_image);
            imageViewWeb=itemView.findViewById(R.id.imageViewWebP);
            imageViewCall=itemView.findViewById(R.id.imageViewCallP);
            imageViewLoc=itemView.findViewById(R.id.imageViewMapP);
            HospitalCard = itemView.findViewById(R.id.appCard);
        }
    }
}
