package com.google.sites.medcare.PathologyLab;

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

public class PathologyAdapter extends RecyclerView.Adapter<PathologyAdapter.PathologyViewHolder> {

    private Context mCt;
    private List<PathologyList> pathList;

    public PathologyAdapter(Context mCt, List<PathologyList> pathList) {
        this.mCt = mCt;
        this.pathList = pathList;
    }

    @NonNull
    @Override
    public PathologyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCt).inflate(R.layout.pathology_row,parent,false);
        return new PathologyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PathologyAdapter.PathologyViewHolder holder, int position) {
        final PathologyList pathologyList = pathList.get(position);
        holder.Address.setText(pathologyList.getAddress());
        holder.Name.setText(pathologyList.getName());
        holder.Facilities.setText(pathologyList.getFacilities());

        holder.Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(pathologyList.getLoc()));
                mCt.startActivity(intent);
            }
        });
        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+pathologyList.getPhone()));
                mCt.startActivity(dialIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }

    class PathologyViewHolder extends RecyclerView.ViewHolder {
        TextView Address;
        TextView Name;
        ImageView Call;
        ImageView Map;
        TextView Facilities;

        public PathologyViewHolder(@NonNull View itemView) {
            super(itemView);

            Address=itemView.findViewById(R.id.pathology_address);
            Map=itemView.findViewById(R.id.imageViewMapP);
            Call = itemView.findViewById(R.id.imageViewCallP);
            Name = itemView.findViewById(R.id.pathology_name);
            Facilities = itemView.findViewById(R.id.textViewFac);
        }
    }

}


