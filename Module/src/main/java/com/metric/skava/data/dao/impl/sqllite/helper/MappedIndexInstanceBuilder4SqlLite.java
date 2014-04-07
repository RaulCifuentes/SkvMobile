package com.metric.skava.data.dao.impl.sqllite.helper;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalFractureTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentTable;
import com.metric.skava.rocksupport.model.ESR;

/**
 * Created by metricboy on 3/18/14.
 */
public class MappedIndexInstanceBuilder4SqlLite  {

    private final Context mContext;

    private LocalExcavationMethodDAO localExcavationMethodDAO;
    private LocalFractureTypeDAO localFractureTypeDAO;


    public MappedIndexInstanceBuilder4SqlLite(Context context) throws DAOException {
        this.mContext = context;

        DAOFactory daoFactory = DAOFactory.getInstance(mContext);

        localExcavationMethodDAO = daoFactory.getLocalExcavationMethodDAO(DAOFactory.Flavour.SQLLITE);
        localFractureTypeDAO = daoFactory.getFractureTypeDAO();

    }


    public Jr buildJrFromCursorRecord(Cursor cursor) throws DAOException {

        String internalCodeID = CursorUtils.getString(AssessmentTable.INTERNAL_CODE_COLUMN, cursor);
//        String internalCode = internalCodeDAO.getInternalCodeByCode(internalCodeID);

        Jr babyJr = new Jr(null, null, null);

//        String faceID = CursorUtils.getString(AssessmentTable.TUNEL_FACE_CODE_COLUMN, cursor);
//        TunnelFace tunnelFace = tunnelFaceDAO.getTunnelFaceByCode(faceID);
//        babyAssessment.setFace(tunnelFace);
//
//        String sectionID = CursorUtils.getString(AssessmentTable.EXCAVATION_SECTION_CODE_COLUMN, cursor);
//        ExcavationSection section = excavationSectionDAO.getExcavationSectionByCode(sectionID);
//        babyAssessment.setSection(section);
//
//        String fractureTypeID = CursorUtils.getString(AssessmentTable.FRACTURE_TYPE_CODE_COLUMN, cursor);
//        FractureType fractureType = localFractureTypeDAO.getFractureTypeByCode(fractureTypeID);
//        babyAssessment.setFractureType(fractureType);
//
//        Long blockSize = CursorUtils.getLong(AssessmentTable.BLOCKS_SIZE_COLUMN, cursor);
//        babyAssessment.setBlockSize(blockSize.shortValue());
//
//        String geologistID = CursorUtils.getString(AssessmentTable.GEOLOGIST_CODE_COLUMN, cursor);
//        Geologist geologist = geologistDAO.getGeologistByCode(geologistID);
//        babyAssessment.setGeologist(geologist);
//
//        Long longRepresentation = CursorUtils.getLong(AssessmentTable.DATE_COLUMN, cursor);
//        Calendar calendar = getCalendarFromFormattedLong(longRepresentation);
//        Date date = calendar.getTime();
//        babyAssessment.setDate(date);
//
//        String outcrop = CursorUtils.getString(AssessmentTable.OUTCROP_COLUMN, cursor);
//        babyAssessment.setOutcropDescription(outcrop);

        return babyJr;
    }


    public Spacing buildSpacingFromCursorRecord(Cursor cursor) {
        return null;
    }

    public ESR buildESRFromCursorRecord(Cursor cursor) {
        ESR babyESR = null;
        return babyESR;
    }

}
