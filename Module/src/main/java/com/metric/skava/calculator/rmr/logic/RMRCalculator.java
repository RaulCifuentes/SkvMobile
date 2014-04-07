package com.metric.skava.calculator.rmr.logic;

/**
 * Created by metricboy on 3/9/14.
 */
public class RMRCalculator {

    public static RMROutput calculate(RMRInput input) {

        RMROutput result = new RMROutput();
        Double sum = 0d;
        if (input != null) {

            if (input.getStrength() != null) {
                sum += input.getStrength();
            }

            if (input.getRqd() != null) {
                sum += input.getRqd();
            }

            if (input.getSpacing() != null) {
                sum += input.getSpacing();
            }

            if (input.getCondition() != null) {
                sum += input.getCondition();
            } else {
                if (input.getPersistence() != null) {
                    sum += input.getPersistence();
                }
                if (input.getAperture() != null) {
                    sum += input.getAperture();
                }
                if (input.getRoughness() != null) {
                    sum += input.getRoughness();
                }
                if (input.getInfilling() != null) {
                    sum += input.getInfilling();
                }
                if (input.getWeathering() != null) {
                    sum += input.getWeathering();
                }
            }

            if (input.getGroundwater() != null) {
                sum += input.getGroundwater();
            }

            if (input.getOrientation() != null) {
                sum += input.getOrientation();
            }

            result.setRMR(sum);
        }
        return result;

    }
}
