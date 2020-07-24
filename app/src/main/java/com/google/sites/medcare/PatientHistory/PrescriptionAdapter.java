package com.google.sites.medcare.PatientHistory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.sites.medcare.R;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder> {

    private Context mCtx;
    private List<PrescriptionList> prescriptionLists;

    public PrescriptionAdapter(Context mCtx, List<PrescriptionList> prescriptionLists) {
        this.mCtx = mCtx;
        this.prescriptionLists = prescriptionLists;
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.prescription_row,parent,false);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionAdapter.PrescriptionViewHolder holder, int position) {
        final PrescriptionList prescriptionList = prescriptionLists.get(position);

        String name = prescriptionList.getName();
        String dose = prescriptionList.getDose();
        String duration = prescriptionList.getDuration();

        String med = name+"-"+dose+"-"+duration;
        holder.Presc.setText(med);
        Log.d("Med0", med);

    }

    @Override
    public int getItemCount() {
        return prescriptionLists.size();
    }

    class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        TextView Presc;

        public PrescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            Presc = itemView.findViewById(R.id.presc_name);
        }
    }
}
