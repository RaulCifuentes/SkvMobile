package com.metric.skava.data.dao;

import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteExcavationMethodDAO {

    public List<ExcavationMethod> getAllExcavationMethods() throws DAOException;

}
