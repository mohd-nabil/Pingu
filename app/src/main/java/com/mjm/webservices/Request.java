package com.mjm.webservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

/**
 * Created by nabilulaleem.md on 23-05-2018.
 */
public class Request {

    private static final String INTERNET_CONNECTION = "Please check your internet connection.";
    private static final String CONNECTION_ERROR = "Connection type must be only 'WiFi' or 'Mobile data'.";
    private static final String WIFI_CONNECTION = "Please turn on WiFi.";
    private static final String MOBILE_CONNECTION = "Please turn on mobile data.";

    private RequestCallback requestCallback;
    private Context mContext;

    private String url;
    private String method;
    private String key;
    private String value;
    private String[] keys;
    private String[] values;
    private String jsonBody;
    private int readTimeout;
    private int connectionTimeout;
    private String contentType;


    Request(String url, String method, String key, String value, String[] keys, String[] values, String jsonBody) {
        this.url = url;
        this.method = method;
        this.key = key;
        this.value = value;
        this.keys = keys;
        this.values = values;
        this.jsonBody = jsonBody;
    }

     Request(String url, String method, String key, String value) {
        this.url = url;
        this.method = method;
        this.key = key;
        this.value = value;
    }

     Request(String url, String method, String[] keys, String[] values) {
        this.url = url;
        this.method = method;
        this.keys = keys;
        this.values = values;
    }

     Request(String url, String method) {
        this.url = url;
        this.method = method;
    }

     void setTimeout(int readTimeout, int connectionTimeout) {
        this.readTimeout = readTimeout;
        this.connectionTimeout = connectionTimeout;
    }

     void setTimeout(int readTimeout, int connectionTimeout, String contentType) {
        this.readTimeout = readTimeout;
        this.connectionTimeout = connectionTimeout;
        this.contentType = contentType;
    }

    public void execute(Context context, RequestCallback requestCallback){
        this.mContext = context;
        this.requestCallback = requestCallback;
        if(MobileConnectivity.checkInternet(mContext)) {
            new ServiceAsync().execute();
        }else{
            requestCallback.onError(new Response(-1, INTERNET_CONNECTION, INTERNET_CONNECTION));
        }
    }

     public void execute(Context context, int connectivityType, RequestCallback requestCallback){
        this.mContext = context;
        this.requestCallback = requestCallback;

        if(connectivityType == ConnectivityManager.TYPE_WIFI) {
            if (MobileConnectivity.checkWiFi(mContext)) {
                new ServiceAsync().execute();
            } else {
                requestCallback.onError(new Response(-1, WIFI_CONNECTION, WIFI_CONNECTION));
            }
        }else if(connectivityType == ConnectivityManager.TYPE_MOBILE){
            if (MobileConnectivity.checkMobileData(mContext)) {
                new ServiceAsync().execute();
            } else {
                requestCallback.onError(new Response(-1, MOBILE_CONNECTION, MOBILE_CONNECTION));
            }
        }else{
            requestCallback.onError(new Response(-1, CONNECTION_ERROR, CONNECTION_ERROR));
        }
    }


    private class ServiceAsync extends AsyncTask<String, Void, Response> {
        @Override
        protected Response doInBackground(String... params) {
            Response response = null;
            try {
                ServiceCall serviceCall = new ServiceCall();
                if(contentType != null && readTimeout != 0 && connectionTimeout != 0){
                    serviceCall = new ServiceCall(readTimeout, connectionTimeout, contentType);
                }else if(readTimeout != 0 && connectionTimeout != 0){
                    serviceCall = new ServiceCall(readTimeout, connectionTimeout);
                }else if(contentType != null){
                    serviceCall = new ServiceCall(contentType);
                }

                if(method.toUpperCase().equals(Methods.GET())){
                    response = serviceCall.initializeGetClient(url, key, value, keys, values);
                }else if(method.toUpperCase().equals(Methods.POST())){
                    response = serviceCall.initializePostClient(url, jsonBody, key, value, keys, values);
                }else if(method.toUpperCase().equals(Methods.DELETE())){
                    response = serviceCall.initializeDeleteClient(url, jsonBody, key, value, keys, values);
                }else if(method.toUpperCase().equals(Methods.PUT())){
                    response = serviceCall.initializePutClient(url, jsonBody, key, value, keys, values);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response s) {
            super.onPostExecute(s);
            if(s != null) {
                requestCallback.onSuccess(s);
            }else{
                requestCallback.onError(new Response(-1, ServiceCall.UNEXPECTED_ERROR, ServiceCall.UNEXPECTED_ERROR));
            }
        }
    }

}
