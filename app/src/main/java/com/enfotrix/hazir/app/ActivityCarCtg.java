package com.enfotrix.hazir.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Adapters.AdapterCarSearch;
import com.enfotrix.hazir.Adapters.AdapterFeaturedCar;
import com.enfotrix.hazir.Constant;
import com.enfotrix.hazir.Loading;
import com.enfotrix.hazir.Models.ModelCarSearch;
import com.enfotrix.hazir.Models.ModelFeaturedCar;
import com.enfotrix.hazir.Models.ModelMyCar;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.databinding.ActivityCarCtgBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityCarCtg extends AppCompatActivity {
    private Context context;
    private List<ModelCarSearch> listModelCarDetails;
    private AdapterCarSearch adapterCarSearch;

    private RequestQueue requestQueue;

    Utils utils;

    String BASE_API = new Constant().getBaseURL();

    ActivityCarCtgBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCarCtgBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context= ActivityCarCtg.this;
        utils= new Utils(context);
        listModelCarDetails = new ArrayList<>();
        binding.rvCarlist.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));






        boolean Searched =getIntent().getBooleanExtra("Searched",false);
        if(Searched==true) carList( getIntent().getStringExtra("ctg"));
        else{
            carList(
                getIntent().getStringExtra("car_model"),// binding.spinerCarModel.getSelectedItem().toString());
                getIntent().getStringExtra("model_year"),// binding.spinerCarModelYear.getSelectedItem().toString());
                getIntent().getStringExtra("car_make"),// binding.spinerCarMake.getSelectedItem().toString());
                getIntent().getStringExtra("engine_capacity"),// binding.spinerCarEngineCapacity.getSelectedItem().toString());
                getIntent().getStringExtra("pickup_city"),// binding.spinerCityAvail.getSelectedItem().toString());
                getIntent().getStringExtra("driver_availability"),// binding.spinerDriverAvail.getSelectedItem().toString());
                getIntent().getStringExtra("car_tranmission"),// binding.spinerCarTransmission.getSelectedItem().toString());
                getIntent().getStringExtra("car_rent"),// binding.etCarRent.getText().toString());
                getIntent().getStringExtra("car_type")// binding.spinerCarType.getSelectedItem().toString());
            );
        }




    }

    private void showBottomSheetDialog(ModelCarSearch car) {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_layout_car_details);
        TextView tvCarMileage=bottomSheetDialog.findViewById(R.id.tvCarMileage);
        TextView tvCarName=bottomSheetDialog.findViewById(R.id.tvCarName);
        TextView tvEngineCapacity=bottomSheetDialog.findViewById(R.id.tvEngineCapacity);
        TextView tvCarModel=bottomSheetDialog.findViewById(R.id.tvCarModel);
        TextView tvCity=bottomSheetDialog.findViewById(R.id.tvCity);
        TextView tvCarRent=bottomSheetDialog.findViewById(R.id.tvCarRent);
        TextView tvCarTransmission=bottomSheetDialog.findViewById(R.id.tvCarTransmission);
        TextView tvDriverAvail=bottomSheetDialog.findViewById(R.id.tvDriverAvail);
        TextView tvCarType=bottomSheetDialog.findViewById(R.id.tvCarType);
        TextView tvCarSeat=bottomSheetDialog.findViewById(R.id.tvCarSeat);
        TextView tvCarDisc=bottomSheetDialog.findViewById(R.id.tvCarDisc);
        ImageView imgCancel=bottomSheetDialog.findViewById(R.id.imgCancel);
        ImageView imgCarPhoto=bottomSheetDialog.findViewById(R.id.imgCarPhoto);
        Button btnBooking=bottomSheetDialog.findViewById(R.id.btnBooking);
        TextView tvCarNo=bottomSheetDialog.findViewById(R.id.tvCarNo);
        tvCarName.setText(car.getCar_make());
        tvCarModel.setText(car.getCar_model()+"-"+car.getModel_year());
        tvCity.setText(car.getPickup_city());
        tvCarRent.setText("Rs: "+car.getCar_rent()+"/day");
        tvCarTransmission.setText(car.getCar_tranmission());
        tvEngineCapacity.setText(car.getEngine_capacity());
        tvDriverAvail.setText(car.getDriver_availability());
        tvCarType.setText(car.getCar_type());
        tvCarSeat.setText(car.getCar_seats());
        tvCarMileage.setText(car.getCar_mileage());
        tvCarNo.setText(car.getCar_no());
        tvCarDisc.setText(car.getDescription());

        if((!car.getImage().isEmpty()) || car.getImage()!=null){
            String imgURI= "https://gaarihazir.com/car-images/"+car.getImage();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(imgCarPhoto);
        }



        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDialog(car.getPhone_no(),car.getPhone_no(),car.getPhone_no());
            }
        });

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

