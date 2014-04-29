package com.metric.skava.instructions.model;

/**
 * Created by metricboy on 3/4/14.
 */
public class SupportRecomendation {

    private Long _id;
    private BoltType boltType;
    private Double boltDiameter;
    private Double boltLength;
    private ShotcreteType shotcreteType;
    private Double thickness;
    private MeshType meshType;
    private Coverage coverage;
    private ArchType archType;
    private Double separation;
    private String observations;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
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

    public ShotcreteType getShotcreteType() {
        return shotcreteType;
    }

    public void setShotcreteType(ShotcreteType shotcreteType) {
        this.shotcreteType = shotcreteType;
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

    public Coverage getCoverage() {
        return coverage;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
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
}
