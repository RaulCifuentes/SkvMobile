package com.metric.skava.app.util;

import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;

import com.metric.skava.app.context.SkavaContext;
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
import com.metric.skava.instructions.model.SupportRecomendation;

import java.util.ArrayList;
import java.util.Date;
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

    public static Assessment createInitialAssessment(SkavaContext skavaContext) throws DAOException {

        int assesmentNumber = SkavaUtils.getRandom(1, 10);

        String internalCode = "SKV-" + assesmentNumber;

        String code = UUID.randomUUID().toString();
        Assessment initialAssessment = new Assessment(code);
        initialAssessment.setInternalCode(internalCode);

        DAOFactory daoFactory = skavaContext.getDAOFactory();


        Date date = new Date();
        initialAssessment.setDate(date);

        //Q Barton (Deafault values for each one of the components of thr Q process)

        RQD rqd = RQDMapper.getInstance().mapJvToRQD(4);

        SRF sRF =  daoFactory.getLocalSrfDAO().getAllSrfs(SRF.Group.a).get(0);
        Jw jw = daoFactory.getLocalJwDAO().getAllJws().get(0);
        Ja ja = daoFactory.getLocalJaDAO().getAllJas(Ja.Group.a).get(0);
        Jn jn = daoFactory.getLocalJnDAO().getAllJns().get(0);
        Jr jr = daoFactory.getLocalJrDAO().getAllJrs(Jr.Group.a).get(0);

        Q_Calculation mQCalculation = new Q_Calculation(rqd, jn, jr, ja, jw, sRF);

        initialAssessment.setQCalculation(mQCalculation);


        StrengthOfRock strenght = daoFactory.getLocalStrengthDAO().getAllStrengths(StrengthOfRock.Group.POINT_LOAD_KEY).get(0);

        Spacing spacing =  daoFactory.getLocalSpacingDAO().getAllSpacings().get(0);

        // ***** Condition was the summarized not needed anymore ****
        //ConditionDiscontinuities condition = provider.getAllConditions().get(0);

        Persistence persistence = daoFactory.getLocalPersistenceDAO().getAllPersistences().get(0);

        Aperture aperture = daoFactory.getLocalApertureDAO().getAllApertures().get(0);

        Roughness roughness = daoFactory.getLocalRoughnessDAO().getAllRoughnesses().get(0);

        Infilling infilling = daoFactory.getLocalInfillingDAO().getAllInfillings().get(0);

        Weathering weathering = daoFactory.getLocalWeatheringDAO().getAllWeatherings().get(0);

        Groundwater groundwater = daoFactory.getLocalGroundwaterDAO().getAllGroundwaters(Groundwater.Group.INFLOW_LENGHT).get(0);

        OrientationDiscontinuities orientation = daoFactory.getLocalOrientationDiscontinuitiesDAO().getAllOrientationDiscontinuities(OrientationDiscontinuities.Group.TUNNELS_MINES).get(0);

        RMR_Calculation mRMRCalculation = new RMR_Calculation(strenght, rqd, spacing, persistence, aperture, roughness, infilling, weathering, groundwater, orientation);

        initialAssessment.setRmrCalculation(mRMRCalculation);

        //Support Requirements
//        ESR esr = provider.getAllESR().get(0);
//        Float span = 100f;
//        ExcavationFactors mExcavationFactors = new ExcavationFactors(esr);
//        mExcavationFactors.setSpan(span);

//        initialAssessment.setExcavationFactors(mExcavationFactors);


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
            DiscontinuityFamily df = new DiscontinuityFamily();
            discontinuitySystem.add(df);
        }

        initialAssessment.setDiscontinuitySystem(discontinuitySystem);

        SupportRecomendation recomendation = new SupportRecomendation();

        initialAssessment.setRecomendation(recomendation);

        return initialAssessment;
    }

}
