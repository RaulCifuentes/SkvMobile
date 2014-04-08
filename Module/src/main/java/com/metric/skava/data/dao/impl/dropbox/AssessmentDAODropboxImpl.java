package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;
import android.net.Uri;

import com.dropbox.sync.android.DbxException;
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
import com.metric.skava.instructions.model.SupportRecomendation;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.rockmass.model.FractureType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public class AssessmentDAODropboxImpl extends DropBoxBaseDAO implements RemoteAssessmentDAO {

    private AssessmentDropboxTable mAssessmentsTable;
    private DiscontinuitiesFamilyDropboxTable mDiscontinuitiesFamilyDropBoxTable;
    private RMRCalculationDropboxTable mRMRCalculationDropBoxTable;
    private QBartonCalculationDropboxTable mQBartonCalculationDropBoxTable;
    private AssessmentBuilder4DropBox assessmentBuilder;


    public AssessmentDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mAssessmentsTable = new AssessmentDropboxTable(getDatastore());
        this.mDiscontinuitiesFamilyDropBoxTable = new DiscontinuitiesFamilyDropboxTable(getDatastore());
        this.mRMRCalculationDropBoxTable = new RMRCalculationDropboxTable(getDatastore());
        this.mQBartonCalculationDropBoxTable = new QBartonCalculationDropboxTable(getDatastore());
        this.assessmentBuilder = new AssessmentBuilder4DropBox(context);
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


    //    public List<Assessment> getAsessmentsSorted() throws DbxException {
//        List<Assessment> resultList = new ArrayList<Assessment>();
//        for (DbxRecord result : mAssessmentsTable.query()) {
//            resultList.add(new AssessmentDTO(result));
//        }
//        Collections.sort(resultList, new Comparator<AssessmentDTO>() {
//            @Override
//            public int compare(AssessmentDTO o1, AssessmentDTO o2) {
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        });
//        return resultList;
//    }

