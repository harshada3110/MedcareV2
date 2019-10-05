package com.google.sites.medcare.Camps;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.sites.medcare.R;

import java.util.List;

public class CampAdapter extends RecyclerView.Adapter<CampAdapter.CampViewHolder> {
    private Context mCtx;
    private List<Camps> campList;

    public CampAdapter(Context mCtx, List<Camps> campList) {
        this.mCtx = mCtx;
        this.campList = campList;
    }

    @NonNull
    @Override
    public CampViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.camp_row,parent,false);

        return new CampViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CampViewHolder holder, int position) {
        final Camps camps=campList.get(position);
        holder.CampType.setText(camps.getType());
        holder.CampVenue.setText(camps.getVenue());
        holder.CampDate.setText(camps.getDate());
        holder.CampDesc.setText(camps.getDescription());
        holder.CampReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(camps.getLink()));
                mCtx.startActivity(webIntent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return campList.size();
    }

    class CampViewHolder extends RecyclerView.ViewHolder {

        TextView CampType;
        TextView CampVenue;
        TextView CampDate;
        TextView CampDesc;
        Button CampReg;

        public CampViewHolder(@NonNull View itemView) {
            super(itemView);

            CampType = itemView.findViewById(R.id.camp_type);
            CampVenue = itemView.findViewById(R.id.camp_venue);
            CampDate = itemView.findViewById(R.id.camp_date);
            CampDesc = itemView.findViewById(R.id.camp_desc);
            CampReg = itemView.findViewById(R.id.buttonRegister);
        }
    }
}