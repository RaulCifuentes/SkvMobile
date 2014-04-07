package com.metric.skava.calculator.rmr.helper;


import com.metric.skava.rockmass.model.RockMass;

/**
 * Created by metricboy on 3/10/14.
 */
public class RmrToRockMassMapper {

    private static RmrToRockMassMapper mInstance;


    public static RmrToRockMassMapper getInstance() {
        if (mInstance == null) {
            mInstance = new RmrToRockMassMapper();
        }
        return mInstance;
    }

    public RockMass.RockMassQualityType mapRMRToRockMassQuality(Double rmrValue) {
        if ((rmrValue > 80)) {
            return RockMass.RockMassQualityType.VERY_GOOD;
        }
        if ((61 <= rmrValue) && (rmrValue <= 80)) {
            return RockMass.RockMassQualityType.GOOD;
        }
        if ((41 <= rmrValue) && (rmrValue <= 60)) {
            return RockMass.RockMassQualityType.FAIR;
        }
        if ((21 <= rmrValue) && (rmrValue <= 40)) {
            return RockMass.RockMassQualityType.POOR;
        }
        if ((rmrValue < 21)) {
            return RockMass.RockMassQualityType.VERY_POOR;
        }
        return null;
    }

    public RockMass.RockMassClass mapRMRToRockMassClass(Double rmrValue) {
        if ((rmrValue > 80)) {
            return RockMass.RockMassClass.I;
        }
        if ((61 <= rmrValue) && (rmrValue <= 80)) {
            return RockMass.RockMassClass.II;
        }
        if ((41 <= rmrValue) && (rmrValue <= 60)) {
            return RockMass.RockMassClass.III;
        }
        if ((21 <= rmrValue) && (rmrValue <= 40)) {
            return RockMass.RockMassClass.IV;
        }
        if ((rmrValue < 21)) {
            return RockMass.RockMassClass.V;
        }
        return null;
    }

}
