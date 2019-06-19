package com.mjm.webservices;

import org.apache.http.entity.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;

public class StringBody {

    public static org.apache.http.entity.mime.content.StringBody form(JSONObject jsonBody){
        return new org.apache.http.entity.mime.content.StringBody(jsonBody.toString(), ContentType.TEXT_PLAIN);
    }

    public static org.apache.http.entity.mime.content.StringBody form(JSONArray jsonBody){
        return new org.apache.http.entity.mime.content.StringBody(jsonBody.toString(), ContentType.TEXT_PLAIN);
    }

    public static org.apache.http.entity.mime.content.StringBody form(String jsonBody){
        return new org.apache.http.entity.mime.content.StringBody(jsonBody, ContentType.TEXT_PLAIN);
    }
}
