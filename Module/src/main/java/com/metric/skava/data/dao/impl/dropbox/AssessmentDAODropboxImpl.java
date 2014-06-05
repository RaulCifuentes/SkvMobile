package com.metric.skava.data.dao.impl.dropbox;

import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxList;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.R;
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
import com.metric.skava.pictures.util.SkavaFilesUtils;
import com.metric.skava.rockmass.model.FractureType;
import com.metric.skava.rocksupport.model.ESR;
import com.metric.skava.rocksupport.model.SupportRequirement;
import com.metric.skava.sync.model.SyncLogEntry;

import java.io.File;
import java.io.IOException;
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
    private SkavaFilesUtils mFilesUtils;


    public AssessmentDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mAssessmentsTable = new AssessmentDropboxTable(getDatastore());
        this.mDiscontinuitiesFamilyDropBoxTable = new DiscontinuitiesFamilyDropboxTable(getDatastore());
        this.mRMRCalculationDropBoxTable = new RMRCalculationDropboxTable(getDatastore());
        this.mQBartonCalculationDropBoxTable = new QBartonCalculationDropboxTable(getDatastore());
        this.mSupportRecommendationDropboxTable = new SupportRecommendationDropboxTable(getDatastore());
        this.assessmentBuilder = new AssessmentBuilder4DropBox(skavaContext);
        this.mFilesUtils = new SkavaFilesUtils(context);
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
    public void saveAssessment(Assessment assessment) throws DAOException {
        try {

            DbxFields assessmentFields = new DbxFields();

            String code = assessment.getCode();
            assessmentFields.set("code", code);

            String internalCode = assessment.getInternalCode();
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

                    String familyRecordID = mDiscontinuitiesFamilyDropBoxTable.persist(discontinuityFamilyFields);

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

                mSupportRecommendationDropboxTable.persist(supportRecommendationFields);
            }

            RMR_Calculation rmrCalculation = assessment.getRmrCalculation();

            if (rmrCalculation != null) {

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

                mRMRCalculationDropBoxTable.persist(rmrCalculationFields);
            }

            Q_Calculation qCalculation = assessment.getQCalculation();
            if (qCalculation != null) {
                DbxFields qCalculationFields = new DbxFields();
                qCalculationFields.set("assessmentCode", assessment.getCode());

                if (tunnel != null) {
                    ESR esr = tunnel.getExcavationFactors().getEsr();
                    if (esr != null) {
                        qCalculationFields.set("esr", esr.getCode());
                    }
                }

                //rockQualityCode
                if (qCalculation.getQResult() != null){
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

                mQBartonCalculationDropBoxTable.persist(qCalculationFields);
            }

            List<Uri> pictureList = assessment.getPictureUriList();
            DbxList uriEncodedList = new DbxList();
            for (Uri uri : pictureList) {
                if (uri != null) {
                    java.lang.String uriEncoded = uri.getLastPathSegment();
                    uriEncodedList.add(uriEncoded);
                }
            }

            assessmentFields.set("picturesURIs", uriEncodedList);

            mAssessmentsTable.persist(assessmentFields);

            getDatastore().sync();

            if (SkavaUtils.hasPictures(pictureList)) {
                // executes the uploading as an async task
                new PictureUploader().execute(assessment.getInternalCode(), pictureList);
            }

        } catch (DbxException e) {
            throw new DAOException(e);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }


    public void deleteAllAssessments(boolean cascade) throws DAOException {
        List<DbxRecord> recordList = mAssessmentsTable.findAll();
        for (DbxRecord dbxRecord : recordList) {
            String assessmentCode = dbxRecord.getString("code");
            deleteAssessment(assessmentCode, cascade);
        }
        List<DbxRecord> discontinuities = mDiscontinuitiesFamilyDropBoxTable.findAll();
        if (discontinuities != null && !discontinuities.isEmpty()){
            for (DbxRecord discontinuity : discontinuities) {
                discontinuity.deleteRecord();
            }
            try {
                getDatastore().sync();
            } catch (DbxException e) {
                throw new DAOException(e);
            }
        }

    }


    @Override
    public void deleteAssessment(String code, boolean cascade) throws DAOException {
        DbxRecord record = mAssessmentsTable.findRecordByCode(code);
        record.deleteRecord();
        if (cascade) {
            record = mQBartonCalculationDropBoxTable.findRecordByCandidateKey("assessmentCode", code);
            if (record != null) {
                record.deleteRecord();
            }

            record = mRMRCalculationDropBoxTable.findRecordByCandidateKey("assessmentCode", code);
            if (record != null) {
                record.deleteRecord();
            }

            record = mSupportRecommendationDropboxTable.findRecordByCandidateKey("assessmentCode", code);
            if (record != null) {
                record.deleteRecord();
            }

            record = mDiscontinuitiesFamilyDropBoxTable.findRecordByCandidateKey("assesmentCode", code);
            if (record != null) {
                record.deleteRecord();
            }
        }
        try {
            getDatastore().sync();
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }

    class PictureUploader extends AsyncTask<Object, Void, Integer> {

        SyncLogEntry errorCondition;
        String assessmentCode;

        @Override
        protected Integer doInBackground(Object[] params) {
            assessmentCode = (String) params[0];
            List<Uri> urlList = (List<Uri>) params[1];
            SyncLogEntry.Source source = null;
            try {
                DbxDatastoreStatus status = getDatastore().getSyncStatus();
                source = status.isConnected ? SyncLogEntry.Source.DROPBOX_REMOTE_DATASTORE : SyncLogEntry.Source.DROPBOX_LOCAL_DATASTORE;
                Integer numPictures = uploadPictures(assessmentCode, urlList);
                if (numPictures == -1) {
                    errorCondition = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncLogEntry.Domain.PICTURES, source, SyncLogEntry.Status.FAIL, 0L);
                }
                return numPictures;
            } catch (DAOException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                errorCondition = new SyncLogEntry(SkavaUtils.getCurrentDate(), SyncLogEntry.Domain.PICTURES, source, SyncLogEntry.Status.FAIL, 0L);
                errorCondition.setMessage(e.getMessage());
                return -1;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == -1 || errorCondition != null) {
                NotificationCompat.Builder mBuilder;
                mBuilder = new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.cloud_icon)
                        .setContentTitle("Skava Mobile notifies")
                        .setContentText("Picture uploading failed :( ");
                // Sets an ID for the notification
                int mNotificationId = 001;
                // Gets an instance of the NotificationManager service
                NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
                // Builds the notification and issues it.
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
            } else {
                //mostrar que termino exitosamente
                NotificationCompat.Builder mBuilder;
                mBuilder = new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.cloud_icon)
                        .setContentTitle("Skava Mobile notifies")
                        .setContentText("Picture uploading succeed !!");
                int mNotificationId = 010;
                // Gets an instance of the NotificationManager service
                NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.build());
                // Update the assessment to inform also the pictures complete
                try {
                    Assessment localAssessment = getDAOFactory().getLocalAssessmentDAO().getAssessment(assessmentCode);
                    localAssessment.setSentToCloud(Assessment.PICS_SENT_TO_CLOUD);
                } catch (DAOException e) {
                    Log.e(SkavaConstants.LOG, e.getMessage());
                }
            }
        }
    }


    private int uploadPictures(String internalCode, List<Uri> pictures) throws DAOException {
        // Create DbxFileSystem for synchronized file access.
        DbxFileSystem dbxFs = getSkavaContext().getFileSystem();

        if (dbxFs.isShutDown()) {
            Log.e(SkavaConstants.LOG, "DbxFileSystem is shutted down");
            throw new DAOException("DbxFileSystem is shutted down");
        }

        //Create a folder named after the datastore's name
        //The datastore name depends on the target environment, so ...
        String folderName = null;
        String target =  getSkavaContext().getTargetEnvironment();
        if (target.equalsIgnoreCase(SkavaConstants.DEV_KEY)) {
            folderName = SkavaConstants.DROPBOX_DS_DEV_NAME;
        } else if (target.equalsIgnoreCase(SkavaConstants.QA_KEY)) {
            folderName = SkavaConstants.DROPBOX_DS_QA_NAME;
        } else if (target.equalsIgnoreCase(SkavaConstants.PROD_KEY)) {
            folderName = SkavaConstants.DROPBOX_DS_PROD_NAME;
        }

        DbxPath skavaFolderPath = new DbxPath(DbxPath.ROOT, folderName);
        try {
            if (!dbxFs.exists(skavaFolderPath)) {
                dbxFs.createFolder(skavaFolderPath);
            }
//            boolean hasSynced = dbxFs.hasSynced();
//            System.out.println("hasSynced = " + hasSynced);
//
//            DbxSyncStatus syncStatus = dbxFs.getSyncStatus();
//            System.out.println("syncStatus = " + syncStatus);
//
//            DbxFileInfo fileInfo = dbxFs.getFileInfo(skavaFolderPath);
//            System.out.println("fileInfo = " + fileInfo);
            DbxPath projectPath = new DbxPath(skavaFolderPath, internalCode);
            for (Uri pictureURI : pictures) {
                if (pictureURI != null) {
                    String name = pictureURI.getLastPathSegment();
                    DbxPath filePath = new DbxPath(projectPath, name);
                    DbxFile targetFile = dbxFs.create(filePath);
                    try {
                        File tabletFile = mFilesUtils.getExistingFileFromUri(pictureURI);
                        targetFile.writeFromExistingFile(tabletFile, false);
                    } finally {
                        targetFile.close();
                    }
                }
            }
            dbxFs.syncNowAndWait();
            return pictures.size();
        } catch (NullPointerException npe) {
            if (npe != null) {
                npe.printStackTrace();
                Log.e(SkavaConstants.LOG, npe.getMessage());
                throw new DAOException(npe);
            }
        } catch (DbxException dbxe) {
            if (dbxe != null) {
                dbxe.printStackTrace();
                Log.e(SkavaConstants.LOG, dbxe.getMessage());
                throw new DAOException(dbxe);
            }
        } catch (IOException ioe) {
            if (ioe != null) {
                ioe.printStackTrace();
                Log.e(SkavaConstants.LOG, ioe.getMessage());
                throw new DAOException(ioe);
            }
        }
        return -1;
    }

}