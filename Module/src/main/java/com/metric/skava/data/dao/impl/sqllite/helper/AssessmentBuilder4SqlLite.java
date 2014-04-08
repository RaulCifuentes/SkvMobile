package com.metric.skava.data.dao.impl.sqllite.helper;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.DateDataFormat;
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

import java.util.Date;

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

    //http://joesapps.blogspot.com/2011/02/managing-date-and-time-data-in-android.html
//    private final java.lang.String DATE_FORMAT = "yyyyMMddHHmmss";
//    private final SimpleDateFormat dateFormat;

    public AssessmentBuilder4SqlLite(Context context) throws DAOException {
        this.mContext = context;

        DAOFactory daoFactory = DAOFactory.getInstance(mContext);

        localExcavationProjectDAO = daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE);

        localTunnelDAO = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE);
        localExcavationSectionDAO = daoFactory.getLocalExcavationSectionDAO(DAOFactory.Flavour.SQLLITE);
        localTunnelFaceDAO = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
        localUserDAO = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE);
        localExcavationMethodDAO = daoFactory.getLocalExcavationMethodDAO(DAOFactory.Flavour.SQLLITE);
        localFractureTypeDAO = daoFactory.getFractureTypeDAO();

    }


    public Assessment buildAssessmentFromCursorRecord(Cursor cursor) throws DAOException {

        String code = CursorUtils.getString(AssessmentTable.CODE_COLUMN, cursor);
        String internalCode = CursorUtils.getString(AssessmentTable.INTERNAL_CODE_COLUMN, cursor);

        Assessment babyAssessment = new Assessment(code);
        babyAssessment.setInternalCode(internalCode);

        java.lang.String faceID = CursorUtils.getString(AssessmentTable.TUNEL_FACE_CODE_COLUMN, cursor);
        TunnelFace tunnelFace = localTunnelFaceDAO.getTunnelFaceByCode(faceID);
        babyAssessment.setFace(tunnelFace);

        java.lang.String geologistID = CursorUtils.getString(AssessmentTable.GEOLOGIST_CODE_COLUMN, cursor);
        User geologist = localUserDAO.getUserByCode(geologistID);
        babyAssessment.setGeologist(geologist);

        Long dateAsLongRep = CursorUtils.getLong(AssessmentTable.DATE_COLUMN, cursor);
        Date date = DateDataFormat.getCalendarFromFormattedLong(dateAsLongRep).getTime();
        babyAssessment.setDate(date);

        java.lang.String sectionID = CursorUtils.getString(AssessmentTable.EXCAVATION_SECTION_CODE_COLUMN, cursor);
        ExcavationSection section = localExcavationSectionDAO.getExcavationSectionByCode(sectionID);
        babyAssessment.setSection(section);

        java.lang.String methodID = CursorUtils.getString(AssessmentTable.EXCAVATION_METHOD_CODE_COLUMN, cursor);
        ExcavationMethod method = localExcavationMethodDAO.getExcavationMethodByCode(methodID);
        babyAssessment.setMethod(method);

        Double initialPk = CursorUtils.getDouble(AssessmentTable.PK_INITIAL_COLUMN, cursor);
        babyAssessment.setInitialPeg(initialPk);

        Double finalPk = CursorUtils.getDouble(AssessmentTable.PK_FINAL_COLUMN, cursor);
        babyAssessment.setFinalPeg(finalPk);

        Double advance = CursorUtils.getDouble(AssessmentTable.ADVANCE_ACUMM_COLUMN, cursor);
        babyAssessment.setAccummAdvance(advance);

        Long orientation =  CursorUtils.getLong(AssessmentTable.ORIENTATION_COLUMN, cursor);
        babyAssessment.setOrientation(orientation.shortValue());

        Long slope = CursorUtils.getLong(AssessmentTable.SLOPE_COLUMN, cursor);
        babyAssessment.setSlope(slope.shortValue());

        java.lang.String fractureTypeID = CursorUtils.getString(AssessmentTable.FRACTURE_TYPE_CODE_COLUMN, cursor);
        FractureType fractureType = localFractureTypeDAO.getFractureTypeByCode(fractureTypeID);
        babyAssessment.setFractureType(fractureType);

        Long blockSize = CursorUtils.getLong(AssessmentTable.BLOCKS_SIZE_COLUMN, cursor);
        babyAssessment.setBlockSize(blockSize.shortValue());

        Long numJoints = CursorUtils.getLong(AssessmentTable.NUMBER_JOINTS_COLUMN, cursor);
        babyAssessment.setNumberOfJoints(numJoints.shortValue());

        java.lang.String outcrop = CursorUtils.getString(AssessmentTable.OUTCROP_COLUMN, cursor);
        babyAssessment.setOutcropDescription(outcrop);

        return babyAssessment;
    }

}
