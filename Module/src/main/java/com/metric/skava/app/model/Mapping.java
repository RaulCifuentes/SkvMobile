package com.metric.skava.app.model;

import android.net.Uri;

import com.metric.skava.app.data.IdentifiableEntity;
import com.metric.skava.app.util.PegNumberFormat;
import com.metric.skava.pictures.model.SkavaPicture;

import java.util.Calendar;
import java.util.List;

/**
 * Created by metricboy on 2/23/14.
 */
public class Mapping implements IdentifiableEntity {

    public static final int PICS_SENT_TO_DATASTORE = 50;
    public static final int PICS_SENT_TO_CLOUD = 200;

    private int picsSentStatus;

    private Long _id;

    private String environment;
    private String code;
    private String internalCode;
    private TunnelFace face;

    private User geologist;
    private ExcavationSection section;
    private Calendar date;
    private Double initialPeg;
    private Double finalPeg;
    private Double currentAdvance;
    private Double referenceChainage;
    private Double accummAdvance;

    private ExcavationMethod method;
    private Short orientation;
    private Double slope;

    private String outcropDescription;
    private List<SkavaPicture> pictureList;
    private Uri mTunnelExpandedViews;

    public Mapping(String code) {
        this.code = code;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getPicsSentStatus() {
        return picsSentStatus;
    }

    public void setPicsSentStatus(int picsSentStatus) {
        this.picsSentStatus = picsSentStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public ExcavationProject getProject() {
        TunnelFace face = getFace();
        if (face != null) {
            Tunnel tunnel = face.getTunnel();
            if (tunnel != null) {
                ExcavationProject project = tunnel.getProject();
                return project;
            }
        }
        return null;
    }

    public Tunnel getTunnel() {
        TunnelFace face = getFace();
        if (face != null) {
            return face.getTunnel();
        }
        return null;
    }


    public Uri getTunnelExpandedView() {
        return mTunnelExpandedViews;
    }

    public void setTunnelExpandedView(Uri tunnelExpandedViews) {
        this.mTunnelExpandedViews = tunnelExpandedViews;
    }

    public TunnelFace getFace() {
        return face;
    }

    public void setFace(TunnelFace face) {
        this.face = face;
    }

    public Calendar getDateTime() {
        return date;
    }

    public void setDateTime(Calendar date) {
        this.date = date;
    }

    public User getGeologist() {
        return geologist;
    }

    public void setGeologist(User geologist) {
        this.geologist = geologist;
    }

    public ExcavationMethod getMethod() {
        return method;
    }

    public void setMethod(ExcavationMethod method) {
        this.method = method;
    }

    public List<SkavaPicture> getPicturesList() {
        return pictureList;
    }

    public void setPicturesList(List<SkavaPicture> pictureUriList) {
        this.pictureList = pictureUriList;
    }

    public ExcavationSection getSection() {
        return section;
    }

    public void setSection(ExcavationSection section) {
        this.section = section;
    }

    public Double getInitialPeg() {
        return initialPeg;
    }

    public void setInitialPeg(Double pk) {
        this.initialPeg = pk;
    }

    public Double getFinalPeg() {
        return finalPeg;
    }

    public void setFinalPeg(Double finalPeg) {
        this.finalPeg = finalPeg;
    }

    public Double getReferenceChainage() {
        return referenceChainage;
    }

    public void setReferenceChainage(Double referenceChainage) {
        this.referenceChainage = referenceChainage;
    }

    public Double getCurrentAdvance() {
        return currentAdvance;
    }

    public void setCurrentAdvance(Double currentAdvance) {
        this.currentAdvance = currentAdvance;
    }

    public Double getAccummAdvance() {
        return accummAdvance;
    }

    public void setAccummAdvance(Double accummAdvance) {
        this.accummAdvance = accummAdvance;
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

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }


    @Override
    public String toString() {
        return "Mapping {" +
                "internalCode = " + internalCode +
                ", project = " + getProject() +
                ", tunnel = " + getTunnel() +
                ", face = " + face +
                '}';
    }


    public String getPseudoCode() {
        String pseudoCode = null;
        if (getTunnel() != null && getFace() != null){
            pseudoCode = getTunnel().getName()+ "-" + getFace().getName() ;
        }
        if (getFinalPeg() != null) {
            PegNumberFormat numberFormatter = new PegNumberFormat();
            String finalPegAsString = numberFormatter.format(finalPeg);
            pseudoCode = pseudoCode + "-" + finalPegAsString;
        }
        return pseudoCode;
    }
}
