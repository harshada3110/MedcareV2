package com.google.sites.medcare.Appointments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.sites.medcare.PatientHistory.AppointmentsList;
import com.google.sites.medcare.PatientHistory.EveryAppointment;
import com.google.sites.medcare.R;

import org.w3c.dom.Text;

import java.util.List;

public class RequestedAppointmentsAdapter extends RecyclerView.Adapter<RequestedAppointmentsAdapter.RequestedAppointmentsViewHolder> {

    private Context mCtx;
    private List<RequestedAppointmentsList> requestedAppointmentsLists;

    public RequestedAppointmentsAdapter(Context mCtx, List<RequestedAppointmentsList> requestedAppointmentsLists) {
        this.mCtx = mCtx;
        this.requestedAppointmentsLists = requestedAppointmentsLists;
    }

    @NonNull
    @Override
    public RequestedAppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.row_requested_appointment,parent,false);

        return new RequestedAppointmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedAppointmentsViewHolder holder, int position) {
        final RequestedAppointmentsList requestedAppointmentsList = requestedAppointmentsLists.get(position);
        holder.HospitalName.setText(requestedAppointmentsList.getHospitalName());
        holder.Specialist.setText(requestedAppointmentsList.getSpecialist());
        holder.Date.setText(requestedAppointmentsList.getDate());
        holder.Time.setText(requestedAppointmentsList.getTime());
        if(requestedAppointmentsList.getApproval() == 0 ){
            holder.Status.setText("Pending");
            holder.Status.setTextColor(Color.parseColor("#FFC107"));
        }
        else if (requestedAppointmentsList.getApproval() == 1 ){
            holder.Status.setText("Accepted");
            holder.Status.setTextColor(Color.parseColor("#4CAF50"));
        }
        else {
            holder.Status.setText("Rejected");
            holder.Status.setTextColor(Color.parseColor("#F44336"));
        }
    }

    @Override
    public int getItemCount() {
        return requestedAppointmentsLists.size();
    }

    class RequestedAppointmentsViewHolder extends RecyclerView.ViewHolder {

        TextView Date;
        TextView HospitalName;
        TextView Specialist;
        TextView Time;
        TextView Status;

        public RequestedAppointmentsViewHolder(@NonNull View itemView) {
            super(itemView);

            HospitalName = itemView.findViewById(R.id.textViewHospName);
            Specialist = itemView.findViewById(R.id.textViewSpec);
            Date = itemView.findViewById(R.id.textViewDate);
            Time = itemView.findViewById(R.id.textViewTime);
            Status = itemView.findViewById(R.id.textViewStatus);
        }
    }
}
