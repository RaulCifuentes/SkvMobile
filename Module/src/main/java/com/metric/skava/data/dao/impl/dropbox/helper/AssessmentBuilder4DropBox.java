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

//  ***** COMMENTED OUT, TRY TO ASSEMBLE THE ENTIRE ASSESSMENT HERE IS TOO MUCH CODE, BETTER TO DIVIDE AND CONQUER, THE ASSESSMENT DROPBOX DAO WILL HANDLE THIS COMPOSITION
//        DbxList discontinuitiesSystemIds;
//        if (assessmentRecord.hasField("discontinuitiesSystem")) {
//            discontinuitiesSystemIds = assessmentRecord.getList("discontinuitiesSystem");
//            if (!discontinuitiesSystemIds.isEmpty()) {
//
//                List<DiscontinuityFamily> discontinuitySystem = new ArrayList<DiscontinuityFamily>();
//
//                for (int i = 0; i < discontinuitiesSystemIds.size(); i++) {
//
//                    String discontinuityId = discontinuitiesSystemIds.getString(i);
//
//                    DbxRecord discontinuityFamilyRecord = mDiscontinuitiesFamilyDropBoxTable.findRecordByCandidateKey("id", discontinuityId);
//
//                    DiscontinuityFamily discontinuity = new DiscontinuityFamily();
//
//                    if (discontinuityFamilyRecord.hasField("number")) {
//                        Long number = discontinuityFamilyRecord.getLong("number");
//                        Integer numero = number.intValue();
//                        discontinuity.setNumber(numero);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("typeCode")) {
//                        String typeCode = discontinuityFamilyRecord.getString("typeCode");
//                        DiscontinuityType type = discontinuityTypeDAO.getDiscontinuityTypeByCode(typeCode);
//                        discontinuity.setType(type);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("relevanceCode")) {
//                        String relevanceCode = discontinuityFamilyRecord.getString("relevanceCode");
//                        DiscontinuityRelevance relevance = discontinuityRelevanceDAO.getDiscontinuityRelevanceByCode(relevanceCode);
//                        discontinuity.setRelevance(relevance);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("dipDirDegrees")) {
//                        Long dipDirDegrees = discontinuityFamilyRecord.getLong("dipDirDegrees");
//                        discontinuity.setDipDirDegrees(dipDirDegrees.shortValue());
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("dipDegrees")) {
//                        Long dipDegrees = discontinuityFamilyRecord.getLong("dipDegrees");
//                        discontinuity.setDipDegrees(dipDegrees.shortValue());
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("spacingCode")) {
//                        String spacingCode = discontinuityFamilyRecord.getString("spacingCode");
//                        Spacing spacing = discontinuitySpacingDAO.getSpacingByUniqueCode(spacingCode);
//                        discontinuity.setSpacing(spacing);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("persistenceCode")) {
//                        String persistenceCode = discontinuityFamilyRecord.getString("persistenceCode");
//                        Persistence persistence = discontinuityPersistenceDAO.getPersistenceByUniqueCode(persistenceCode);
//                        discontinuity.setPersistence(persistence);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("apertureCode")) {
//                        String apertureCode = discontinuityFamilyRecord.getString("apertureCode");
//                        Aperture aperture = discontinuityApertureDAO.getApertureByUniqueCode(apertureCode);
//                        discontinuity.setAperture(aperture);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("shapeCode")) {
//                        String shapeCode = discontinuityFamilyRecord.getString("shapeCode");
//                        DiscontinuityShape shape = discontinuityShapeDAO.getDiscontinuityShapeByCode(shapeCode);
//                        discontinuity.setShape(shape);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("roughnessCode")) {
//                        String roughnessCode = discontinuityFamilyRecord.getString("roughnessCode");
//                        Roughness roughness = discontinuityRoughnessDAO.getRoughnessByUniqueCode(roughnessCode);
//                        discontinuity.setRoughness(roughness);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("infillingCode")) {
//                        String infillingCode = discontinuityFamilyRecord.getString("infillingCode");
//                        Infilling infilling = discontinuityInfillingDAO.getInfillingByUniqueCode(infillingCode);
//                        discontinuity.setInfilling(infilling);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("weatheringCode")) {
//                        String weatheringCode = discontinuityFamilyRecord.getString("weatheringCode");
//                        Weathering weathering = discontinuityWeatheringDAO.getWeatheringByUniqueCode(weatheringCode);
//                        discontinuity.setWeathering(weathering);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("waterCode")) {
//                        String waterCode = discontinuityFamilyRecord.getString("waterCode");
//                        DiscontinuityWater water = discontinuityWaterDAO.getDiscontinuityWaterByCode(waterCode);
//                        discontinuity.setWater(water);
//                    }
//
//                    if (discontinuityFamilyRecord.hasField("jrCode")) {
//                        String jrCode = discontinuityFamilyRecord.getString("jrCode");
//                        Jr jr = discontinuityJrDAO.getJrByUniqueCode(jrCode);
//                        discontinuity.setJr(jr);
//                    }
//
//                    discontinuitySystem.add(discontinuity);
//                }
//
//                babyAssessment.setDiscontinuitySystem(discontinuitySystem);
//            }
//
//        }

        return babyAssessment;
    }



    public Q_Calculation buildQCalculationFromRecord(DbxRecord qCalculationRecord) throws DAOException {
        Integer rqdValue = (int)(qCalculationRecord.getLong(QCalculationTable.RQD_COLUMN));
        String jnCode = qCalculationRecord.getString(QCalculationTable.Jn_CODE_COLUMN);
        String jrCode = qCalculationRecord.getString(QCalculationTable.Jr_CODE_COLUMN);
        String jaCode = qCalculationRecord.getString(QCalculationTable.Ja_CODE_COLUMN);
        String jwCode = qCalculationRecord.getString(QCalculationTable.Jw_CODE_COLUMN);
        String srfCode = qCalculationRecord.getString(QCalculationTable.SRF_CODE_COLUMN);
        //This seems to be persisted only to transfer to Dropbox but not needed in the deserialization/parsing process
        Double qValue = qCalculationRecord.getDouble(QCalculationTable.Q_COLUMN);

        RQD rqd = new RQD(rqdValue);
        Jn jn = jnCode != null? mLocalJnDAO.getJnByUniqueCode(jnCode):null;
        Jr jr = jrCode != null? mLocalJrDAO.getJrByUniqueCode(jrCode):null;
        Ja ja = jaCode != null? mLocalJaDAO.getJaByUniqueCode(jaCode):null;
        Jw jw = jwCode != null? mLocalJwDAO.getJwByUniqueCode(jwCode):null;
        SRF srf = srfCode != null? mLocalSrfDAO.getSrfByUniqueCode(srfCode):null;

        Q_Calculation qCalculation = new Q_Calculation(rqd, jn, jr, ja, jw, srf);
        return qCalculation;
    }


    public RMR_Calculation buildRMRCalculationFromRecord(DbxRecord rmrCalculationRecord) throws DAOException {
        String strenghtCode = rmrCalculationRecord.getString(RMRCalculationTable.STRENGTHOFROCK_CODE_COLUMN);
        String rqdKey = rmrCalculationRecord.getString(RMRCalculationTable.RQD_RMR_CODE_COLUMN);
        String spacingCode = rmrCalculationRecord.getString(RMRCalculationTable.SPACING_CODE_COLUMN);
        String persistenceCode = rmrCalculationRecord.getString(RMRCalculationTable.PERSISTENCE_CODE_COLUMN);
        String apertureCode = rmrCalculationRecord.getString(RMRCalculationTable.APERTURE_CODE_COLUMN);
        String roughnessCode = rmrCalculationRecord.getString(RMRCalculationTable.ROUGHNESS_CODE_COLUMN);
        String infillingCode = rmrCalculationRecord.getString(RMRCalculationTable.INFILLING_CODE_COLUMN);
        String weatheringCode = rmrCalculationRecord.getString(RMRCalculationTable.WEATHERING_CODE_COLUMN);
        String groundwaterCode = rmrCalculationRecord.getString(RMRCalculationTable.GROUNDWATER_CODE_COLUMN);
        String orientationCode = rmrCalculationRecord.getString(RMRCalculationTable.ORIENTATION_CODE_COLUMN);

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
        String assessment = recommendationRecord.getString(SupportRecommendationTable.ASSESSMENT_CODE_COLUMN);

        String requirementCode = recommendationRecord.getString(SupportRecommendationTable.SUPPORT_REQUIREMENT_BASE_CODE_COLUMN);
        SupportRequirement baseRequirement = null;
        if (requirementCode != null) {
            baseRequirement = daoFactory.getLocalSupportRequirementDAO().getSupportRequirement(requirementCode);
        }

        String patternTypeCode = recommendationRecord.getString(SupportRecommendationTable.ROOF_PATTERN_TYPE_CODE_COLUMN);
        Double distanceX = recommendationRecord.getDouble(SupportRecommendationTable.ROOF_PATTERN_DX_COLUMN);
        Double distanceY = recommendationRecord.getDouble(SupportRecommendationTable.ROOF_PATTERN_DY_COLUMN);
        SupportPatternType type = daoFactory.getLocalSupportPatternTypeDAO().getSupportPatternTypeByUniqueCode(patternTypeCode);
        SupportPattern roofPattern = new SupportPattern(type, distanceX, distanceY);

        patternTypeCode = recommendationRecord.getString(SupportRecommendationTable.WALL_PATTERN_TYPE_CODE_COLUMN);
        distanceX = recommendationRecord.getDouble(SupportRecommendationTable.WALL_PATTERN_DX_COLUMN);
        distanceY = recommendationRecord.getDouble(SupportRecommendationTable.WALL_PATTERN_DY_COLUMN);
        type = daoFactory.getLocalSupportPatternTypeDAO().getSupportPatternTypeByUniqueCode(patternTypeCode);
        SupportPattern wallPattern = new SupportPattern(type, distanceX, distanceY);

        String boltTypeCode = recommendationRecord.getString(SupportRecommendationTable.BOLT_TYPE_CODE_COLUMN);
        BoltType boltType = daoFactory.getLocalBoltTypeDAO().getBoltTypeByCode(boltTypeCode);

        Double boltDiameter = recommendationRecord.getDouble(SupportRecommendationTable.BOLT_DIAMETER_COLUMN);
        Double boltLength = recommendationRecord.getDouble(SupportRecommendationTable.BOLT_LENGTH_COLUMN);

        String coverageCode = recommendationRecord.getString(SupportRecommendationTable.MESH_COVERAGE_CODE_COLUMN);
        Coverage meshCoverage = daoFactory.getLocalCoverageDAO().getCoverageByCode(coverageCode);

        String meshTypeCode = recommendationRecord.getString(SupportRecommendationTable.MESH_TYPE_CODE_COLUMN);
        MeshType meshType = daoFactory.getLocalMeshTypeDAO().getMeshTypeByCode(meshTypeCode);

        String shotcreteTypeCode = recommendationRecord.getString(SupportRecommendationTable.SHOTCRETE_TYPE_CODE_COLUMN);
        coverageCode = recommendationRecord.getString(SupportRecommendationTable.SHOTCRETE_COVERAGE_CODE_COLUMN);
        Coverage shotcreteCoverage = daoFactory.getLocalCoverageDAO().getCoverageByCode(coverageCode);

        ShotcreteType shotcreteType = daoFactory.getLocalShotcreteTypeDAO().getShotcreteTypeByCode(shotcreteTypeCode);

        String archTypeCode = recommendationRecord.getString(SupportRecommendationTable.ARCH_TYPE_CODE_COLUMN);
        ArchType archType = daoFactory.getLocalArchTypeDAO().getArchTypeByCode(archTypeCode);

        Double separation = recommendationRecord.getDouble(SupportRecommendationTable.SEPARATION_COLUMN);
        Double thickness = recommendationRecord.getDouble(SupportRecommendationTable.THICKNESS_COLUMN);
        String observations = recommendationRecord.getString(SupportRecommendationTable.OBSERVATIONS_COLUMN);

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
