package com.mjm.webservices;

import org.apache.http.entity.mime.content.StringBody;

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
    private String bodyKey;
    private StringBody stringBody;
    private String[] filePathKeys;
    private String[] filePathValues;

    RequestFile(String url, String method, String[] filePathKeys, String[] filePathValues) {
        this.url = url;
        this.method = method;
        this.filePathKeys = filePathKeys;
        this.filePathValues = filePathValues;
    }

    RequestFile(String url, String method, String key, String value, String[] filePathKeys, String[] filePathValues) {
        this.url = url;
        this.method = method;
        this.key = key;
        this.value = value;
        this.headerKey = key;
        this.headerValue = value;
        this.filePathKeys = filePathKeys;
        this.filePathValues = filePathValues;
    }

    RequestFile(String url, String method, String[] keys, String[] values, String[] filePathKeys, String[] filePathValues) {
        this.url = url;
        this.method = method;
        this.keys = keys;
        this.values = values;
        this.headerKeys = keys;
        this.headerValues = values;
        this.filePathKeys = filePathKeys;
        this.filePathValues = filePathValues;
    }

    public RequestFile(String url, String method, String key, String value, String bodyKey, StringBody stringBody, String[] filePathKeys, String[] filePathValues) {
        this.url = url;
        this.method = method;
        this.headerKey = key;
        this.headerValue = value;
        this.bodyKey = bodyKey;
        this.stringBody = stringBody;
        this.filePathKeys = filePathKeys;
        this.filePathValues = filePathValues;
    }

    public RequestFile(String url, String method, String[] keys, String[] values, String bodyKey, StringBody stringBody, String[] filePathKeys, String[] filePathValues) {
        this.url = url;
        this.method = method;
        this.headerKeys = keys;
        this.headerValues = values;
        this.bodyKey = bodyKey;
        this.stringBody = stringBody;
        this.filePathKeys = filePathKeys;
        this.filePathValues = filePathValues;
    }

    public RequestFile(String url, String method, String bodyKey, StringBody stringBody, String[] filePathKeys, String[] filePathValues) {
        this.url = url;
        this.method = method;
        this.bodyKey = bodyKey;
        this.stringBody = stringBody;
        this.filePathKeys = filePathKeys;
        this.filePathValues = filePathValues;
    }

    public RequestMultipart request(){
        return new RequestMultipart(url, method, headerKey, headerValue, headerKeys, headerValues, key, value, keys, values, bodyKey, stringBody, filePathKeys, filePathValues);
    }

    public RequestMultipart request(int readTimeout, int connectionTimeout){
        RequestMultipart requestMultipart = new RequestMultipart(url, method, headerKey, headerValue, headerKeys, headerValues, key, value, keys, values, bodyKey, stringBody, filePathKeys, filePathValues);
        requestMultipart.setTimeout(readTimeout, connectionTimeout);
        return requestMultipart;
    }

    public RequestMultipart request(int readTimeout, int connectionTimeout, String contentType){
        RequestMultipart requestMultipart = new RequestMultipart(url, method,headerKey, headerValue, headerKeys, headerValues,  key, value, keys, values, bodyKey, stringBody, filePathKeys, filePathValues);
        requestMultipart.setTimeout(readTimeout, connectionTimeout, contentType);
        return requestMultipart;
    }
}
