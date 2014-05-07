package com.metric.skava.rocksupport.model;

/**
 * Created by metricboy on 3/9/14.
 */
public class ExcavationFactors {

    private ESR esr;
    private Double span;

    public ExcavationFactors(ESR esr, Double span) {
        this.esr = esr;
        this.span = span;
    }


    public Double getSpan() {
        return span;
    }

    public void setSpan(Double span) {
        this.span = span;
    }

    public ESR getEsr() {
        return esr;
    }

    public void setEsr(ESR esr) {
        this.esr = esr;
    }
}
