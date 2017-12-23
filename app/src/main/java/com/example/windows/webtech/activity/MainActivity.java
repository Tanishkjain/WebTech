package com.example.windows.webtech.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.windows.webtech.App;
import com.example.windows.webtech.R;
import com.example.windows.webtech.adapter.GetBrandAdapter;
import com.example.windows.webtech.adapter.GetSearchAdapter;
import com.example.windows.webtech.helper.DatabaseHandler;
import com.example.windows.webtech.model.BrandData;
import com.example.windows.webtech.model.GetBrandData;
import com.example.windows.webtech.model.Serachdata;
import com.example.windows.webtech.util.WebServices;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends BaseActivity {
    FloatingActionButton addbrand;
    ArrayList<GetBrandData.BrandList> gb;
    RecyclerView rc;
    GetBrandAdapter getBrandAdapter;
    GetSearchAdapter getSearchAdapter;
    LinearLayoutManager layoutManager;
    LinearLayoutManager searchManager;
    Dialog Addtagdialog;
    ArrayList<String> name;
    ArrayList<String> description;
    EditText brandId, timedata;
    ImageView searchimage;
    RecyclerView searchdata_rc;
    ArrayList<Serachdata.Brand> searchdata;
    Button searchdata_button;
    ImageView timestamp;
    DatabaseHandler db;
    ArrayList<BrandData> brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gb = new ArrayList<>();
        searchdata = new ArrayList<>();
        db = new DatabaseHandler(this);
        rc = findViewById(R.id.recycledata);
        getProductDetails();
        showProgress();

        layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchManager = new LinearLayoutManager(MainActivity.this);
        searchManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchimage = findViewById(R.id.searchimage);
        timestamp = findViewById(R.id.timestamp);
        name = new ArrayList<>();
        description = new ArrayList<>();
        addbrand = (FloatingActionButton) findViewById(R.id.fab);
        addbrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBrand();
            }
        });

        brand = db.getAllDetails();

        getBrandAdapter = new GetBrandAdapter(gb, brand, MainActivity.this);
        readData();
        rc.setLayoutManager(layoutManager);
        rc.setAdapter(getBrandAdapter);
        searchimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDesign("search");
            }
        });
        timestamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDesign("timestamp");
            }
        });
    }


    //===============================getAllProduct==============================================
    public void getProductDetails() {
        String url = WebServices.getProductList;
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("======response=========" + response);
                GetBrandData brandData = new Gson().fromJson(response, GetBrandData.class);
                gb.addAll(brandData.getBrandList());
                addData();
                getBrandAdapter.notifyDataSetChanged();
                hideProgress();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
            }
        });
        App.getInstance().addToRequestQueue(sr);
    }

    //==============================AddBrandDetais============================================
    public void dialogBrand() {
        Addtagdialog = new Dialog(MainActivity.this);
        Addtagdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Addtagdialog.setContentView(R.layout.addbrand);
        Addtagdialog.setCancelable(false);
        Addtagdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Addtagdialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Addtagdialog.getWindow().getAttributes());
        Addtagdialog.getWindow().setAttributes(lp);
        Addtagdialog.show();
        Button cancle = Addtagdialog.findViewById(R.id.cancle);
        Button addMore = Addtagdialog.findViewById(R.id.addmore);
        Button save = Addtagdialog.findViewById(R.id.save);
        final TextView brandName = Addtagdialog.findViewById(R.id.brandName);
        final TextView descriptionEd = Addtagdialog.findViewById(R.id.descreiption);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addtagdialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.size() > 0 || description.size() > 0) {  //call when more data will add
                    new AddBrand().execute();
                } else {
                    name.clear();
                    description.clear();
                    if(!brandName.getText().toString().equals("")&&!descriptionEd.getText().toString().equals("")) {
                        name.add(brandName.getText().toString());
                        description.add(descriptionEd.getText().toString());
                        new AddBrand().execute();
                    }
                        else {
                         Toast.makeText(MainActivity.this,"Please Enter Data",Toast.LENGTH_SHORT).show();
                }
                }
            }
        });

        addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.add(brandName.getText().toString());
                description.add(descriptionEd.getText().toString());
                brandName.setText("");
                descriptionEd.setText("");
                System.out.println("======name======" + name);
                System.out.println("======description===" + description);
            }
        });

    }

    public class AddBrand extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL(WebServices.addBrand);

                JSONObject postDataParams = new JSONObject();

                 JSONObject j1 = new JSONObject();
                 JSONArray jsPoints = new JSONArray();

                for (int i = 0; i < name.size(); i++) {
                    JSONObject js1 = null;

                    try {
                        js1 = new JSONObject();
                        js1.put("name", name.get(i));
                        js1.put("description", description.get(i));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (js1 != null)
                        jsPoints.put(js1);
                }
                j1.put("brand", jsPoints);
                postDataParams.put("data", j1);


                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject js = new JSONObject(result);
                String messgae = js.get("message").toString();
                System.out.println("=======messgae=========" + messgae);
                getBrandAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), messgae,
                        Toast.LENGTH_LONG).show();
                name.clear();
                description.clear();
                Addtagdialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    //==============================SearchById==================================================
    public void dialogDesign(String status) {
        Addtagdialog = new Dialog(MainActivity.this);
        Addtagdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Addtagdialog.setContentView(R.layout.searchdata);
        brandId = Addtagdialog.findViewById(R.id.brandName);
        timedata = Addtagdialog.findViewById(R.id.timedata);
        searchdata_rc = Addtagdialog.findViewById(R.id.searchdata_rc);
        //searchdata_rc.setLayoutManager(searchManager);
        Addtagdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Addtagdialog.getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        searchdata_button = Addtagdialog.findViewById(R.id.searchdata_button);
        searchdata_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTimetoTimeStamp();
            }
        });
        if (status.equals("search")) {
            timedata.setVisibility(View.GONE);
            searchdata_button.setVisibility(View.GONE);
            brandId.setVisibility(View.VISIBLE);
        } else {
            brandId.setVisibility(View.GONE);
            timedata.setVisibility(View.VISIBLE);
            searchdata_button.setVisibility(View.VISIBLE);
        }
        brandId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getSearchApi();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        lp.copyFrom(Addtagdialog.getWindow().getAttributes());
        Addtagdialog.getWindow().setAttributes(lp);
        Addtagdialog.show();


    }

    public void getSearchApi() {
        String url = WebServices.searchApi;
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject j1 = new JSONObject(response.toString());

                    if (j1.getInt("error_code") == 2) {
                        searchdata.clear();
                        Toast.makeText(MainActivity.this, j1.getString("message").toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Serachdata s_data = new Gson().fromJson(response, Serachdata.class);
                        searchdata.add(s_data.getBrand());
                        System.out.println("========searchdata========" + searchdata.size());
                        if (searchdata.size() > 0) {
                            getSearchAdapter = new GetSearchAdapter(searchdata, MainActivity.this);
                            searchdata_rc.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            searchdata_rc.setAdapter(getSearchAdapter);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
            }
        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                if (brandId.getText() != null) {
                    params.put("id", brandId.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this, "Please enter data", Toast.LENGTH_SHORT).show();
                }
                return params;
            }
        };


        App.getInstance().addToRequestQueue(sr);


    }

    //================================  getTimeStamp Api==================================

    public void changeTimetoTimeStamp() {
        String str_date ="2017-12-22 11:01:00" ;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = (Date) formatter.parse(str_date);
            String timestamp = String.valueOf(date.getDate());
            System.out.println("=============" + timestamp);
            getTimeStamp(timestamp);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void getTimeStamp(final String time) {
        StringRequest sr = new StringRequest(Request.Method.POST, WebServices.timeStamp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject js=new JSONObject(response.toString());
                    System.out.println("======response=========" + response);
                    if(js.getInt("error_code")==2){
                        Toast.makeText(MainActivity.this,js.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                    else {
                        GetBrandData brandData = new Gson().fromJson(response, GetBrandData.class);
                        gb.addAll(brandData.getBrandList());
                        getBrandAdapter = new GetBrandAdapter(gb, brand, MainActivity.this);
                        searchdata_rc.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        searchdata_rc.setAdapter(getBrandAdapter);
                        hideProgress();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
            }
        }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("timestamp", time);
                return params;
            }
        };
        App.getInstance().addToRequestQueue(sr);
    }

    //============================= SqLite DataBase========================

    // Reading all Brand
    public void readData() {

        Log.e("Reading: ", "Reading all contacts..");

        for (BrandData cn : brand) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getBrandName() + " ,Des: " + cn.getDescreption() + "  createdAt" + cn.getCreatedAt();
            // Writing brand to log
            Log.d("Name: ", log);

            getBrandAdapter.notifyDataSetChanged();
        }
    }

    //add data
    public void addData() {

        for (int i = 0; i < gb.size(); i++) {
            db.addBrand(new BrandData(gb.get(i).getId(), gb.get(i).getName(), gb.get(i).getDescription(), gb.get(i).getCreatedAt()));
        }
    }

}



