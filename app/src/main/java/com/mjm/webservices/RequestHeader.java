package com.mjm.webservices;

/**
 * Created by nabilulaleem.md on 23-05-2018.
 */
public class RequestHeader {

    private String url;
    private String method;
    private String headerKey;
    private String headerValue;

    RequestHeader(String url, String method, String key, String value){
        this.url = url;
        this.method = method;
        this.headerKey = key;
        this.headerValue = value;
    }

    public RequestFile file(String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, filePathKeys, filePathValues);
    }

    public RequestFile file(String key, String value, String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, headerKey, headerValue, key, value, filePathKeys, filePathValues);
    }

    public RequestFile file(String[] keys, String[] values, String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, headerKey, headerValue, keys, values, filePathKeys, filePathValues);
    }

    public RequestBody json(String jsonBody){
        return new RequestBody(url, method, headerKey, headerValue, jsonBody);
    }

    public Request request(){
        return new Request(url, method, headerKey, headerValue);
    }

    public Request request(int readTimeout, int connectionTimeout){
        Request request = new Request(url, method, headerKey, headerValue);
        request.setTimeout(readTimeout, connectionTimeout);
        return request;
    }

    public Request request(int readTimeout, int connectionTimeout, String contentType){
        Request request = new Request(url, method, headerKey, headerValue);
        request.setTimeout(readTimeout, connectionTimeout, contentType);
        return request;
    }
}
