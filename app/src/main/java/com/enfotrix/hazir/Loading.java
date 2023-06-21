package com.enfotrix.hazir;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

public class Loading {

    Context mContext;

    final Dialog dialog;

    public Loading(Context context) {
        this.mContext=context;
        dialog = new Dialog(mContext);
    }
    public void start(){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.loading);
        dialog.show();
    }
    public void end(){
        dialog.dismiss();
    }

}
