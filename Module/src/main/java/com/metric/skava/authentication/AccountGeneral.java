package com.metric.skava.authentication;

public class AccountGeneral {

//   Thanks to
//   http://udinic.wordpress.com/2013/04/24/write-your-own-android-authenticator/

    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "com.skava.auth_example";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "Skava";

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an Skava account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an Skava account";

    public static final ServerAuthenticate sServerAuthenticate = new ParseComServerAuthenticate();
}
