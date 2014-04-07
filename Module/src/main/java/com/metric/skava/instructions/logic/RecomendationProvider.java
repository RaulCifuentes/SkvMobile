package com.metric.skava.instructions.logic;

import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.Recomendation;
import com.metric.skava.instructions.model.ShotcreteType;

/**
 * Created by metricboy on 3/13/14.
 */
public class RecomendationProvider {

    //TODO Read the Support Requirement configuration and build a Recomendation


    public Recomendation recomend() {
        Recomendation recomendation = new Recomendation();
        BoltType boltType = null;
        recomendation.setBoltType(boltType);
        Double diameter = null;
        recomendation.setBoltDiameter(diameter);
        Double length = null;
        recomendation.setBoltLength(length);
        ShotcreteType shotcreteType = null;
        recomendation.setShotcreteType(shotcreteType);
        Double thickness = null;
        recomendation.setThickness(thickness);
        MeshType meshType = null;
        recomendation.setMeshType(meshType);
        Coverage coverage = null;
        recomendation.setCoverage(coverage);
        ArchType archType = null;
        recomendation.setArchType(archType);
        Double separation = null;
        recomendation.setSeparation(separation);
        return recomendation;
    }

}
