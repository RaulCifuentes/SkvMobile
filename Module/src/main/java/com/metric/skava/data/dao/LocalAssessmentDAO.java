package com.metric.skava.data.dao;

import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalAssessmentDAO {

    public Assessment getAssessment(String code) throws DAOException;

    public Assessment getAssessmentByInternalCode(String internalCode) throws DAOException;

    public List<Assessment> getAssessmentsByUser(User user) throws DAOException;

    public List<Assessment> getAssessmentsByTunnelFace(TunnelFace face) throws DAOException;

    public List<Assessment> getAllAssessments() throws DAOException;

    public void saveAssessment(Assessment assessment) throws DAOException;

    public void updateAssessment(Assessment newSkavaEntity, boolean includeRelations) throws DAOException;

    public boolean deleteAssessment(String code);


}