/*
        LinearLayout copy = bottomSheetDialog.findViewById(R.id.copyLinearLayout);
        LinearLayout share = bottomSheetDialog.findViewById(R.id.shareLinearLayout);
        LinearLayout upload = bottomSheetDialog.findViewById(R.id.uploadLinearLayout);
        LinearLayout download = bottomSheetDialog.findViewById(R.id.download);
        LinearLayout delete = bottomSheetDialog.findViewById(R.id.delete);*/

        bottomSheetDialog.show();
    }

    private void contactDialog(String WA, String Call,String Msg){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_contact);

        LinearLayout layMsg = (LinearLayout) dialog.findViewById(R.id.layMsg);
        LinearLayout layWA = (LinearLayout) dialog.findViewById(R.id.layWA);
        LinearLayout layCall = (LinearLayout) dialog.findViewById(R.id.layCall);
        ImageView imgClose = (ImageView) dialog.findViewById(R.id.imgClose);

        layMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"+Msg));
                startActivity(sendIntent);

            }
        });

        layWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:" + WA);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, ""));
            }
        });

        layCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+Call));
                startActivity(intent);
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();

    }


    public void carList(  String car_model,String model_year,String car_make,String engine_capacity,String pickup_city,
                          String driver_availability,String car_tranmission,String car_rent,String car_type){


        adapterCarSearch = new AdapterCarSearch();
        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request= new JsonArrayRequest(BASE_API+"allcars", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {


                    for(int i =0; i<response.length();i++){

                        JSONObject JSONModelCar=response.getJSONObject(i);
                        JSONObject JSONDriver= JSONModelCar.getJSONObject("driver");


                        ModelCarSearch modelCarSearch = new ModelCarSearch(
                                JSONModelCar.getInt("id"),
                                JSONModelCar.getString("driver_id"),
                                JSONModelCar.getString("car_model"),
                                JSONModelCar.getString("car_make"),
                                JSONModelCar.getString("model_year"),
                                JSONModelCar.getString("car_assembly"),// binding.spinerCarAssembly.getSelectedItem().toString());
                                JSONModelCar.getString("engine_capacity"),// binding.spinerCarEngineCapacity.getSelectedItem().toString());
                                JSONModelCar.getString("body_color"),// binding.spinerBodyColor.getSelectedItem().toString());
                                JSONModelCar.getString("between_cities"),// binding.spinerInCity.getSelectedItem().toString());// InCity spiner -> available for out of city -> Yes/No
                                JSONModelCar.getString("registration_city"),// binding.spinerRegCity.getSelectedItem().toString());
                                JSONModelCar.getString("pickup_city"),// binding.spinerCityAvail.getSelectedItem().toString());
                                JSONModelCar.getString("car_no"),// binding.etCarNumber.getText().toString());
                                JSONModelCar.getString("driver_availability"),// binding.spinerDriverAvail.getSelectedItem().toString());
                                JSONModelCar.getString("car_mileage"),// binding.spinerMilage.getSelectedItem().toString());
                                JSONModelCar.getString("car_rent"),// binding.etCarRent.getText().toString());
                                JSONModelCar.getString("description"),// binding.etDisc.getText().toString());
                                JSONModelCar.getString("insured"),// binding.spinerInsured.getSelectedItem().toString());
                                JSONModelCar.getString("car_seats"),// binding.spinerCarSeat.getSelectedItem().toString());
                                JSONModelCar.getString("car_type"),// binding.spinerCarType.getSelectedItem().toString());
                                JSONModelCar.getString("car_status"),// availability);
                                JSONModelCar.getString("state"),// binding.spinerCarStateAvail.getSelectedItem().toString());
                                JSONModelCar.getString("car_tranmission"),
                                JSONModelCar.getString("image"),
                                JSONDriver.getString("phone_no")

                        );

                        if(modelCarSearch !=null)
                            if(modelCarSearch.getCar_status().equals("Available")) listModelCarDetails.add(modelCarSearch);

                    }


                    adapterCarSearch = new AdapterCarSearch(context,listModelCarDetails);
                    adapterCarSearch.setOnAdapterCarSearchListner(new AdapterCarSearch.OnAdapterCarSearchListner() {
                        @Override
                        public void OnItemClick(ModelCarSearch modelCarSearch) {
                            showBottomSheetDialog(modelCarSearch);
                        }
                    });

                    binding.rvCarlist.setAdapter(adapterCarSearch);
                    adapterCarSearch.notifyDataSetChanged();
                    loading.end();

                }
                catch (Exception ex){
                    loading.end();

                    Toast.makeText(context, ex+"", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.end();
                        Toast.makeText(context, error+"", Toast.LENGTH_SHORT).show();

                    }
                }
        );

        requestQueue.add(request);


    }
    public void carList(String ctg){


        adapterCarSearch = new AdapterCarSearch();
        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request= new JsonArrayRequest(BASE_API+"allcars", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {


                    for(int i =0; i<response.length();i++){

                        JSONObject JSONModelCar=response.getJSONObject(i);
                        JSONObject JSONDriver= JSONModelCar.getJSONObject("driver");


                        ModelCarSearch modelCarSearch = new ModelCarSearch(
                                JSONModelCar.getInt("id"),
                                JSONModelCar.getString("driver_id"),
                                JSONModelCar.getString("car_model"),
                                JSONModelCar.getString("car_make"),
                                JSONModelCar.getString("model_year"),
                                JSONModelCar.getString("car_assembly"),// binding.spinerCarAssembly.getSelectedItem().toString());
                                JSONModelCar.getString("engine_capacity"),// binding.spinerCarEngineCapacity.getSelectedItem().toString());
                                JSONModelCar.getString("body_color"),// binding.spinerBodyColor.getSelectedItem().toString());
                                JSONModelCar.getString("between_cities"),// binding.spinerInCity.getSelectedItem().toString());// InCity spiner -> available for out of city -> Yes/No
                                JSONModelCar.getString("registration_city"),// binding.spinerRegCity.getSelectedItem().toString());
                                JSONModelCar.getString("pickup_city"),// binding.spinerCityAvail.getSelectedItem().toString());
                                JSONModelCar.getString("car_no"),// binding.etCarNumber.getText().toString());
                                JSONModelCar.getString("driver_availability"),// binding.spinerDriverAvail.getSelectedItem().toString());
                                JSONModelCar.getString("car_mileage"),// binding.spinerMilage.getSelectedItem().toString());
                                JSONModelCar.getString("car_rent"),// binding.etCarRent.getText().toString());
                                JSONModelCar.getString("description"),// binding.etDisc.getText().toString());
                                JSONModelCar.getString("insured"),// binding.spinerInsured.getSelectedItem().toString());
                                JSONModelCar.getString("car_seats"),// binding.spinerCarSeat.getSelectedItem().toString());
                                JSONModelCar.getString("car_type"),// binding.spinerCarType.getSelectedItem().toString());
                                JSONModelCar.getString("car_status"),// availability);
                                JSONModelCar.getString("state"),// binding.spinerCarStateAvail.getSelectedItem().toString());
                                JSONModelCar.getString("car_tranmission"),
                                JSONModelCar.getString("image"),
                                JSONDriver.getString("phone_no")
                        );

                        if(modelCarSearch !=null)
                            if(modelCarSearch.getCar_type().equals(ctg))
                                if(modelCarSearch.getCar_status().equals("Available"))
                                    listModelCarDetails.add(modelCarSearch);

                    }


                    adapterCarSearch = new AdapterCarSearch(context,listModelCarDetails);
                    adapterCarSearch.setOnAdapterCarSearchListner(new AdapterCarSearch.OnAdapterCarSearchListner() {
                        @Override
                        public void OnItemClick(ModelCarSearch modelCarSearch) {
                            showBottomSheetDialog(modelCarSearch);
                        }
                    });

                    binding.rvCarlist.setAdapter(adapterCarSearch);
                    adapterCarSearch.notifyDataSetChanged();
                    loading.end();

                }
                catch (Exception ex){
                    loading.end();

                    Toast.makeText(context, ex+"", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.end();
                        Toast.makeText(context, error+"", Toast.LENGTH_SHORT).show();

                    }
                }
        );

        requestQueue.add(request);


    }




}