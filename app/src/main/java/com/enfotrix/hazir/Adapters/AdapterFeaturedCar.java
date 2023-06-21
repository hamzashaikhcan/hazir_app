package com.enfotrix.hazir.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Models.ModelFeaturedCar;
import com.enfotrix.hazir.R;

import java.util.List;

public class AdapterFeaturedCar extends RecyclerView.Adapter<AdapterFeaturedCar.ViewHolder>implements View.OnClickListener {


    private List<ModelFeaturedCar> listModelFeaturedCar;
    private LayoutInflater mInflater;


    Context context;


    OnAdapterIntractionListner onAdapterIntractionListner;

    public OnAdapterIntractionListner getOnAdapterIntractionListner() {
        return onAdapterIntractionListner;
    }

    public void setOnAdapterIntractionListner(OnAdapterIntractionListner onAdapterIntractionListner) {
        this.onAdapterIntractionListner = onAdapterIntractionListner;
    }

    public AdapterFeaturedCar(Context context, List<ModelFeaturedCar> listModelFeaturedCars) {
        this.mInflater = LayoutInflater.from(context);
        this.listModelFeaturedCar= listModelFeaturedCars;
        this.context= context;

    }
    public AdapterFeaturedCar() {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_item_featured_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ModelFeaturedCar tempFeaturedCar = listModelFeaturedCar.get(position);
        holder.tvCarName.setText(tempFeaturedCar.getCar_make());
        holder.tvCarRent.setText("Rs. "+tempFeaturedCar.getCar_rent()+"/day");
        holder.tvCityAvailable.setText(tempFeaturedCar.getPickup_city());
        holder.tvCarModelYear.setText(tempFeaturedCar.getCar_model()+"-"+tempFeaturedCar.getModel_year());


        if((!tempFeaturedCar.getImage().isEmpty()) || tempFeaturedCar.getImage()!=null){
            String imgURI= "https://gaarihazir.com/car-images/"+tempFeaturedCar.getImage();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(holder.imgCarPhoto);
        }



    }


    @Override
    public int getItemCount() {
        return listModelFeaturedCar.size();
    }


    public interface OnAdapterIntractionListner{
        void OnItemClick( ModelFeaturedCar modelFeaturedCar);
    }
    @Override
    public void onClick(View view) {


    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCarName,tvCarRent,tvCityAvailable,tvCarModelYear;
        ImageView imgCarPhoto;

        LinearLayout layFeaturedCar;
        ViewHolder(View itemView) {
            super(itemView);

            this.tvCarName = (TextView) itemView.findViewById(R.id.tvCarName);
            this.tvCarRent = (TextView) itemView.findViewById(R.id.tvCarRent);
            this.tvCityAvailable = (TextView) itemView.findViewById(R.id.tvCityAvailable);
            this.tvCarModelYear = (TextView) itemView.findViewById(R.id.tvCarModelYear);
            this.imgCarPhoto = (ImageView) itemView.findViewById(R.id.imgCarPhoto);
            this.layFeaturedCar =  itemView.findViewById(R.id.layFeaturedCar);

            layFeaturedCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAdapterIntractionListner.OnItemClick(listModelFeaturedCar.get(getAdapterPosition()));
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
