package com.google.sites.medcare.Ambulance;

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

import com.google.sites.medcare.R;

import java.util.List;

public class AmbulanceAdapter extends RecyclerView.Adapter<AmbulanceAdapter.AmbulanceViewHolder> {

    private Context mCtx;
    private List<AmbulanceList> ambList;

    public AmbulanceAdapter(Context mCtx, List<AmbulanceList> ambList) {
        this.mCtx = mCtx;
        this.ambList = ambList;
    }

    @NonNull
    @Override
    public AmbulanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.ambulance_row,parent,false);
        return new AmbulanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmbulanceViewHolder holder, int position) {
        final AmbulanceList ambulanceList =ambList.get(position);
        holder.AmbulanceName.setText(ambulanceList.getName());
        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+ambulanceList.getNumber()));
                mCtx.startActivity(dialIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ambList.size();
    }

    class AmbulanceViewHolder extends RecyclerView.ViewHolder {

        TextView AmbulanceName;
        ImageView Call;

        public AmbulanceViewHolder(@NonNull View itemView) {
            super(itemView);

            AmbulanceName=itemView.findViewById(R.id.ambulance_name);
            Call = itemView.findViewById(R.id.imageViewCallA);

        }
    }


}

