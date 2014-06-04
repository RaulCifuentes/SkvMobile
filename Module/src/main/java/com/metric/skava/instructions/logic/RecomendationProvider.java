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
import com.metric.skava.instructions.model.SupportRecommendation;
import com.metric.skava.rocksupport.model.SupportRequirement;

/**
 * Created by metricboy on 3/13/14.
 */
public class RecomendationProvider {


    private final SkavaContext mSkavaContext;

    public RecomendationProvider(SkavaContext skavaContext) {
        this.mSkavaContext = skavaContext;
    }

    public SupportRecommendation recomend(Assessment assessment) throws DAOException {

        DAOFactory daoFactory = mSkavaContext.getDAOFactory();

        LocalSupportRequirementDAO supportRequirementDAO = daoFactory.getLocalSupportRequirementDAO();
        Tunnel tunnel = assessment.getTunnel();

        SupportRequirement supportRequirement;
        supportRequirement = supportRequirementDAO.findSupportRequirement(tunnel, assessment.getQCalculation().getQResult().getQBarton());

        SupportRecommendation recommendation = new SupportRecommendation();

        if (supportRequirement != null) {

            recommendation.setRequirement(supportRequirement);

            BoltType boltType = supportRequirement.getBoltType();
            if (boltType != null) {
                recommendation.setBoltType(boltType);
            }
            Double boltDiameter = supportRequirement.getDiameter();
            if (boltDiameter != null) {
                recommendation.setBoltDiameter(boltDiameter);
            }
            Double boltLength = supportRequirement.getLength();
            if (boltLength != null) {
                recommendation.setBoltLength(boltLength);
            }
            SupportPattern roofPattern = supportRequirement.getRoofPattern();
            if (roofPattern != null) {
                recommendation.setRoofPattern(roofPattern);
            }
            SupportPattern wallPattern = supportRequirement.getWallPattern();
            if (wallPattern != null) {
                recommendation.setWallPattern(wallPattern);
            }
            ShotcreteType shotcreteType = supportRequirement.getShotcreteType();
            if (shotcreteType != null) {
                recommendation.setShotcreteType(shotcreteType);
            }
            Double thickness = supportRequirement.getThickness();
            if (thickness != null) {
                recommendation.setThickness(thickness);
            }
            MeshType meshType = supportRequirement.getMeshType();
            if (meshType != null) {
                recommendation.setMeshType(meshType);
            }
            Coverage coverage = supportRequirement.getCoverage();
            if (coverage != null) {
                recommendation.setMeshCoverage(coverage);
            }
            ArchType archType = supportRequirement.getArchType();
            if (meshType != null) {
                recommendation.setArchType(archType);
            }
            Double separation = supportRequirement.getSeparation();
            if (thickness != null) {
                recommendation.setSeparation(separation);
            }
        }
        return recommendation;
    }

}
