package com.metric.skava.app.util;

public class SkavaConstants {

	public static final String LOG = "com.metric.skava";

	public static final String IMAGE_DIRECTORY_NAME = "SkavaPictures";

    public static final String DATABASE_DIRECTORY_NAME = "SkavaDatabase";

	public static final String ROLE_ADMIN_NAME = "ADMINISTRATOR";

	public static final String ROLE_ANALYST_NAME = "ANALYST";

	public static final String ROLE_GEOLOGIST_NAME = "GEOLOGIST";

    public static final String DROPBOX_APP_NAME = "skavasyncservice";

    public static final String DROPBOX_DS_DEV_NAME = "skavadev";
    public static final String DROPBOX_DS_QA_NAME = "skavaqa";
    public static final String DROPBOX_DS_PROD_NAME = "skava";

    public static final String DEV_KEY = "DEV";
    public static final String QA_KEY = "QA";
    public static final String PROD_KEY = "PROD";

    //Use the same value of the action on the UploaderModule in order to Intent by action
    // and avoid the need of include a library on buildPath
    public static final String CUSTOM_ACTION = "com.metric.skava.uploader.UPLOAD_SERVICE_ACTION";

//    SKAVAAPP DATASTORE ONLY
//    public static final String DROBOX_APP_KEY = "wv7jc0wirvu2c8y";
//    public static final String DROBOX_APP_SECRET = "qebhcnh6lxqu7di";

    // SKAVA ONLY FILE ACCESS
//    public static final String DROBOX_APP_KEY = "wipi7ui8mgii76j";
//    public static final String DROBOX_APP_SECRET = "rzqlw9qn9i24ttv";
//
    //FABIAN TEST
//    public static final String DROBOX_APP_KEY = "zt0gfu84ayugdhp";
//    public static final String DROBOX_APP_SECRET = "d31ifw9hsvgfo39";
//    public static final String DROPBOX_DS_DEV_NAME = "test";

    //SKAVA_DEV
    public static final String DROBOX_APP_KEY = "1ij9s1t1ab2evpu";
    public static final String DROBOX_APP_SECRET = "sxr158c6hlqajex";

    public static final String AVIARY_APP_KEY = "cd9f4966112e789c";

    //    SKAVAAPP FULL ACCESS
//    public static final String DROBOX_APP_KEY = "1xwvqk4tepx9w1r";
//    public static final String DROBOX_APP_SECRET = "nabmi5njy4lgxct";


    //RAULCIFUENTES
//    public static final String DROBOX_APP_KEY = "bzsvwf0odnoslis";
//    public static final String DROBOX_APP_SECRET = "pc2wwbqnblh0r53";

    public static final int REQUEST_LINK_TO_DROPBOX = 510;

    public static final String EXTRA_INTERNAL_CODE = "EXTRA_INTERNAL_CODE";
    public static final String EXTRA_ASSESSMENT_CODE = "EXTRA_ASSESSMENT_CODE";
    public static final String EXTRA_PICTURES = "EXTRA_PICTURES";
    public static final String EXTRA_ENVIRONMENT_NAME = "EXTRA_ENVIRONMENT_NAME";

    public static final String MOBILE_SOURCE = "MOBILE";
    public static final String BUGSENSE_API_KEY = "41e76766";
    public static final String EXTRA_OPERATION = "EXTRA_OPERATION";
    public static final String EXTRA_OPERATION_INSERT = "INSERT";
    public static final String EXTRA_OPERATION_DELETE = "DELETE";

    public static final String SKAVA_APP_SRC = "SKAVA_APP_SRC";
}
