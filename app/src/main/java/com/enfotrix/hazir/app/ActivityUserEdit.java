package com.enfotrix.hazir.app;

import static com.enfotrix.hazir.app.ActivityAddCar.PICK_IMAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.enfotrix.hazir.Loading;
import com.enfotrix.hazir.MainActivity;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.databinding.ActivityAddCarBinding;
import com.enfotrix.hazir.databinding.ActivityUserEditBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ActivityUserEdit extends AppCompatActivity {

    Utils utils;
    Context context;
    ActivityUserEditBinding binding;
    RequestQueue requestQueue;

    String encodedImageCnicFront,encodedImageCnicBack,encodedImageLicFront,encodedImageLicBack,encodedImageProfile;

    int imageSelect=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_edit);
        binding = ActivityUserEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context= ActivityUserEdit.this;
        utils= new Utils(context);


        getDriverDetails();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ActivityUserEdit.this, utils.getToken()+"", Toast.LENGTH_SHORT).show();
                updateUser(utils.getToken());
            }
        });
        binding.btnUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageSelect=1;
                getImageToGallery();
            }
        });
        binding.btnCnicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageSelect=2;
                getImageToGallery();
            }
        });
        binding.btnCnicBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageSelect=3;
                getImageToGallery();
            }
        });
        binding.btnLicFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageSelect=4;
                getImageToGallery();
            }
        });
        binding.btnLicBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageSelect=5;
                getImageToGallery();
            }
        });





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
                            Glide.with(context).load( imgURI).into(binding.imgUserProfile);

                            binding.etName.setText(obj.getString("name"));
                            binding.etCNIC.setText(obj.getString("cnic"));
                            binding.etPhone.setText(obj.getString("phone_no"));
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




    public void getImageToGallery(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});
        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        else if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            //Toast.makeText(context, uri.getPath()+"", Toast.LENGTH_SHORT).show();



            try {
                final InputStream imageStream;
                imageStream = getContentResolver().openInputStream(uri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);



                if(selectedImage!=null){
                    if(imageSelect==1){
                        Glide.with(context).load(new File(uri.getPath())) // Uri of the picture
                                .into(binding.imgUserProfile);
                        Glide.with(context).load(selectedImage) // Uri of the picture
                                .into(binding.imgUserProfile);
                        //String encodedImage = encodeImage(uri.getPath());
                        encodedImageProfile = encodeImage(selectedImage);
                    }
                    else if(imageSelect==2) {
                        encodedImageCnicFront = encodeImage(selectedImage);
                        binding.imgCnicFront.setImageResource(R.drawable.tick_mark);
                    }
                    else if(imageSelect==3) {
                        encodedImageCnicBack = encodeImage(selectedImage);
                        binding.imgCnicBack.setImageResource(R.drawable.tick_mark);

                    }
                    else if(imageSelect==4) {
                        encodedImageLicFront = encodeImage(selectedImage);
                        binding.imgLicenseFront.setImageResource(R.drawable.tick_mark);

                    }
                    else if(imageSelect==5) {
                        encodedImageLicBack = encodeImage(selectedImage);
                        binding.imgLicenseBack.setImageResource(R.drawable.tick_mark);

                    }
                }




            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }
    private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }

















    public void updateUser(String id){


        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(this);

        StringRequest myReq = new StringRequest(Request.Method.POST,
                "https://gaarihazir.com/api/driverupdate",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject obj = new JSONObject(response);

                            if(obj.getBoolean("message")==true){

                                JSONObject driver=obj.getJSONObject("data");

                                utils.setName(driver.getString("name")+"");
                                utils.setCNICNumber(driver.getString("cnic")+"");
                                utils.setPhoneNumber(driver.getString("phone_no")+"");


                                loading.end();
                                Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, MainActivity.class));
                                finish();
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
                })
        {

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driver_id", id);
                params.put("cnic_front", encodedImageCnicFront);
                params.put("cnic_back", encodedImageCnicBack);
                params.put("licence", encodedImageLicFront);
                params.put("licence_back", encodedImageLicBack);
                params.put("profile_photo", encodedImageProfile);
                params.put("name", binding.etName.getText().toString());
                params.put("cnic", binding.etCNIC.getText().toString());
                params.put("phone_no", binding.etPhone.getText().toString());
                return params;
            };
        };
        requestQueue.add(myReq);






    }




}