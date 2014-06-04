package com.metric.skava.rocksupport.model;

import com.metric.skava.app.data.SkavaEntity;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;

/**
 * Created by metricboy on 3/13/14.
 */
public class SupportRequirement extends SkavaEntity {

    private Tunnel tunnel;
    private Double qBartonLowerBoundary;
    private Double qBartonUpperBoundary;
    private BoltType boltType;
    private Double diameter;
    private Double length;
    private SupportPattern roofPattern;
    private SupportPattern wallPattern;
    private ShotcreteType shotcreteType;
    private Coverage shotcreteCoverage;
    private Double thickness;
    private MeshType meshType;
    private Coverage meshCoverage;
    private ArchType archType;
    private Double separation;


    public SupportRequirement(Tunnel tunnel, String code, String name) {
        super(code, name);
        this.tunnel = tunnel;
    }

    public void setTunnel(Tunnel tunnel) {
        this.tunnel = tunnel;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public Double getqBartonLowerBoundary() {
        return qBartonLowerBoundary;
    }

    public void setqBartonLowerBoundary(Double qBartonLowerBoundary) {
        this.qBartonLowerBoundary = qBartonLowerBoundary;
    }

    public Double getqBartonUpperBoundary() {
        return qBartonUpperBoundary;
    }

    public void setqBartonUpperBoundary(Double qBartonUpperBoundary) {
        this.qBartonUpperBoundary = qBartonUpperBoundary;
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
}
