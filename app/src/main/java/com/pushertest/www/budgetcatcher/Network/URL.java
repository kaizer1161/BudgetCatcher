package com.pushertest.www.budgetcatcher.Network;

public class URL {

    /*Api base url*/
    public static final String base = "http://htwistingmill.com/api/";

    /*URL values for key*/
    public static final String VALUE_CODE_SUCCESS = "0000";

    /*URL status code*/
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_SERVER_NOT_FOUND = 404;
    public static final int STATUS_SERVER_INTERNAL_ERROR = 500;
    public static final int STATUS_SERVER_RESPONSE_OK = 200;
    public static final int STATUS_SERVER_CREATED = 201;

    public static final String deliverRequest = "delivery_request";

    /*URL Suffix*/
    public static final String createAccount = "client_registration_request";
    public static final String userLogin = "client_login";
    public static final String checkPrice = "totalPrice";
    public static final String deliveryServiceRequest = "delivery_transporter_request";
    public static final String deliveryServiceResponse = "delivery_service_response";

    /*URL response Json key*/
    public static final String jsonData = "data";

    /*API key*/
    public static final String API_KEY_WEIGHT = "weight";
    public static final String API_KEY_DISTANCE = "distance";
    public static final String API_KEY_GEO_START_LAT = "geoStartLatitude";
    public static final String API_KEY_GEO_START_LONG = "geoStartLongitude";
    public static final String API_KEY_GEO_END_LAT = "geoEndLatitude";
    public static final String API_KEY_GEO_END_LONG = "geoEndLongitude";
    public static final String API_KEY_RECEIVER_NAME = "receiverName";
    public static final String API_KEY_RECEIVER_PHONE = "receiverPhone";
    public static final String API_KEY_CLIENT_ID = "clientId";
    public static final String API_KEY_PAY_PERSON = "payPerson";
    public static final String API_KEY_TOTAL_PRICE = "TotalPrice";
    public static final String API_KEY_PRODUCT_PRICE = "productPrice";
    public static final String API_KEY_DELIVERY_ID = "Delivery Id";
    public static final String API_KEY_DELIVERY_ID_FOR_CHECK = "did";
    public static final String API_KEY_DELIVERY = "delivery";
    public static final String API_KEY_TRANSPORTER = "transporter";
    public static final String API_KEY_TRANSPORTER_GEOLAT = "geoLat";
    public static final String API_KEY_TRANSPORTER_GEOLONG = "geoLan";

    /*STATUS for delivery response*/
    public static final String API_KEY_STATUS = "status";
    public static final String API_KEY_STATUS_FOUND = "found";
    public static final String API_KEY_STATUS_NOT_FOUND = "not found";
    public static final String API_KEY_STATUS_CANCELED = "canceled";
    public static final String API_KEY_STATUS_PICKED = "picked";
    public static final String API_KEY_STATUS_END = "end";

    /*URL key */
    public static final String key_code = "code";
    public static final String keyClientId = "clientId";
    public static final String keyReceiverName = "receiverName";
    public static final String keyReceiverPhone = "receiverPhone";
    public static final String keyWeight = "weight";
    public static final String keyGeoEndLongitude = "geoEndLongitude";
    public static final String keyGeoEndLatitude = "geoEndLatitude";
    public static final String keyGeoStartLongitude = "geoStartLongitude";
    public static final String keyGeoStartLatitude = "geoStartLatitude";


}
