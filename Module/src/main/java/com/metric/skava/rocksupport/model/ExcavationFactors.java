package com.metric.skava.rocksupport.model;

/**
 * Created by metricboy on 3/9/14.
 */
public class ExcavationFactors {

    private ESR esr;
    private Float span;

    public ExcavationFactors(ESR esr) {
        this.esr = esr;
    }


    public Float getSpan() {
        return span;
    }

    public void setSpan(Float span) {
        this.span = span;
    }

    public ESR getEsr() {
        return esr;
    }

    public void setEsr(ESR esr) {
        this.esr = esr;
    }
}
