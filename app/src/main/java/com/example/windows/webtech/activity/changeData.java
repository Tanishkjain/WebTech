package com.example.windows.webtech.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Windows on 12/22/2017.
 */

public class changeData extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONObject mobiledata = new JSONObject();
        JSONObject j1=new JSONObject();
        JSONObject jdata= new JSONObject();
        try {
            mobiledata.put("name","vivo");
            mobiledata.put("description","hii");
            JSONArray js= new JSONArray();
            js.put(mobiledata);
            j1.put("brand",js);
            jdata.put("data",j1);

            System.out.println("=============jata========="+jdata);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
