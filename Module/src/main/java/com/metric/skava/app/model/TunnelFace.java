package com.metric.skava.app.model;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 2/16/14.
 */
public class TunnelFace extends SkavaEntity {

    private Tunnel tunnel;
    private Short orientation;
    private Double slope;
    private Double referencePK;


    public TunnelFace(Tunnel tunnel, String code, String name, Short orientation, Double slope) {
        super(code, name);
        this.tunnel = tunnel;
        this.orientation = orientation;
        this.slope = slope;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public void setTunnel(Tunnel tunnel) {
        this.tunnel = tunnel;
    }

    public Short getOrientation() {
        return orientation;
    }

    public void setOrientation(Short orientation) {
        this.orientation = orientation;
    }

    public Double getSlope() {
        return slope;
    }

    public void setSlope(Double slope) {
        this.slope = slope;
    }

    public Double getReferencePK() {
        return referencePK;
    }

    public void setReferencePK(Double referencePK) {
        this.referencePK = referencePK;
    }
}
