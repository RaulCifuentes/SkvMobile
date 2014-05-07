package com.metric.skava.calculator.barton.model;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 5/5/14.
 */
public class RockQuality extends SkavaEntity {

    public enum AccordingTo { RMR, Q}

    private Double lowerBoundary, higherBoundary;
    private AccordingTo accordingTo;
    private String classification;

    public RockQuality(AccordingTo accordingTo, String code, String name, Double lowerBoundary, Double higherBoundary) {
        super(code, name);
        this.accordingTo = accordingTo;
        this.lowerBoundary = lowerBoundary;
        this.higherBoundary = higherBoundary;
    }

    public Double getLowerBoundary() {
        return lowerBoundary;
    }

    public void setLowerBoundary(Double lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
    }

    public Double getHigherBoundary() {
        return higherBoundary;
    }

    public void setHigherBoundary(Double higherBoundary) {
        this.higherBoundary = higherBoundary;
    }

    public AccordingTo getAccordingTo() {
        return accordingTo;
    }

    public void setAccordingTo(AccordingTo accordingTo) {
        this.accordingTo = accordingTo;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
