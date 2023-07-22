package com.enfotrix.hazir.ui.home;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Adapters.AdapterCarAd;
import com.enfotrix.hazir.Adapters.AdapterFeaturedCar;
import com.enfotrix.hazir.Constant;
import com.enfotrix.hazir.Loading;
import com.enfotrix.hazir.Models.ModelCarAd;
import com.enfotrix.hazir.Models.ModelFeaturedCar;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.app.ActivityCarCtg;
import com.enfotrix.hazir.app.ActivitySearch;
import com.enfotrix.hazir.app.ActivitySignIn;
import com.enfotrix.hazir.databinding.FragmentHomeBinding;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment   {

    private AdapterCarAd adapterCarAd;
    private List<ModelCarAd> listModelCarAds;
    private AdapterFeaturedCar adapterFeaturedCar;
    private List<ModelFeaturedCar> listModelFeaturedCar;
    private FragmentHomeBinding binding;
    private Context context;
    private RequestQueue requestQueue;
    String ctg="Sedan";
    Utils utils;

    String BASE_API = new Constant().getBaseURL();

    SpinKitView spinKitView;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spinKitView = root.findViewById(R.id.spin_kit);
        Sprite circle = new Circle();
        spinKitView.setIndeterminateDrawable(circle);

        context= getContext();
        utils= new Utils(context);


        listModelCarAds = new ArrayList<>();
        listModelFeaturedCar = new ArrayList<>();
        binding.rvAd.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.rvFCar.setLayoutManager(new GridLayoutManager(getContext(), 2));


        //Toast.makeText(context, utils.getToken()+"", Toast.LENGTH_SHORT).show();

//        binding.layCtg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), ActivityCarCtg.class));
//            }
//        });



        binding.laySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), ActivitySearch.class));
            }
        });
        /*binding.layLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logoutDialog();
            }
        });*/


        binding.cdBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctg="Bus";
                Intent intent= new Intent(getActivity(), ActivityCarCtg.class);
                intent.putExtra("Searched", true);
                intent.putExtra("ctg",ctg);
                startActivity(intent);

            }
        });
        binding.cdHatchBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctg="Hatchback";
                Intent intent= new Intent(getActivity(), ActivityCarCtg.class);
                intent.putExtra("Searched", true);
                intent.putExtra("ctg",ctg);
                startActivity(intent);
            }
        });
        binding.cdSedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctg="Sedan";
                Intent intent= new Intent(getActivity(), ActivityCarCtg.class);
                intent.putExtra("Searched", true);
                intent.putExtra("ctg",ctg);
                startActivity(intent);
            }
        });
        binding.cdSUV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctg="SUV";
                Intent intent= new Intent(getActivity(), ActivityCarCtg.class);
                intent.putExtra("Searched", true);
                intent.putExtra("ctg",ctg);
                startActivity(intent);
            }
        });
        binding.cdVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctg="Van";
                Intent intent= new Intent(getActivity(), ActivityCarCtg.class);
                intent.putExtra("Searched", true);
                intent.putExtra("ctg",ctg);
                startActivity(intent);
            }
        });

        setCarAd();
        setFeaturedCar();

       // final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.tvAddCar){

        }
        if(item.getItemId()==R.id.tvUserProfile){

        }
        if(item.getItemId()==R.id.tvUserLogout){

           logoutDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void logoutDialog(){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);

        TextView tvHeader = (TextView) dialog.findViewById(R.id.tvHeader);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);

        tvHeader.setText("Do you want to Logout!");

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.UserLogout();
                startActivity(new Intent(getActivity(), ActivitySignIn.class));
                getActivity().finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
    private void showBottomSheetDialog(ModelFeaturedCar car) {


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

        CardView c2 = bottomSheetDialog.findViewById(R.id.card2);
        CardView c3 = bottomSheetDialog.findViewById(R.id.card3);
        CardView c4 = bottomSheetDialog.findViewById(R.id.card4);
        CardView c5 = bottomSheetDialog.findViewById(R.id.card5);

        ImageView imgCancel=bottomSheetDialog.findViewById(R.id.imgCancel);

        ImageView imgCarPhoto=bottomSheetDialog.findViewById(R.id.imgCarPhoto);
        ImageView imgCarPhoto2=bottomSheetDialog.findViewById(R.id.imgCarPhoto2);
        ImageView imgCarPhoto3=bottomSheetDialog.findViewById(R.id.imgCarPhoto3);
        ImageView imgCarPhoto4=bottomSheetDialog.findViewById(R.id.imgCarPhoto4);
        ImageView imgCarPhoto5=bottomSheetDialog.findViewById(R.id.imgCarPhoto5);

        c2.setVisibility(View.GONE);
        c3.setVisibility(View.GONE);
        c4.setVisibility(View.GONE);
        c5.setVisibility(View.GONE);

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
        if((!car.getImage2().isEmpty()) || car.getImage2()!=null){
            String imgURI= "https://gaarihazir.com/car-images/"+car.getImage2();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(imgCarPhoto2);
            c2.setVisibility(View.VISIBLE);
        }
        if((!car.getImage3().isEmpty()) || car.getImage3()!=null){
            String imgURI= "https://gaarihazir.com/car-images/"+car.getImage3();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(imgCarPhoto3);
            c3.setVisibility(View.VISIBLE);
        }
        if((!car.getImage4().isEmpty()) || car.getImage4()!=null){
            String imgURI= "https://gaarihazir.com/car-images/"+car.getImage4();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(imgCarPhoto4);
            c4.setVisibility(View.VISIBLE);

        }
        if((!car.getImage5().isEmpty()) || car.getImage5()!=null){
            String imgURI= "https://gaarihazir.com/car-images/"+car.getImage5();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(imgCarPhoto5);
            c5.setVisibility(View.VISIBLE);
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


    public boolean valiateContact (EditText fuel, EditText days) {
        if(fuel.getText().toString().trim().isEmpty()){
            fuel.setError("Please enter Fuel");
            return false;
        }
        if(days.getText().toString().trim().isEmpty()){
            days.setError("Please enter total number of days");
            return false;
        }
        return true;
    }
    private void  contactDialog(String WA, String Call,String Msg){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_contact);

        LinearLayout layMsg = (LinearLayout) dialog.findViewById(R.id.layMsg);
        LinearLayout layWA = (LinearLayout) dialog.findViewById(R.id.layWA);
        LinearLayout layCall = (LinearLayout) dialog.findViewById(R.id.layCall);
        ImageView imgClose = (ImageView) dialog.findViewById(R.id.imgClose);

        EditText fuel = dialog.findViewById(R.id.fuel);
        EditText days = dialog.findViewById(R.id.days);

        String sms_message;

        if(valiateContact(fuel, days)){
            Msg = Msg+"\nFuel: "+fuel.getText().toString().trim()+"\nTotal Days: "+days.getText().toString().trim();
        }

        sms_message  = "I need booking with following details \nFuel: "+fuel.getText().toString().trim()+"\nTotal Days: "+days.getText().toString().trim();

        String finalMsg = Msg;
        layMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"+ finalMsg));
                sendIntent.putExtra(Intent.EXTRA_TEXT, sms_message);
                startActivity(sendIntent);

            }
        });

        layWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "923058222281";
                Uri uri = Uri.parse("+923058222281");
                PackageManager pm= getActivity().getPackageManager();
