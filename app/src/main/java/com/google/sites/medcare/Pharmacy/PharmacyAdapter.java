package com.google.sites.medcare.Pharmacy;

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

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder> {

    private Context mCt2;
    private List<PharmacyList> pharmList;

    public PharmacyAdapter(Context mCt2, List<PharmacyList> pharmList) {
        this.mCt2 = mCt2;
        this.pharmList = pharmList;
    }


    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCt2).inflate(R.layout.pharmacy_row,parent,false);
        return new PharmacyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyAdapter.PharmacyViewHolder holder, int position) {
        final PharmacyList pharmacyList = pharmList.get(position);
        holder.Address.setText(pharmacyList.getAddress());
        holder.Name.setText(pharmacyList.getName());
        holder.Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(pharmacyList.getLoc()));
                mCt2.startActivity(intent);
            }
        });
        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+pharmacyList.getPhone()));
                mCt2.startActivity(dialIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pharmList.size();
    }

    class PharmacyViewHolder extends RecyclerView.ViewHolder {
        TextView Address;
        TextView Name;
        ImageView Call;
        ImageView Map;

        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.pharmacy_address);
            Map = itemView.findViewById(R.id.imageViewMap);
            Call = itemView.findViewById(R.id.imageViewCall);
            Name = itemView.findViewById(R.id.pharmacy_name);
        }
    }
}


