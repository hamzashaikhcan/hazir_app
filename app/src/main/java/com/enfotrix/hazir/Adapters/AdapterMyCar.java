package com.enfotrix.hazir.Adapters;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Models.ModelMyCar;
import com.enfotrix.hazir.Models.ModelMyCar;
import com.enfotrix.hazir.R;

import java.util.List;

public class AdapterMyCar extends RecyclerView.Adapter<AdapterMyCar.ViewHolder>implements View.OnClickListener {


    private List<ModelMyCar> listModelMyCar;
    private LayoutInflater mInflater;
    Context context;
    OnAdapterIntractionListner onAdapterIntractionListner;

    public OnAdapterIntractionListner getOnAdapterIntractionListner() {
        return onAdapterIntractionListner;
    }

    public void setOnAdapterIntractionListner(OnAdapterIntractionListner onAdapterIntractionListner) {
        this.onAdapterIntractionListner = onAdapterIntractionListner;
    }

    public AdapterMyCar(Context context, List<ModelMyCar> listModelMyCars) {
        this.mInflater = LayoutInflater.from(context);
        this.listModelMyCar= listModelMyCars;
        this.context= context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_item_my_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ModelMyCar tempMyCar = listModelMyCar.get(position);

        holder.tvCarMake.setText(tempMyCar.getCar_make());
        holder.tvCarModel.setText(tempMyCar.getCar_model());
        holder.tvCarModelYear.setText(tempMyCar.getModel_year());
        holder.tvCarLocation.setText(tempMyCar.getPickup_city());
        holder.tvCarStatus.setText(tempMyCar.getCar_status());
        holder.tvCarRent.setText("Rent: Rs "+tempMyCar.getCar_rent()+" /day");
        if(tempMyCar.getCar_status().equals("Available")) holder.switchIsAvailable.setChecked(true);
        if((!tempMyCar.getImage().isEmpty()) || tempMyCar.getImage()!=null){
            String imgURI= "https://gaarihazir.com/car-images/"+tempMyCar.getImage();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(holder.imgCarPhot);
        }



        //car_owner_name`


        //holder.imgCarPhot.setText(tempMyCar.getPhoto());






    }


    @Override
    public int getItemCount() {

        return listModelMyCar.size();

    }


    public interface OnAdapterIntractionListner{
        void OnItemClick( ModelMyCar modelMyCar);
        void OnEditImgClick( ModelMyCar modelMyCar);
        void OnDeleteCar( ModelMyCar modelMyCar, int position);
        void OnStatusChange( ModelMyCar modelMyCar , TextView tvStatus, int position);
    }
    @Override
    public void onClick(View view) {


    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCarMake,tvCarModel,tvCarModelYear,tvCarLocation,tvCarRent,tvCarStatus;
        Switch switchIsAvailable;
        ImageView imgCarPhot;
        ImageView imgEditCar,imgDeleteCar;
        RelativeLayout layCarlist;
        ViewHolder(View itemView) {
            super(itemView);


            this.switchIsAvailable = (Switch) itemView.findViewById(R.id.switchIsAvailable);
            this.tvCarStatus = (TextView) itemView.findViewById(R.id.tvCarStatus);
            this.tvCarMake = (TextView) itemView.findViewById(R.id.tvCarMake);
            this.tvCarModel = (TextView) itemView.findViewById(R.id.tvCarModel);
            this.tvCarModelYear = (TextView) itemView.findViewById(R.id.tvCarModelYear);
            this.tvCarLocation = (TextView) itemView.findViewById(R.id.tvCarLocation);
            this.tvCarRent = (TextView) itemView.findViewById(R.id.tvCarRent);
            this.imgCarPhot = (ImageView) itemView.findViewById(R.id.imgCarPhoto);
            this.imgEditCar = (ImageView) itemView.findViewById(R.id.imgEditCar);
            this.imgDeleteCar = (ImageView) itemView.findViewById(R.id.imgDeleteCar);
            this.layCarlist = (RelativeLayout) itemView.findViewById(R.id.layCarlist);



            layCarlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAdapterIntractionListner.OnItemClick(listModelMyCar.get(getAdapterPosition()));
                }
            });
            imgEditCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAdapterIntractionListner.OnEditImgClick(listModelMyCar.get(getAdapterPosition()));
                }
            });
            imgDeleteCar.setOnClickListener(new View.OnClickListener() {
                @Override


                public void onClick(View view) {

                    onAdapterIntractionListner.OnDeleteCar(listModelMyCar.get(getAdapterPosition()),getAdapterPosition());

                }
            });
            switchIsAvailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // do something, the isChecked will be
                    // true if the switch is in the On position
                    ModelMyCar modelMyCar= listModelMyCar.get(getAdapterPosition());
                    if(isChecked){
                        modelMyCar.setCar_status("Available");

                        onAdapterIntractionListner.OnStatusChange(modelMyCar,tvCarStatus, getAdapterPosition());


                    }
                    else {
                        modelMyCar.setCar_status("Booked");

                        onAdapterIntractionListner.OnStatusChange(modelMyCar,tvCarStatus,getAdapterPosition());

                    }
                    //else if(!isChecked) modelMyCar.setCar_status("Booked");

                }
            });



        /*myTextView = itemView.findViewById(R.id.);
        itemView.setOnClickListener(this);*/


        }



        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }


    }


}
