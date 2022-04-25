package com.rebirth.veterinaryexample.app.web.resources;

public final class WebResourceConstants {

    private final static String API_VERSION = "v00";

    private final static String API = "/api/" + API_VERSION;

    public final static String API_PET = API + "/pet";
    public final static String API_BREED = API + "/breed";
    public final static String API_PETDETAIL = API + "/petdetail";
    public final static String API_USER = API + "/user";

    private WebResourceConstants() {
    }
}
