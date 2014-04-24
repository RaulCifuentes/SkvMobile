package com.metric.skava.data.dao.impl.dropbox.helper;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.LocalFractureTypeDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rockmass.model.FractureType;

import java.util.Date;

/**
 * Created by metricboy on 3/5/14.
 */
public class AssessmentBuilder4DropBox {

    private final Context mContext;

    private LocalTunnelDAO localTunnelDAO;
    private LocalExcavationProjectDAO excavationProjectDAO;
    private LocalExcavationSectionDAO excavationSectionDAO;
    private LocalTunnelFaceDAO localTunnelFaceDAO;
    private LocalUserDAO localUserDAO;
    private LocalExcavationMethodDAO excavationMethodDAO;
    private LocalFractureTypeDAO fractureTypeDAO;

    public AssessmentBuilder4DropBox(Context context) throws DAOException {

        this.mContext = context;
        DAOFactory daoFactory = DAOFactory.getInstance(mContext);
        excavationProjectDAO = daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE);
        excavationSectionDAO = daoFactory.getLocalExcavationSectionDAO(DAOFactory.Flavour.SQLLITE);
        excavationMethodDAO = daoFactory.getLocalExcavationMethodDAO(DAOFactory.Flavour.SQLLITE);
        localTunnelFaceDAO = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
        localTunnelDAO = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE);
        localUserDAO = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE);
    }


    public Assessment buildAssessmentFromRecord(DbxRecord assessmentRecord) throws DAOException {

        String internalCode = assessmentRecord.getString("internalCode");

        Assessment babyAssessment = new Assessment(internalCode);

        java.lang.String faceID = assessmentRecord.getString("face");
        TunnelFace tunnelFace = localTunnelFaceDAO.getTunnelFaceByCode(faceID);
        babyAssessment.setFace(tunnelFace);

        java.lang.String sectionID = assessmentRecord.getString("section");
        ExcavationSection section = excavationSectionDAO.getExcavationSectionByCode(sectionID);
        babyAssessment.setSection(section);

        Double pk = assessmentRecord.getDouble("pk");
        babyAssessment.setInitialPeg(pk);

        Double advance = assessmentRecord.getDouble("advance");
        babyAssessment.setCurrentAdvance(advance);

        java.lang.String methodID = assessmentRecord.getString("method");
        ExcavationMethod method = excavationMethodDAO.getExcavationMethodByCode(methodID);
        babyAssessment.setMethod(method);

        Long orientation = assessmentRecord.getLong("orientation");
        babyAssessment.setOrientation(orientation.shortValue());

        Double slope = assessmentRecord.getDouble("slope");
        babyAssessment.setSlope(slope.doubleValue());

        java.lang.String fractureTypeID = assessmentRecord.getString("fractureType");
        FractureType fractureType = fractureTypeDAO.getFractureTypeByCode(fractureTypeID);
        babyAssessment.setFractureType(fractureType);

        Long blockSize = assessmentRecord.getLong("blockSize");
        babyAssessment.setBlockSize(blockSize.shortValue());

        Long numJoints = assessmentRecord.getLong("numJoints");
        babyAssessment.setNumberOfJoints(numJoints.shortValue());

        java.lang.String geologistID = assessmentRecord.getString("geologist");
        User geologist = localUserDAO.getUserByCode(geologistID);
        babyAssessment.setGeologist(geologist);

        Date date = assessmentRecord.getDate("date");
        babyAssessment.setDate(date);

        return babyAssessment;
    }

}
