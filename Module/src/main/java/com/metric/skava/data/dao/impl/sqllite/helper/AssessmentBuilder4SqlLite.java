package com.metric.skava.data.dao.impl.sqllite.helper;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.DateDataFormat;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.LocalFractureTypeDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentTable;
import com.metric.skava.rockmass.model.FractureType;

import java.util.Calendar;

/**
 * Created by metricboy on 3/18/14.
 */
public class AssessmentBuilder4SqlLite {

    private final Context mContext;

    private LocalExcavationProjectDAO localExcavationProjectDAO;

    private LocalTunnelDAO localTunnelDAO;
    private LocalExcavationSectionDAO localExcavationSectionDAO;
    private LocalTunnelFaceDAO localTunnelFaceDAO;
    private LocalUserDAO localUserDAO;
    private LocalExcavationMethodDAO localExcavationMethodDAO;
    private LocalFractureTypeDAO localFractureTypeDAO;

    public AssessmentBuilder4SqlLite(Context context, SkavaContext skavaContext) throws DAOException {
        this.mContext = context;

        DAOFactory daoFactory = skavaContext.getDAOFactory();

        localExcavationProjectDAO = daoFactory.getLocalExcavationProjectDAO();

        localTunnelDAO = daoFactory.getLocalTunnelDAO();
        localExcavationSectionDAO = daoFactory.getLocalExcavationSectionDAO();
        localTunnelFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        localUserDAO = daoFactory.getLocalUserDAO();
        localExcavationMethodDAO = daoFactory.getLocalExcavationMethodDAO();
        localFractureTypeDAO = daoFactory.getLocalFractureTypeDAO();

    }


    public Assessment buildBareAssessmentFromCursorRecord(Cursor cursor) throws DAOException {

        String code = CursorUtils.getString(AssessmentTable.CODE_COLUMN, cursor);
        String internalCode = CursorUtils.getString(AssessmentTable.INTERNAL_CODE_COLUMN, cursor);

        Assessment babyAssessment = new Assessment(code);
        babyAssessment.setInternalCode(internalCode);

        String environmentCode = CursorUtils.getString(AssessmentTable.ENVIRONMENT_COLUMN, cursor);
        if (environmentCode != null) {
            if (    environmentCode.equalsIgnoreCase(SkavaConstants.DEV_KEY)
                    || environmentCode.equalsIgnoreCase(SkavaConstants.QA_KEY)
                    || environmentCode.equalsIgnoreCase(SkavaConstants.PROD_KEY)
                ) {
                babyAssessment.setEnvironment(environmentCode);
            } else {
                throw new DAOException("Unknown environment code: " + environmentCode);
            }
        }

        String faceID = CursorUtils.getString(AssessmentTable.TUNEL_FACE_CODE_COLUMN, cursor);
        if (faceID != null) {
            TunnelFace tunnelFace = localTunnelFaceDAO.getTunnelFaceByCode(faceID);
            babyAssessment.setFace(tunnelFace);
        }

        String geologistID = CursorUtils.getString(AssessmentTable.GEOLOGIST_CODE_COLUMN, cursor);
        if (geologistID != null) {
            User geologist = localUserDAO.getUserByCode(geologistID);
            babyAssessment.setGeologist(geologist);
        }

        Long dateAsLongRep = CursorUtils.getLong(AssessmentTable.DATE_COLUMN, cursor);
        Calendar date = DateDataFormat.getDateTimeFromFormattedLong(dateAsLongRep);
        babyAssessment.setDateTime(date);

        java.lang.String sectionID = CursorUtils.getString(AssessmentTable.EXCAVATION_SECTION_CODE_COLUMN, cursor);
        if (sectionID != null) {
            ExcavationSection section = localExcavationSectionDAO.getExcavationSectionByCode(sectionID);
            babyAssessment.setSection(section);
        }

        java.lang.String methodID = CursorUtils.getString(AssessmentTable.EXCAVATION_METHOD_CODE_COLUMN, cursor);
        if (methodID != null) {
            ExcavationMethod method = localExcavationMethodDAO.getExcavationMethodByCode(methodID);
            babyAssessment.setMethod(method);
        }

        Double initialPk = CursorUtils.getDouble(AssessmentTable.INITIAL_CHAINAGE_COLUMN, cursor);
        babyAssessment.setInitialPeg(initialPk);

        Double finalPk = CursorUtils.getDouble(AssessmentTable.FINAL_CHAINAGE_COLUMN, cursor);
        babyAssessment.setFinalPeg(finalPk);

        Double referencePk = CursorUtils.getDouble(AssessmentTable.REFERENCE_CHAINAGE_COLUMN, cursor);
        babyAssessment.setReferenceChainage(referencePk);

        Double advance = CursorUtils.getDouble(AssessmentTable.ADVANCE_COLUMN, cursor);
        babyAssessment.setCurrentAdvance(advance);

        Double accumAvance = CursorUtils.getDouble(AssessmentTable.ACCUM_ADVANCE_COLUMN, cursor);
        babyAssessment.setAccummAdvance(accumAvance);

        Long orientation =  CursorUtils.getLong(AssessmentTable.ORIENTATION_COLUMN, cursor);
        babyAssessment.setOrientation(orientation.shortValue());

        Double slope = CursorUtils.getDouble(AssessmentTable.SLOPE_COLUMN, cursor);
        babyAssessment.setSlope(slope.doubleValue());

        java.lang.String fractureTypeID = CursorUtils.getString(AssessmentTable.FRACTURE_TYPE_CODE_COLUMN, cursor);
        if (fractureTypeID != null) {
            FractureType fractureType = localFractureTypeDAO.getFractureTypeByCode(fractureTypeID);
            babyAssessment.setFractureType(fractureType);
        }

        Double blockSize = CursorUtils.getDouble(AssessmentTable.BLOCKS_SIZE_COLUMN, cursor);
        babyAssessment.setBlockSize(blockSize.doubleValue());

        Long numJoints = CursorUtils.getLong(AssessmentTable.NUMBER_JOINTS_COLUMN, cursor);
        babyAssessment.setNumberOfJoints(numJoints.shortValue());

        java.lang.String outcrop = CursorUtils.getString(AssessmentTable.OUTCROP_COLUMN, cursor);
        babyAssessment.setOutcropDescription(outcrop);

        java.lang.String rockSampleIdentification = CursorUtils.getString(AssessmentTable.ROCK_SAMPLE_IDENTIFICATION_COLUMN, cursor);
        babyAssessment.setRockSampleIdentification(rockSampleIdentification);

        String dataSentStatus = CursorUtils.getString(AssessmentTable.DATA_SENT_STATUS_COLUMN, cursor);
        babyAssessment.setDataSentStatus(Assessment.SendingStatus.valueOf(dataSentStatus));

        String filesSentStatus = CursorUtils.getString(AssessmentTable.FILES_SENT_STATUS_COLUMN, cursor);
        babyAssessment.setPicsSentStatus(Assessment.SendingStatus.valueOf(filesSentStatus));

        String savingStatus = CursorUtils.getString(AssessmentTable.SAVING_STATUS_COLUMN, cursor);
        babyAssessment.setSavedStatus(Assessment.SavingStatus.valueOf(savingStatus));

        return babyAssessment;
    }

}
