package com.example.dcelmer.workmanagersyncer.communication;

import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FocusRequest extends StringRequest {

    private static final String HEADER_USER_AGENT = "MobileLite 2.4";

    /**
     * Creates a new request with the given method.
     *
     * @param method        the request {@link Method} to use
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public FocusRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Request.Priority getPriority() {
        return Request.Priority.HIGH;
    }

    @Override
    public Map getHeaders() {
        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", HEADER_USER_AGENT);
        headers.put("X-app-type", "lite");
        return headers;
    }
}
