package com.metric.skava.data.dao;

import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalExcavationMethodDAO {

    public ExcavationMethod getExcavationMethodByCode(String code) throws DAOException;

    public List<ExcavationMethod> getAllExcavationMethods() throws DAOException;

    public void saveExcavationMethod(ExcavationMethod newEntity) throws DAOException;

    public boolean deleteExcavationMethod(String code);

    public int deleteAllExcavationMethods();


}
