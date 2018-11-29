package com.mjm.webservices;

/**
 * Created by nabilulaleem.md on 23-05-2018.
 */
public class RequestUrl {

    private String url;
    public RequestUrl(String  url){
        this.url = url;
    }

    public RequestMethod method(String method){
        return new RequestMethod(url, method);
    }
}
