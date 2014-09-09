package com.metric.skava.app.model;

import android.net.Uri;

import com.metric.skava.app.data.IdentifiableEntity;
import com.metric.skava.app.util.PegNumberFormat;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.instructions.model.SupportRecommendation;
import com.metric.skava.pictures.model.SkavaPicture;
import com.metric.skava.rockmass.model.FractureType;

import java.util.Calendar;
import java.util.List;

/**
 * Created by metricboy on 2/23/14.
 */
public class Assessment implements IdentifiableEntity {

    private Long _id;

    public enum SavingStatus {NOT_SAVED, DRAFT, PERSISTENT};
    public enum SendingStatus {NOT_SENT, SENT_TO_DATASTORE, SENT_TO_CLOUD};
    public enum Originator {WEB, MOBILE};

    private SendingStatus dataSentStatus;
    private SendingStatus picsSentStatus;

    private SavingStatus savedStatus;

    private String originatorDeviceID;

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

    private FractureType fractureType;
    private Double blockSize;
    private Short numberOfJoints;
    private String outcropDescription;
    private String rockSampleIdentification;

    private Originator source;

    private SupportRecommendation recomendation;
    private List<DiscontinuityFamily> discontinuitySystem;
    private List<SkavaPicture> pictureList;
    private Q_Calculation qCalculation;
    private RMR_Calculation rmrCalculation;

    private Uri mTunnelExpandedViews;

    public Assessment(String code) {
        this.code = code;
    }


    public String getOriginatorDeviceID() {
        return originatorDeviceID;
    }

    public void setOriginatorDeviceID(String originatorDeviceID) {
        this.originatorDeviceID = originatorDeviceID;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public SavingStatus getSavedStatus() {
        return savedStatus;
    }

    public void setSavedStatus(SavingStatus savedStatus) {
        this.savedStatus = savedStatus;
    }

    public SendingStatus getDataSentStatus() {
        return dataSentStatus;
    }

    public void setDataSentStatus(SendingStatus dataSentStatus) {
        this.dataSentStatus = dataSentStatus;
    }

    public SendingStatus getPicsSentStatus() {
        return picsSentStatus;
    }

    public void setPicsSentStatus(SendingStatus picsSentStatus) {
        this.picsSentStatus = picsSentStatus;
    }

    public java.lang.String getCode() {
        return code;
    }

    public void setCode(java.lang.String code) {
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

    public Q_Calculation getQCalculation() {
        return qCalculation;
    }

    public void setQCalculation(Q_Calculation qCalculation) {
        this.qCalculation = qCalculation;
    }

    public RMR_Calculation getRmrCalculation() {
        return rmrCalculation;
    }

    public void setRmrCalculation(RMR_Calculation rmrCalculation) {
        this.rmrCalculation = rmrCalculation;
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

    public FractureType getFractureType() {
        return fractureType;
    }

    public void setFractureType(FractureType fractureType) {
        this.fractureType = fractureType;
    }

    public Double getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(Double blockSize) {
        this.blockSize = blockSize;
    }

    public Short getNumberOfJoints() {
        return numberOfJoints;
    }

    public void setNumberOfJoints(Short numberOfJoints) {
        this.numberOfJoints = numberOfJoints;
    }

    public java.lang.String getOutcropDescription() {
        return outcropDescription;
    }

    public void setOutcropDescription(java.lang.String outcropDescription) {
        this.outcropDescription = outcropDescription;
    }

    public String getRockSampleIdentification() {
        return rockSampleIdentification;
    }

    public void setRockSampleIdentification(String rockSampleIdentification) {
        this.rockSampleIdentification = rockSampleIdentification;
    }

    public SupportRecommendation getRecomendation() {
        return recomendation;
    }

    public void setRecomendation(SupportRecommendation recomendation) {
        this.recomendation = recomendation;
    }

    public Originator getSource() {
        return source;
    }

    public void setSource(Originator source) {
        this.source = source;
    }

    public List<DiscontinuityFamily> getDiscontinuitySystem() {
        return discontinuitySystem;
    }

    public void setDiscontinuitySystem(List<DiscontinuityFamily> discontinuitySystem) {
        this.discontinuitySystem = discontinuitySystem;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }


    @Override
    public java.lang.String toString() {
        return "Assessment{" +
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
