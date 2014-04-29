package com.metric.skava.data.dao.impl.dropbox.helper;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
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


    private LocalTunnelDAO localTunnelDAO;
    private LocalExcavationProjectDAO excavationProjectDAO;
    private LocalExcavationSectionDAO excavationSectionDAO;
    private LocalTunnelFaceDAO localTunnelFaceDAO;
    private LocalUserDAO localUserDAO;
    private LocalExcavationMethodDAO excavationMethodDAO;
    private LocalFractureTypeDAO fractureTypeDAO;

    public AssessmentBuilder4DropBox(SkavaContext skavaContext) throws DAOException {

//        this.mContext = context;
        DAOFactory daoFactory = skavaContext.getDAOFactory();
        excavationProjectDAO = daoFactory.getLocalExcavationProjectDAO();
        excavationSectionDAO = daoFactory.getLocalExcavationSectionDAO();
        excavationMethodDAO = daoFactory.getLocalExcavationMethodDAO();
        localTunnelFaceDAO = daoFactory.getLocalTunnelFaceDAO();
        localTunnelDAO = daoFactory.getLocalTunnelDAO();
        localUserDAO = daoFactory.getLocalUserDAO();
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

        Double blockSize = assessmentRecord.getDouble("blockSize");
        babyAssessment.setBlockSize(blockSize.doubleValue());

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
