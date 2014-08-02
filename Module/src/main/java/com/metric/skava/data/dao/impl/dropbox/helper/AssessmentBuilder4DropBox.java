package com.metric.skava.data.dao.impl.dropbox.helper;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxList;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalApertureDAO;
import com.metric.skava.data.dao.LocalDiscontinuityRelevanceDAO;
import com.metric.skava.data.dao.LocalDiscontinuityShapeDAO;
import com.metric.skava.data.dao.LocalDiscontinuityTypeDAO;
import com.metric.skava.data.dao.LocalDiscontinuityWaterDAO;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.LocalFractureTypeDAO;
import com.metric.skava.data.dao.LocalInfillingDAO;
import com.metric.skava.data.dao.LocalJrDAO;
import com.metric.skava.data.dao.LocalPersistenceDAO;
import com.metric.skava.data.dao.LocalRoughnessDAO;
import com.metric.skava.data.dao.LocalSpacingDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.LocalWeatheringDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.DiscontinuitiesFamilyDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.QBartonCalculationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RMRCalculationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRecommendationDropboxTable;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;
import com.metric.skava.rockmass.model.FractureType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public class AssessmentBuilder4DropBox {

    private SkavaContext mSkavaContext;

    private LocalExcavationProjectDAO excavationProjectDAO;
    private LocalTunnelDAO localTunnelDAO;
    private LocalTunnelFaceDAO localTunnelFaceDAO;
    private LocalUserDAO localUserDAO;
    private LocalExcavationSectionDAO excavationSectionDAO;
    private LocalExcavationMethodDAO excavationMethodDAO;
    private LocalFractureTypeDAO fractureTypeDAO;
    private LocalDiscontinuityTypeDAO discontinuityTypeDAO;
    private LocalDiscontinuityRelevanceDAO discontinuityRelevanceDAO;
    private LocalDiscontinuityShapeDAO discontinuityShapeDAO;
    private LocalPersistenceDAO discontinuityPersistenceDAO;
    private LocalSpacingDAO discontinuitySpacingDAO;
    private LocalApertureDAO discontinuityApertureDAO;
    private LocalRoughnessDAO discontinuityRoughnessDAO;
    private LocalInfillingDAO discontinuityInfillingDAO;
    private LocalWeatheringDAO discontinuityWeatheringDAO;
    private LocalDiscontinuityWaterDAO discontinuityWaterDAO;
    private LocalJrDAO discontinuityJrDAO;

    private DiscontinuitiesFamilyDropboxTable mDiscontinuitiesFamilyDropBoxTable;
    private RMRCalculationDropboxTable mRMRCalculationDropBoxTable;
    private QBartonCalculationDropboxTable mQBartonCalculationDropBoxTable;
    private SupportRecommendationDropboxTable mSupportRecommendationDropboxTable;

    public AssessmentBuilder4DropBox(SkavaContext skavaContext) throws DAOException {
        this.mSkavaContext = skavaContext;
        DAOFactory daoFactory = skavaContext.getDAOFactory();
        excavationProjectDAO = daoFactory.getLocalExcavationProjectDAO();
        localTunnelFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        localTunnelDAO = daoFactory.getLocalTunnelDAO();
        localUserDAO = daoFactory.getLocalUserDAO();
        excavationSectionDAO = daoFactory.getLocalExcavationSectionDAO();
        excavationMethodDAO = daoFactory.getLocalExcavationMethodDAO();
        fractureTypeDAO = daoFactory.getLocalFractureTypeDAO();
        discontinuityTypeDAO = daoFactory.getLocalDiscontinuityTypeDAO();
        discontinuityRelevanceDAO = daoFactory.getLocalDiscontinuityRelevanceDAO();
        discontinuityShapeDAO = daoFactory.getLocalDiscontinuityShapeDAO();
        discontinuityPersistenceDAO = daoFactory.getLocalPersistenceDAO();
        discontinuitySpacingDAO = daoFactory.getLocalSpacingDAO();
        discontinuityApertureDAO = daoFactory.getLocalApertureDAO();
        discontinuityRoughnessDAO = daoFactory.getLocalRoughnessDAO();
        discontinuityInfillingDAO = daoFactory.getLocalInfillingDAO();
        discontinuityWeatheringDAO = daoFactory.getLocalWeatheringDAO();
        discontinuityWaterDAO = daoFactory.getLocalDiscontinuityWaterDAO();
        discontinuityJrDAO = daoFactory.getLocalJrDAO();

        this.mDiscontinuitiesFamilyDropBoxTable = new DiscontinuitiesFamilyDropboxTable(getDatastore());
        this.mRMRCalculationDropBoxTable = new RMRCalculationDropboxTable(getDatastore());
        this.mQBartonCalculationDropBoxTable = new QBartonCalculationDropboxTable(getDatastore());
        this.mSupportRecommendationDropboxTable = new SupportRecommendationDropboxTable(getDatastore());

    }

    public DbxDatastore getDatastore() throws DAOException {
        return mSkavaContext.getDatastore();
    }

    public Assessment buildAssessmentFromRecord(DbxRecord assessmentRecord) throws DAOException {

        Assessment babyAssessment = null;
        if (assessmentRecord.hasField("code")) {
            String assessmentCode = assessmentRecord.getString("code");
            babyAssessment = new Assessment(assessmentCode);
        }

        //find out if the datastore where this values comes from are skavadev, skavaprod, or skavaqa
        String datastoreName = mSkavaContext.getDatastore().getId();
        if (datastoreName.equalsIgnoreCase(SkavaConstants.DROPBOX_DS_DEV_NAME)) {
            babyAssessment.setEnvironment(SkavaConstants.DEV_KEY);
        } else if (datastoreName.equalsIgnoreCase(SkavaConstants.DROPBOX_DS_QA_NAME)) {
            babyAssessment.setEnvironment(SkavaConstants.QA_KEY);
        } else if (datastoreName.equalsIgnoreCase(SkavaConstants.DROPBOX_DS_PROD_NAME)) {
            babyAssessment.setEnvironment(SkavaConstants.PROD_KEY);
        } else {
            throw new DAOException("Unknown datastore name : " + datastoreName);
        }

        if (assessmentRecord.hasField("faceCode")) {
            java.lang.String faceCode = assessmentRecord.getString("faceCode");
            TunnelFace tunnelFace = localTunnelFaceDAO.getTunnelFaceByCode(faceCode);
            babyAssessment.setFace(tunnelFace);
        }

        if (assessmentRecord.hasField("geologistCode")) {
            String geologistCode = assessmentRecord.getString("geologistCode");
            User user = localUserDAO.getUserByCode(geologistCode);
            babyAssessment.setGeologist(user);
        }

        if (assessmentRecord.hasField("date")) {
            Date date = assessmentRecord.getDate("date");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            babyAssessment.setDateTime(calendar);
        }

        if (assessmentRecord.hasField("sectionCode")) {
            String sectionCode = assessmentRecord.getString("sectionCode");
            ExcavationSection section = excavationSectionDAO.getExcavationSectionByCode(sectionCode);
            babyAssessment.setSection(section);
        }

        if (assessmentRecord.hasField("methodCode")) {
            String methodCode = assessmentRecord.getString("methodCode");
            ExcavationMethod method = excavationMethodDAO.getExcavationMethodByCode(methodCode);
            babyAssessment.setMethod(method);
        }

        if (assessmentRecord.hasField("initialPeg")) {
            Double initialPeg = assessmentRecord.getDouble("initialPeg");
            babyAssessment.setInitialPeg(initialPeg);
        }

        if (assessmentRecord.hasField("finalPeg")) {
            Double finalPeg = assessmentRecord.getDouble("finalPeg");
            babyAssessment.setInitialPeg(finalPeg);
        }

        if (assessmentRecord.hasField("finalPeg")) {
            Double finalPeg = assessmentRecord.getDouble("finalPeg");
            babyAssessment.setInitialPeg(finalPeg);
        }

        if (assessmentRecord.hasField("referenceChainage")) {
            Double referenceChainage = assessmentRecord.getDouble("referenceChainage");
            babyAssessment.setReferenceChainage(referenceChainage);
        }

        if (assessmentRecord.hasField("advance")) {
            Double advance = assessmentRecord.getDouble("advance");
            babyAssessment.setCurrentAdvance(advance);
        }

        if (assessmentRecord.hasField("accumAdvance")) {
            Double accumAvance = assessmentRecord.getDouble("accumAdvance");
            babyAssessment.setAccummAdvance(accumAvance);
        }

        if (assessmentRecord.hasField("orientation")) {
            Long orientation = assessmentRecord.getLong("orientation");
            babyAssessment.setOrientation(orientation.shortValue());
        }

        if (assessmentRecord.hasField("slope")) {
            Double slope = assessmentRecord.getDouble("slope");
            babyAssessment.setSlope(slope);
        }

        if (assessmentRecord.hasField("fractureTypeCode")) {
            String fractureTypeCode = assessmentRecord.getString("fractureTypeCode");
            FractureType fracture = fractureTypeDAO.getFractureTypeByCode(fractureTypeCode);
            babyAssessment.setFractureType(fracture);
        }

        if (assessmentRecord.hasField("blockSize")) {
            Double blockSize = assessmentRecord.getDouble("blockSize");
            babyAssessment.setBlockSize(blockSize);
        }

        if (assessmentRecord.hasField("numJoints")) {
            Long numJoints = assessmentRecord.getLong("numJoints");
            babyAssessment.setOrientation(numJoints.shortValue());
        }

        if (assessmentRecord.hasField("outcropGeologicalDescription")) {
            String outcrop = assessmentRecord.getString("outrcropGeologicalDescription");
            babyAssessment.setOutcropDescription(outcrop);
        }

        if (assessmentRecord.hasField("rockSampleIdentification")) {
            String rockSampleId = assessmentRecord.getString("rockSampleIdentification");
            babyAssessment.setRockSampleIdentification(rockSampleId);
        }

        DbxList discontinuitiesSystemIds;
        if (assessmentRecord.hasField("discontinuitiesSystem")) {
            discontinuitiesSystemIds = assessmentRecord.getList("discontinuitiesSystem");
            if (!discontinuitiesSystemIds.isEmpty()) {


                List<DiscontinuityFamily> discontinuitySystem = new ArrayList<DiscontinuityFamily>();

                for (int i = 0; i < discontinuitiesSystemIds.size(); i++) {

                    String discontinuityId = discontinuitiesSystemIds.getString(i);

                    DbxRecord discontinuityFamilyRecord = mDiscontinuitiesFamilyDropBoxTable.findRecordByCandidateKey("id", discontinuityId);

                    DiscontinuityFamily discontinuity = new DiscontinuityFamily();

                    if (discontinuityFamilyRecord.hasField("number")) {
                        Long number = discontinuityFamilyRecord.getLong("number");
                        Integer numero = number.intValue();
                        discontinuity.setNumber(numero);
                    }

                    if (discontinuityFamilyRecord.hasField("typeCode")) {
                        String typeCode = discontinuityFamilyRecord.getString("typeCode");
                        DiscontinuityType type = discontinuityTypeDAO.getDiscontinuityTypeByCode(typeCode);
                        discontinuity.setType(type);
                    }

                    if (discontinuityFamilyRecord.hasField("relevanceCode")) {
                        String relevanceCode = discontinuityFamilyRecord.getString("relevanceCode");
                        DiscontinuityRelevance relevance = discontinuityRelevanceDAO.getDiscontinuityRelevanceByCode(relevanceCode);
                        discontinuity.setRelevance(relevance);
                    }

                    if (discontinuityFamilyRecord.hasField("dipDirDegrees")) {
                        Long dipDirDegrees = discontinuityFamilyRecord.getLong("dipDirDegrees");
                        discontinuity.setDipDirDegrees(dipDirDegrees.shortValue());
                    }

                    if (discontinuityFamilyRecord.hasField("dipDegrees")) {
                        Long dipDegrees = discontinuityFamilyRecord.getLong("dipDegrees");
                        discontinuity.setDipDegrees(dipDegrees.shortValue());
                    }

                    if (discontinuityFamilyRecord.hasField("spacingCode")) {
                        String spacingCode = discontinuityFamilyRecord.getString("spacingCode");
                        Spacing spacing = discontinuitySpacingDAO.getSpacingByUniqueCode(spacingCode);
                        discontinuity.setSpacing(spacing);
                    }

                    if (discontinuityFamilyRecord.hasField("persistenceCode")) {
                        String persistenceCode = discontinuityFamilyRecord.getString("persistenceCode");
                        Persistence persistence = discontinuityPersistenceDAO.getPersistenceByUniqueCode(persistenceCode);
                        discontinuity.setPersistence(persistence);
                    }

                    if (discontinuityFamilyRecord.hasField("apertureCode")) {
                        String apertureCode = discontinuityFamilyRecord.getString("apertureCode");
                        Aperture aperture = discontinuityApertureDAO.getApertureByUniqueCode(apertureCode);
                        discontinuity.setAperture(aperture);
                    }

                    if (discontinuityFamilyRecord.hasField("shapeCode")) {
                        String shapeCode = discontinuityFamilyRecord.getString("shapeCode");
                        DiscontinuityShape shape = discontinuityShapeDAO.getDiscontinuityShapeByCode(shapeCode);
                        discontinuity.setShape(shape);
                    }

                    if (discontinuityFamilyRecord.hasField("roughnessCode")) {
                        String roughnessCode = discontinuityFamilyRecord.getString("roughnessCode");
                        Roughness roughness = discontinuityRoughnessDAO.getRoughnessByUniqueCode(roughnessCode);
                        discontinuity.setRoughness(roughness);
                    }

                    if (discontinuityFamilyRecord.hasField("infillingCode")) {
                        String infillingCode = discontinuityFamilyRecord.getString("infillingCode");
                        Infilling infilling = discontinuityInfillingDAO.getInfillingByUniqueCode(infillingCode);
                        discontinuity.setInfilling(infilling);
                    }

                    if (discontinuityFamilyRecord.hasField("weatheringCode")) {
                        String weatheringCode = discontinuityFamilyRecord.getString("weatheringCode");
                        Weathering weathering = discontinuityWeatheringDAO.getWeatheringByUniqueCode(weatheringCode);
                        discontinuity.setWeathering(weathering);
                    }

                    if (discontinuityFamilyRecord.hasField("waterCode")) {
                        String waterCode = discontinuityFamilyRecord.getString("waterCode");
                        DiscontinuityWater water = discontinuityWaterDAO.getDiscontinuityWaterByCode(waterCode);
                        discontinuity.setWater(water);
                    }

                    if (discontinuityFamilyRecord.hasField("jrCode")) {
                        String jrCode = discontinuityFamilyRecord.getString("jrCode");
                        Jr jr = discontinuityJrDAO.getJrByUniqueCode(jrCode);
                        discontinuity.setJr(jr);
                    }

                    discontinuitySystem.add(discontinuity);
                }

                babyAssessment.setDiscontinuitySystem(discontinuitySystem);
            }

        }

        return babyAssessment;
    }


}
