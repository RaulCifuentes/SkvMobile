package com.metric.skava.calculator.barton.helper;


import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.exception.SkavaSystemException;
import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/9/14.
 */
public class QToQualityMapper {

    private static QToQualityMapper mInstance;
    private final SkavaContext mSkavaContext;
    private DAOFactory daoFactory;

    private QToQualityMapper(SkavaContext skavaContext) {
        this.mSkavaContext = skavaContext;
        this.daoFactory = skavaContext.getDAOFactory();
    }

    public static QToQualityMapper getInstance (SkavaContext skavaContext) {
        if (mInstance == null){
            mInstance = new QToQualityMapper(skavaContext);
        }
        return mInstance;
    }

    public RockQuality mapQToRockMassQuality(Double qBarton) {
        try {
            List<RockQuality> qualities = daoFactory.getLocalRockQualityDAO().getAllRockQualities(RockQuality.AccordingTo.Q);
            for (RockQuality currQuality : qualities) {
                if (currQuality.getLowerBoundary() < qBarton && qBarton <currQuality.getHigherBoundary() ) {
                    return currQuality;
                }
            }
        } catch (DAOException e) {
            throw new SkavaSystemException("It was not possible to find the rock quality for Q value " + qBarton, e);
        }
        return null;
    }

}
