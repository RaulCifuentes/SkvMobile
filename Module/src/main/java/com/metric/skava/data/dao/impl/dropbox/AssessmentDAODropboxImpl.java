package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxList;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.calculator.barton.logic.QBartonOutput;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.calculator.barton.model.Q_Calculation;
import com.metric.skava.calculator.barton.model.RQD;
import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.calculator.rmr.logic.RMROutput;
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
import com.metric.skava.data.dao.RemoteAssessmentDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.AssessmentDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.DiscontinuitiesFamilyDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.QBartonCalculationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.RMRCalculationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRecommendationDropboxTable;
import com.metric.skava.data.dao.impl.dropbox.helper.AssessmentBuilder4DropBox;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;
import com.metric.skava.discontinuities.model.DiscontinuityShape;
import com.metric.skava.discontinuities.model.DiscontinuityType;
import com.metric.skava.discontinuities.model.DiscontinuityWater;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportRecommendation;
import com.metric.skava.pictures.model.SkavaPicture;
import com.metric.skava.rockmass.model.FractureType;
import com.metric.skava.rocksupport.model.ESR;
import com.metric.skava.rocksupport.model.SupportRequirement;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.model.AssessmentSyncTrace;
import com.metric.skava.sync.model.DataToSync;
import com.metric.skava.sync.model.FileToSync;
import com.metric.skava.sync.model.RecordToSync;
import com.metric.skava.sync.model.SyncQueue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by metricboy on 3/5/14.
 */
public class AssessmentDAODropboxImpl extends DropBoxBaseDAO implements RemoteAssessmentDAO {

    private AssessmentDropboxTable mAssessmentsTable;
    private DiscontinuitiesFamilyDropboxTable mDiscontinuitiesFamilyDropBoxTable;
    private RMRCalculationDropboxTable mRMRCalculationDropBoxTable;
    private QBartonCalculationDropboxTable mQBartonCalculationDropBoxTable;
    private SupportRecommendationDropboxTable mSupportRecommendationDropboxTable;
    private AssessmentBuilder4DropBox assessmentBuilder;
    private SyncLoggingDAO mSyncLoggingDAO;

