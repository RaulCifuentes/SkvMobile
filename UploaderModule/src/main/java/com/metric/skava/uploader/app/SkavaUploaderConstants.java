package com.metric.skava.uploader.app;

/**
 * Created by metricboy on 6/25/14.
 */
public class SkavaUploaderConstants {

    public static final String LOG = "com.metric.skava.uploader";

    public static final String DROPBOX_DS_DEV_NAME = "skavadev";
    public static final String DROPBOX_DS_QA_NAME = "skavaqa";
    public static final String DROPBOX_DS_PROD_NAME = "skava";

    public static final String DEV_KEY = "DEV";
    public static final String QA_KEY = "QA";
    public static final String PROD_KEY = "PROD";

    public static final String DROBOX_APP_KEY = "cuv2qsemrj5o4xw";
    public static final String DROBOX_APP_SECRET = "va39b5k96eroaht";

    public static final String PERSISTENCE_BUCKET_FILE = "PERSISTENCE_BUCKET";
    public final static String ACCESS_KEY_NAME = "ACCESS_KEY";
    public final static String ACCESS_SECRET_NAME = "ACCESS_SECRET";

    public static final String EXTRA_ENVIRONMENT_NAME = "EXTRA_ENVIRONMENT_NAME";
    public static final String EXTRA_INTERNAL_CODE = "EXTRA_INTERNAL_CODE";
    public static final String EXTRA_ASSESSMENT_CODE = "EXTRA_ASSESSMENT_CODE";
    public static final String EXTRA_PICTURES = "EXTRA_PICTURES";

    public static final String EXTRA_OPERATION = "EXTRA_OPERATION";
    public static final String EXTRA_OPERATION_DELETE = "DELETE";
    public static final String EXTRA_OPERATION_INSERT = "INSERT";

    public  static final boolean USE_OAUTH1 = false;

    //Use the same value of the action on the app Module in order to Intent by action
    // and avoid the need of include a library on buildPath
    public static final String INTENT_SERVICE_TAG = "MyUploaderService";

    public static final String BUGSENSE_API_KEY = "41e76766";
    public static final String REMOTE_FOLDER_SEPARATOR = "/";
    public static final int THUMBSIZE_WIDTH = 400;
    public static final int THUMBSIZE_HEIGHT = 300;
}

