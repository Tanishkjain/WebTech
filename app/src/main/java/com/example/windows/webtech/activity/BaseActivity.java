package com.example.windows.webtech.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Windows on 12/21/2017.
 */

public class BaseActivity extends AppCompatActivity {
    ProgressDialog progress;

    public void showProgress() {
        try {
            progress = new ProgressDialog(BaseActivity.this);
            progress.setMessage("Loading...");
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {
        if (progress != null) {
            try {
                progress.dismiss();
            } catch (Exception e) {

            }
        }
    }
}
