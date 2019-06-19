package com.mjm.webservices;

import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by nabilulaleem.md on 24-05-2018.
 */
public class DeleteService {

    private String TAG = "ServiceCall";
    private static int readTimeout = 20000;
    private static int connectTimeout = 25000;
    private static String contentType = "application/json; charset=UTF-8";

    private OkHttpClient client;
    private Request.Builder requestBuilder;

    public DeleteService(){}

    public DeleteService(String contentType){
        DeleteService.contentType = contentType;
    }

    public DeleteService(int readTimeout, int connectTimeout){
        DeleteService.readTimeout = readTimeout;
        DeleteService.connectTimeout = connectTimeout;
    }

    public DeleteService(int readTimeout, int connectTimeout, String contentType){
        DeleteService.readTimeout = readTimeout;
        DeleteService.connectTimeout = connectTimeout;
        DeleteService.contentType = contentType;
    }

    private void initialiseConnection(String url){
        client = new OkHttpClient.Builder().connectTimeout(connectTimeout, TimeUnit.MILLISECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).build();
        requestBuilder = new Request.Builder().url(url);
    }

    public Response initializeGetClient(String url, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
        initialiseConnection(url);
        requestBuilder.get();
        if(paramsKey != null && paramsValue != null) {
            addHeader(paramsKey, paramsValue);
        }
        if(paramsKeys != null && paramsValues != null) {
            addHeader(paramsKeys, paramsValues);
        }
        return getResponse();
    }

    public Response initializePostClient(String url, String jsonBody, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
        initialiseConnection(url);
        if(paramsKey != null && paramsValue != null) {
            addHeader(paramsKey, paramsValue);
        }
        if(paramsKeys != null && paramsValues != null) {
            addHeader(paramsKeys, paramsValues);
        }
        if(jsonBody != null){
            requestBuilder.post(addBody(jsonBody));
        }
        return getResponse();
    }

    public Response initializePutClient(String url, String jsonBody, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
        initialiseConnection(url);
        if(paramsKey != null && paramsValue != null) {
            addHeader(paramsKey, paramsValue);
        }
        if(paramsKeys != null && paramsValues != null) {
            addHeader(paramsKeys, paramsValues);
        }
        if(jsonBody != null){
            requestBuilder.put(addBody(jsonBody));
        }
        return getResponse();
    }

    public Response initializeDeleteClient(String url, String jsonBody, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
        initialiseConnection(url);
        if(paramsKey != null && paramsValue != null) {
            addHeader(paramsKey, paramsValue);
        }
        if(paramsKeys != null && paramsValues != null) {
            addHeader(paramsKeys, paramsValues);
        }
        if(jsonBody != null){
            requestBuilder.delete(addBody(jsonBody));
        }
        return getResponse();
    }

    private Response getResponse() throws IOException {
        Request request = requestBuilder.build();
        okhttp3.Response response = client.newCall(request).execute();

        ResponseHeader.Builder headers = new ResponseHeader.Builder();

        Map<String, List<String>> entries = response.headers().toMultimap();
        Set<String> set = entries.keySet();
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            String key = it.next();
            String value = response.headers().get(key);
            headers.add(key, value);
        }
        return new Response(response.code(), response.message(), response.body().string(), headers.build());
    }

    private void addHeader(String paramKey, String paramValue) {
        Log.d(TAG, "**KEY**" + paramKey + "=" + paramValue);
        requestBuilder.addHeader(paramKey, paramValue);
    }

    private void addHeader(String[] paramKeys, String[] paramValues){
        for(int i=0; i<paramKeys.length; i++) {
            Log.d(TAG, "**KEY**" + paramKeys[i] + "=" + paramValues[i]);
            requestBuilder.addHeader(paramKeys[i], paramValues[i]);
        }
    }

    private RequestBody addBody(String json){
        Log.d(TAG, "**JsonBody**" + "  " + json);
        RequestBody body = RequestBody.create(MediaType.parse(contentType), json);
        return body;
    }
}
