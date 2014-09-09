package com.metric.skava.data.dao.impl.dropbox.helper;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.barton.model.RQD;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.RMR_Calculation;
import com.metric.skava.calculator.rmr.model.RQD_RMR;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
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
import com.metric.skava.data.dao.LocalGroundwaterDAO;
import com.metric.skava.data.dao.LocalInfillingDAO;
import com.metric.skava.data.dao.LocalJaDAO;
import com.metric.skava.data.dao.LocalJnDAO;
import com.metric.skava.data.dao.LocalJrDAO;
import com.metric.skava.data.dao.LocalJwDAO;
import com.metric.skava.data.dao.LocalOrientationDiscontinuitiesDAO;
import com.metric.skava.data.dao.LocalPersistenceDAO;
import com.metric.skava.data.dao.LocalRoughnessDAO;
import com.metric.skava.data.dao.LocalSpacingDAO;
import com.metric.skava.data.dao.LocalSrfDAO;
import com.metric.skava.data.dao.LocalStrengthDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.LocalWeatheringDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.DiscontinuitiesFamilyDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.QBartonCalculationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RMRCalculationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRecommendationDropboxTable;
import com.metric.skava.data.dao.impl.sqllite.table.QCalculationTable;
import com.metric.skava.data.dao.impl.sqllite.table.RMRCalculationTable;
import com.metric.skava.data.dao.impl.sqllite.table.SupportRecommendationTable;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportPatternType;
import com.metric.skava.instructions.model.SupportRecommendation;
import com.metric.skava.rockmass.model.FractureType;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by metricboy on 3/5/14.
 */
public class AssessmentBuilder4DropBox {


    private SkavaContext mSkavaContext;

    private DAOFactory daoFactory;

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

    private LocalJnDAO mLocalJnDAO;
    private LocalJrDAO mLocalJrDAO;
    private LocalJwDAO mLocalJwDAO;
    private LocalJaDAO mLocalJaDAO;
    private LocalSrfDAO mLocalSrfDAO;

    private LocalStrengthDAO mLocalStrengthDAO;
    private LocalSpacingDAO mLocalSpacingDAO;
    private LocalPersistenceDAO mLocalPersistenceDAO;
    private LocalApertureDAO mLocalApertureDAO;
    private LocalRoughnessDAO mLocalRoughnessDAO;
    private LocalInfillingDAO mLocalInfillingDAO;
    private LocalWeatheringDAO mLocalWeatheringDAO;
    private LocalGroundwaterDAO mLocalGroundwaterDAO;
    private LocalOrientationDiscontinuitiesDAO mLocalOrientationDAO;


    private DiscontinuitiesFamilyDropboxTable mDiscontinuitiesFamilyDropBoxTable;
    private RMRCalculationDropboxTable mRMRCalculationDropBoxTable;
    private QBartonCalculationDropboxTable mQBartonCalculationDropBoxTable;
    private SupportRecommendationDropboxTable mSupportRecommendationDropboxTable;

    public AssessmentBuilder4DropBox(SkavaContext skavaContext) throws DAOException {
        this.mSkavaContext = skavaContext;
        daoFactory = skavaContext.getDAOFactory();
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

        mLocalJaDAO = daoFactory.getLocalJaDAO();
        mLocalJnDAO = daoFactory.getLocalJnDAO();
        mLocalJrDAO = daoFactory.getLocalJrDAO();
        mLocalJwDAO = daoFactory.getLocalJwDAO();
        mLocalSrfDAO = daoFactory.getLocalSrfDAO();

        mLocalStrengthDAO = daoFactory.getLocalStrengthDAO();
        mLocalSpacingDAO = daoFactory.getLocalSpacingDAO();
        mLocalPersistenceDAO = daoFactory.getLocalPersistenceDAO();
        mLocalApertureDAO = daoFactory.getLocalApertureDAO();
        mLocalRoughnessDAO = daoFactory.getLocalRoughnessDAO();
        mLocalInfillingDAO = daoFactory.getLocalInfillingDAO();
        mLocalWeatheringDAO = daoFactory.getLocalWeatheringDAO();
        mLocalGroundwaterDAO = daoFactory.getLocalGroundwaterDAO();
        mLocalOrientationDAO = daoFactory.getLocalOrientationDiscontinuitiesDAO();

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

        babyAssessment.setOriginatorDeviceID(assessmentRecord.getString("deviceID"));

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
            String outcrop = assessmentRecord.getString("outcropGeologicalDescription");
            babyAssessment.setOutcropDescription(outcrop);
        }

