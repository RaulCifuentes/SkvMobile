package com.metric.skava.instructions.logic;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalSupportRequirementDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportRecomendation;
import com.metric.skava.rocksupport.model.SupportRequirement;

/**
 * Created by metricboy on 3/13/14.
 */
public class RecomendationProvider {


    private final SkavaContext mSkavaContext;

    public RecomendationProvider(SkavaContext skavaContext) {
        this.mSkavaContext = skavaContext;
    }

    public SupportRecomendation recomend(Assessment assessment) throws DAOException {

        DAOFactory daoFactory = mSkavaContext.getDAOFactory();
        LocalSupportRequirementDAO supportRequirementDAO = daoFactory.getLocalSupportRequirementDAO();
        Tunnel tunnel = assessment.getTunnel();
        SupportRequirement supportRequirement;
        supportRequirement = supportRequirementDAO.getSupportRequirementByTunnel(tunnel, assessment.getQCalculation().getQResult().getQBarton());
        SupportRecomendation recomendation = new SupportRecomendation();
        if (supportRequirement != null) {
            BoltType boltType = supportRequirement.getBoltType();
            if (boltType != null) {
                recomendation.setBoltType(boltType);
            }
            Double boltDiameter = supportRequirement.getDiameter();
            if (boltDiameter != null) {
                recomendation.setBoltDiameter(boltDiameter);
            }
            Double boltLength = supportRequirement.getLength();
            if (boltLength != null) {
                recomendation.setBoltLength(boltLength);
            }
            SupportPattern roofPattern = supportRequirement.getRoofPattern();
            if (roofPattern != null) {
                recomendation.setRoofPattern(roofPattern);
            }
            SupportPattern wallPattern = supportRequirement.getWallPattern();
            if (wallPattern != null) {
                recomendation.setWallPattern(wallPattern);
            }
            ShotcreteType shotcreteType = supportRequirement.getShotcreteType();
            if (shotcreteType != null) {
                recomendation.setShotcreteType(shotcreteType);
            }
            Double thickness = supportRequirement.getThickness();
            if (thickness != null) {
                recomendation.setThickness(thickness);
            }
            MeshType meshType = supportRequirement.getMeshType();
            if (meshType != null) {
                recomendation.setMeshType(meshType);
            }
            Coverage coverage = supportRequirement.getCoverage();
            if (coverage != null) {
                recomendation.setCoverage(coverage);
            }
            ArchType archType = supportRequirement.getArchType();
            if (meshType != null) {
                recomendation.setArchType(archType);
            }
            Double separation = supportRequirement.getSeparation();
            if (thickness != null) {
                recomendation.setSeparation(separation);
            }
        }
        return recomendation;
    }

}
