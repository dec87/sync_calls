package com.example.dcelmer.workmanagersyncer.communication;


import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class FocusService {
    private static FocusService mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;
    private SharedPreferences mPrefs;

    private static final String PREFS = "com.example.dcelmer.workmanagersyncer";
    private static final String BASE_URL = "https://damiancelmer.pl";

    private FocusService(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mPrefs = mContext.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    private String prepareUrl(String path) {
        String url = BASE_URL;
        String domain =  mPrefs.getString("domain", "");

        if (domain.indexOf("dev-") == -1) {
            url = BASE_URL.replace("{domain}", domain);
        }
        return url + path;
    }

    public static synchronized FocusService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FocusService(context);
        }
        return mInstance;
    }

    public void getNewToken() {
        FocusRequest request = new FocusRequest(
                Request.Method.POST,
                prepareUrl("/external-api/mobile-create-jwt-token"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        getRequestQueue().add(request);
    }

    public void syncCalls() {
        FocusRequest request = new FocusRequest(
                Request.Method.POST,
                prepareUrl("/external-api/mobile-add-identified-call"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        getRequestQueue().add(request);
    }
}
