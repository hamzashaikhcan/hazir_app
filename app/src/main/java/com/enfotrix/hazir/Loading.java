package com.enfotrix.hazir;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class Loading {

    Context mContext;

    final Dialog dialog;
    final Dialog progressDialog;

    public Loading(Context context) {
        this.mContext=context;
        dialog = new Dialog(mContext);
        progressDialog = new Dialog(mContext);
    }
    public void start(){
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setContentView(R.layout.loading);
//        dialog.show();

        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        progressDialog.setContentView(R.layout.custom_dialog_progress);

        /* Custom setting to change TextView text,Color and Text Size according to your Preference*/

//        TextView progressTv = progressDialog.findViewById(R.id.progress_tv);
//        progressTv.setText("");
//        progressTv.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
//        progressTv.setTextSize(19F);
        if(progressDialog.getWindow() != null)
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        progressDialog.setCancelable(false);
        progressDialog.show();

    }
    public void end(){
        progressDialog.cancel();
//        dialog.dismiss();
    }

}
