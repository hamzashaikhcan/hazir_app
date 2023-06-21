package com.enfotrix.hazir.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Models.ModelCarAd;
import com.enfotrix.hazir.R;

import java.util.List;

public class AdapterCarAd extends RecyclerView.Adapter<AdapterCarAd.ViewHolder> {


    private List<ModelCarAd> listModelCarAd;
    private LayoutInflater mInflater;
    Context context;

    public AdapterCarAd(Context context, List<ModelCarAd> listModelCarAds) {
        this.mInflater = LayoutInflater.from(context);
        this.listModelCarAd= listModelCarAds;
        this.context= context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.list_item_car_ad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ModelCarAd tempCarAd = listModelCarAd.get(position);
        holder.tvCarName.setText(tempCarAd.getName());

        Glide.with(context)
                .load(tempCarAd.getPhoto())
                .into(holder.imgCarPhot);

        //holder.imgCarPhot.setText(tempCarAd.getPhoto());

    }

    @Override
    public int getItemCount() {
        return listModelCarAd.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCarName;
        ImageView imgCarPhot;
        ViewHolder(View itemView) {
            super(itemView);

            this.tvCarName = (TextView) itemView.findViewById(R.id.tvCarName);
            this.imgCarPhot = (ImageView) itemView.findViewById(R.id.imgCarPhoto);
        /*myTextView = itemView.findViewById(R.id.);
        itemView.setOnClickListener(this);*/
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }


    }


}

