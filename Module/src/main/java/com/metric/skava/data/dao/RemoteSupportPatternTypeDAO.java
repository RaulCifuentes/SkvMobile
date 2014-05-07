package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.SupportPatternType;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteSupportPatternTypeDAO {

    public List<SupportPatternType> getAllSupportPatternTypes() throws DAOException;

}

