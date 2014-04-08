package com.metric.skava.rocksupport.model;

import com.metric.skava.app.data.SkavaEntity;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.rockmass.model.RockMass;

/**
 * Created by metricboy on 3/13/14.
 */
public class SupportRequirement extends SkavaEntity {


    private Tunnel tunnel;
    private Double spanOverESRLowerBoundary;
    private Double spanOverESRUpperBoundary;
    private RockMass.RockMassQualityType rockMassQualityType;
    private BoltType boltType;
    private Double diameter;
    private Double length;
    private ShotcreteType shotcreteType;
    private Double thickness;
    private MeshType meshType;
    private Coverage coverage;
    private ArchType archType;
    private Double separation;


    public SupportRequirement(String code, String name) {
        super(code, name);
    }

    public ExcavationProject getProject() {
        return getTunnel().getProject();
    }

    public void setTunnel(Tunnel tunnel) {
        this.tunnel = tunnel;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }


    public Double getSpanOverESRLower() {
        return spanOverESRLowerBoundary;
    }

    public void setSpanOverESRLower(Double spanOverESR) {
        this.spanOverESRLowerBoundary = spanOverESR;
    }

    public Double getSpanOverESRUpper() {
        return spanOverESRUpperBoundary;
    }

    public void setSpanOverESRUpper(Double spanOverESR) {
        this.spanOverESRUpperBoundary = spanOverESR;
    }

    public RockMass.RockMassQualityType getRockMassQualityType() {
        return rockMassQualityType;
    }

    public void setRockMassQualityType(RockMass.RockMassQualityType rockMassQualityType) {
        this.rockMassQualityType = rockMassQualityType;
    }

    public BoltType getBoltType() {
        return boltType;
    }

    public void setBoltType(BoltType boltType) {
        this.boltType = boltType;
    }

    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
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
}
