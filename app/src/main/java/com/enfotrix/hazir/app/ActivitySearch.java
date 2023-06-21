package com.enfotrix.hazir.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.enfotrix.hazir.R;
import com.enfotrix.hazir.Utils;
import com.enfotrix.hazir.databinding.ActivityCarCtgBinding;
import com.enfotrix.hazir.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

public class ActivitySearch extends AppCompatActivity {

    private Context context;

    ActivitySearchBinding binding;
    Utils utils;

    ArrayAdapter<String>
            adapterCarMake,adapterCarModelYear,adapterCarAssembly,adapterCarTransmission,adapterCarState, adapterCarEngineCapacity, adapterCarColor,adapterYesNo,adapterCity,adapterMilage,
            adapterCarSeat,adapterCarType, adapterSuzukiCarModel,adapterHondaCarModel,adapterToyotaCarModel,adapterHyundaiCarModel,adapterKIACarModel,adapterMGCarModel,adapterChanganCarModel;


    List<String> listCarMake,listCarModelYear,listCarAssembly,listCarTransmission,listCarState, listCarEngineCapacity, listCarColor,listYesNo,listCity,listMilage,
            listCarSeat,listCarType, listSuzukiCarModel,listHondaCarModel,listToyotaCarModel,listHyundaiCarModel,listKIACarModel,listMGCarModel,listChanganCarModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        context= ActivitySearch.this;

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
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCarAttribute();

            }
        });

    }


    public void getCarAttribute(){

        Intent intent= new Intent(context, ActivityCarCtg.class);

        intent.putExtra("car_model", binding.spinerCarModel.getSelectedItem().toString());
        intent.putExtra("model_year", binding.spinerCarModelYear.getSelectedItem().toString());
        intent.putExtra("car_make", binding.spinerCarMake.getSelectedItem().toString());
        intent.putExtra("engine_capacity", binding.spinerCarEngineCapacity.getSelectedItem().toString());
        intent.putExtra("pickup_city", binding.spinerCityAvail.getSelectedItem().toString());
        intent.putExtra("driver_availability", binding.spinerDriverAvail.getSelectedItem().toString());
        intent.putExtra("car_tranmission", binding.spinerCarTransmission.getSelectedItem().toString());
        intent.putExtra("car_rent", binding.etCarRent.getText().toString());
        intent.putExtra("car_type", binding.spinerCarType.getSelectedItem().toString());
        intent.putExtra("Searched", true);

        startActivity(intent);

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
        adapterCarTransmission = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarTransmission);
        binding.spinerCarTransmission.setAdapter(adapterCarTransmission);
        adapterCarEngineCapacity = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarEngineCapacity);
        binding.spinerCarEngineCapacity.setAdapter(adapterCarEngineCapacity);
        adapterCity = new ArrayAdapter<String>(this, R.layout.item_spiner, listCity);
        binding.spinerCityAvail.setAdapter(adapterCity);
        adapterCarType = new ArrayAdapter<String>(this, R.layout.item_spiner, listCarType);
        binding.spinerCarType.setAdapter(adapterCarType);
        adapterYesNo = new ArrayAdapter<String>(this, R.layout.item_spiner, listYesNo);
        binding.spinerDriverAvail.setAdapter(adapterYesNo);


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



}