    public AssessmentDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mAssessmentsTable = new AssessmentDropboxTable(getDatastore());
        this.mDiscontinuitiesFamilyDropBoxTable = new DiscontinuitiesFamilyDropboxTable(getDatastore());
        this.mRMRCalculationDropBoxTable = new RMRCalculationDropboxTable(getDatastore());
        this.mQBartonCalculationDropBoxTable = new QBartonCalculationDropboxTable(getDatastore());
        this.mSupportRecommendationDropboxTable = new SupportRecommendationDropboxTable(getDatastore());
        this.assessmentBuilder = new AssessmentBuilder4DropBox(skavaContext);
        this.mSyncLoggingDAO = getDAOFactory().getSyncLoggingDAO();
    }

    @Override
    public List<Assessment> getAllAssessments() throws DAOException {
        List<Assessment> listAssessments = new ArrayList<Assessment>();
        List<DbxRecord> recordList = mAssessmentsTable.findAll();
        Assessment newAssessment = null;
        for (DbxRecord currentDbxRecord : recordList) {
            newAssessment = assessmentBuilder.buildAssessmentFromRecord(currentDbxRecord);
            listAssessments.add(newAssessment);
        }
        return listAssessments;
    }

    @Override
    public List<Assessment> getAssessmentsByUser(User user) throws DAOException {
        return null;
    }

    @Override
    public List<Assessment> getAssessmentsByTunnelFace(TunnelFace face) throws DAOException {
        return null;
    }


    @Override
    public void saveAssessment(final Assessment assessment) throws DAOException {
        try {

            DbxFields assessmentFields = new DbxFields();

            assessmentFields.set("source", SkavaConstants.MOBILE_SOURCE);

            String code = assessment.getCode();
            assessmentFields.set("code", code);

            final String internalCode = assessment.getInternalCode();
            if (internalCode != null) {
                assessmentFields.set("skavaInternalCode", internalCode);
            }

            ExcavationProject project = assessment.getProject();
            if (SkavaUtils.isDefined(project)) {
                assessmentFields.set("projectCode", project.getCode());
            }

            Tunnel tunnel = assessment.getTunnel();
            if (SkavaUtils.isDefined(tunnel)) {
                assessmentFields.set("tunnelCode", tunnel.getCode());
            }

            TunnelFace face = assessment.getFace();
            if (SkavaUtils.isDefined(face)) {
                assessmentFields.set("faceCode", face.getCode());
            }

            ExcavationSection section = assessment.getSection();
            if (SkavaUtils.isDefined(section)) {
                assessmentFields.set("sectionCode", section.getCode());
            }

            Date date = assessment.getDateTime().getTime();
            if (date != null) {
                assessmentFields.set("date", date);
            }

            User geologist = assessment.getGeologist();
            if (SkavaUtils.isDefined(geologist)) {
                assessmentFields.set("geologistCode", geologist.getCode());
            }

            Double initialPeg = assessment.getInitialPeg();
            if (initialPeg != null) {
                assessmentFields.set("initialPeg", initialPeg);
            }

            Double finalPeg = assessment.getFinalPeg();
            if (initialPeg != null) {
                assessmentFields.set("finalPeg", finalPeg);
            }

            Double advance = assessment.getCurrentAdvance();
            if (advance != null) {
                assessmentFields.set("advance", advance);
            }

            Double referenceChainage = assessment.getReferenceChainage();
            if (referenceChainage != null) {
                assessmentFields.set("referenceChainage", referenceChainage);
            }

            Double accumAdvance = assessment.getAccummAdvance();
            if (accumAdvance != null) {
                assessmentFields.set("accumAdvance", accumAdvance);
            }

            ExcavationMethod method = assessment.getMethod();
            if (SkavaUtils.isDefined(method)) {
                assessmentFields.set("methodCode", method.getCode());
            }

            Short orientation = assessment.getOrientation();
            if (orientation != null) {
                assessmentFields.set("orientation", orientation);
            }

            Double slope = assessment.getSlope();
            if (slope != null) {
                assessmentFields.set("slope", slope);
            }

            FractureType fractureType = assessment.getFractureType();
            if (SkavaUtils.isDefined(fractureType)) {
                assessmentFields.set("fractureTypeCode", fractureType.getCode());
            }

            Double blockSize = assessment.getBlockSize();
            if (blockSize != null) {
                assessmentFields.set("blockSize", blockSize);
            }

            Short numJoints = assessment.getNumberOfJoints();
            if (numJoints != null) {
                assessmentFields.set("numJoints", numJoints);
            }

            String outcropGeologicalDescription = assessment.getOutcropDescription();
            if (outcropGeologicalDescription != null) {
                assessmentFields.set("outrcropGeologicalDescription", outcropGeologicalDescription);
            }

            String rockSampleIdentification = assessment.getRockSampleIdentification();
            if (rockSampleIdentification != null) {
                assessmentFields.set("rockSampleIdentification", rockSampleIdentification);
            }


            List<DiscontinuityFamily> discontinuitySystem = assessment.getDiscontinuitySystem();
            if (discontinuitySystem != null) {

                DbxList discontinuitiesFamilySystem = new DbxList();

                for (DiscontinuityFamily family : discontinuitySystem) {
                    if (family == null || !family.hasSelectedAnything()) {
                        continue;
                    }
                    DbxFields discontinuityFamilyFields = new DbxFields();
                    //This UUID is requested by Fabian in order to uniquely identify a record on Dropbox
                    String familyPK = UUID.randomUUID().toString();
                    discontinuityFamilyFields.set("familyPK", familyPK);

                    discontinuityFamilyFields.set("assesmentCode", assessment.getCode());

                    int number = family.getNumber();
                    discontinuityFamilyFields.set("number", number);

                    DiscontinuityType type = family.getType();
                    if (SkavaUtils.isDefined(type)) {
                        discontinuityFamilyFields.set("typeCode", type.getCode());
                    }

                    DiscontinuityRelevance relevance = family.getRelevance();
                    if (SkavaUtils.isDefined(relevance)) {
                        discontinuityFamilyFields.set("relevanceCode", relevance.getCode());
                    }

                    Short dipDirDegrees = family.getDipDirDegrees();
                    if (dipDirDegrees != null) {
                        discontinuityFamilyFields.set("dipDirDegrees", dipDirDegrees);
                    }

                    Short dipDegrees = family.getDipDegrees();
                    if (dipDegrees != null) {
                        discontinuityFamilyFields.set("dipDegrees", dipDegrees);
                    }

                    Spacing spacing = family.getSpacing();
                    if (SkavaUtils.isDefined(spacing)) {
                        discontinuityFamilyFields.set("spacingCode", spacing.getCode());
                    }

                    Persistence persistence = family.getPersistence();
                    if (SkavaUtils.isDefined(persistence)) {
                        discontinuityFamilyFields.set("persistenceCode", persistence.getCode());
                    }

                    Aperture aperture = family.getAperture();
                    if (SkavaUtils.isDefined(aperture)) {
                        discontinuityFamilyFields.set("apertureCode", aperture.getCode());
                    }

                    DiscontinuityShape shape = family.getShape();
                    if (SkavaUtils.isDefined(shape)) {
                        discontinuityFamilyFields.set("shapeCode", shape.getCode());
                    }

                    Roughness roughness = family.getRoughness();
                    if (SkavaUtils.isDefined(roughness)) {
                        discontinuityFamilyFields.set("roughnessCode", roughness.getCode());
                    }

                    Infilling infilling = family.getInfilling();
                    if (SkavaUtils.isDefined(infilling)) {
                        discontinuityFamilyFields.set("infillingCode", infilling.getCode());
                    }

                    Weathering weathering = family.getWeathering();
                    if (SkavaUtils.isDefined(weathering)) {
                        discontinuityFamilyFields.set("weatheringCode", weathering.getCode());
                    }

                    DiscontinuityWater water = family.getWater();
                    if (SkavaUtils.isDefined(water)) {
                        discontinuityFamilyFields.set("waterCode", water.getCode());
                    }

                    Jr jr = family.getJr();
                    if (SkavaUtils.isDefined(jr)) {
                        discontinuityFamilyFields.set("jrCode", jr.getCode());
                    }

                    Ja ja = family.getJa();
                    if (SkavaUtils.isDefined(ja)) {
                        discontinuityFamilyFields.set("jaCode", ja.getCode());
                    }

                    DbxRecord alreadyRemoted = mDiscontinuitiesFamilyDropBoxTable.findRecordByCandidateKey(new String[]{"assessmentCode", "number"}, new Object[]{code, number});
                    String familyRecordID;
                    if (alreadyRemoted == null) {
                        familyRecordID = mDiscontinuitiesFamilyDropBoxTable.persist(discontinuityFamilyFields);
                    } else {
                        familyRecordID = alreadyRemoted.getId();
                        alreadyRemoted.setAll(discontinuityFamilyFields);
                    }

                    discontinuitiesFamilySystem.add(familyRecordID);
                }

                assessmentFields.set("discontinuitiesSystem", discontinuitiesFamilySystem);
            }


            SupportRecommendation recomendation = assessment.getRecomendation();
            if (recomendation != null && recomendation.hasSelectedAnything()) {

                DbxFields supportRecommendationFields = new DbxFields();

                supportRecommendationFields.set("assessmentCode", assessment.getCode());

                SupportRequirement base = recomendation.getRequirementBase();
                if (base != null) {
                    supportRecommendationFields.set("supportRequirementBaseCode", base.getCode());
                }

                BoltType boltType = recomendation.getBoltType();
                if (SkavaUtils.isDefined(boltType)) {
                    supportRecommendationFields.set("boltTypeCode", boltType.getCode());
                }

                Double boltDiameter = recomendation.getBoltDiameter();
                if (boltDiameter != null) {
                    supportRecommendationFields.set("boltDiameter", boltDiameter);
                }

                Double boltLength = recomendation.getBoltLength();
                if (boltLength != null) {
                    supportRecommendationFields.set("boltLength", boltLength);
                }

                SupportPattern roofPattern = recomendation.getRoofPattern();
                if (roofPattern != null) {
                    if (SkavaUtils.isDefined(roofPattern.getType())) {
                        supportRecommendationFields.set("roofPatternTypeCode", roofPattern.getType().getCode());
                    }
                    supportRecommendationFields.set("roofPatternDx", roofPattern.getDistanceX());
                    supportRecommendationFields.set("roofPatternDy", roofPattern.getDistanceY());
                }

                SupportPattern wallPattern = recomendation.getWallPattern();
                if (wallPattern != null) {
                    if (SkavaUtils.isDefined(wallPattern.getType())) {
                        supportRecommendationFields.set("wallPatternTypeCode", wallPattern.getType().getCode());
                    }
                    supportRecommendationFields.set("wallPatternDx", wallPattern.getDistanceX());
                    supportRecommendationFields.set("wallPatternDy", wallPattern.getDistanceY());
                }

                ShotcreteType shotcreteType = recomendation.getShotcreteType();
                if (SkavaUtils.isDefined(shotcreteType)) {
                    supportRecommendationFields.set("shotcreteTypeCode", shotcreteType.getCode());
                }

                Coverage coverage = recomendation.getShotcreteCoverage();
                if (SkavaUtils.isDefined(coverage)) {
                    supportRecommendationFields.set("shotcreteCoverageCode", coverage.getCode());
                }

                Double thickness = recomendation.getThickness();
                if (thickness != null) {
                    supportRecommendationFields.set("thickness", thickness);
                }

                MeshType meshType = recomendation.getMeshType();
                if (SkavaUtils.isDefined(meshType)) {
                    supportRecommendationFields.set("meshTypeCode", meshType.getCode());
                }

                coverage = recomendation.getMeshCoverage();
                if (SkavaUtils.isDefined(coverage)) {
                    supportRecommendationFields.set("meshCoverageCode", coverage.getCode());
                }

                ArchType archType = recomendation.getArchType();
                if (SkavaUtils.isDefined(archType)) {
                    supportRecommendationFields.set("archTypeCode", archType.getCode());
                }

                Double separation = recomendation.getSeparation();
                if (separation != null) {
                    supportRecommendationFields.set("separation", separation);
                }

                String observations = recomendation.getObservations();
                if (observations != null) {
                    supportRecommendationFields.set("observations", observations);
                }

                DbxRecord alreadyRemoted = mSupportRecommendationDropboxTable.findRecordByCandidateKey("assessmentCode", code);
                String supportRecommendationRecordId;
                if (alreadyRemoted == null) {
                    supportRecommendationRecordId = mSupportRecommendationDropboxTable.persist(supportRecommendationFields);
                } else {
                    supportRecommendationRecordId = alreadyRemoted.getId();
                    alreadyRemoted.setAll(supportRecommendationFields);
                }

            }

            RMR_Calculation rmrCalculation = assessment.getRmrCalculation();

            if (rmrCalculation != null && rmrCalculation.hasSelectedAnything()) {

                DbxFields rmrCalculationFields = new DbxFields();

                rmrCalculationFields.set("assessmentCode", assessment.getCode());

                StrengthOfRock strengthOfRock = rmrCalculation.getStrengthOfRock();
                if (SkavaUtils.isDefined(strengthOfRock)) {
                    rmrCalculationFields.set("strengthCode", strengthOfRock.getCode());
                }

                Spacing spacingDiscontinuities = rmrCalculation.getSpacing();
                if (SkavaUtils.isDefined(spacingDiscontinuities)) {
                    rmrCalculationFields.set("spacingCode", spacingDiscontinuities.getCode());
                }

                Persistence persistence = rmrCalculation.getPersistence();
                if (SkavaUtils.isDefined(persistence)) {
                    rmrCalculationFields.set("persistenceCode", persistence.getCode());
                }

                Aperture aperture = rmrCalculation.getAperture();
                if (SkavaUtils.isDefined(aperture)) {
                    rmrCalculationFields.set("apertureCode", aperture.getCode());
                }

                Roughness roughness = rmrCalculation.getRoughness();
                if (SkavaUtils.isDefined(roughness)) {
                    rmrCalculationFields.set("roughnessCode", roughness.getCode());
                }

                Infilling infilling = rmrCalculation.getInfilling();
                if (SkavaUtils.isDefined(infilling)) {
                    rmrCalculationFields.set("infillingCode", infilling.getCode());
                }

                Weathering weathering = rmrCalculation.getWeathering();
                if (SkavaUtils.isDefined(weathering)) {
                    rmrCalculationFields.set("weatheringCode", weathering.getCode());
                }

                Groundwater groundwater = rmrCalculation.getGroundwater();
                if (SkavaUtils.isDefined(groundwater)) {
                    rmrCalculationFields.set("groundwaterCode", groundwater.getCode());
                }

                OrientationDiscontinuities orientationDiscontinuities = rmrCalculation.getOrientationDiscontinuities();
                if (SkavaUtils.isDefined(orientationDiscontinuities)) {
                    rmrCalculationFields.set("orientationCode", orientationDiscontinuities.getCode());
                }

                RQD_RMR rqd = rmrCalculation.getRqd();
                if (rqd != null) {
                    rmrCalculationFields.set("rqd", rqd.getValue());
                }

                RMROutput rmrOutput = rmrCalculation.getRMRResult();
                if (rmrOutput != null) {
                    rmrCalculationFields.set("rmr", rmrOutput.getRMR());
                }

                DbxRecord alreadyRemoted = mRMRCalculationDropBoxTable.findRecordByCandidateKey("assessmentCode", code);
                String rmrCalculationRecordId;
                if (alreadyRemoted == null) {
                    rmrCalculationRecordId = mRMRCalculationDropBoxTable.persist(rmrCalculationFields);
                } else {
                    rmrCalculationRecordId = alreadyRemoted.getId();
                    alreadyRemoted.setAll(rmrCalculationFields);
                }

            }

            Q_Calculation qCalculation = assessment.getQCalculation();
            if (qCalculation != null && qCalculation.hasSelectedAnything()) {
                DbxFields qCalculationFields = new DbxFields();
                qCalculationFields.set("assessmentCode", assessment.getCode());

                if (tunnel != null) {
                    ESR esr = tunnel.getExcavationFactors().getEsr();
                    if (esr != null) {
                        qCalculationFields.set("esr", esr.getCode());
                    }
                }

                //rockQualityCode
                if (qCalculation.getQResult() != null) {
                    RockQuality quality = qCalculation.getQResult().getRockQuality();
                    if (quality != null) {
                        qCalculationFields.set("rockQualityCode", quality.getCode());
                    }
                }

                RQD rqd = qCalculation.getRqd();
                if (rqd != null) {
                    qCalculationFields.set("rqd", rqd.getValue());
                }

                Jn jn = qCalculation.getJn();
                if (jn != null) {
                    qCalculationFields.set("jnCode", jn.getCode());
                }

                Jr jr = qCalculation.getJr();
                if (jr != null) {
                    qCalculationFields.set("jrCode", jr.getCode());
                }

                Ja ja = qCalculation.getJa();
                if (ja != null) {
                    qCalculationFields.set("jaCode", ja.getCode());
                }

                Jw jw = qCalculation.getJw();
                if (jw != null) {
                    qCalculationFields.set("jwCode", jw.getCode());
                }

                SRF srf = qCalculation.getSrf();
                if (srf != null) {
                    qCalculationFields.set("srfCode", srf.getCode());
                }

                QBartonOutput qBarton = qCalculation.getQResult();
                if (qBarton != null) {
                    qCalculationFields.set("qBarton", qBarton.getQBarton());
                }

                DbxRecord alreadyRemoted = mQBartonCalculationDropBoxTable.findRecordByCandidateKey("assessmentCode", code);
                String qCalculationRecordId;
                if (alreadyRemoted == null) {
                    qCalculationRecordId = mQBartonCalculationDropBoxTable.persist(qCalculationFields);
                } else {
                    qCalculationRecordId = alreadyRemoted.getId();
                    alreadyRemoted.setAll(qCalculationFields);
                }

            }

            Uri tunnelExpanded = assessment.getTunnelExpandedView();
            if (tunnelExpanded != null) {
                String tunnelExpandedAsString = tunnelExpanded.getLastPathSegment();
                assessmentFields.set("tunnelExpanded", tunnelExpandedAsString);
            }


            List<SkavaPicture> pictureList = assessment.getPicturesList();
            DbxList uriEncodedList = new DbxList();
            for (SkavaPicture currentPicture : pictureList) {
                if (currentPicture != null) {
                    java.lang.String uriEncoded = currentPicture.getPictureLocation().getLastPathSegment();
                    uriEncodedList.add(uriEncoded);
                }
            }

            assessmentFields.set("picturesURIs", uriEncodedList);

            DbxRecord alreadyRemoted = mAssessmentsTable.findRecordByCode(code);
            String recordId;
            if (alreadyRemoted == null) {
                //Do the actual insert on Datastore.
                //From now on Dropbox middle man has the power
                recordId = mAssessmentsTable.persist(assessmentFields);
            } else {
                recordId = alreadyRemoted.getId();
                alreadyRemoted.setAll(assessmentFields);
            }
            //****** THE ASSESSMENT SAVE WAS JUST CALLED, SO RECORD THIS ON THE SYNC QUEUE
            RecordToSync recordToSync = new RecordToSync(assessment.getCode());
            recordToSync.setOperation(RecordToSync.Operation.INSERT);
            recordToSync.setSkavaEntityCode(assessment.getCode());
            recordToSync.setRecordID(recordId);
            recordToSync.setDate(SkavaUtils.getCurrentDate());
            recordToSync.setStatus(DataToSync.Status.QUEUED);

            //This is just the assessment syncing log table for eventual tracing
            AssessmentSyncTrace syncTrace = new AssessmentSyncTrace(assessment.getCode());
            syncTrace.addRecord(recordToSync);

            //This is the on-memory middleman to check if there´s any pending sync request
            SyncQueue middlemanInbox = getSkavaContext().getMiddlemanInbox();
            middlemanInbox.addRecord(recordToSync);

            //****** Proceed saving the pictures if any of this assessment
            //Here tunnelExpanded will work similar to pictures so..
            if (SkavaUtils.hasPictures(pictureList) || tunnelExpanded != null ) {
                //Invoke a service to do the upload of the Pictures
                //com.metric.skava.uploader.MyUploaderService
                //Creates a new Thread and run the service on it
                String folderName = null;
                String target = getSkavaContext().getTargetEnvironment();
                if (target.equalsIgnoreCase(SkavaConstants.DEV_KEY)) {
                    folderName = SkavaConstants.DROPBOX_DS_DEV_NAME;
                } else if (target.equalsIgnoreCase(SkavaConstants.QA_KEY)) {
                    folderName = SkavaConstants.DROPBOX_DS_QA_NAME;
                } else if (target.equalsIgnoreCase(SkavaConstants.PROD_KEY)) {
                    folderName = SkavaConstants.DROPBOX_DS_PROD_NAME;
                }

                final ArrayList<String> picturesAsStringList = new ArrayList<String>();
                //Here we check specifically for pictures but not tunnel expanded view
                if (SkavaUtils.hasPictures(pictureList)){
                    for (SkavaPicture skavaPicture : pictureList) {
                        if (skavaPicture != null && skavaPicture.getPictureLocation() != null) {
                            //add the full Uri as String to the list sent to the upload service
                            picturesAsStringList.add(skavaPicture.getPictureLocation().toString());
                            //*** The thread will be called soon, so lets RECORD THIS ON THE SYNC QUEUE too
                            FileToSync pendingFile = new FileToSync(assessment.getCode());
                            pendingFile.setOperation(DataToSync.Operation.INSERT);
                            pendingFile.setFileName(skavaPicture.getPictureLocation().getLastPathSegment());
                            pendingFile.setDate(SkavaUtils.getCurrentDate());
                            pendingFile.setStatus(DataToSync.Status.QUEUED);
                            //This is just the assessment syncing log table for eventual tracing
                            syncTrace.addFile(pendingFile);
                            //And this is the on-memory middleman to check if there´s any pending sync request
                            middlemanInbox.addFile(pendingFile);
                        }
                    }
                }
                //And here we check specifically tunnel expanded view
                if (tunnelExpanded != null) {
                    //add the full Uri as String to the list sent to the upload service
                    picturesAsStringList.add(tunnelExpanded.toString());
                    //*** The thread will be called soon, so lets RECORD THIS ON THE SYNC QUEUE too
                    FileToSync pendingFile = new FileToSync(assessment.getCode());
                    pendingFile.setOperation(DataToSync.Operation.INSERT);
                    pendingFile.setFileName(tunnelExpanded.getLastPathSegment());
                    pendingFile.setDate(SkavaUtils.getCurrentDate());
                    pendingFile.setStatus(DataToSync.Status.QUEUED);
                    //This is just the assessment syncing log table for eventual tracing
                    syncTrace.addFile(pendingFile);
                    //And this is the on-memory middleman to check if there´s any pending sync request
                    middlemanInbox.addFile(pendingFile);
                }

                final String finalFolderName = folderName;
                Thread myThread = new Thread(new Runnable() {
                    public void run() {
                        String internalCode = assessment.getInternalCode();
                        String assessmentCode = assessment.getCode();
                        invokeTheService(DataToSync.Operation.INSERT, finalFolderName, internalCode, assessmentCode, picturesAsStringList);
                    }
                });
                myThread.start();
            }
            //Before leaving save this assessmentSyncTrace for tracing purposes
            mSyncLoggingDAO.saveAssessmentSyncTrace(syncTrace);

        } catch (Exception e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            e.printStackTrace();
            throw new DAOException(e);
        }
    }


    public void invokeTheService(DataToSync.Operation operation, String folderName, String internalCode, String assessmentCode, ArrayList<String> picturesAsStringList) {
        Intent serviceIntent;
        switch (operation) {
            case INSERT:
                serviceIntent = new Intent(SkavaConstants.CUSTOM_ACTION);
                serviceIntent.putExtra(SkavaConstants.EXTRA_OPERATION, operation.name());
                serviceIntent.putExtra(SkavaConstants.EXTRA_ENVIRONMENT_NAME, folderName);
                serviceIntent.putExtra(SkavaConstants.EXTRA_INTERNAL_CODE, internalCode);
                serviceIntent.putExtra(SkavaConstants.EXTRA_ASSESSMENT_CODE, assessmentCode);
                serviceIntent.putStringArrayListExtra(SkavaConstants.EXTRA_PICTURES, picturesAsStringList);
                mContext.startService(serviceIntent);
                break;
            case DELETE:
                serviceIntent = new Intent(SkavaConstants.CUSTOM_ACTION);
                serviceIntent.putExtra(SkavaConstants.EXTRA_OPERATION, operation.name());
                serviceIntent.putExtra(SkavaConstants.EXTRA_ENVIRONMENT_NAME, folderName);
                serviceIntent.putExtra(SkavaConstants.EXTRA_INTERNAL_CODE, internalCode);
                serviceIntent.putExtra(SkavaConstants.EXTRA_ASSESSMENT_CODE, assessmentCode);
                //picturesAsStringList is expected to be null as the delete will remove all pictures
                //serviceIntent.putStringArrayListExtra(SkavaConstants.EXTRA_PICTURES, picturesAsStringList);
                mContext.startService(serviceIntent);
        }

    }


    public void deleteAllAssessments(boolean cascade) throws DAOException {
        List<DbxRecord> recordList = mAssessmentsTable.findAll();
        for (DbxRecord dbxRecord : recordList) {
            if (dbxRecord.hasField("code")) {
                String assessmentCode = dbxRecord.getString("code");
                deleteAssessment(assessmentCode, cascade);
            }
        }

        // If for some reason theres still some records on dropbox tables run the following
        List<DbxRecord> multipleQCalculations = mQBartonCalculationDropBoxTable.findAll();
        for (DbxRecord currCalculation : multipleQCalculations) {
            if (currCalculation != null) {
                currCalculation.deleteRecord();
            }
        }
        List<DbxRecord> multipleRmrCalculations = mRMRCalculationDropBoxTable.findAll();
        for (DbxRecord currCalculation : multipleRmrCalculations) {
            if (currCalculation != null) {
                currCalculation.deleteRecord();
            }
        }
        List<DbxRecord> multipleRecommendations = mSupportRecommendationDropboxTable.findAll();
        for (DbxRecord currRecommendation : multipleRecommendations) {
            if (currRecommendation != null) {
                currRecommendation.deleteRecord();
            }
        }
        List<DbxRecord> records = mDiscontinuitiesFamilyDropBoxTable.findAll();
        for (DbxRecord dbxRecord : records) {
            if (dbxRecord != null) {
                dbxRecord.deleteRecord();
            }
        }

    }


    @Override
    public void deleteAssessment(String assessmentCode, boolean cascade) throws DAOException {
        //Should be just one but if for some any reason (during DEV tests for example) there is multiple records for same assessmentCode
        List<DbxRecord> multipleAssessmentsWithSameCode = mAssessmentsTable.findRecordsByCriteria(new String[]{"code"}, new String[]{assessmentCode});

        //This is just the assessment syncing log table for eventual tracing
        AssessmentSyncTrace syncTrace = new AssessmentSyncTrace(assessmentCode);

        //This is the on-memory middleman log space (used somewhere to check if there´s any pending sync request)
        SyncQueue middlemanInbox = getSkavaContext().getMiddlemanInbox();

        for (DbxRecord assessmentRecordToDelete : multipleAssessmentsWithSameCode) {
            if (cascade) {
                //Againg should be just one but if for some any reason (during DEV tests for example) there is multiple records for same assessmentCode
                List<DbxRecord> multipleQCalculations = mQBartonCalculationDropBoxTable.findRecordsByCriteria(new String[]{"assessmentCode"}, new String[]{assessmentCode});
                for (DbxRecord currCalculation : multipleQCalculations) {
                    if (currCalculation != null) {
                        currCalculation.deleteRecord();
                    }
                }
                //Againg should be just one but ..
                List<DbxRecord> multipleRmrCalculations = mRMRCalculationDropBoxTable.findRecordsByCriteria(new String[]{"assessmentCode"}, new String[]{assessmentCode});
                for (DbxRecord currCalculation : multipleRmrCalculations) {
                    if (currCalculation != null) {
                        currCalculation.deleteRecord();
                    }
                }
                //Againg should be just one but ..
                List<DbxRecord> multipleRecommendations = mSupportRecommendationDropboxTable.findRecordsByCriteria(new String[]{"assessmentCode"}, new String[]{assessmentCode});
                for (DbxRecord currRecommendation : multipleRecommendations) {
                    if (currRecommendation != null) {
                        currRecommendation.deleteRecord();
                    }
                }
                //Here the multiplicity is not an error, nor strange, is in fact very usual
                List<DbxRecord> records = mDiscontinuitiesFamilyDropBoxTable.findRecordsByCriteria(new String[]{"assesmentCode"}, new String[]{assessmentCode});
                for (DbxRecord dbxRecord : records) {
                    if (dbxRecord != null) {
                        dbxRecord.deleteRecord();
                    }
                }
            }

            //Log the DELETE intention in the middle man records log space
            RecordToSync recordToSync = new RecordToSync(assessmentCode);
            recordToSync.setOperation(RecordToSync.Operation.DELETE);
            recordToSync.setSkavaEntityCode(assessmentCode);
            recordToSync.setRecordID(assessmentRecordToDelete.getId());
            recordToSync.setDate(SkavaUtils.getCurrentDate());
            recordToSync.setStatus(DataToSync.Status.QUEUED);

            //This is just the assessment syncing log table for eventual tracing
            syncTrace.addRecord(recordToSync);
            //This is the on-memory middleman log space (used somewhere to check if there´s any pending sync request)
            middlemanInbox.addRecord(recordToSync);

            //Don't forget to request also the deletion of pictures linked to this assessment
            String assessmentInternalCode = readString(assessmentRecordToDelete, "skavaInternalCode");
            ArrayList<String> picturesList = null;
            if (assessmentRecordToDelete.hasField("picturesURIs")) {
                DbxList dbxPictureLists = assessmentRecordToDelete.getList("picturesURIs");
                picturesList = new ArrayList<String>();
                for (int i = 0; i < dbxPictureLists.size(); i++) {
                    picturesList.add(dbxPictureLists.getString(i));
                }
            }
            if (assessmentInternalCode != null && picturesList != null && !picturesList.isEmpty()) {
                //delete the folder with the pictures of this assessment
                this.deletePictures(assessmentInternalCode, assessmentCode, picturesList);
            }

            //finally delete the remote assessment record
            assessmentRecordToDelete.deleteRecord();

        }
        //Before leaving save this assessmentSyncTrace for tracing purposes
        mSyncLoggingDAO.saveAssessmentSyncTrace(syncTrace);
    }


    private void deletePictures(final String assessmentInternalCode, final String assessmentCode, final ArrayList<String> picturesList) throws DAOException {

        String folderName = null;
        String target = getSkavaContext().getTargetEnvironment();
        if (target.equalsIgnoreCase(SkavaConstants.DEV_KEY)) {
            folderName = SkavaConstants.DROPBOX_DS_DEV_NAME;
        } else if (target.equalsIgnoreCase(SkavaConstants.QA_KEY)) {
            folderName = SkavaConstants.DROPBOX_DS_QA_NAME;
        } else if (target.equalsIgnoreCase(SkavaConstants.PROD_KEY)) {
            folderName = SkavaConstants.DROPBOX_DS_PROD_NAME;
        }


        //the param picturesList contains just the name of the picture, but the full Uri is needed so
//        ArrayList<String> picturesURIsAsStringList = null;
//        if (picturesList!= null && !picturesList.isEmpty()){
//            //pictureList must be full URI not just the name of the picture
//            picturesURIsAsStringList = new ArrayList<String>();
//            SkavaPictureFilesUtils filesUtils = new SkavaPictureFilesUtils(mContext);
//            File skavaPictureStorageDir = filesUtils.getSkavaPicturesFolder();
//            for (String currPictureName : picturesList) {
//                String pictureCompleteUri = skavaPictureStorageDir + File.separator + currPictureName;
//                //Why if this is goinig to delete remotely need to be URi of the local pic file???
//                //Where and when the local file is deleted??
//                File Uri.fromFile(pictureCompleteUri);
//                picturesURIsAsStringList.add(pictureCompleteUri);
//            }
//        }

        final String finalFolderName = folderName;
//        final ArrayList<String> finalPicturesURIsAsStringList = picturesURIsAsStringList;
        //it will delete the folder entirely so null is enough as pictureList param
        Thread myThread = new Thread(new Runnable() {
            public void run() {
                invokeTheService(DataToSync.Operation.DELETE, finalFolderName, assessmentInternalCode, assessmentCode, null);
            }
        });
        myThread.start();

        //This is just the assessment syncing log table for eventual tracing
        AssessmentSyncTrace syncTrace = new AssessmentSyncTrace(assessmentCode);
        //This is the on-memory middleman log space (used somewhere to check if there´s any pending sync request)
        SyncQueue middlemanInbox = getSkavaContext().getMiddlemanInbox();

        for (String currPicture : picturesList) {
            FileToSync pendingFile = new FileToSync(assessmentCode);
            //record each picture of this asssessment as a DELETE
            pendingFile.setOperation(DataToSync.Operation.DELETE);
            pendingFile.setFileName(currPicture);
            pendingFile.setDate(SkavaUtils.getCurrentDate());
            pendingFile.setStatus(DataToSync.Status.QUEUED);

            middlemanInbox.addFile(pendingFile);
            syncTrace.addFile(pendingFile);
        }
        //Before leaving save this assessmentSyncTrace for tracing purposes
        mSyncLoggingDAO.saveAssessmentSyncTrace(syncTrace);
    }


}