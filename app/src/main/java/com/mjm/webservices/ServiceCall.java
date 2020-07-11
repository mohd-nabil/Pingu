package com.mjm.webservices;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ServiceCall {

    private static final String TAG = "ServiceCall";
    public static final String status = "999";

    static final String UNAUTHORISED_ACCESS = "Unauthorised access, please login again.";
    static final String BAD_REQUEST = "Request not available";
    static final String INTERNAL_SERVER_ERROR = "Internal server error, please try again.";
    static final String UNEXPECTED_ERROR = "Unexpected error occurred";

    private static final String MSG = "msg";
    private static final String STATUS = "status";

    private static final String FILE_NOT_FOUND = "File not found";
    private static final String PERMISSION_DENIED = "Permission denied, please add uses permissions";

    private static int readTimeout = 20000;
    private static int connectTimeout = 25000;
    private static String contentType = "application/json; charset=UTF-8";
    private String authToken;

    private static HttpURLConnection conn;

    ServiceCall(){}

    ServiceCall(String contentType){
        ServiceCall.contentType = contentType;
    }

    ServiceCall(int readTimeout, int connectTimeout){
        ServiceCall.readTimeout = readTimeout;
        ServiceCall.connectTimeout = connectTimeout;
    }

    ServiceCall(int readTimeout, int connectTimeout, String contentType){
        ServiceCall.readTimeout = readTimeout;
        ServiceCall.connectTimeout = connectTimeout;
        ServiceCall.contentType = contentType;
    }

    public void authToken(String authToken){
        this.authToken = authToken;
    }
    private void initializeConnection(String URL) throws IOException {
        Log.d(TAG, URL);
        java.net.URL url = new URL(URL);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(readTimeout);
        conn.setConnectTimeout(connectTimeout);
        if(authToken != null){
            conn.setRequestProperty("Authorization", authToken);
        }
    }

    private void setupGetProperties() throws ProtocolException {
        conn.setRequestMethod(Methods.GET());
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
    }

    private void setupProperties(String method) throws ProtocolException {
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", contentType);
    }

    private void setupDeleteProperties(String method) throws ProtocolException {
        conn.setDoOutput(true);
//        conn.setDoInput(true);
//        conn.setRequestProperty("X-HTTP-Method-Override", method);
//        conn.setRequestMethod(method);
//        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestMethod(method);
    }

    private void setupMultipartProperties(String method) throws ProtocolException {
        conn.setUseCaches(false);
        conn.setDoOutput(true);    // indicates POST method
        conn.setDoInput(true);
//        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + System.currentTimeMillis());
        conn.setRequestMethod(method);
    }

    Response initializeGetClient(String URL, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
        initializeConnection(URL);
        setupGetProperties();
        if(paramsKey != null && paramsValue != null) {
            conn = setParamsAndValues(conn, paramsKey, paramsValue);
        }
        if(paramsKeys != null && paramsValues != null) {
            conn = setParamsAndValues(conn, paramsKeys, paramsValues);
        }
        return getResponse(conn);
    }

    Response initializePostClient(String URL, String jsonBody, String paramsKey, String paramsValue,  String[] paramsKeys, String[] paramsValues) throws IOException {
        initializeConnection(URL);
        setupProperties(Methods.POST());
        if(paramsKey != null && paramsValue != null) {
            conn = setParamsAndValues(conn, paramsKey, paramsValue);
        }
        if(paramsKeys != null && paramsValues != null) {
            conn = setParamsAndValues(conn, paramsKeys, paramsValues);
        }
        if(jsonBody != null) {
            setJSONValue(jsonBody);
        }
        return getResponse(conn);
    }

    Response initializePutClient(String URL, String jsonBody, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
        initializeConnection(URL);
        setupProperties(Methods.PUT());
        if(paramsKey != null && paramsValue != null) {
            conn = setParamsAndValues(conn, paramsKey, paramsValue);
        }
        if(paramsKeys != null && paramsValues != null) {
            conn = setParamsAndValues(conn, paramsKeys, paramsValues);
        }
        if(jsonBody != null) {
            setJSONValue(jsonBody);
        }
        return getResponse(conn);
    }

    Response initializeDeleteClient(String URL, String jsonBody, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
//        initializeConnection(URL);
//        setupDeleteProperties(DELETE);
//        if(paramsKey != null && paramsValue != null) {
//            conn = setParamsAndValues(conn, paramsKey, paramsValue);
//        }
//        if(paramsKeys != null && paramsValues != null) {
//            conn = setParamsAndValues(conn, paramsKeys, paramsValues);
//        }
//        if(jsonBody != null) {
//            setJSONValue(jsonBody);
//        }
        DeleteService deleteService = new DeleteService(readTimeout, connectTimeout, contentType);
        deleteService.authToken(authToken);
        Response response = deleteService.initializeDeleteClient(URL, jsonBody, paramsKey, paramsValue, paramsKeys, paramsValues);
        return response;
    }

    Response initializeMultipart(String URL, String headerKey, String headerValue, String[] headerKeys, String[] headerValues, String key, String value, String[] keys, String[] values, String bodyKey, StringBody stringBody, String[] filePathKeys, String[] filePathValues) throws IOException {
        initializeConnection(URL);
        setupMultipartProperties(Methods.POST());
        if(headerKey != null && headerValue != null) {
            conn = setParamsAndValues(conn, headerKey, headerValue);
        }

        if(headerKeys != null && headerValues != null) {
            conn = setParamsAndValues(conn, headerKeys, headerValues);
        }

        AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                new AndroidMultiPartEntity.ProgressListener() {
                    @Override
                    public void transferred(long num) {
                    }
                });
        if(key != null && value != null){
            entity = setParamsAndValues(entity, key, value);
        }

        if(keys != null && values != null){
            entity = setParamsAndValues(entity, keys, values);
        }

        if(bodyKey != null && stringBody != null){
            entity = setFormBody(entity, bodyKey, stringBody);
        }

        if(filePathKeys != null && filePathValues != null){
            entity = setFileBody(entity, filePathKeys, filePathValues);
        }

        try {
            return getResponse(entity);
        }catch (FileNotFoundException e){
            return new Response(-1, FILE_NOT_FOUND, FILE_NOT_FOUND);
        }catch (SecurityException se){
            return new Response(-1, PERMISSION_DENIED, PERMISSION_DENIED);
        }
    }

    private AndroidMultiPartEntity setFormBody(AndroidMultiPartEntity entity, String bodyKey, StringBody stringBody) {
        FormBodyPart formBodyPart = new FormBodyPart(bodyKey, stringBody);
        entity.addPart(formBodyPart);
        return entity;
    }

    private AndroidMultiPartEntity setFileBody(AndroidMultiPartEntity entity, String[] filePathKeys, String[] filePathValues) throws FileNotFoundException{
        for(int i=0; i<filePathValues.length; i++) {
            String filePathKey = filePathKeys[i];
            String filePathValue = filePathValues[i];
            if(filePathKey != null && filePathValue != null) {
                File sourceFile = new File(filePathValue);
                entity.addPart(filePathKey, new FileBody(sourceFile));
            }
        }
        return entity;
    }

    private static void setJSONValue(String jsonBody) throws IOException {
        OutputStream os = conn.getOutputStream();
        os.write(jsonBody.getBytes("UTF-8"));
        os.close();
    }

    private static HttpURLConnection setParamsAndValues(HttpURLConnection conn, String paramsKey, String paramsValue){
        try {
            Log.d(TAG, "**KEY**" + paramsKey + ":" + paramsValue);
            conn.setRequestProperty(paramsKey, paramsValue);
        }catch (Exception e){
            Log.d(TAG, ""+e);
        }
        return conn;
    }
    private static HttpURLConnection setParamsAndValues(HttpURLConnection conn, String[] paramsKeys, String[] paramsValues){
        for(int i=0; i<paramsKeys.length; i++){
            try {
                Log.d(TAG, "**KEY**" + paramsKeys[i] + ":" + paramsValues[i]);
                conn.setRequestProperty(paramsKeys[i], paramsValues[i]);
            }catch (Exception e){
                Log.d(TAG, ""+e);
            }
        }
        return conn;
    }
    private static AndroidMultiPartEntity setParamsAndValues(AndroidMultiPartEntity entity, String paramsKey, String paramsValue){
        try {
            Log.d(TAG, "**KEY**" + paramsKey + ":" + paramsValue);
            entity.addPart(paramsKey, new StringBody(paramsValue, ContentType.TEXT_PLAIN));
        } catch (Exception e){
            Log.d(TAG, ""+e);
        }
        return entity;
    }
    private static AndroidMultiPartEntity setParamsAndValues(AndroidMultiPartEntity entity, String[] paramsKeys, String[] paramsValues){
        for(int i=0; i<paramsKeys.length; i++){
            try {
                Log.d(TAG, "**KEY**" + paramsKeys[i] + ":" + paramsValues[i]);
                entity.addPart(paramsKeys[i], new StringBody(paramsValues[i]));
            }catch (Exception e){
                Log.d(TAG, ""+e);
            }
        }
        return entity;
    }

    private Response getResponse(AndroidMultiPartEntity entity) throws IOException {
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.addRequestProperty("Content-length", entity.getContentLength()+"");
        conn.addRequestProperty(entity.getContentType().getName(), entity.getContentType().getValue());
        OutputStream os = conn.getOutputStream();
        entity.writeTo(conn.getOutputStream());
        os.close();
        conn.connect();
        return getResponse(conn);
    }

    private Response getResponse(HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        String responseMessage = conn.getResponseMessage();
        Log.d(TAG, "**REQUEST CODE**" + responseCode);
        ResponseHeader.Builder headers = new ResponseHeader.Builder();

        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            for(Map.Entry<String, List<String>> entries : conn.getHeaderFields().entrySet()){
                try {
                    StringBuilder values = new StringBuilder();
                    for (String value : entries.getValue()) {
                        values.append(value).append(",");
                    }
                    headers.add(entries.getKey(), values.substring(0, values.length()-1));
                }catch (Exception e){}
            }

            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response.append(line);
            }
        }else {
            try {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }catch (Exception e){
                e.printStackTrace();
                String message = UNEXPECTED_ERROR;
                if(responseCode == 401 || responseCode == 403 || responseCode == 407){
                    message = UNAUTHORISED_ACCESS;
                }else if(responseCode == 400 || responseCode == 404 || responseCode == 405){
                    message = BAD_REQUEST;
                }else if(responseCode == 500){
                    message = INTERNAL_SERVER_ERROR;
                }
                response = new StringBuilder(message);
            }
        }
        Log.d(TAG, "**RESPONSE**" + response);
        conn.connect();
        return  new Response(responseCode, responseMessage, response.toString(), headers.build());
    }
}
