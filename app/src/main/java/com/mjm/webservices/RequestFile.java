package com.mjm.webservices;

/**
 * Created by nabilulaleem.md on 28-05-2018.
 */
public class RequestFile {

    private String url;
    private String method;
    private String headerKey;
    private String headerValue;
    private String[] headerKeys;
    private String[] headerValues;
    private String key;
    private String value;
    private String[] keys;
    private String[] values;
    private String[] filePath;

    RequestFile(String url, String method, String[] filePath) {
        this.url = url;
        this.method = method;
        this.filePath = filePath;
    }

    RequestFile(String url, String method, String key, String value, String[] filePath) {
        this.url = url;
        this.method = method;
        this.key = key;
        this.value = value;
        this.headerKey = key;
        this.headerValue = value;
        this.filePath = filePath;
    }

    RequestFile(String url, String method, String[] keys, String[] values, String[] filePath) {
        this.url = url;
        this.method = method;
        this.keys = keys;
        this.values = values;
        this.headerKey = key;
        this.headerValue = value;
        this.headerKeys = keys;
        this.headerValues = values;
        this.filePath = filePath;
    }

    public RequestMultipart request(){
        return new RequestMultipart(url, method, headerKey, headerValue, headerKeys, headerValues, key, value, keys, values, filePath);
    }

    public RequestMultipart request(int readTimeout, int connectionTimeout){
        RequestMultipart requestMultipart = new RequestMultipart(url, method, headerKey, headerValue, headerKeys, headerValues, key, value, keys, values, filePath);
        requestMultipart.setTimeout(readTimeout, connectionTimeout);
        return requestMultipart;
    }

    public RequestMultipart request(int readTimeout, int connectionTimeout, String contentType){
        RequestMultipart requestMultipart = new RequestMultipart(url, method,headerKey, headerValue, headerKeys, headerValues,  key, value, keys, values, filePath);
        requestMultipart.setTimeout(readTimeout, connectionTimeout, contentType);
        return requestMultipart;
    }
}
