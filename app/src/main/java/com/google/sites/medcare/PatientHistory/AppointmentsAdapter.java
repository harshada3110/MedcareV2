package com.google.sites.medcare.PatientHistory;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.sites.medcare.R;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.AppointmentsViewHolder> {

    private Context mCtx;
    private List<AppointmentsList> appointmentsLists;

    public AppointmentsAdapter(Context mCtx, List<AppointmentsList> appointmentsLists) {
        this.mCtx = mCtx;
        this.appointmentsLists = appointmentsLists;
    }

    @NonNull
    @Override
    public AppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.listview_appo_item,parent,false);

        return new AppointmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsViewHolder holder, int position) {
        final AppointmentsList appointmentsList = appointmentsLists.get(position);
        holder.HospitalName.setText(appointmentsList.getHospitalName());
        holder.Specialist.setText(appointmentsList.getSpecialist());
        holder.Date.setText(appointmentsList.getDate());

        holder.appCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mCtx, EveryAppointment.class);
                intent.putExtra("HospName",appointmentsList.getHospitalName());
                intent.putExtra("Specialists",appointmentsList.getSpecialist());
                intent.putExtra("Date",appointmentsList.getDate());
                intent.putExtra("Time",appointmentsList.getTime());
                intent.putExtra("Comments",appointmentsList.getComments());
                intent.putExtra("Location",appointmentsList.getLocation());
                String key = appointmentsList.key;
                intent.putExtra("Prescription",appointmentsList.key);
                Log.d("Parents", key);

                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentsLists.size();
    }

    class AppointmentsViewHolder extends RecyclerView.ViewHolder {

        TextView Date;
        TextView HospitalName;
        TextView Specialist;
        CardView appCard;

        public AppointmentsViewHolder(@NonNull View itemView) {
            super(itemView);

            HospitalName = itemView.findViewById(R.id.textViewHospName);
            Specialist = itemView.findViewById(R.id.textViewSpec);
            Date = itemView.findViewById(R.id.textViewDate);
            appCard = itemView.findViewById(R.id.appCard);
        }
    }
}
