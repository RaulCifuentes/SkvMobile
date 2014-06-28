package com.metric.skava.data.dao;

import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalExcavationProjectDAO {

    public ExcavationProject getExcavationProjectByCode(String code) throws DAOException;

    public List<ExcavationProject> getAllExcavationProjects() throws DAOException;

    public List<ExcavationProject> getExcavationProjectsByUser(User user) throws DAOException;

    public void saveExcavationProject(ExcavationProject newEntity) throws DAOException;

    public boolean deleteExcavationProject(String code);

    public int deleteAllExcavationProjects() throws DAOException;

    public Long countProjects();
}
