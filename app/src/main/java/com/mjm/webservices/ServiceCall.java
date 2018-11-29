package com.mjm.webservices;

import android.util.Log;

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
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

public class ServiceCall {

    private static final String TAG = "ServiceCall";
    public static final String status = "999";

    public static final String UNAUTHORISED_ACCESS = "Unauthorised access, please login again.";
    public static final String BAD_REQUEST = "Request not available";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error, please try again.";
    public static final String UNEXPECTED_ERROR = "Unexpected error occurred";

    private static final String MSG = "msg";
    private static final String STATUS = "status";

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    private static final String FILE_NOT_FOUND = "File not found";
    private static final String PERMISSION_DENIED = "Permission denied, please add uses permissions";

    private static int readTimeout = 20000;
    private static int connectTimeout = 25000;
    private static String contentType = "application/json; charset=UTF-8";

    private static HttpURLConnection conn;

    public ServiceCall(){}

    public ServiceCall(String contentType){
        ServiceCall.contentType = contentType;
    }

    public ServiceCall(int readTimeout, int connectTimeout){
        ServiceCall.readTimeout = readTimeout;
        ServiceCall.connectTimeout = connectTimeout;
    }

    public ServiceCall(int readTimeout, int connectTimeout, String contentType){
        ServiceCall.readTimeout = readTimeout;
        ServiceCall.connectTimeout = connectTimeout;
        ServiceCall.contentType = contentType;
    }

    private void initializeConnection(String URL) throws IOException {
        Log.d(TAG, URL);
        java.net.URL url = new URL(URL);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(readTimeout);
        conn.setConnectTimeout(connectTimeout);
    }

    private void setupGetProperties() throws ProtocolException {
        conn.setRequestMethod(GET);
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

    public Response initializeGetClient(String URL, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
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

    public Response initializePostClient(String URL, String jsonBody, String paramsKey, String paramsValue,  String[] paramsKeys, String[] paramsValues) throws IOException {
        initializeConnection(URL);
        setupProperties(POST);
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

    public Response initializePutClient(String URL, String jsonBody, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
        initializeConnection(URL);
        setupProperties(PUT);
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

    public Response initializeDeleteClient(String URL, String jsonBody, String paramsKey, String paramsValue, String[] paramsKeys, String[] paramsValues) throws IOException {
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
        Response response = deleteService.initializeDeleteClient(URL, jsonBody, paramsKey, paramsValue, paramsKeys, paramsValues);
        return response;
    }

    public Response initializeMultipart(String URL, String paramKey, String paramValue, String[] paramKeys, String[] paramValues, String[] filePath) throws IOException {
        initializeConnection(URL);
        setupMultipartProperties(POST);
        AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                new AndroidMultiPartEntity.ProgressListener() {
                    @Override
                    public void transferred(long num) {
                    }
                });
        if(paramKey != null && paramValue != null){
            entity = setParamsAndValues(entity, paramKey, paramValue);
        }

        if(paramKeys != null && paramValues != null){
            entity = setParamsAndValues(entity, paramKeys, paramValues);
        }

        if(filePath != null){
            entity = setFileBody(entity, filePath);
        }

        try {
            return getResponse(entity);
        }catch (FileNotFoundException e){
            return new Response(-1, FILE_NOT_FOUND, FILE_NOT_FOUND);
        }catch (SecurityException se){
            return new Response(-1, PERMISSION_DENIED, PERMISSION_DENIED);
        }
    }

    private AndroidMultiPartEntity setFileBody(AndroidMultiPartEntity entity, String[] filePathArr) throws FileNotFoundException{
        for(String filePath: filePathArr) {
            File sourceFile = new File(filePath);
            entity.addPart("filePath", new FileBody(sourceFile));
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
            Log.d(TAG, "**KEY**" + paramsKey + "=" + paramsValue);
            conn.setRequestProperty(paramsKey, paramsValue);
        }catch (Exception e){
            Log.d(TAG, ""+e);
        }
        return conn;
    }
    private static HttpURLConnection setParamsAndValues(HttpURLConnection conn, String[] paramsKeys, String[] paramsValues){
        for(int i=0; i<paramsKeys.length; i++){
            try {
                Log.d(TAG, "**KEY**" + paramsKeys[i] + "=" + paramsValues[i]);
                conn.setRequestProperty(paramsKeys[i], paramsValues[i]);
            }catch (Exception e){
                Log.d(TAG, ""+e);
            }
        }
        return conn;
    }
    private static AndroidMultiPartEntity setParamsAndValues(AndroidMultiPartEntity entity, String paramsKey, String paramsValue){
        try {
            Log.d(TAG, "**KEY**" + paramsKey + "=" + paramsValue);
            entity.addPart(paramsKey, new StringBody(paramsValue));
        }catch (Exception e){
            Log.d(TAG, ""+e);
        }
        return entity;
    }
    private static AndroidMultiPartEntity setParamsAndValues(AndroidMultiPartEntity entity, String[] paramsKeys, String[] paramsValues){
        for(int i=0; i<paramsKeys.length; i++){
            try {
                Log.d(TAG, "**KEY**" + paramsKeys[i] + "=" + paramsValues[i]);
                entity.addPart(paramsKeys[i], new StringBody(paramsValues[i]));
            }catch (Exception e){
                Log.d(TAG, ""+e);
            }
        }
        return entity;
    }

    private Response getResponse(AndroidMultiPartEntity entity) throws IOException, FileNotFoundException {
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
        Headers.Builder headers = new Headers.Builder();

        String response = "";
        if (responseCode == HttpURLConnection.HTTP_OK) {
            for(Map.Entry<String, List<String>> entries : conn.getHeaderFields().entrySet()){
                try {
                    String values = "";
                    for (String value : entries.getValue()) {
                        values += value + ",";
                    }
                    headers.add(entries.getKey(), values.substring(0, values.length()-1));
                }catch (Exception e){}
            }

            String line;
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line=br.readLine()) != null) {
                response+=line;
            }
        }else {
            try {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
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
                response = message;
            }
        }
        Log.d(TAG, "**RESPONSE**" + response);
        conn.connect();
        return  new Response(responseCode, responseMessage, response, headers.build());
    }
}
