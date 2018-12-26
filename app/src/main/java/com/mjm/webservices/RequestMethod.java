package com.mjm.webservices;

/**
 * Created by nabilulaleem.md on 23-05-2018.
 */
public class RequestMethod {
    private String url;
    private String method;

    RequestMethod(String url, String method){
        this.url = url;
        this.method = method;
    }

    public RequestHeader header(String key, String value){
        return new RequestHeader(url, method, key, value);
    }

    public RequestHeaders headers(String[] keys, String[] values){
        return new RequestHeaders(url, method, keys, values);
    }

    public RequestFile file(String[] filePath){
        return new RequestFile(url, method, filePath);
    }

    public RequestFile file(String key, String value, String[] filePath){
        return new RequestFile(url, method, key, value, filePath);
    }

    public RequestFile file(String[] keys, String[] values, String[] filePath){
        return new RequestFile(url, method, keys, values, filePath);
    }

    public RequestBody json(String jsonBody){
        return new RequestBody(url, method, jsonBody);
    }

    public Request request(){
        return new Request(url, method);
    }

    public Request request(int readTimeout, int connectionTimeout){
        Request request = new Request(url, method);
        request.setTimeout(readTimeout, connectionTimeout);
        return request;
    }

    public Request request(int readTimeout, int connectionTimeout, String contentType){
        Request request = new Request(url, method);
        request.setTimeout(readTimeout, connectionTimeout, contentType);
        return request;
    }
}
