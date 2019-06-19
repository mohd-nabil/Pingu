package com.mjm.webservices;

import java.util.HashMap;

public class ResponseHeader {

    private HashMap<String, String> headersMap;

    public ResponseHeader(Builder builder){
        headersMap = builder.map();
    }

    public static class Builder {
        public static HashMap<String, String> headersMap = new HashMap<>();

        public Builder add(String key, String value){
            headersMap.put(key, value);
            return this;
        }

        public Builder remove(String key){
            headersMap.remove(key);
            return this;
        }

        public Builder removeAll(){
            headersMap.clear();
            return this;
        }

        public HashMap<String, String> map(){
            return headersMap;
        }

        public String get(String key){
            return headersMap.get(key);
        }

        public HashMap<String, String> getAll(){
            return headersMap;
        }

        public ResponseHeader build(){
            return new ResponseHeader(this);
        }
    }

    public String get(String key){
        return headersMap.get(key);
    }
}