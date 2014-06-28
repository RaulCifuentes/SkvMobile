package com.metric.skava.data.dao.impl.dropbox.helper;

import android.util.Log;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.TunnelFace;
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

import java.util.Calendar;
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

        Assessment babyAssessment = null;
        if (assessmentRecord.hasField("code")){
            String assessmentCode = assessmentRecord.getString("code");
            babyAssessment = new Assessment(assessmentCode);
        }

        if (assessmentRecord.hasField("skavaInternalCode")){
            String internalCode = assessmentRecord.getString("skavaInternalCode");
            babyAssessment.setInternalCode(internalCode);
        }

        if (assessmentRecord.hasField("date")){
            Date date = assessmentRecord.getDate("date");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            babyAssessment.setDateTime(calendar);
        }

        if (assessmentRecord.hasField("faceCode")){
            java.lang.String faceID = assessmentRecord.getString("faceCode");
            try {
                TunnelFace tunnelFace = localTunnelFaceDAO.getTunnelFaceByCode(faceID);
                babyAssessment.setFace(tunnelFace);
            } catch (DAOException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
            }
        }

        return babyAssessment;
    }



}
