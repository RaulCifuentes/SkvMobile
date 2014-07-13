package com.metric.skava.data.dao;

import com.metric.skava.app.model.Assessment;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteAssessmentDAO {

    public List<Assessment> getAllAssessments() throws DAOException;

//    public List<Assessment> getAssessmentsByUser(User user) throws DAOException;
//
//    public List<Assessment> getAssessmentsByTunnelFace(TunnelFace face) throws DAOException;

    public void saveAssessment(Assessment assessment) throws DAOException;

    public void deleteAssessment(String code, boolean cascade) throws DAOException;

    public void deleteAllAssessments(boolean cascade) throws DAOException;


}
