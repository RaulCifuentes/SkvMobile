package com.metric.skava.instructions.model;

/**
  * Created by metricboy on 5/5/14.
  */
 public class SupportPattern {

    private SupportPatternType type;
    private Double distanceX, distanceY;

    public SupportPattern(SupportPatternType type, Double dx, Double dy) {
        this.type = type;
        this.distanceX = dx;
        this.distanceY = dy;
    }

    public SupportPatternType getType() {
        return type;
    }

    public void setType(SupportPatternType type) {
        this.type = type;
    }

    public Double getDistanceX() {
        return distanceX;
    }

    public void setDistanceX(Double distanceX) {
        this.distanceX = distanceX;
    }

    public Double getDistanceY() {
        return distanceY;
    }

    public void setDistanceY(Double distanceY) {
        this.distanceY = distanceY;
    }
}
