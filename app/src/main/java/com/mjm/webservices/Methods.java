package com.mjm.webservices;

public enum Methods {
    POST,
    GET,
    PUT,
    DELETE,
    PATCH;

    public static String POST() {
        return POST.toString();
    }

    public static String GET() {
        return GET.toString();
    }

    public static String PUT() {
        return PUT.toString();
    }

    public static String DELETE() {
        return DELETE.toString();
    }

    public static String PATCH() {
        return PATCH.toString();
    }
}
