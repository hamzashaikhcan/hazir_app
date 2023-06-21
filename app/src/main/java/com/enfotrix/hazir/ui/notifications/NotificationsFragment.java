package com.enfotrix.hazir.ui.notifications;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Adapters.AdapterMyCar;
import com.enfotrix.hazir.Loading;
import com.enfotrix.hazir.MainActivity;
import com.enfotrix.hazir.Models.ModelCarAd;
import com.enfotrix.hazir.Models.ModelMyCar;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.app.ActivityAddCar;
import com.enfotrix.hazir.app.ActivitySignIn;
import com.enfotrix.hazir.app.ActivityUserEdit;
import com.enfotrix.hazir.databinding.FragmentNotificationsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {



    String WA,  Call, Msg;
    Utils utils;
    Context context;
    private RequestQueue requestQueue;
    int countAllCar, countAvailCar;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        countAllCar=0;
        countAvailCar=0;




        context= getContext();
        utils= new Utils(context);


        setUserData();
        //getMyCars();
        WA="0340324";
        Call="037234242";
        Msg="98238954";

        getDriverDetails();

        binding.cdLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog();
            }
        });
        binding.cvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(context, ActivityUserEdit.class).putExtra("token", utils.getToken()));


            }
        });
        binding.layLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog();
            }
        });

        binding.cvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+Call));
                startActivity(intent);
            }
        });

        binding.cvMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"+Msg));
                startActivity(sendIntent);
            }
        });

        binding.cvWA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:" + WA);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
                startActivity(Intent.createChooser(i, ""));
            }
        });



        return root;
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
        dialog.show();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.UserLogout();
                dialog.dismiss();
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
    public void setUserData(){

        binding.tvUserName.setText(utils.getName());
        binding.tvPhoneNum.setText(utils.getPhoneNumber());
        binding.tvCNIC.setText(utils.getCNICNumber());



    }


    public void getDriverDetails(){





        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);
        StringRequest myReq = new StringRequest(Request.Method.GET,
                "https://gaarihazir.com/api/getuserdetails/"+utils.getToken(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);




                            String imgURI= "https://gaarihazir.com/driver-profile/"+obj.getString("profile_photo");
                            Glide.with(context).load( imgURI).into(binding.imgUserPhoto);

                            loading.end();

                            //Toast.makeText(context, obj.getString("message")+"", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            loading.end();

                            //throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.end();
                        //Toast.makeText(context, error+"", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(myReq);





    }




}