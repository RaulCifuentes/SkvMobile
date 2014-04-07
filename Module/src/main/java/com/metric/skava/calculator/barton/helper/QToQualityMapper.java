package com.metric.skava.calculator.barton.helper;

import com.metric.skava.rockmass.model.RockMass;

/**
 * Created by metricboy on 3/9/14.
 */
public class QToQualityMapper {

    private static QToQualityMapper mInstance;

    private QToQualityMapper(){
    }

    public static QToQualityMapper getInstance () {
        if (mInstance == null){
            mInstance = new QToQualityMapper();
        }
        return mInstance;
    }

    public RockMass.RockMassQualityType mapQToRockMassQuality(Double qBarton){
        if ((qBarton > 400)) {
            return RockMass.RockMassQualityType.EXCEPTIONALLY_GOOD;
        }
        if ((100 <= qBarton ) && (qBarton <= 400)) {
            return RockMass.RockMassQualityType.EXTREMELY_GOOD;
        }
        if ((40 <= qBarton ) && (qBarton <= 100)) {
            return RockMass.RockMassQualityType.VERY_GOOD;
        }
        if ((10 <= qBarton ) && (qBarton <= 40)) {
            return RockMass.RockMassQualityType.GOOD;
        }
        if ((4 <= qBarton ) && (qBarton <= 10)) {
            return RockMass.RockMassQualityType.FAIR;
        }
        if ((1 <= qBarton ) && (qBarton <= 4)) {
            return RockMass.RockMassQualityType.POOR;
        }
        if ((0.4 <= qBarton ) && (qBarton <= 1)) {
            return RockMass.RockMassQualityType.VERY_POOR;
        }
        if ((0.01 <= qBarton ) && (qBarton <= 0.04)) {
            return RockMass.RockMassQualityType.EXTREMELY_POOR;
        }
        if ((0.001 <= qBarton ) && (qBarton <= 0.01)) {
            return RockMass.RockMassQualityType.EXCEPTIONALLY_POOR;
        }
        return null;
    }

}