        if (assessmentRecord.hasField("rockSampleIdentification")) {
            String rockSampleId = assessmentRecord.getString("rockSampleIdentification");
            babyAssessment.setRockSampleIdentification(rockSampleId);
        }

        if (assessmentRecord.hasField("source")) {
            Assessment.Originator source = Assessment.Originator.valueOf(assessmentRecord.getString("source"));
            babyAssessment.setSource(source);
        }

        babyAssessment.setPicsSentStatus(Assessment.SendingStatus.SENT_TO_CLOUD);
        babyAssessment.setDataSentStatus(Assessment.SendingStatus.SENT_TO_CLOUD);
        babyAssessment.setSavedStatus(Assessment.SavingStatus.PERSISTENT);

        return babyAssessment;
    }


    public Integer readInteger(DbxRecord record, String name) {
        Integer integer =  readLong(record, name)!= null ? readLong(record, name).intValue() : null ;
        return integer;
    }


    public Double readDouble(DbxRecord record, String name) {
        if (record != null) {
            if (record.hasField(name)) {
                return record.getDouble(name);
            }
        }
        return null;
    }

    public String readString(DbxRecord record, String name) {
        if (record != null) {
            if (record.hasField(name)) {
                return record.getString(name);
            }
        }
        return null;
    }

    public Long readLong(DbxRecord record, String name) {
        if (record != null) {
            if (record.hasField(name)) {
                return record.getLong(name);
            }
        }
        return null;
    }


    public Q_Calculation buildQCalculationFromRecord(DbxRecord qCalculationRecord) throws DAOException {
        Integer rqdValue = readInteger(qCalculationRecord, QCalculationTable.RQD_COLUMN);
        String jnCode = readString(qCalculationRecord, QCalculationTable.Jn_CODE_COLUMN);
        String jrCode = readString(qCalculationRecord, QCalculationTable.Jr_CODE_COLUMN);
        String jaCode = readString(qCalculationRecord, QCalculationTable.Ja_CODE_COLUMN);
        String jwCode = readString(qCalculationRecord, QCalculationTable.Jw_CODE_COLUMN);
        String srfCode = readString(qCalculationRecord, QCalculationTable.SRF_CODE_COLUMN);
        //This seems to be persisted only to transfer to Dropbox but not needed in the deserialization/parsing process
        Double qValue = readDouble(qCalculationRecord, QCalculationTable.Q_COLUMN);
        RQD rqd = rqdValue != null? new RQD(rqdValue) : null;
        Jn jn = jnCode != null? mLocalJnDAO.getJnByUniqueCode(jnCode):null;
        Jr jr = jrCode != null? mLocalJrDAO.getJrByUniqueCode(jrCode):null;
        Ja ja = jaCode != null? mLocalJaDAO.getJaByUniqueCode(jaCode):null;
        Jw jw = jwCode != null? mLocalJwDAO.getJwByUniqueCode(jwCode):null;
        SRF srf = srfCode != null? mLocalSrfDAO.getSrfByUniqueCode(srfCode):null;
        Q_Calculation qCalculation = new Q_Calculation(rqd, jn, jr, ja, jw, srf);
        return qCalculation;
    }


    public RMR_Calculation buildRMRCalculationFromRecord(DbxRecord rmrCalculationRecord) throws DAOException {
        String strenghtCode = readString(rmrCalculationRecord, RMRCalculationTable.STRENGTHOFROCK_CODE_COLUMN);
        String rqdKey = readString(rmrCalculationRecord, RMRCalculationTable.RQD_RMR_CODE_COLUMN);
        String spacingCode = readString(rmrCalculationRecord, RMRCalculationTable.SPACING_CODE_COLUMN);
        String persistenceCode = readString(rmrCalculationRecord, RMRCalculationTable.PERSISTENCE_CODE_COLUMN);
        String apertureCode = readString(rmrCalculationRecord, RMRCalculationTable.APERTURE_CODE_COLUMN);
        String roughnessCode = readString(rmrCalculationRecord, RMRCalculationTable.ROUGHNESS_CODE_COLUMN);
        String infillingCode = readString(rmrCalculationRecord, RMRCalculationTable.INFILLING_CODE_COLUMN);
        String weatheringCode = readString(rmrCalculationRecord, RMRCalculationTable.WEATHERING_CODE_COLUMN);
        String groundwaterCode = readString(rmrCalculationRecord, RMRCalculationTable.GROUNDWATER_CODE_COLUMN);
        String orientationCode = readString(rmrCalculationRecord, RMRCalculationTable.ORIENTATION_CODE_COLUMN);

        //This seems to be persisted only to transfer to Dropbox but not needed in the deserialization/parsing process
        StrengthOfRock strenght = strenghtCode != null ? mLocalStrengthDAO.getStrengthByUniqueCode(strenghtCode) : null;
        RQD_RMR rqd = rqdKey != null ? RQD_RMR.findRQD_RMRByKey(rqdKey) : null;
        Spacing spacing = spacingCode != null ? mLocalSpacingDAO.getSpacingByUniqueCode(spacingCode) : null;
        Groundwater groundwater = groundwaterCode != null ? mLocalGroundwaterDAO.getGroundwaterByUniqueCode(groundwaterCode) : null;
        Persistence persistence = persistenceCode != null ? mLocalPersistenceDAO.getPersistenceByUniqueCode(persistenceCode) : null;
        Aperture aperture = apertureCode != null ? mLocalApertureDAO.getApertureByUniqueCode(apertureCode) : null;
        Roughness roughness = roughnessCode != null ? mLocalRoughnessDAO.getRoughnessByUniqueCode(roughnessCode) : null;
        Infilling infilling = infillingCode != null ? mLocalInfillingDAO.getInfillingByUniqueCode(infillingCode) : null;
        Weathering weathering = weatheringCode != null ? mLocalWeatheringDAO.getWeatheringByUniqueCode(weatheringCode) : null;
        OrientationDiscontinuities orientation = orientationCode != null ? mLocalOrientationDAO.getOrientationDiscontinuitiesByUniqueCode(orientationCode) : null;

        RMR_Calculation newInstance = new RMR_Calculation(strenght, rqd, spacing, persistence, aperture, roughness, infilling, weathering, groundwater, orientation);

        return newInstance;
    }


    public SupportRecommendation buildSupportRecommendation(DbxRecord recommendationRecord) throws DAOException {
        String assessment = readString(recommendationRecord, SupportRecommendationTable.ASSESSMENT_CODE_COLUMN);

        String requirementCode = readString(recommendationRecord, SupportRecommendationTable.SUPPORT_REQUIREMENT_BASE_CODE_COLUMN);

        SupportRequirement baseRequirement = null;
        if (requirementCode != null) {
            baseRequirement = daoFactory.getLocalSupportRequirementDAO().getSupportRequirement(requirementCode);
        }

        String patternTypeCode = readString(recommendationRecord, SupportRecommendationTable.ROOF_PATTERN_TYPE_CODE_COLUMN);
        Double distanceX = readDouble(recommendationRecord, SupportRecommendationTable.ROOF_PATTERN_DX_COLUMN);
        Double distanceY = readDouble(recommendationRecord, SupportRecommendationTable.ROOF_PATTERN_DY_COLUMN);
        SupportPatternType type = null;
        if (patternTypeCode != null) {
            type = daoFactory.getLocalSupportPatternTypeDAO().getSupportPatternTypeByUniqueCode(patternTypeCode);
        }
        SupportPattern roofPattern = new SupportPattern(type, distanceX, distanceY);

        patternTypeCode = readString(recommendationRecord, SupportRecommendationTable.WALL_PATTERN_TYPE_CODE_COLUMN);
        distanceX = readDouble(recommendationRecord, SupportRecommendationTable.WALL_PATTERN_DX_COLUMN);
        distanceY = readDouble(recommendationRecord, SupportRecommendationTable.WALL_PATTERN_DY_COLUMN);
        if (patternTypeCode != null) {
            type = daoFactory.getLocalSupportPatternTypeDAO().getSupportPatternTypeByUniqueCode(patternTypeCode);
        }
        SupportPattern wallPattern = new SupportPattern(type, distanceX, distanceY);

        String boltTypeCode = readString(recommendationRecord, SupportRecommendationTable.BOLT_TYPE_CODE_COLUMN);
        BoltType boltType = null;
        if (boltTypeCode != null) {
            boltType = daoFactory.getLocalBoltTypeDAO().getBoltTypeByCode(boltTypeCode);
        }

        Double boltDiameter = readDouble(recommendationRecord, SupportRecommendationTable.BOLT_DIAMETER_COLUMN);
        Double boltLength = readDouble(recommendationRecord, SupportRecommendationTable.BOLT_LENGTH_COLUMN);

        String coverageCode = readString(recommendationRecord, SupportRecommendationTable.MESH_COVERAGE_CODE_COLUMN);
        Coverage meshCoverage = null;
        if (coverageCode != null)
            meshCoverage = daoFactory.getLocalCoverageDAO().getCoverageByCode(coverageCode);

        String meshTypeCode = readString(recommendationRecord, SupportRecommendationTable.MESH_TYPE_CODE_COLUMN);
        MeshType meshType = null;
        if (meshTypeCode != null) {
            meshType = daoFactory.getLocalMeshTypeDAO().getMeshTypeByCode(meshTypeCode);
        }

        String shotcreteTypeCode = readString(recommendationRecord, SupportRecommendationTable.SHOTCRETE_TYPE_CODE_COLUMN);
        ShotcreteType shotcreteType = null;
        if (shotcreteTypeCode != null) {
            shotcreteType = daoFactory.getLocalShotcreteTypeDAO().getShotcreteTypeByCode(shotcreteTypeCode);
        }

        coverageCode = readString(recommendationRecord, SupportRecommendationTable.SHOTCRETE_COVERAGE_CODE_COLUMN);
        Coverage shotcreteCoverage = null;
        if (coverageCode != null) {
            shotcreteCoverage = daoFactory.getLocalCoverageDAO().getCoverageByCode(coverageCode);
        }

        String archTypeCode = readString(recommendationRecord,SupportRecommendationTable.ARCH_TYPE_CODE_COLUMN);
        ArchType archType = null;
        if (archTypeCode != null) {
            archType = daoFactory.getLocalArchTypeDAO().getArchTypeByCode(archTypeCode);
        }

        Double separation = readDouble(recommendationRecord, SupportRecommendationTable.SEPARATION_COLUMN);
        Double thickness = readDouble(recommendationRecord, SupportRecommendationTable.THICKNESS_COLUMN);
        String observations = readString(recommendationRecord,SupportRecommendationTable.OBSERVATIONS_COLUMN);

        SupportRecommendation newInstance = new SupportRecommendation();
        newInstance.setArchType(archType);
        newInstance.setBoltType(boltType);
        newInstance.setRoofPattern(roofPattern);
        newInstance.setWallPattern(wallPattern);
        newInstance.setRequirement(baseRequirement);
        newInstance.setBoltDiameter(boltDiameter);
        newInstance.setBoltLength(boltLength);
        newInstance.setMeshType(meshType);
        newInstance.setMeshCoverage(meshCoverage);
        newInstance.setObservations(observations);
        newInstance.setSeparation(separation);
        newInstance.setShotcreteType(shotcreteType);
        newInstance.setShotcreteCoverage(shotcreteCoverage);
        newInstance.setThickness(thickness);

        return newInstance;
    }

}
