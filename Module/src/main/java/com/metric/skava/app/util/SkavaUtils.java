package com.metric.skava.app.util;

import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.data.IdentifiableEntity;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.calculator.barton.helper.RQDMapper;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.barton.model.RQD;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;

import java.util.ArrayList;
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

    public static Assessment createInitialAssessment(SkavaContext skavaContext) throws DAOException {

        String code = UUID.randomUUID().toString();
        Assessment initialAssessment = new Assessment(code);

        DAOFactory daoFactory = skavaContext.getDAOFactory();

        Date date = SkavaUtils.getCurrentDate();
        initialAssessment.setDate(date);

        //Q Barton (Deafault values for each one of the components of thr Q process)

        RQD rqd = RQDMapper.getInstance().mapJvToRQD(4);

        List<SRF> listSRF = daoFactory.getLocalSrfDAO().getAllSrfs(SRF.Group.a);
        SRF sRF = listSRF.get(0);

        List<Jw> listJw = daoFactory.getLocalJwDAO().getAllJws();
        Jw jw = listJw.get(0);

        List<Ja> listJa = daoFactory.getLocalJaDAO().getAllJas(Ja.Group.a);
        Ja ja = listJa.get(0);

        List<Jn> listJn = daoFactory.getLocalJnDAO().getAllJns();
        Jn jn = listJn.get(0);

        List<Jr> listJr = daoFactory.getLocalJrDAO().getAllJrs(Jr.Group.a);
        Jr jr =  listJr.get(0);

        Q_Calculation mQCalculation = new Q_Calculation(rqd, jn, jr, ja, jw, sRF);

        initialAssessment.setQCalculation(mQCalculation);

        StrengthOfRock strenght = daoFactory.getLocalStrengthDAO().getAllStrengths(StrengthOfRock.Group.POINT_LOAD_KEY).get(0);

        Spacing spacing =  daoFactory.getLocalSpacingDAO().getAllSpacings().get(0);

        Persistence persistence = daoFactory.getLocalPersistenceDAO().getAllPersistences().get(0);

        Aperture aperture = daoFactory.getLocalApertureDAO().getAllApertures().get(0);

        Roughness roughness = daoFactory.getLocalRoughnessDAO().getAllRoughnesses().get(0);

        Infilling infilling = daoFactory.getLocalInfillingDAO().getAllInfillings().get(0);

        Weathering weathering = daoFactory.getLocalWeatheringDAO().getAllWeatherings().get(0);

        Groundwater groundwater = daoFactory.getLocalGroundwaterDAO().getAllGroundwaters(Groundwater.Group.INFLOW_LENGHT).get(0);

        OrientationDiscontinuities orientation = daoFactory.getLocalOrientationDiscontinuitiesDAO().getAllOrientationDiscontinuities(OrientationDiscontinuities.Group.TUNNELS_MINES).get(0);

        RMR_Calculation mRMRCalculation = new RMR_Calculation(strenght, null, spacing, persistence, aperture, roughness, infilling, weathering, groundwater, orientation);

        initialAssessment.setRmrCalculation(mRMRCalculation);

        //Default 4 picture placeholders
        ArrayList<Uri> pictureUriList = new ArrayList<Uri>(5);
        pictureUriList.add(null);
        pictureUriList.add(null);
        pictureUriList.add(null);
        pictureUriList.add(null);

        initialAssessment.setPictureUriList(pictureUriList);

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

    public static boolean hasPictures(List<Uri> pictureList) {
        for (Uri uri : pictureList) {
            if (uri != null) {
                return true;
            };
        }
        return false;
    }
}
