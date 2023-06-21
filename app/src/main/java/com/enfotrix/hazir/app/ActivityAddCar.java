package com.enfotrix.hazir.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
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
import com.enfotrix.hazir.Models.ModelMyCar;
import com.enfotrix.hazir.NegativeRentSeekBar;
import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.databinding.ActivityAddCarBinding;
import com.enfotrix.hazir.databinding.ActivitySignInBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAddCar extends AppCompatActivity {


    private Context context;
    ActivityAddCarBinding binding;
    int price;
    RequestQueue requestQueue;
    ModelMyCar modelMyCar;
    public static final int PICK_IMAGE = 1;
    boolean edit=false;
    String encodedImage;
    Utils utils;


    private static final int REQUEST_CODE_SELECT_PHOTO = 1;


    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private static final int REQUEST_CODE_EXTERNAL_STORAGE = 1;


    ArrayAdapter<String>
            adapterCarMake,adapterCarModelYear,adapterCarAssembly,adapterCarTransmission,adapterCarState, adapterCarEngineCapacity, adapterCarColor,adapterYesNo,adapterCity,adapterMilage,
            adapterCarSeat,adapterCarType, adapterSuzukiCarModel,adapterHondaCarModel,adapterToyotaCarModel,adapterHyundaiCarModel,adapterKIACarModel,adapterMGCarModel,adapterChanganCarModel;


    List<String> listCarMake,listCarModelYear,listCarAssembly,listCarTransmission,listCarState, listCarEngineCapacity, listCarColor,listYesNo,listCity,listMilage,
            listCarSeat,listCarType, listSuzukiCarModel,listHondaCarModel,listToyotaCarModel,listHyundaiCarModel,listKIACarModel,listMGCarModel,listChanganCarModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        binding = ActivityAddCarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context= ActivityAddCar.this;
        utils= new Utils(context);
        price=500;
        binding.tvRent.setText(price+" PKR");


        listSuzukiCarModel= new ArrayList<String>();//.add("Suzuki Ravi");
        listHondaCarModel= new ArrayList<String>();//.add("Honda CRV");
        listToyotaCarModel= new ArrayList<String>();//.add("Toyota Corolla");
        listHyundaiCarModel= new ArrayList<String>();//.add("hyundai sonata");
        listKIACarModel= new ArrayList<String>();//.add("kia stonic");
        listMGCarModel= new ArrayList<String>();//.add("MG 3");
        listChanganCarModel= new ArrayList<String>();//.add("Changan Alsvin");
        listCarMake= new ArrayList<String>();
        listCarModelYear= new ArrayList<String>();
        listCarAssembly= new ArrayList<String>();
        listCarTransmission= new ArrayList<String>();
        listCarState= new ArrayList<String>();
        listCarColor= new ArrayList<String>();
        listYesNo= new ArrayList<String>();
        listCity= new ArrayList<String>();
        listCarType= new ArrayList<String>();
        listCarSeat= new ArrayList<String>();
        listCarEngineCapacity= new ArrayList<String>();
        listMilage= new ArrayList<String>();


        init();



        edit =getIntent().getBooleanExtra("edit",false);


        if(edit==true)
        {
            modelMyCar = (ModelMyCar) getIntent().getSerializableExtra("modelCar");
            if(modelMyCar!=null)
                InitEdit(modelMyCar);
        }

        binding.btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate()){
                    if(edit==true)
                    {
                        if(modelMyCar!=null)
                            editCar(String.valueOf(modelMyCar.getId()));
                    }
                    else addCar();
                }
                else Toast.makeText(ActivityAddCar.this, "Please fill complete Form!", Toast.LENGTH_SHORT).show();


            }
        });

        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //performExternalStorageOperation();

                //checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);


                getImageToGallery();

            }
        });




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

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(ActivityAddCar.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show();
            //getImageToGallery();
        }
    }

    ////////////////////////////////////////////



    private void performExternalStorageOperation() {
        if (isExternalStoragePermissionGranted()) {
            // External storage permission is already granted
            // Perform your desired operations here
            getImageToGallery();

        } else {
            // External storage permission is not granted
            // Request the permission from the user
            requestExternalStoragePermission();
        }
    }

    private void requestExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL_STORAGE);
        }
    }

    private boolean isExternalStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return true; // Permissions are automatically granted on devices below API level 23
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == REQUEST_CODE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // External storage permission granted
                // Perform your desired operations here

                getImageToGallery();
            } else {
                // External storage permission denied
                // Handle accordingly (e.g., show an error message, disable certain functionality)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);




        /*super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                getImageToGallery();
            }
            else {
                Toast.makeText(context, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }*/
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean validate(){
        boolean validate=true;
        if(binding.imgCar.getDrawable()==null) validate=false;
        else if(binding.etCarNumber.getText().toString().isEmpty()) validate=false;
        else if(binding.etCarRent.getText().toString().isEmpty()) validate=false;
        else if(binding.etDisc.getText().toString().isEmpty()) validate=false;
        return validate;

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

            Glide.with(context).load(new File(uri.getPath())) // Uri of the picture
                    .into(binding.imgCar);

            try {
                final InputStream imageStream;
                imageStream = getContentResolver().openInputStream(uri);

                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Glide.with(context).load(selectedImage) // Uri of the picture
                        .into(binding.imgCar);
                //String encodedImage = encodeImage(uri.getPath());
                encodedImage = encodeImage(selectedImage);


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
    public void InitEdit(ModelMyCar modelMyCar){


        binding.tvHeader.setText("Edit Your Car!");
        binding.btnAddUpdate.setText("Update");
        binding.spinerCarMake.setSelection(adapterCarMake.getPosition(modelMyCar.getCar_make()));
        binding.spinerCarModelYear.setSelection(adapterCarModelYear.getPosition(modelMyCar.getModel_year()));
        binding.spinerCarAssembly.setSelection(adapterCarAssembly.getPosition(modelMyCar.getCar_assembly()));
        binding.spinerCarTransmission.setSelection(adapterCarTransmission.getPosition(modelMyCar.getCar_tranmission()));
        binding.spinerCarEngineCapacity.setSelection(adapterCarEngineCapacity.getPosition(modelMyCar.getEngine_capacity()));
        binding.spinerBodyColor.setSelection(adapterCarColor.getPosition(modelMyCar.getBody_color()));
        binding.spinerInCity.setSelection(adapterCity.getPosition(modelMyCar.getBetween_cities()));
        binding.spinerCityAvail.setSelection(adapterCity.getPosition(modelMyCar.getPickup_city()));
        binding.spinerRegCity.setSelection(adapterCity.getPosition(modelMyCar.getRegistration_city()));
        binding.spinerDriverAvail.setSelection(adapterYesNo.getPosition(modelMyCar.getDriver_availability()));
        binding.spinerMilage.setSelection(adapterMilage.getPosition(modelMyCar.getCar_mileage()));
        binding.spinerInsured.setSelection(adapterYesNo.getPosition(modelMyCar.getInsured()));
        binding.spinerCarSeat.setSelection(adapterCarSeat.getPosition(modelMyCar.getCar_seats()));
        binding.spinerCarType.setSelection(adapterCarType.getPosition(modelMyCar.getCar_type()));
        binding.spinerCarStateAvail.setSelection(adapterCarState.getPosition(modelMyCar.getState()));
        binding.etCarNumber.setText(modelMyCar.getCar_no());
        binding.etCarRent.setText(modelMyCar.getCar_rent());
        binding.etDisc.setText(modelMyCar.getDescription());
        if((!modelMyCar.getImage().isEmpty()) || modelMyCar.getImage()!=null){

            String imgURI= "https://gaarihazir.com/car-images/"+modelMyCar.getImage();
            Glide.with(context).load( imgURI) // Uri of the picture
                    .into(binding.imgCar);

        }
        if(modelMyCar.getCar_status().equals("Available")) binding.switchIsAvailable.setChecked(true);
        binding.spinerCarMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                if(position==0){
                    binding.spinerCarModel.setAdapter(adapterSuzukiCarModel);
                    binding.spinerCarModel.setSelection(adapterSuzukiCarModel.getPosition(modelMyCar.getCar_model()));
                }
                else if(position==1){
                    binding.spinerCarModel.setAdapter(adapterHondaCarModel);
                    binding.spinerCarModel.setSelection(adapterHondaCarModel.getPosition(modelMyCar.getCar_model()));
                }
                else if(position==2){
                    binding.spinerCarModel.setAdapter(adapterToyotaCarModel);
                    binding.spinerCarModel.setSelection(adapterToyotaCarModel.getPosition(modelMyCar.getCar_model()));
                }
                else if(position==3){
                    binding.spinerCarModel.setAdapter(adapterHyundaiCarModel);
                    binding.spinerCarModel.setSelection(adapterHyundaiCarModel.getPosition(modelMyCar.getCar_model()));
                }
                else if(position==4){
                    binding.spinerCarModel.setAdapter(adapterKIACarModel);
                    binding.spinerCarModel.setSelection(adapterKIACarModel.getPosition(modelMyCar.getCar_model()));
                }
                else if(position==5){
                    binding.spinerCarModel.setAdapter(adapterMGCarModel);
                    binding.spinerCarModel.setSelection(adapterMGCarModel.getPosition(modelMyCar.getCar_model()));
                }
                else if(position==6){
                    binding.spinerCarModel.setAdapter(adapterChanganCarModel);
                    binding.spinerCarModel.setSelection(adapterChanganCarModel.getPosition(modelMyCar.getCar_model()));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });







    }
    public void init(){








        listMilage.add("8 KM/L");
        listMilage.add("9 KM/L");
        listMilage.add("10 KM/L");
        listMilage.add("11 KM/L");
        listMilage.add("12 KM/L");
        listMilage.add("13 KM/L");
        listMilage.add("14 KM/L");
        listMilage.add("15 KM/L");
        listMilage.add("16 KM/L");
        listMilage.add("17 KM/L");
        listMilage.add("18 KM/L");
        listMilage.add("19 KM/L");
        listMilage.add("20 KM/L");
        listMilage.add("21 KM/L");
        listMilage.add("22 KM/L");
        listMilage.add("23 KM/L");
        listMilage.add("24 KM/L");
        listMilage.add("25 KM/L");
        listMilage.add("25+ KM/L");




        listCarEngineCapacity.add("1.0L");
        listCarEngineCapacity.add("1.3L");
        listCarEngineCapacity.add("1.5L");
        listCarEngineCapacity.add("1.8L");
        listCarEngineCapacity.add("2.0L");
        listCarEngineCapacity.add("2.0+L");

        listCarSeat.add("4");
        listCarSeat.add("5");
        listCarSeat.add("6");
        listCarSeat.add("7");
        listCarSeat.add("8");



        listCarType.add("SUV");
        listCarType.add("Sedan");
        listCarType.add("Hatchback");
        listCarType.add("Pickup Truck");
        listCarType.add("Van");
        listCarType.add("Bus");


        listCity.add("Karachi");
        listCity.add("Lahore");
        listCity.add("Faisalabad");
        listCity.add("Rawalpindi");
        listCity.add("Gujranwala");
        listCity.add("Peshawar");
        listCity.add("Multan");
        listCity.add("Islamabad");
        listCity.add("Quetta");
        listCity.add("Bahawalpur");
        listCity.add("Sargodha");
        listCity.add("Sialkot");
        listCity.add("Sukkur");
        listCity.add("Larkana");
        listCity.add("Chiniot");
        listCity.add("Shekhupura");
        listCity.add("Jhang");
        listCity.add("DG Khan");
        listCity.add("Gujrat");
        listCity.add("Rahimyar Khan");
        listCity.add("Kasur");
        listCity.add("Mardan");
        listCity.add("Sahiwal");
        listCity.add("Mirpur Khas");
        listCity.add("Okara");
        listCity.add("Jacobabad");
        listCity.add("Saddiqabad");
        listCity.add("Kohat");
        listCity.add("Gojra");
        listCity.add("Mandi Bahauddin");
        listCity.add("Abbottabad");
        listCity.add("Bahawalnagar");
        listCity.add("Pakpattan");
        listCity.add("Tando Allahyar");
        listCity.add("Vihari");
        listCity.add("Jaranwala");
        listCity.add("Mirpur");
        listCity.add("Muzaffarabad");
        listCity.add("Mianwali");
        listCity.add("Jalalpur Jattan");
        listCity.add("Bhakkar");
        listCity.add("Bhalwal");
        listCity.add("Pattoki");



        listYesNo.add("Yes");
        listYesNo.add("No");


        listCarColor.add("White");
        listCarColor.add("Black");
        listCarColor.add("Gray");
        listCarColor.add("Blur");
        listCarColor.add("Silver");
        listCarColor.add("Red");
        listCarColor.add("Brown");
        listCarColor.add("Other");



        listCarState.add("Punjab");
        listCarState.add("Sindh");
        listCarState.add("KPK");
        listCarState.add("Blochistan");
        listCarState.add("Fedral");


        listCarTransmission.add("Auto");
        listCarTransmission.add("Manual");


        listCarAssembly.add("Imported");
        listCarAssembly.add("Local");


        listCarModelYear.add("2000");
        listCarModelYear.add("2001");
        listCarModelYear.add("2002");
        listCarModelYear.add("2003");
        listCarModelYear.add("2004");
        listCarModelYear.add("2005");
        listCarModelYear.add("2006");
        listCarModelYear.add("2007");
        listCarModelYear.add("2008");
        listCarModelYear.add("2009");
        listCarModelYear.add("2010");
        listCarModelYear.add("2011");
        listCarModelYear.add("2012");
        listCarModelYear.add("2013");
        listCarModelYear.add("2014");
        listCarModelYear.add("2015");
        listCarModelYear.add("2016");
        listCarModelYear.add("2017");
        listCarModelYear.add("2018");
        listCarModelYear.add("2019");
        listCarModelYear.add("2020");
        listCarModelYear.add("2021");
        listCarModelYear.add("2022");
        listCarModelYear.add("2023");



        listCarMake.add("Suzuki");
        listSuzukiCarModel.add("Suzuki Alto");
        listSuzukiCarModel.add("Suzuki Wagon R");
        listSuzukiCarModel.add("Suzuki Cultus");
        listSuzukiCarModel.add("Suzuki Swift");
        listSuzukiCarModel.add("Suzuki Bolan");
        listSuzukiCarModel.add("Suzuki Ravi");


        listCarMake.add("Honda");
        listHondaCarModel.add("Honda City");
        listHondaCarModel.add("Honda Civic");
        listHondaCarModel.add("Honda HRV");
        listHondaCarModel.add("Honda BRV");
        listHondaCarModel.add("Honda Accord");
        listHondaCarModel.add("Honda CRV");


        listCarMake.add("Toyota");
        listToyotaCarModel.add("Toyota Auris");
        listToyotaCarModel.add("Toyota Yaris");
        listToyotaCarModel.add("Toyota Belta");
        listToyotaCarModel.add("Toyota Camry");
        listToyotaCarModel.add("Toyota Corolla");


        listCarMake.add("Hyundai");
        listHyundaiCarModel.add("Hyundai Elantra");
        listHyundaiCarModel.add("Hyundai Tucson");
        listHyundaiCarModel.add("hyundai sonata");

        listCarMake.add("KIA");
        listKIACarModel.add("KIA Sorento");
        listKIACarModel.add("Kia Picanto");
        listKIACarModel.add("KIA Grand Carnival");
        listKIACarModel.add("Kia Stinger");
        listKIACarModel.add("Kia Sportage");
        listKIACarModel.add("kia stonic");


        listCarMake.add("MG");
        listMGCarModel.add("MG 5");
        listMGCarModel.add("MG HS");
        listMGCarModel.add("MG ZS");
        listMGCarModel.add("MG 3");

        listCarMake.add("Changan");
        listChanganCarModel.add("Changan Alsvin");
        listChanganCarModel.add("Changan Karvaan");
        listChanganCarModel.add("Changan Oshan");
        listChanganCarModel.add("Changan Kaghan");




        adapterCarMake = new ArrayAdapter<String>(context, R.layout.item_spiner, listCarMake);
        binding.spinerCarMake.setAdapter(adapterCarMake);
        adapterCarModelYear = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarModelYear);
        binding.spinerCarModelYear.setAdapter(adapterCarModelYear);
        adapterCarAssembly = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarAssembly);
        binding.spinerCarAssembly.setAdapter(adapterCarAssembly);
        adapterCarTransmission = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarTransmission);
        binding.spinerCarTransmission.setAdapter(adapterCarTransmission);
        adapterCarState = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarState);
        binding.spinerCarStateAvail.setAdapter(adapterCarState);
        adapterCarEngineCapacity = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarEngineCapacity);
        binding.spinerCarEngineCapacity.setAdapter(adapterCarEngineCapacity);
        adapterCarColor = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarColor);
        binding.spinerBodyColor.setAdapter(adapterCarColor);
        adapterCity = new ArrayAdapter<String>(this, R.layout.item_spiner, listCity);
        binding.spinerCityAvail.setAdapter(adapterCity);
        binding.spinerRegCity.setAdapter(adapterCity);
        adapterMilage = new ArrayAdapter<String>(this, R.layout.item_spiner, listMilage);
        binding.spinerMilage.setAdapter(adapterMilage);
        adapterCarSeat = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarSeat);
        binding.spinerCarSeat.setAdapter(adapterCarSeat);
        adapterCarType = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarType);
        binding.spinerCarType.setAdapter(adapterCarType);

        adapterYesNo = new ArrayAdapter<String>(this, R.layout.item_spiner, listYesNo);
        binding.spinerInCity.setAdapter(adapterYesNo);
        binding.spinerDriverAvail.setAdapter(adapterYesNo);
        binding.spinerInsured.setAdapter(adapterYesNo);

        adapterSuzukiCarModel = new ArrayAdapter<String>(this, R.layout.item_spiner, listSuzukiCarModel);
        adapterHondaCarModel = new ArrayAdapter<String>(this, R.layout.item_spiner, listHondaCarModel);
        adapterToyotaCarModel = new ArrayAdapter<String>(this, R.layout.item_spiner, listToyotaCarModel);
        adapterHyundaiCarModel = new ArrayAdapter<String>(this, R.layout.item_spiner, listHyundaiCarModel);
        adapterKIACarModel = new ArrayAdapter<String>(this, R.layout.item_spiner, listKIACarModel);
        adapterMGCarModel = new ArrayAdapter<String>(this, R.layout.item_spiner, listMGCarModel);
        adapterChanganCarModel = new ArrayAdapter<String>(this,R.layout.item_spiner, listChanganCarModel);


        binding.spinerCarMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                if(position==0){
                    binding.spinerCarModel.setAdapter(adapterSuzukiCarModel);
                }
                else if(position==1){
                    binding.spinerCarModel.setAdapter(adapterHondaCarModel);
                }
                else if(position==2){
                    binding.spinerCarModel.setAdapter(adapterToyotaCarModel);
                }
                else if(position==3){
                    binding.spinerCarModel.setAdapter(adapterHyundaiCarModel);
                }
                else if(position==4){
                    binding.spinerCarModel.setAdapter(adapterKIACarModel);
                }
                else if(position==5){
                    binding.spinerCarModel.setAdapter(adapterMGCarModel);
                }
                else if(position==6){
                    binding.spinerCarModel.setAdapter(adapterChanganCarModel);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }


    public void addCar(){

        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);

        StringRequest myReq = new StringRequest(Request.Method.POST,
                "https://gaarihazir.com/api/storeCar",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            //Toast.makeText(context, "debug 1", Toast.LENGTH_SHORT).show();
                            JSONObject objRes = new JSONObject(response);

                            JSONObject objData= objRes.getJSONObject("data");



                            loading.end();
                            Toast.makeText(context, "Car Added Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context,MainActivity.class));
                            finish();


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


                String imgHeader="data:image/png;base64,";

                Map<String, String> params = new HashMap<String, String>();

                //params.put("images", imgHeader+encodedImage);
                //Toast.makeText(ActivityAddCar.this, encodedImage+ "", Toast.LENGTH_SHORT).show();


                params.put("car_model", binding.spinerCarModel.getSelectedItem().toString());
                params.put("model_year", binding.spinerCarModelYear.getSelectedItem().toString());
                params.put("car_make", binding.spinerCarMake.getSelectedItem().toString());
                params.put("car_assembly", binding.spinerCarAssembly.getSelectedItem().toString());
                params.put("engine_capacity", binding.spinerCarEngineCapacity.getSelectedItem().toString());
                params.put("body_color", binding.spinerBodyColor.getSelectedItem().toString());
                params.put("between_cities", binding.spinerInCity.getSelectedItem().toString());// InCity spiner -> available for out of city -> Yes/No
                params.put("registration_city", binding.spinerRegCity.getSelectedItem().toString());
                params.put("pickup_city", binding.spinerCityAvail.getSelectedItem().toString());
                params.put("car_no", binding.etCarNumber.getText().toString());
                params.put("driver_availability", binding.spinerDriverAvail.getSelectedItem().toString());
                params.put("car_mileage", binding.spinerMilage.getSelectedItem().toString());
                params.put("description", binding.etDisc.getText().toString());
                params.put("insured", binding.spinerInsured.getSelectedItem().toString());
                params.put("car_seats", binding.spinerCarSeat.getSelectedItem().toString());
                params.put("state", binding.spinerCarStateAvail.getSelectedItem().toString());
                params.put("car_tranmission", binding.spinerCarTransmission.getSelectedItem().toString());
                params.put("car_rent", binding.etCarRent.getText().toString());
                params.put("car_type", binding.spinerCarType.getSelectedItem().toString());
                params.put("image", encodeImage(((BitmapDrawable)binding.imgCar.getDrawable()).getBitmap()));

                String availability="Booked";
                if(binding.switchIsAvailable.isChecked())availability="Available";
                params.put("car_status", availability);
                params.put("driver_id", String.valueOf(utils.getToken()));

                return params;

            };
        };
        requestQueue.add(myReq);


    }
    public void editCar(String id){

        Loading loading= new Loading(context);
        loading.start();
        requestQueue = Volley.newRequestQueue(context);

        StringRequest myReq = new StringRequest(Request.Method.POST,
                "https://gaarihazir.com/api/carupdate/"+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            //Toast.makeText(context, "debug 1", Toast.LENGTH_SHORT).show();
                            JSONObject objRes = new JSONObject(response);

                            JSONObject objData= objRes.getJSONObject("data");

                            //Toast.makeText(context, objData+"", Toast.LENGTH_SHORT).show();


                            loading.end();
                            Toast.makeText(context, "Car Updated Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context,MainActivity.class));
                            finish();


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



               // Map<String, String> paramss = new HashMap<String, String>();



                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("car_model", binding.spinerCarModel.getSelectedItem().toString());
                params.put("model_year", binding.spinerCarModelYear.getSelectedItem().toString());
                params.put("car_make", binding.spinerCarMake.getSelectedItem().toString());
                params.put("car_assembly", binding.spinerCarAssembly.getSelectedItem().toString());
                params.put("engine_capacity", binding.spinerCarEngineCapacity.getSelectedItem().toString());
                params.put("body_color", binding.spinerBodyColor.getSelectedItem().toString());
                params.put("between_cities", binding.spinerInCity.getSelectedItem().toString());// InCity spiner -> available for out of city -> Yes/No
                params.put("registration_city", binding.spinerRegCity.getSelectedItem().toString());
                params.put("pickup_city", binding.spinerCityAvail.getSelectedItem().toString());
                params.put("car_no", binding.etCarNumber.getText().toString());
                params.put("driver_availability", binding.spinerDriverAvail.getSelectedItem().toString());
                params.put("car_mileage", binding.spinerMilage.getSelectedItem().toString());
                params.put("description", binding.etDisc.getText().toString());
                params.put("insured", binding.spinerInsured.getSelectedItem().toString());
                params.put("car_seats", binding.spinerCarSeat.getSelectedItem().toString());
                params.put("state", binding.spinerCarStateAvail.getSelectedItem().toString());
                params.put("car_tranmission", binding.spinerCarTransmission.getSelectedItem().toString());
                params.put("car_rent", binding.etCarRent.getText().toString());
                params.put("car_type", binding.spinerCarType.getSelectedItem().toString());
                params.put("image", encodeImage(((BitmapDrawable)binding.imgCar.getDrawable()).getBitmap()));
                String availability="Booked";
                if(binding.switchIsAvailable.isChecked())availability="Available";
                params.put("car_status", availability);
                params.put("driver_id", String.valueOf(utils.getToken()));

                return params;
            };
        };
        requestQueue.add(myReq);

        //Toast.makeText(ActivityAddCar.this, ""+encodeImage(((BitmapDrawable)binding.imgCar.getDrawable()).getBitmap()), Toast.LENGTH_SHORT).show();

    }


}