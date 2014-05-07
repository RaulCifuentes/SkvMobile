package com.metric.skava.instructions.model;

/**
  * Created by metricboy on 5/5/14.
  */
 public class SupportPattern {

    private SupportPatternType type;
    private String pattern;

    public SupportPattern(SupportPatternType type, String pattern) {
        this.type = type;
        this.pattern = pattern;
    }

    public SupportPatternType getType() {
        return type;
    }

    public void setType(SupportPatternType type) {
        this.type = type;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
