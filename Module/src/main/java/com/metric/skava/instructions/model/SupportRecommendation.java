package com.metric.skava.instructions.model;

import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.rocksupport.model.SupportRequirement;

/**
 * Created by metricboy on 3/4/14.
 */
public class SupportRecommendation {

    private Long _id;
    private SupportRequirement requirementBase;
    private BoltType boltType;
    private Double boltDiameter;
    private Double boltLength;
    private SupportPattern roofPattern;
    private SupportPattern wallPattern;
    private ShotcreteType shotcreteType;
    private Coverage shotcreteCoverage;
    private Double thickness;
    private MeshType meshType;
    private Coverage meshCoverage;
    private ArchType archType;
    private Double separation;
    private String observations;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public SupportRequirement getRequirementBase() {
        return requirementBase;
    }

    public void setRequirement(SupportRequirement requirementBase) {
        this.requirementBase = requirementBase;
    }

    public BoltType getBoltType() {
        return boltType;
    }

    public void setBoltType(BoltType boltType) {
        this.boltType = boltType;
    }

    public Double getBoltDiameter() {
        return boltDiameter;
    }

    public void setBoltDiameter(Double boltDiameter) {
        this.boltDiameter = boltDiameter;
    }

    public Double getBoltLength() {
        return boltLength;
    }

    public void setBoltLength(Double boltLength) {
        this.boltLength = boltLength;
    }

    public SupportPattern getRoofPattern() {
        return roofPattern;
    }

    public void setRoofPattern(SupportPattern roofPattern) {
        this.roofPattern = roofPattern;
    }

    public SupportPattern getWallPattern() {
        return wallPattern;
    }

    public void setWallPattern(SupportPattern wallPattern) {
        this.wallPattern = wallPattern;
    }

    public ShotcreteType getShotcreteType() {
        return shotcreteType;
    }

    public void setShotcreteType(ShotcreteType shotcreteType) {
        this.shotcreteType = shotcreteType;
    }

    public Coverage getShotcreteCoverage() {
        return shotcreteCoverage;
    }

    public void setShotcreteCoverage(Coverage shotcreteCoverage) {
        this.shotcreteCoverage = shotcreteCoverage;
    }

    public Double getThickness() {
        return thickness;
    }

    public void setThickness(Double thickness) {
        this.thickness = thickness;
    }

    public MeshType getMeshType() {
        return meshType;
    }

    public void setMeshType(MeshType meshType) {
        this.meshType = meshType;
    }

    public Coverage getMeshCoverage() {
        return meshCoverage;
    }

    public void setMeshCoverage(Coverage meshCoverage) {
        this.meshCoverage = meshCoverage;
    }

    public ArchType getArchType() {
        return archType;
    }

    public void setArchType(ArchType archType) {
        this.archType = archType;
    }

    public Double getSeparation() {
        return separation;
    }

    public void setSeparation(Double separation) {
        this.separation = separation;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public boolean isComplete() {
        return getArchType() != null &&
                getBoltDiameter() != null &&
                getBoltLength() != null &&
                getBoltType() != null &&
                getMeshCoverage() != null &&
                getMeshType() != null &&
                getObservations() != null &&
                getRoofPattern() != null &&
                getSeparation() != null &&
                getShotcreteType() != null &&
                getShotcreteCoverage() != null &&
                getThickness() != null &&
                getWallPattern() != null;

    }

    public boolean hasSelectedAnything() {
        return SkavaUtils.isDefined(getArchType()) ||
                (getBoltDiameter() != null) ||
                (getBoltLength() != null) ||
                SkavaUtils.isDefined(getBoltType()) ||
                SkavaUtils.isDefined(getMeshCoverage()) ||
                SkavaUtils.isDefined(getMeshType()) ||
                (getObservations() != null) ||
                (getRoofPattern() != null) ||
                (getSeparation() != null) ||
                SkavaUtils.isDefined(getShotcreteType()) ||
                SkavaUtils.isDefined(getShotcreteCoverage()) ||
                (getThickness() != null) ||
                (getWallPattern() != null);
    }
}
