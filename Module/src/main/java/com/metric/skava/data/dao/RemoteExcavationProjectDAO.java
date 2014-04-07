package com.metric.skava.data.dao;

import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteExcavationProjectDAO {

    public List<ExcavationProject> getAllExcavationProjects() throws DAOException;

    public ExcavationProject getExcavationProjectByCode(String code) throws DAOException;

//    public List<ExcavationProject> getExcavationProjectsByUser(User user) throws DAOException;

}
