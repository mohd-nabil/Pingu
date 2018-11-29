package com.mjm.webservices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import java.io.File;

/**
 * Created by nabilulaleem.md on 28-05-2018.
 */
public class RequestMultipart {

    private static final String INTERNET_CONNECTION = "Please check your internet connection.";
    private static final String CONNECTION_ERROR = "Connection type must be only 'WiFi' or 'Mobile data'.";
    private static final String WIFI_CONNECTION = "Please turn on WiFi.";
    private static final String MOBILE_CONNECTION = "Please turn on mobile data.";

    private String url;
    private String method;
    private String key;
    private String value;
    private String[] keys;
    private String[] values;
    private String[] filePath;

    private int readTimeout;
    private int connectionTimeout;
    private String contentType;

    private Context mContext;
    private RequestCallback requestCallback;

    public RequestMultipart(String url, String method, String key, String value, String[] keys, String[] values, String[] filePath){
        this.url = url;
        this.method = method;
        this.key = key;
        this.value = value;
        this.keys = keys;
        this.values = values;
        this.filePath = filePath;
    }

    public void setTimeout(int readTimeout, int connectionTimeout) {
        this.readTimeout = readTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    public void setTimeout(int readTimeout, int connectionTimeout, String contentType) {
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

                if(method.toUpperCase().equals(ServiceCall.POST)){
                    response = serviceCall.initializeMultipart(url, key, value, keys, values, filePath);
                }else {
                    response = new Response();
                    response.setResponseCode(-1);
                    response.setResponseMessage("Not allowed");
                    if (method.toUpperCase().equals(ServiceCall.GET)) {
                        response.setResponse("GET method not supported");
                    } else if (method.toUpperCase().equals(ServiceCall.DELETE)) {
                        response.setResponse("DELETE method not supported");
                    } else if (method.toUpperCase().equals(ServiceCall.PUT)) {
                        response.setResponse("PUT method not supported");
                    }
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
