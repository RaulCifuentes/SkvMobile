package com.metric.skava.app.util;

import android.os.Build;
import android.os.Build.VERSION_CODES;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.data.IdentifiableEntity;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.pictures.model.SkavaPicture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by metricboy on 3/5/14.
 */
public class SkavaUtils {

    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }


    public static Date getCurrentDate() {
        return new Date();
    }

    public static Calendar getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar;
    }


    public static Assessment createInitialAssessment(SkavaContext skavaContext) throws DAOException {

        String code = UUID.randomUUID().toString();
        Assessment initialAssessment = new Assessment(code);

        DAOFactory daoFactory = skavaContext.getDAOFactory();

        // Get the current time
        Calendar currentDateTime = SkavaUtils.getCurrentDateTime();
        initialAssessment.setDateTime(currentDateTime);


        //Q Barton (Default values for each one of the components of thr Q process)
        Q_Calculation mQCalculation = new Q_Calculation(null, null, null, null, null, null);
        initialAssessment.setQCalculation(mQCalculation);

        RMR_Calculation mRMRCalculation = new RMR_Calculation(null, null, null, null, null, null, null, null, null, null);
        initialAssessment.setRmrCalculation(mRMRCalculation);

        //Default 4 picture placeholders
        ArrayList<SkavaPicture> pictureList = new ArrayList<SkavaPicture>(5);
        pictureList.add(null); //0
        pictureList.add(null);
        pictureList.add(null);
        pictureList.add(null);
        pictureList.add(null);
        pictureList.add(null);
        pictureList.add(null);
        pictureList.add(null); //7

        initialAssessment.setPicturesList(pictureList);

        //Discontinuity System
        int dfItems = 7;
        ArrayList<DiscontinuityFamily> discontinuitySystem = new ArrayList<DiscontinuityFamily>(dfItems);
        for(int i=0; i < dfItems; i++){
//            DiscontinuityFamily df = new DiscontinuityFamily();
//            df.setNumber(i);
            discontinuitySystem.add(null);
        }

        initialAssessment.setDiscontinuitySystem(discontinuitySystem);

        return initialAssessment;
    }

    public static boolean isUndefined(IdentifiableEntity skavaEntity) {
        if (skavaEntity == null || skavaEntity.getCode() == null || skavaEntity.getCode().equals("HINT")) {
            return true;
        }
        return false;
    }

    public static boolean isDefined(IdentifiableEntity skavaEntity) {
        return !isUndefined(skavaEntity);
    }

    public static String breakLongLine(String longLine, int rowMaxLength) {
        List<String> shorterStrings = splitEqually(longLine, rowMaxLength);
        StringBuffer stringBuffer = new StringBuffer();
        int renglones = shorterStrings.size() - 1;
        for (int i = 0; i < renglones; i++) {
            String shorterString = shorterStrings.get(i);
            stringBuffer.append(shorterString).append("\n");
        }
        stringBuffer.append(shorterStrings.get(renglones));
        return stringBuffer.toString();
    }

    private static List<String> splitEqually(String text, int size) {
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);
        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

    public static boolean hasPictures(List<SkavaPicture> pictureList) {
        for (SkavaPicture picture : pictureList) {
            if (picture != null) {
                return true;
            };
        }
        return false;
    }
}
