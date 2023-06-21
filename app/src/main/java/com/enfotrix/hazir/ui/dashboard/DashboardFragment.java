package com.enfotrix.hazir.ui.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Adapters.AdapterMyCar;
import com.enfotrix.hazir.Loading;
import com.enfotrix.hazir.Models.ModelMyCar;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.app.ActivityAddCar;
import com.enfotrix.hazir.databinding.FragmentDashboardBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private Context context;
    private List<ModelMyCar> listModelMyCar;
    private AdapterMyCar adapterMyCar;
    private RequestQueue requestQueue;

    private FragmentDashboardBinding binding;
    private Utils utils;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context= getContext();
        utils= new Utils(context);
        listModelMyCar = new ArrayList<>();
        binding.rvMyCar.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        carList();

        binding.btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),ActivityAddCar.class);
                intent.putExtra("edit", false);
                startActivity(intent);

            }
        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    private void showBottomSheetDialog(ModelMyCar car ) {

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



        btnBooking.setVisibility(View.INVISIBLE);


        /*btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactDialog(car.getPhone_no(),car.getPhone_no(),car.getPhone_no());
            }
        });
*/
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




    private void deleteDialog(int carID,int position){

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete);

        dialog.show();

        TextView tvHeader = (TextView) dialog.findViewById(R.id.tvHeader);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCar(carID ,dialog,position);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
    private void deleteCar(int carID ,Dialog dialog,int position){

        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);

        String url="https://gaarihazir.com/api/deletecar/"+carID;

        StringRequest myReq= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                        loading.end();
                        JSONObject obj = new JSONObject(response);
                        dialog.dismiss();
                        adapterMyCar.notifyItemRemoved(position);
                    adapterMyCar.notifyDataSetChanged();
//                        JSONObject car= obj.getJSONObject("car");
//                        boolean update= obj.getBoolean("message");
//                        String carStatus=car.getString("car_status");
//                        if(update) {
//                            tvStatus.setText(carStatus);
//                            Toast.makeText(context, "Status Updated to "+carStatus, Toast.LENGTH_SHORT).show();
//                        }







                } catch (JSONException e) {
                    loading.end();

                    throw new RuntimeException(e);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.end();

                        //Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(myReq);

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
    public void carList(){




        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest myReq = new JsonArrayRequest("https://gaarihazir.com/api/drivercars/"+utils.getToken(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {



                            for(int i =0; i<response.length();i++){

                                JSONObject JSONModelCar=response.getJSONObject(i);

                                ModelMyCar modelMyCar= new ModelMyCar(
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
                                        JSONModelCar.getString("image")
                                );

                                if(modelMyCar!=null) listModelMyCar.add(modelMyCar);




                            }

                            loading.end();

                            adapterMyCar = new AdapterMyCar(getContext(),listModelMyCar);

                            adapterMyCar.setOnAdapterIntractionListner(new AdapterMyCar.OnAdapterIntractionListner() {
                                @Override
                                public void OnItemClick(ModelMyCar adapterMyCar) {

                                    showBottomSheetDialog(adapterMyCar);
                                    //Toast.makeText(getContext(), modelFeaturedCar.getName()+"", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void OnEditImgClick(ModelMyCar modelMyCar) {

                                    if(modelMyCar!=null){

                                        Intent intent = new Intent(context,ActivityAddCar.class);
                                        intent.putExtra("modelCar", modelMyCar);
                                        intent.putExtra("edit", true);

                                        startActivity(intent);

                                    }

                                }

                                @Override
                                public void OnDeleteCar(ModelMyCar modelMyCar, int position) {
                                    deleteDialog(modelMyCar.getId(),position);
                                }

                                @Override
                                public void OnStatusChange(ModelMyCar modelMyCar, TextView tvStatus, int position) {
                                    updateStatus(modelMyCar,tvStatus,position);
                                }

                               /* @Override
                                public void OnDeleteCar(ModelMyCar modelMyCar) {
                                    deleteDialog(modelMyCar.getId());
                                }

                                @Override
                                public void OnStatusChange(ModelMyCar modelMyCar, TextView tvStatus) {
                                    updateStatus(modelMyCar,tvStatus);

                                }*/

                            });

                            binding.rvMyCar.setAdapter(adapterMyCar);
                            adapterMyCar.notifyDataSetChanged();
                            loading.end();

                        }
                        catch (Exception ex){
                            loading.end();

                           // Toast.makeText(context, ex+"", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.end();

                       // Toast.makeText(context, error+"", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(myReq);







/*
        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);


        JsonArrayRequest request= new JsonArrayRequest("https://carapi.cricdigital.com/api/drivercars", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {



                    for(int i =0; i<response.length();i++){

                        JSONObject JSONModelCar=response.getJSONObject(i);







                        ModelMyCar modelMyCar= new ModelMyCar(
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
                                JSONModelCar.getString("car_tranmission")

                        );
                        if(modelMyCar!=null)
                            listModelMyCar.add(modelMyCar);




                    }

                    loading.end();

                    adapterMyCar = new AdapterMyCar(getContext(),listModelMyCar);

                    adapterMyCar.setOnAdapterIntractionListner(new AdapterMyCar.OnAdapterIntractionListner() {
                        @Override
                        public void OnItemClick(ModelMyCar adapterMyCar) {

                            showBottomSheetDialog(adapterMyCar);
                            //Toast.makeText(getContext(), modelFeaturedCar.getName()+"", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void OnImgClick(ModelMyCar modelMyCar) {

                        }
                    });

                    binding.rvMyCar.setAdapter(adapterMyCar);
                    adapterMyCar.notifyDataSetChanged();
                    loading.end();

                }
                catch (Exception ex){
                    Toast.makeText(context, ex+"", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, error+"", Toast.LENGTH_SHORT).show();
                        loading.end();
                    }
                }
        )
        {
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id",utils.getToken() );//renter/user id

                return params;
            };
        };

        requestQueue.add(request);*/
















    }


    public void updateStatus(ModelMyCar modelMyCar,TextView tvStatus,int position){





        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);

        String url="https://gaarihazir.com/api/carstatus/"+modelMyCar.getId()+"/"+modelMyCar.getCar_status();

        StringRequest myReq= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    loading.end();
                    JSONObject obj = new JSONObject(response);
                    JSONObject car= obj.getJSONObject("car");
                    boolean update= obj.getBoolean("message");
                    String carStatus=car.getString("car_status");
                    if(update) {
                        tvStatus.setText(carStatus);
                        Toast.makeText(context, "Status Updated to "+carStatus, Toast.LENGTH_SHORT).show();
                        adapterMyCar.notifyItemChanged(position);
                        adapterMyCar.notifyDataSetChanged();

                    }






                } catch (JSONException e) {
                    loading.end();

                    throw new RuntimeException(e);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.end();

                        Toast.makeText(context, error.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(myReq);


    }


}