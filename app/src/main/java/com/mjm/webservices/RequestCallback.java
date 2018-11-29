package com.mjm.webservices;
/**
 * Created by nabilulaleem.md on 23-05-2018.
 */
public interface RequestCallback {

    void onSuccess(Response response);

    void onError(Response response);
}
