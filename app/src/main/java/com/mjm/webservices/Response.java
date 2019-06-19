package com.mjm.webservices;

import okhttp3.Headers;

/**
 * Created by nabilulaleem.md on 24-05-2018.
 */
public class Response {

    private int responseCode;
    private String responseMessage;
    private String response;
    private ResponseHeader headers;

    public Response(){}

    public Response(int responseCode, String responseMessage, String response){
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.response = response;
    }

    public Response(int responseCode, String responseMessage, String response, ResponseHeader headers){
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.response = response;
        this.headers = headers;
    }

//    public Response(int code, String message, String string, Headers headers) {
//        this.responseCode = responseCode;
//        this.responseMessage = responseMessage;
//        this.response = response;
//        headers.toMultimap();
//        this.headers = headers;
//    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ResponseHeader getHeaders() {
        return headers;
    }

    public void setHeaders(ResponseHeader headers) {
        this.headers = headers;
    }

}