//    @Override
//    public Assessment getAssessment(String code) throws DAOException {
//        Assessment assessment;
//        DbxRecord assessmentRecord = mAssessmentsTable.findRecordByInternalCode(code);
//        assessment = assessmentBuilder.buildAssessmentFromRecord(assessmentRecord);
//        return assessment;
//    }


    @Override
    public void saveAssessment(Assessment assessment) throws DAOException {
        try {

            DbxFields assessmentFields = new DbxFields();

            String code = assessment.getCode();
            assessmentFields.set("code", code);

            String internalCode = assessment.getInternalCode();
            assessmentFields.set("skavaInternalCode", internalCode);

            ExcavationProject project = assessment.getProject();
            assessmentFields.set("project", project.getCode());

            Tunnel tunnel = assessment.getTunnel();
            assessmentFields.set("tunnel", tunnel.getCode());

            TunnelFace face = assessment.getFace();
            assessmentFields.set("face", face.getCode());

            ExcavationSection section = assessment.getSection();
            assessmentFields.set("section", section.getCode());

            Date date = assessment.getDate();
            assessmentFields.set("date", date);

            User geologist = assessment.getGeologist();
            assessmentFields.set("geologist", geologist.getCode());

            Double PK = assessment.getInitialPeg();
            assessmentFields.set("pk", PK);

            Double advance = assessment.getCurrentAdvance();
            assessmentFields.set("advance", advance);

            ExcavationMethod method = assessment.getMethod();
            assessmentFields.set("method", method.getCode());

            Short orientation = assessment.getOrientation();
            assessmentFields.set("orientation", orientation);

            Short slope = assessment.getSlope();
            assessmentFields.set("slope", slope);

            FractureType fractureType = assessment.getFractureType();
            assessmentFields.set("fractureType", fractureType.getCode());

            Short blockSize = assessment.getBlockSize();
            assessmentFields.set("blockSize", blockSize);

            Short numJoints = assessment.getNumberOfJoints();
            assessmentFields.set("numJoints", numJoints);

            List<Uri> pictureList = assessment.getPictureUriList();
            //TODO Check the toString/parse methods or encode/decode to persist the URI info
            DbxList uriEncodedList = new DbxList();
            for (Uri uri : pictureList) {
                if (uri != null){
                    java.lang.String uriEncoded = uri.toString();
                    uriEncodedList.add(uriEncoded);
                }
            }
            assessmentFields.set("uri", uriEncodedList);

            List<DiscontinuityFamily> discontinuitySystem = assessment.getDiscontinuitySystem();

            DbxList discontinuitiesFamilySystem = new DbxList();

            for (DiscontinuityFamily family : discontinuitySystem) {
                DbxFields discontinuityFamilyFields = new DbxFields();

                int number = family.getNumber();
                discontinuityFamilyFields.set("number", number);

                DiscontinuityType type = family.getType();
                discontinuityFamilyFields.set("type", type.getCode());

                DiscontinuityRelevance relevance = family.getRelevance();
                discontinuityFamilyFields.set("relevance", relevance.getCode());

                Short dipDirDegrees = family.getDipDirDegrees();
                discontinuityFamilyFields.set("dipDirDegrees", dipDirDegrees);

                Short dipDegrees = family.getDipDegrees();
                discontinuityFamilyFields.set("dipDegrees", dipDegrees);

                Spacing spacing = family.getSpacing();
                discontinuityFamilyFields.set("spacing", spacing.getKey());

                Persistence persistence = family.getPersistence();
                discontinuityFamilyFields.set("persistence", persistence.getKey());

                Aperture aperture = family.getAperture();
                discontinuityFamilyFields.set("aperture", aperture.getKey());

                DiscontinuityShape shape = family.getShape();
                discontinuityFamilyFields.set("shape", shape.getCode());

                Roughness rouhness = family.getRoughness();
                discontinuityFamilyFields.set("roughness", rouhness.getKey());

                Infilling infilling = family.getInfilling();
                discontinuityFamilyFields.set("infilling", infilling.getKey());

                Weathering weathering = family.getWeathering();
                discontinuityFamilyFields.set("weathering", weathering.getKey());

                DiscontinuityWater water = family.getWater();
                discontinuityFamilyFields.set("water", water.getCode());

                DbxRecord discontinuityFamilyRecord = mDiscontinuitiesFamilyDropBoxTable.persist(discontinuityFamilyFields);

                discontinuitiesFamilySystem.add(discontinuityFamilyRecord.getId());
            }

            assessmentFields.set("discontinuitiesSystem", discontinuitiesFamilySystem);


            SupportRecomendation recomendation = assessment.getRecomendation();

            BoltType boltType = recomendation.getBoltType();
            assessmentFields.set("boltType", boltType.getCode());

            Double boltDiameter = recomendation.getBoltDiameter();
            assessmentFields.set("boltDiameter", boltDiameter);

            Double boltLength = recomendation.getBoltLength();
            assessmentFields.set("boltLength", boltLength);

            ShotcreteType shotcreteType = recomendation.getShotcreteType();
            assessmentFields.set("shotcreteType", shotcreteType.getCode());

            Double thickness = recomendation.getThickness();
            assessmentFields.set("thickness", thickness);

            MeshType meshType = recomendation.getMeshType();
            assessmentFields.set("meshType", meshType.getCode());

            Coverage coverage = recomendation.getCoverage();
            assessmentFields.set("coverage", coverage.getCode());

            ArchType archType = recomendation.getArchType();
            assessmentFields.set("archType", archType.getCode());

            Double separation = recomendation.getSeparation();
            assessmentFields.set("separation", separation);

            java.lang.String observations = recomendation.getObservations();
            assessmentFields.set("observations", observations);

            RMR_Calculation rmrCalculation = assessment.getRmrCalculation();

            DbxFields rmrCalculationFields = new DbxFields();

            StrengthOfRock strengthOfRock = rmrCalculation.getStrengthOfRock();
            rmrCalculationFields.set("strength", strengthOfRock.getKey());

            Spacing spacingDiscontinuities = rmrCalculation.getSpacing();
            rmrCalculationFields.set("spacing", spacingDiscontinuities.getKey());

            Persistence persistence = rmrCalculation.getPersistence();
            rmrCalculationFields.set("persistence", persistence.getKey());

            Aperture aperture = rmrCalculation.getAperture();
            rmrCalculationFields.set("aperture", aperture.getKey());

            Roughness roughness = rmrCalculation.getRoughness();
            rmrCalculationFields.set("roughness", roughness.getKey());

            Infilling infilling = rmrCalculation.getInfilling();
            rmrCalculationFields.set("infilling", infilling.getKey());

            Weathering weathering = rmrCalculation.getWeathering();
            rmrCalculationFields.set("weathering", weathering.getKey());

            Groundwater groundwater = rmrCalculation.getGroundwater();
            rmrCalculationFields.set("groundwater", groundwater.getKey());

            int orientationType = rmrCalculation.getOrientationType();
            rmrCalculationFields.set("orientationType", orientationType);
            OrientationDiscontinuities orientationDiscontinuities = rmrCalculation.getOrientationDiscontinuities();
            rmrCalculationFields.set("orientation", orientationDiscontinuities.getKey());

            RQD_RMR rqd = rmrCalculation.getRqd();
            rmrCalculationFields.set("rqd", rqd.getKey());

//            ConditionDiscontinuities conditionDiscontinuities = rmrCalculation.getConditionDiscontinuities();
//            rmrCalculationFields.set();

            mRMRCalculationDropBoxTable.persist(rmrCalculationFields);


            mAssessmentsTable.persist(assessmentFields);

            getDatastore().sync();

        } catch (DbxException e) {
            throw new DAOException(e);
        } catch (Exception e){
            throw new DAOException(e);
        }

    }


}
