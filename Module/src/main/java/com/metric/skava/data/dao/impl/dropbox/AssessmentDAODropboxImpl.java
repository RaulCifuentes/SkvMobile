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
import com.metric.skava.calculator.barton.logic.QBartonOutput;
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
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportRecomendation;
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
        this.assessmentBuilder = new AssessmentBuilder4DropBox(skavaContext);
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
            if (project != null) {
                assessmentFields.set("project", project.getCode());
            }

            Tunnel tunnel = assessment.getTunnel();
            if (tunnel != null) {
                assessmentFields.set("tunnel", tunnel.getCode());
            }

            TunnelFace face = assessment.getFace();
            if (face != null) {
                assessmentFields.set("face", face.getCode());
            }

            ExcavationSection section = assessment.getSection();
            if (section != null) {
                assessmentFields.set("section", section.getCode());
            }

            Date date = assessment.getDate();
            if (date != null) {
                assessmentFields.set("date", date);
            }

            User geologist = assessment.getGeologist();
            if (geologist != null) {
                assessmentFields.set("geologist", geologist.getCode());
            }

            Double PK = assessment.getInitialPeg();
            if (PK != null) {
                assessmentFields.set("pk", PK);
            }

            Double advance = assessment.getCurrentAdvance();
            if (advance != null) {
                assessmentFields.set("advance", advance);
            }

            ExcavationMethod method = assessment.getMethod();
            if (method != null) {
                assessmentFields.set("method", method.getCode());
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
            if (fractureType != null) {
                assessmentFields.set("fractureType", fractureType.getCode());
            }

            Double blockSize = assessment.getBlockSize();
            if (blockSize != null) {
                assessmentFields.set("blockSize", blockSize);
            }

            Short numJoints = assessment.getNumberOfJoints();
            if (numJoints != null) {
            assessmentFields.set("numJoints", numJoints);
            }

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
            if (discontinuitySystem != null) {

                DbxList discontinuitiesFamilySystem = new DbxList();

                for (DiscontinuityFamily family : discontinuitySystem) {
                    if (family == null || !family.isComplete()) {
                        continue;
                    }
                    DbxFields discontinuityFamilyFields = new DbxFields();

                    discontinuityFamilyFields.set("assesment_code", assessment.getCode());

                    int number = family.getNumber();
                    discontinuityFamilyFields.set("number", number);

                    DiscontinuityType type = family.getType();
                    if (type != null) {
                        discontinuityFamilyFields.set("type", type.getCode());
                    }

                    DiscontinuityRelevance relevance = family.getRelevance();
                    if (relevance != null) {
                        discontinuityFamilyFields.set("relevance", relevance.getCode());
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
                    if (spacing != null) {
                        discontinuityFamilyFields.set("spacing", spacing.getKey());
                    }

                    Persistence persistence = family.getPersistence();
                    if (persistence != null) {
                        discontinuityFamilyFields.set("persistence", persistence.getKey());
                    }

                    Aperture aperture = family.getAperture();
                    if (aperture != null) {
                        discontinuityFamilyFields.set("aperture", aperture.getKey());
                    }

                    DiscontinuityShape shape = family.getShape();
                    if (shape != null) {
                        discontinuityFamilyFields.set("shape", shape.getCode());
                    }

                    Roughness roughness = family.getRoughness();
                    if (roughness != null) {
                    discontinuityFamilyFields.set("roughness", roughness.getKey());
                    }

                    Infilling infilling = family.getInfilling();
                    if (infilling != null) {
                        discontinuityFamilyFields.set("infilling", infilling.getKey());
                    }

                    Weathering weathering = family.getWeathering();
                    if (weathering != null) {
                    discontinuityFamilyFields.set("weathering", weathering.getKey());
                    }

                    DiscontinuityWater water = family.getWater();
                    if (water != null) {
                    discontinuityFamilyFields.set("water", water.getCode());
                    }

                    DbxRecord discontinuityFamilyRecord = mDiscontinuitiesFamilyDropBoxTable.persist(discontinuityFamilyFields);

                    discontinuitiesFamilySystem.add(discontinuityFamilyRecord.getId());
                }

                assessmentFields.set("discontinuitiesSystem", discontinuitiesFamilySystem);
            }

            SupportRecomendation recomendation = assessment.getRecomendation();
            if (recomendation != null){
                BoltType boltType = recomendation.getBoltType();
                if (boltType != null) {
                assessmentFields.set("boltType", boltType.getCode());
                }

                Double boltDiameter = recomendation.getBoltDiameter();
                if (boltDiameter != null) {
                assessmentFields.set("boltDiameter", boltDiameter);
                }

                Double boltLength = recomendation.getBoltLength();
                if (boltLength != null) {
                    assessmentFields.set("boltLength", boltLength);
                }

                ShotcreteType shotcreteType = recomendation.getShotcreteType();
                if (shotcreteType != null) {
                    assessmentFields.set("shotcreteType", shotcreteType.getCode());
                }

                Double thickness = recomendation.getThickness();
                if (thickness != null) {
                    assessmentFields.set("thickness", thickness);
                }

                MeshType meshType = recomendation.getMeshType();
                if (meshType != null) {
                    assessmentFields.set("meshType", meshType.getCode());
                }

                Coverage coverage = recomendation.getCoverage();
                if (coverage != null) {
                    assessmentFields.set("coverage", coverage.getCode());
                }

                ArchType archType = recomendation.getArchType();
                if (archType != null) {
                    assessmentFields.set("archType", archType.getCode());
                }

                Double separation = recomendation.getSeparation();
                if (separation != null) {
                    assessmentFields.set("separation", separation);
                }

                String observations = recomendation.getObservations();
                if (observations != null) {
                    assessmentFields.set("observations", observations);
                }
            }

            RMR_Calculation rmrCalculation = assessment.getRmrCalculation();

            if (rmrCalculation != null) {

                DbxFields rmrCalculationFields = new DbxFields();

                StrengthOfRock strengthOfRock = rmrCalculation.getStrengthOfRock();
                if (strengthOfRock != null) {
                    rmrCalculationFields.set("strength", strengthOfRock.getKey());
                }

                Spacing spacingDiscontinuities = rmrCalculation.getSpacing();
                if (spacingDiscontinuities != null) {
                rmrCalculationFields.set("spacing", spacingDiscontinuities.getKey());
                }

                Persistence persistence = rmrCalculation.getPersistence();
                if (persistence != null) {
                    rmrCalculationFields.set("persistence", persistence.getKey());
                }

                Aperture aperture = rmrCalculation.getAperture();
                if (aperture != null) {
                    rmrCalculationFields.set("aperture", aperture.getKey());
                }

                Roughness roughness = rmrCalculation.getRoughness();
                if (roughness != null) {
                rmrCalculationFields.set("roughness", roughness.getKey());
                }

                Infilling infilling = rmrCalculation.getInfilling();
                if (infilling != null) {
                    rmrCalculationFields.set("infilling", infilling.getKey());
                }

                Weathering weathering = rmrCalculation.getWeathering();
                if (weathering != null) {
                    rmrCalculationFields.set("weathering", weathering.getKey());
                }

                Groundwater groundwater = rmrCalculation.getGroundwater();
                if (groundwater != null) {
                    rmrCalculationFields.set("groundwater", groundwater.getKey());
                }

                int orientationType = rmrCalculation.getOrientationType();
                rmrCalculationFields.set("orientationType", orientationType);

                OrientationDiscontinuities orientationDiscontinuities = rmrCalculation.getOrientationDiscontinuities();
                if (orientationDiscontinuities != null) {
                    rmrCalculationFields.set("orientation", orientationDiscontinuities.getKey());
                }

                RQD rqd = rmrCalculation.getRqd();
                if (rqd != null) {
                rmrCalculationFields.set("rqd", rqd.getValue());
                }

                mRMRCalculationDropBoxTable.persist(rmrCalculationFields);
            }

            Q_Calculation qCalculation = assessment.getQCalculation();
            if (qCalculation != null) {
                DbxFields qCalculationFields = new DbxFields();
                qCalculationFields.set("assessment_code", assessment.getCode());

                RQD rqd = qCalculation.getRqd();
                if (rqd != null) {
                    qCalculationFields.set("rqd", rqd.getValue());
                }

                Jn jn = qCalculation.getJn();
                if (jn != null) {
                    qCalculationFields.set("jn", jn.getValue());
                }

                Jr jr = qCalculation.getJr();
                if (jr != null) {
                    qCalculationFields.set("jr", jr.getValue());
                }

                Ja ja = qCalculation.getJa();
                if (ja != null) {
                    qCalculationFields.set("ja", ja.getValue());
                }

                Jw jw = qCalculation.getJw();
                if (jw != null) {
                    qCalculationFields.set("jw", jw.getValue());
                }

                SRF srf = qCalculation.getSrf();
                if (srf != null) {
                    qCalculationFields.set("srf", srf.getKey());
                }

                QBartonOutput qBarton = qCalculation.getQResult();
                if (qBarton != null) {
                    qCalculationFields.set("qBarton", qBarton.getQBarton());
                }

                mQBartonCalculationDropBoxTable.persist(qCalculationFields);
            }

            mAssessmentsTable.persist(assessmentFields);

            getDatastore().sync();

        } catch (DbxException e) {
            throw new DAOException(e);
        } catch (Exception e){
            throw new DAOException(e);
        }
    }
}