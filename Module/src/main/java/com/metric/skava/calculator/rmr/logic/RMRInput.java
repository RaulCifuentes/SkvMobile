package com.metric.skava.calculator.rmr.logic;

/**
 * Created by metricboy on 3/9/14.
 */
public class RMRInput {

    private Double strength;
    private Double rqd;
    private Double spacing;
    private Double condition;
    private Double persistence;
    private Double aperture;
    private Double roughness;
    private Double infilling;
    private Double weathering;
    private Double groundwater;
    private Double orientation;

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    public Double getRqd() {
        return rqd;
    }

    public void setRqd(Double rqd) {
        this.rqd = rqd;
    }

    public Double getSpacing() {
        return spacing;
    }

    public void setSpacing(Double spacing) {
        this.spacing = spacing;
    }

    public Double getCondition() {
        return condition;
    }

    public void setCondition(Double condition) {
        this.condition = condition;
    }

    public Double getPersistence() {
        return persistence;
    }

    public void setPersistence(Double persistence) {
        this.persistence = persistence;
    }

    public Double getAperture() {
        return aperture;
    }

    public void setAperture(Double aperture) {
        this.aperture = aperture;
    }

    public Double getRoughness() {
        return roughness;
    }

    public void setRoughness(Double roughness) {
        this.roughness = roughness;
    }

    public Double getInfilling() {
        return infilling;
    }

    public void setInfilling(Double infilling) {
        this.infilling = infilling;
    }

    public Double getWeathering() {
        return weathering;
    }

    public void setWeathering(Double weathering) {
        this.weathering = weathering;
    }

    public Double getGroundwater() {
        return groundwater;
    }

    public void setGroundwater(Double groundwater) {
        this.groundwater = groundwater;
    }

    public Double getOrientation() {
        return orientation;
    }

    public void setOrientation(Double orientation) {
        this.orientation = orientation;
    }

    public boolean isComplete(){
        boolean partial = (rqd != null) && (strength != null) && (orientation != null) && (spacing != null) && (groundwater != null);
        if (condition != null) {
            return partial;
        } else {
            boolean detailed = (persistence != null) && (aperture != null) && (roughness != null) && (infilling!= null) && (weathering!= null);
            return partial && detailed;
        }


    }
}

