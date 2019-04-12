package com.rootsoftit.pensiontracker.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtility {
    private static ProgressDialog progressDialog;

    public static void show(Context context) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }


    public static void setMessage(String message) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.setMessage(message);
            }
        } catch (IllegalArgumentException e) {
        }
    }

    public static void dismiss() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();

        }
    }

}
