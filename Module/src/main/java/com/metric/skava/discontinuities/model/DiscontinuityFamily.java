package com.metric.skava.discontinuities.model;

import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.Weathering;

/**
 * Created by metricboy on 3/1/14.
 */
public class DiscontinuityFamily {

    private Long _id;
    private int number;
    private DiscontinuityType type;
    private DiscontinuityRelevance relevance;
    private Short dipDirDegrees;
    private Short dipDegrees;
    private DiscontinuityShape shape;

    private Spacing spacing;

    private Roughness roughness;
    private Weathering weathering;
    private DiscontinuityWater water;
    private Persistence persistence;
    private Aperture aperture;
    private Infilling infilling;

    private Ja ja;

    private Jr jr;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public DiscontinuityType getType() {
        return type;
    }

    public void setType(DiscontinuityType type) {
        this.type = type;
    }

    public DiscontinuityRelevance getRelevance() {
        return relevance;
    }

    public void setRelevance(DiscontinuityRelevance relevance) {
        this.relevance = relevance;
    }

    public Short getDipDirDegrees() {
        return dipDirDegrees;
    }

    public void setDipDirDegrees(Short dipDirDegrees) {
        this.dipDirDegrees = dipDirDegrees;
    }

    public Short getDipDegrees() {
        return dipDegrees;
    }

    public void setDipDegrees(Short dipDegrees) {
        this.dipDegrees = dipDegrees;
    }

    public DiscontinuityShape getShape() {
        return shape;
    }

    public void setShape(DiscontinuityShape shape) {
        this.shape = shape;
    }

    public Spacing getSpacing() {
        return spacing;
    }

    public void setSpacing(Spacing spacing) {
        this.spacing = spacing;
    }

    public Roughness getRoughness() {
        return roughness;
    }

    public void setRoughness(Roughness roughness) {
        this.roughness = roughness;
    }

    public Persistence getPersistence() {
        return persistence;
    }

    public void setPersistence(Persistence persistence) {
        this.persistence = persistence;
    }

    public Infilling getInfilling() {
        return infilling;
    }

    public void setInfilling(Infilling infilling) {
        this.infilling = infilling;
    }

    public Ja getJa() {
        return ja;
    }

    public void setJa(Ja ja) {
        this.ja = ja;
    }

    public Jr getJr() {
        return jr;
    }

    public void setJr(Jr jr) {
        this.jr = jr;
    }

    public Weathering getWeathering() {
        return weathering;
    }

    public void setWeathering(Weathering weathering) {
        this.weathering = weathering;
    }

    public DiscontinuityWater getWater() {
        return water;
    }

    public void setWater(DiscontinuityWater water) {
        this.water = water;
    }

    public Aperture getAperture() {
        return aperture;
    }

    public void setAperture(Aperture aperture) {
        this.aperture = aperture;
    }
    
    public boolean isComplete() {
        return
            this.getType() != null &&
            this.getRelevance() != null &&
            this.getDipDegrees() != null &&
            this.getDipDirDegrees() != null &&
            this.getShape() != null &&
            this.getSpacing() != null &&
            this.getRoughness() != null &&
            this.getWeathering() != null &&
            this.getWater().getCode() != null &&
            this.getPersistence() != null &&
            this.getAperture() != null &&
            this.getInfilling() != null &&
            this.getJa() != null &&
            this.getJr() != null;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
