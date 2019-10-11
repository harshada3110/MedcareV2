package com.google.sites.medcare.BloodBank;

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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.sites.medcare.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class BloodAdapter extends RecyclerView.Adapter<BloodAdapter.BloodViewHolder> {
    private Context mCtx;
    private List<BloodBankList> bloodList;


    public BloodAdapter(Context mCtx,List<BloodBankList> bloodList) {
        this.mCtx=mCtx;
        this.bloodList=bloodList;

    }

    @NonNull
    @Override
    public BloodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.blood_row,parent,false);

        return new BloodViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final BloodViewHolder holder, int position) {
        final BloodBankList blood = bloodList.get(position);
        holder.BloodBankName.setText(blood.getName());
        holder.BloodBankAddress.setText(blood.getAddress());

        holder.AbPos.setText(Integer.toString(blood.getABpos()));
        holder.AbNeg.setText(Integer.toString(blood.getABneg()));
        holder.APos.setText(Integer.toString(blood.getApos()));
        holder.ANeg.setText(Integer.toString(blood.getAneg()));
        holder.BPos.setText(Integer.toString(blood.getBpos()));
        holder.BNeg.setText(Integer.toString(blood.getBneg()));
        holder.OPos.setText(Integer.toString(blood.getOpos()));
        holder.ONeg.setText(Integer.toString(blood.getOneg()));

        /*holder.BloodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, EveryBloodBank.class);
                intent.putExtra("AbPosVal",blood.getABpos());
                intent.putExtra("AbNegVal",blood.getABneg());
                intent.putExtra("APosVal",blood.getApos());
                intent.putExtra("ANegVal",blood.getAneg());
                intent.putExtra("BPosVal",blood.getBpos());
                intent.putExtra("BNegVal",blood.getBneg());
                intent.putExtra("OPosVal",blood.getOpos());
                intent.putExtra("ONegVal",blood.getOneg());
                intent.putExtra("AbPossVal",blood.getABpos());
                intent.putExtra("BloodBankName",blood.getName());
                intent.putExtra("BloodBankPhone",blood.getPhone());
                intent.putExtra("BloodBankAddress",blood.getAddress());
                intent.putExtra("BloodBankLoc",blood.getLink());

                mCtx.startActivity(intent);
            }
        });*/

        holder.AbPosVal = blood.getABpos();
        holder.AbNegVal = blood.getABneg();
        holder.APosVal = blood.getApos();
        holder.ANegVal = blood.getAneg();
        holder.BPosVal = blood.getBpos();
        holder.BNegVal = blood.getBneg();
        holder.OPosVal = blood.getOpos();
        holder.ONegVal = blood.getOneg();

        holder.Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(blood.getLink()));
                mCtx.startActivity(intent);
            }
        });

        holder.Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+blood.getPhone()));
                mCtx.startActivity(dialIntent);
            }
        });

        if(holder.AbPosVal>=1){
            Picasso.get().load(R.drawable.check).into(holder.AbPosImg);
        }
        else{
            Picasso.get().load(R.drawable.cross).into(holder.AbPosImg);
        }

        if(holder.AbNegVal>=1){
            Picasso.get().load(R.drawable.check).into(holder.AbNegImg);
        }
        else{
            Picasso.get().load(R.drawable.cross).into(holder.AbNegImg);
        }

        if(holder.APosVal>=1){
            Picasso.get().load(R.drawable.check).into(holder.APosImg);
        }
        else{
            Picasso.get().load(R.drawable.cross).into(holder.APosImg);
        }

        if(holder.ANegVal>=1){
            Picasso.get().load(R.drawable.check).into(holder.ANegImg);
        }
        else{
            Picasso.get().load(R.drawable.cross).into(holder.ANegImg);
        }

        if(holder.BPosVal>=1){
            Picasso.get().load(R.drawable.check).into(holder.BPosImg);
        }
        else{
            Picasso.get().load(R.drawable.cross).into(holder.BPosImg);
        }

        if(holder.BNegVal>=1){
            Picasso.get().load(R.drawable.check).into(holder.BNegImg);
        }
        else{
            Picasso.get().load(R.drawable.cross).into(holder.BNegImg);
        }

        if(holder.OPosVal>=1){
            Picasso.get().load(R.drawable.check).into(holder.OPosImg);
        }
        else{
            Picasso.get().load(R.drawable.cross).into(holder.OPosImg);
        }

        if(holder.ONegVal>=1){
            Picasso.get().load(R.drawable.check).into(holder.ONegImg);
        }
        else{
            Picasso.get().load(R.drawable.cross).into(holder.ONegImg);
        }

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.constraintLayoutbb.getVisibility() == View.GONE) {
                    holder.constraintLayoutbb.setVisibility(View.VISIBLE);
                    ViewCompat.animate(holder.arrow).rotation(180).start();

                } else {
                    holder.constraintLayoutbb.setVisibility(View.GONE);
                    ViewCompat.animate(holder.arrow).rotation(0).start();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bloodList.size();
    }

    class BloodViewHolder extends RecyclerView.ViewHolder {

        TextView BloodBankName;
        TextView BloodBankAddress;
        CardView BloodCard;
        ImageView Call;
        ImageView Map;
        ImageView arrow;
        ConstraintLayout constraintLayoutbb;
        ImageView AbPosImg,AbNegImg,APosImg,ANegImg,BPosImg,BNegImg,OPosImg,ONegImg;
        TextView AbPos,AbNeg,APos,ANeg,BPos,BNeg,OPos,ONeg;
        int AbPosVal,AbNegVal,APosVal,ANegVal,BPosVal,BNegVal,OPosVal,ONegVal;

        public BloodViewHolder(@NonNull View itemView) {
            super(itemView);

            BloodBankName=itemView.findViewById(R.id.blood_name);
            BloodBankAddress=itemView.findViewById(R.id.bloodaddress);
            BloodCard=itemView.findViewById(R.id.bloodCard);
            Call = itemView.findViewById(R.id.imageViewCallP);
            Map = itemView.findViewById(R.id.imageViewMapP);
            arrow = itemView.findViewById(R.id.arrow);
            constraintLayoutbb = itemView.findViewById(R.id.constraintLayoutBB);
            AbPosImg = itemView.findViewById(R.id.AbPosImg);
            AbNegImg = itemView.findViewById(R.id.AbNegImg);
            OPosImg = itemView.findViewById(R.id.OPosImg);
            ONegImg = itemView.findViewById(R.id.ONegImg);
            APosImg = itemView.findViewById(R.id.APosImg);
            ANegImg = itemView.findViewById(R.id.ANegImg);
            BPosImg = itemView.findViewById(R.id.BPosImg);
            BNegImg = itemView.findViewById(R.id.BNegImg);

            AbPos = itemView.findViewById(R.id.textViewABpos);
            AbNeg = itemView.findViewById(R.id.textViewABneg);
            APos = itemView.findViewById(R.id.textViewApos);
            ANeg = itemView.findViewById(R.id.textViewAneg);
            BPos = itemView.findViewById(R.id.textViewBpos);
            BNeg = itemView.findViewById(R.id.textViewBneg);
            OPos = itemView.findViewById(R.id.textViewOpos);
            ONeg = itemView.findViewById(R.id.textViewOneg);
        }
    }
}
