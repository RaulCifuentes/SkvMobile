package com.metric.skava.uploader.app.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by metricboy on 8/12/14.
 */
public class SkavaUploaderUtils {

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Calendar getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar;
    }
}
