package com.enfotrix.hazir.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Models.ModelCarSearch;
import com.enfotrix.hazir.Models.ModelMyCar;
import com.enfotrix.hazir.R;

import java.util.List;

public class AdapterCarSearch extends RecyclerView.Adapter<AdapterCarSearch.ViewHolder>implements View.OnClickListener {


    private List<ModelCarSearch> listModelCarSearch;
    private LayoutInflater mInflater;


    Context context;


    OnAdapterCarSearchListner onAdapterCarSearchListner;

    public interface OnAdapterCarSearchListner{
        void OnItemClick( ModelCarSearch modelCarSearch);
    }


    public OnAdapterCarSearchListner getOnAdapterCarSearchListner() {
        return onAdapterCarSearchListner;
    }

    public void setOnAdapterCarSearchListner(OnAdapterCarSearchListner onAdapterCarSearchListner) {
        this.onAdapterCarSearchListner = onAdapterCarSearchListner;
    }

    public AdapterCarSearch(){

    }
    public AdapterCarSearch(Context context, List<ModelCarSearch> listModelCarSearches) {
        this.mInflater = LayoutInflater.from(context);
        this.listModelCarSearch = listModelCarSearches;
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


        final ModelCarSearch tempMyCar = listModelCarSearch.get(position);

        holder.tvCarMake.setText(tempMyCar.getCar_make());
        holder.tvCarModel.setText(tempMyCar.getCar_model());
        holder.tvCarModelYear.setText(tempMyCar.getModel_year());
        holder.tvCarLocation.setText(tempMyCar.getPickup_city());
        holder.tvCarStatus.setText(tempMyCar.getCar_status());
        holder.tvCarRent.setText("Rent: Rs "+tempMyCar.getCar_rent()+" /day");
        if((!tempMyCar.getImage().isEmpty()) || tempMyCar.getImage()!=null){
            String imgURI= "https://gaarihazir.com/car-images/"+tempMyCar.getImage();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(holder.imgCarPhot);
        }

    }


    @Override
    public int getItemCount() {
        return listModelCarSearch.size();
    }


  
    @Override
    public void onClick(View view) {


    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCarMake,tvCarModel,tvCarModelYear,tvCarLocation,tvCarRent,tvCarStatus;
        ImageView imgCarPhot;
        LinearLayout layAvailable;
        RelativeLayout layCarlist;
        CardView cdDelete,cdEdit;
        ViewHolder(View itemView) {
            super(itemView);


            this.tvCarStatus = (TextView) itemView.findViewById(R.id.tvCarStatus);
            this.tvCarMake = (TextView) itemView.findViewById(R.id.tvCarMake);
            this.tvCarModel = (TextView) itemView.findViewById(R.id.tvCarModel);
            this.tvCarModelYear = (TextView) itemView.findViewById(R.id.tvCarModelYear);
            this.tvCarLocation = (TextView) itemView.findViewById(R.id.tvCarLocation);
            this.tvCarRent = (TextView) itemView.findViewById(R.id.tvCarRent);
            this.imgCarPhot = (ImageView) itemView.findViewById(R.id.imgCarPhoto);



            this.layCarlist = itemView.findViewById(R.id.layCarlist);
            this.layAvailable = itemView.findViewById(R.id.layAvailable);
            this.cdDelete =  itemView.findViewById(R.id.cdDelete);
            this.cdEdit =  itemView.findViewById(R.id.cd2);

            layAvailable.setVisibility(View.GONE);
            cdDelete.setVisibility(View.GONE);
            cdEdit.setVisibility(View.GONE);

            layCarlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAdapterCarSearchListner.OnItemClick(listModelCarSearch.get(getAdapterPosition()));

                }
            });

            
        }




        @Override
        public void onClick(View view) {


        }


    }


}
