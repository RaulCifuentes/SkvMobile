package com.metric.skava.app.util;

import android.os.Build;
import android.os.Build.VERSION_CODES;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.data.IdentifiableEntity;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ClientDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ExcavationProjectDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.ParametersDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrCategoriesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrIndexesDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RmrParametersDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RoleDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRequirementDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.TunnelFaceDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.UserDropboxTable;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.pictures.model.SkavaPicture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by metricboy on 3/5/14.
 */
public class SkavaUtils {

    /**
     * Return pseudo unique ID
     * @return ID
     */
    public static String getUniquePsuedoID()
    {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their phone or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their phone, there will be a duplicate entry
        String serial = null;
        try
        {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }
        catch (Exception e)
        {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }


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

    public static Assessment cloneAssessment(SkavaContext skavaContext, Assessment baseTemplate) throws DAOException {
        String code = UUID.randomUUID().toString();
        Assessment cloneAssessment = new Assessment(code);
        cloneAssessment.setOriginatorDeviceID(getUniquePsuedoID());

        String targetEnvironment = skavaContext.getTargetEnvironment();
        cloneAssessment.setEnvironment(targetEnvironment);

        // Get the current time
        Calendar currentDateTime = SkavaUtils.getCurrentDateTime();
        cloneAssessment.setDateTime(currentDateTime);

        //Q Barton (Default values for each one of the components of thr Q process)
        Q_Calculation mQCalculation = baseTemplate.getQCalculation();
        cloneAssessment.setQCalculation(mQCalculation);

        RMR_Calculation mRMRCalculation = baseTemplate.getRmrCalculation();
        cloneAssessment.setRmrCalculation(mRMRCalculation);
        cloneAssessment.setDiscontinuitySystem(baseTemplate.getDiscontinuitySystem());
        cloneAssessment.setBlockSize(baseTemplate.getBlockSize());
        cloneAssessment.setFace(baseTemplate.getFace());
        cloneAssessment.setFractureType(baseTemplate.getFractureType());
        cloneAssessment.setGeologist(baseTemplate.getGeologist());
        cloneAssessment.setInternalCode(baseTemplate.getInternalCode());
        cloneAssessment.setMethod(baseTemplate.getMethod());
        cloneAssessment.setNumberOfJoints(baseTemplate.getNumberOfJoints());
        cloneAssessment.setOrientation(baseTemplate.getOrientation());
        cloneAssessment.setOutcropDescription(baseTemplate.getOutcropDescription());

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
        cloneAssessment.setPicturesList(pictureList);

        cloneAssessment.setRecomendation(baseTemplate.getRecomendation());
        cloneAssessment.setReferenceChainage(baseTemplate.getReferenceChainage());
        cloneAssessment.setRockSampleIdentification(baseTemplate.getRockSampleIdentification());
        cloneAssessment.setSection(baseTemplate.getSection());
        cloneAssessment.setSlope(baseTemplate.getSlope());

        cloneAssessment.setSavedStatus(Assessment.SavingStatus.NOT_SAVED);
        cloneAssessment.setDataSentStatus(Assessment.SendingStatus.NOT_SENT);
        cloneAssessment.setPicsSentStatus(Assessment.SendingStatus.NOT_SENT);

        return cloneAssessment;
    }

    public static Assessment createInitialAssessment(SkavaContext skavaContext) throws DAOException {

        String code = UUID.randomUUID().toString();
        Assessment initialAssessment = new Assessment(code);

        initialAssessment.setOriginatorDeviceID(getUniquePsuedoID());

        initialAssessment.setSource(Assessment.Originator.MOBILE);

        // determine the getDatastore().getTitle().
        // skavaContext.getTargetEnvironment().
        String targetEnvironment = skavaContext.getTargetEnvironment();
        initialAssessment.setEnvironment(targetEnvironment);

        // Get the current time
        Calendar currentDateTime = SkavaUtils.getCurrentDateTime();
        initialAssessment.setDateTime(currentDateTime);

        //Q Barton (Default values for each one of the components of thr Q process)
        Q_Calculation mQCalculation = new Q_Calculation(null, null, null, null, null, null, null);
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
        for (int i = 0; i < dfItems; i++) {
            discontinuitySystem.add(null);
        }

        initialAssessment.setDiscontinuitySystem(discontinuitySystem);

        initialAssessment.setSavedStatus(Assessment.SavingStatus.NOT_SAVED);
        initialAssessment.setDataSentStatus(Assessment.SendingStatus.NOT_SENT);
        initialAssessment.setPicsSentStatus(Assessment.SendingStatus.NOT_SENT);

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
            }
            ;
        }
        return false;
    }



    public static boolean includesAppOrUserData(Set<String> incomingChangesTables) {
        return includesAppData(incomingChangesTables) || includesUserData(incomingChangesTables);
    }

    public static boolean includesAppAndUserData(Set<String> incomingChangesTables) {
        return includesAppData(incomingChangesTables) && includesUserData(incomingChangesTables);
    }


    public static boolean includesAppData(Set<String> incomingChangesTables) {
        boolean app = false;
        for (String tablename : incomingChangesTables) {
            if (isPartOfAppData(tablename)) {
                app = true;
                break;
            }
        }
        return app;
    }

    public static boolean includesUserData(Set<String> incomingChangesTables) {
        boolean user = false;
        for (String tablename : incomingChangesTables) {
            if (isPartOfUserData(tablename)) {
                user = true;
                break;
            }
        }
        return user;
    }

    public static boolean includesOnlyAppData(Set<String> incomingChangesTables) {
        return includesAppData(incomingChangesTables) && !(includesUserData(incomingChangesTables));
    }

    public static boolean includesOnlyUserData(Set<String> incomingChangesTables) {
        return includesUserData(incomingChangesTables) && !(includesAppData(incomingChangesTables));
    }

    public static boolean isPartOfAppOrUserData(String tablename) {
        return isPartOfAppData(tablename) || isPartOfUserData(tablename);
    }

    public static boolean isPartOfAppData(String tablename) {
        boolean app = false;
        if (tablename.equals(ParametersDropboxTable.PARAMETERS_DROPBOX_TABLE)
                || tablename.equals(RmrParametersDropboxTable.RMR_PARAMETERS_TABLE)
                || tablename.equals(RmrIndexesDropboxTable.RMR_INDEXES_TABLE)
                || tablename.equals(RmrCategoriesDropboxTable.RMR_CATEGORIES_TABLE)
                ) {
            app = true;
        }
        return app;
    }

    public static boolean isPartOfUserData(String tablename) {
        boolean user = false;
        if (tablename.equals(RoleDropboxTable.ROLES_DROPBOX_TABLE) ||
                tablename.equals(ClientDropboxTable.CLIENTS_DROPBOX_TABLE) ||
                tablename.equals(ExcavationProjectDropboxTable.PROJECTS_DROPBOX_TABLE) ||
                tablename.equals(TunnelDropboxTable.TUNNELS_DROPBOX_TABLE) ||
                tablename.equals(SupportRequirementDropboxTable.SUPPORT_REQUIREMENTS_DROPBOX_TABLE) ||
                tablename.equals(TunnelFaceDropboxTable.FACES_DROPBOX_TABLE) ||
                tablename.equals(UserDropboxTable.USERS_DROPBOX_TABLE)) {
            user = true;
        }
        return user;
    }
}
