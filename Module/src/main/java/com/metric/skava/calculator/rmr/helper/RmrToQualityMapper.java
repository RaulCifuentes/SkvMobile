package com.metric.skava.calculator.rmr.helper;


import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/10/14.
 */
public class RmrToQualityMapper {

    private static RmrToQualityMapper mInstance;
    private final SkavaContext mSkavaContext;
    private DAOFactory daoFactory;

    private RmrToQualityMapper(SkavaContext skavaContext) {
        this.mSkavaContext = skavaContext;
        this.daoFactory = skavaContext.getDAOFactory();
    }

    public static RmrToQualityMapper getInstance (SkavaContext skavaContext) {
        if (mInstance == null){
            mInstance = new RmrToQualityMapper(skavaContext);
        }
        return mInstance;
    }

    public RockQuality mapRMRToRockMassQuality(Double rmr) {
        try {
            List<RockQuality> qualities = daoFactory.getLocalRockQualityDAO().getAllRockQualities(RockQuality.AccordingTo.RMR);
            for (RockQuality currQuality : qualities) {
                if (currQuality.getLowerBoundary() <= rmr && rmr <currQuality.getHigherBoundary() ) {
                    return currQuality;
                }
            }
            if (rmr == 100) {
                return qualities.get(qualities.size() - 1);
            }
        } catch (DAOException e) {
            throw new SkavaSystemException("It was not possible to find the rock quality for RMR value " + rmr, e);
        }
        return null;
    }

}
