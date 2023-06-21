package com.enfotrix.hazir;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {




    Context context;
    SharedPreferences pref;
    String MY_PREFS_NAME= "HazirPref";
    String TOKEN="token", PHONE="Phone",CNIC="cnic",NAME="name";
    SharedPreferences.Editor editor;



    public Utils(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        editor= pref.edit();
    }

    public boolean IsLogedIn(){
        boolean isLogedIn=false;
        if(!getToken().equals("null"))isLogedIn=true;
        return isLogedIn;
    }
    public String getToken(){
        String token="null";
        if(pref.getString(TOKEN, null)!=null)
            token= pref.getString(TOKEN, null);
        return token;
    }


    public boolean UserLogin(String token){
        editor.putString(TOKEN, token);
        editor.apply();
        return true;
    }

    public boolean UserLogout(){
        editor.putString(TOKEN, "null");
        editor.apply();
        return true;
    }


    public void setPhoneNumber(String phoneNumber){
        editor.putString(PHONE, phoneNumber);
        editor.apply();
    }

    public String getPhoneNumber(){
        String PhoneNumber="";
        if(pref.getString(PHONE, null)!=null)
            PhoneNumber= pref.getString(PHONE, null);

        return PhoneNumber;
    }


    public void setCNICNumber(String CNICNumber){
        editor.putString(CNIC, CNICNumber);
        editor.apply();
    }

    public String getCNICNumber(){
        String CNICNumber="";
        if(pref.getString(CNIC, null)!=null)
            CNICNumber= pref.getString(CNIC, null);

        return CNICNumber;
    }



    public void setName(String Name){
        editor.putString(NAME, Name);
        editor.apply();
    }

    public String getName(){
        String Name="";
        if(pref.getString(NAME, null)!=null)
            Name= pref.getString(NAME, null);

        return Name;
    }

}
