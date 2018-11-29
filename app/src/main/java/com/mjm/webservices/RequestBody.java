package com.mjm.webservices;

/**
 * Created by nabilulaleem.md on 23-05-2018.
 */
public class RequestBody {
    private String url;
    private String method;
    private String key;
    private String value;
    private String[] keys;
    private String[] values;
    private String jsonBody;

    public RequestBody(String url, String method, String key, String value, String jsonBody){
        this.url = url;
        this.method = method;
        this.key = key;
        this.value = value;
        this.jsonBody = jsonBody;
    }

    public RequestBody(String url, String method, String[] keys, String[] values, String jsonBody){
        this.url = url;
        this.method = method;
        this.keys = keys;
        this.values = values;
        this.jsonBody = jsonBody;
    }

    public RequestBody(String url, String method, String jsonBody) {
        this.url = url;
        this.method = method;
        this.jsonBody = jsonBody;
    }

    public Request request(){
        return new Request(url, method, key, value, keys, values, jsonBody);
    }

    public Request request(int readTimeout, int connectionTimeout){
        Request request = new Request(url, method, key, value, keys, values, jsonBody);
        request.setTimeout(readTimeout, connectionTimeout);
        return request;
    }

    public Request request(int readTimeout, int connectionTimeout, String contentType){
        Request request = new Request(url, method, key, value, keys, values, jsonBody);
        request.setTimeout(readTimeout, connectionTimeout, contentType);
        return request;
    }
}