//                Intent i = new Intent(Intent.ACTION_SENDTO, uri);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=" + number + "&text=" + sms_message;
                sendIntent.setData(Uri.parse(url));
                startActivity(sendIntent);
//                Intent i
//                        = new Intent(
//                        Intent.ACTION_SENDTO, uri);
//                i.putExtra(Intent.EXTRA_TEXT, message);
//                i.setType("text/plain");
//                i.setPackage("com.whatsapp");

//                i.putExtra(Intent.EXTRA_TEXT, message);
//                i.setPackage("com.whatsapp");
//                startActivity(Intent.createChooser(i, ""));
//                if (i.resolveActivity(getActivity().getPackageManager())
//                        == null) {
//                    Toast.makeText(getContext(),"Please install whatsapp first.", Toast.LENGTH_SHORT).show();
//                }
//                startActivity(i);

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
    public void setCarAd(){


        listModelCarAds.add( new ModelCarAd(R.drawable.car_sample,"Toyota Yaris"));
        listModelCarAds.add( new ModelCarAd(R.drawable.cultus,"Suzuki Cultus"));
        listModelCarAds.add( new ModelCarAd(R.drawable.civic,"Honda Civic"));
        listModelCarAds.add( new ModelCarAd(R.drawable.kia,"Kia Sportage"));
        listModelCarAds.add( new ModelCarAd(R.drawable.city,"Honda City"));
        adapterCarAd = new AdapterCarAd(getContext(),listModelCarAds);
        binding.rvAd.setAdapter(adapterCarAd);
        adapterCarAd.notifyDataSetChanged();


    }
    public void setFeaturedCar(){

        adapterFeaturedCar = new AdapterFeaturedCar();
//        Loading loading= new Loading(context);
//        loading.start();
        requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request= new JsonArrayRequest(BASE_API+"allcars", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {
//                    loading.end();
                    spinKitView.setVisibility(View.GONE);

                    for(int i =0; i<response.length();i++){

                        JSONObject JSONModelCar=response.getJSONObject(i);
                        JSONObject JSONDriver= JSONModelCar.getJSONObject("driver");

                        ModelFeaturedCar modelFeaturedCar= new ModelFeaturedCar(
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
                                JSONModelCar.getString("image2"),
                                JSONModelCar.getString("image3"),
                                JSONModelCar.getString("image4"),
                                JSONModelCar.getString("image5"),
                                JSONDriver.getString("phone_no")

                        );

                        //ModelFeaturedCar tempModelCar= gson.fromJson(String.valueOf(response.getJSONObject(i)),ModelFeaturedCar.class);
                        //listModelFeaturedCar.add(objectMapper.readValue((DataInput) response.getJSONObject(i), ModelFeaturedCar.class));
                        if(modelFeaturedCar!=null)
                            if(modelFeaturedCar.getCar_status().equals("Available")) listModelFeaturedCar.add(modelFeaturedCar);



                    }


                    adapterFeaturedCar = new AdapterFeaturedCar(getContext(),listModelFeaturedCar);

                    adapterFeaturedCar.setOnAdapterIntractionListner(new AdapterFeaturedCar.OnAdapterIntractionListner() {
                        @Override
                        public void OnItemClick(ModelFeaturedCar modelFeaturedCar) {

                            showBottomSheetDialog(modelFeaturedCar);
                            //Toast.makeText(getContext(), modelFeaturedCar.getName()+"", Toast.LENGTH_SHORT).show();
                        }
                    });

                    binding.rvFCar.setAdapter(adapterFeaturedCar);
                    adapterFeaturedCar.notifyDataSetChanged();
//                    loading.end();
                    spinKitView.setVisibility(View.GONE);
                }
                catch (Exception ex){
                   // Toast.makeText(context, ex+"", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                      //  Toast.makeText(context, error+"", Toast.LENGTH_SHORT).show();
//                        loading.end();
                        spinKitView.setVisibility(View.GONE);

                    }
                }
        )
        {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id",utils.getToken() );


                return params;


            };
        };

        requestQueue.add(request);


        //Toast.makeText(context, listModelCarAds+"", Toast.LENGTH_SHORT).show();

       /* listModelFeaturedCar.add( new ModelFeaturedCar("","Toyota Yaris"));
        listModelFeaturedCar.add( new ModelFeaturedCar("","Honda Civic"));
        listModelFeaturedCar.add( new ModelFeaturedCar("","Suzuki Cultus"));
        listModelFeaturedCar.add( new ModelFeaturedCar("","Corola Grande"));
        listModelFeaturedCar.add( new ModelFeaturedCar("","Mark X"));*/




    }



}