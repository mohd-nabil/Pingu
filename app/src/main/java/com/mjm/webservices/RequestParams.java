package com.mjm.webservices;

/**
 * Created by nabilulaleem.md on 23-05-2018.
 */
public class RequestParams {
    private String url;
    private String method;
    private String[] keys;
    private String[] values;

    public RequestParams(String url, String method, String[] keys, String[] values){
        this.url = url;
        this.method = method;
        this.keys = keys;
        this.values = values;
    }

    public RequestBody json(String jsonBody){
        return new RequestBody(url, method, keys, values, jsonBody);
    }

    public Request request(){
        return new Request(url, method, keys, values);
    }

    public Request request(int readTimeout, int connectionTimeout){
        Request request = new Request(url, method, keys, values);
        request.setTimeout(readTimeout, connectionTimeout);
        return request;
    }

    public Request request(int readTimeout, int connectionTimeout, String contentType){
        Request request = new Request(url, method, keys, values);
        request.setTimeout(readTimeout, connectionTimeout, contentType);
        return request;
    }
}
