package com.metric.skava.app.model;

import com.metric.skava.app.data.SkavaEntity;

/**
 * Created by metricboy on 2/16/14.
 */
public class TunnelFace extends SkavaEntity {

    private Tunnel tunnel;
    private Short orientation;
    private Short slope;

    public TunnelFace(String id, String name) {
        super(id, name);
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

    public Short getSlope() {
        return slope;
    }

    public void setSlope(Short slope) {
        this.slope = slope;
    }
}
