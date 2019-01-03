package com.mjm.webservices;

import org.apache.http.entity.mime.content.StringBody;

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

    public RequestFile file(String key, String value, String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, key, value, filePathKeys, filePathValues);
    }
    public RequestFile file(String[] keys, String[] values, String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, keys, values, filePathKeys, filePathValues);
    }

    public RequestFile file(String key, String value, String bodyKey, StringBody stringBody, String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, key, value, bodyKey, stringBody, filePathKeys, filePathValues);
    }
    public RequestFile file(String[] keys, String[] values, String bodyKey, StringBody stringBody, String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, keys, values, bodyKey, stringBody, filePathKeys, filePathValues);
    }

    public RequestFile file(String bodyKey, StringBody stringBody, String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, bodyKey, stringBody, filePathKeys, filePathValues);
    }

    public RequestFile file(String[] filePathKeys, String[] filePathValues){
        return new RequestFile(url, method, filePathKeys, filePathValues);
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
