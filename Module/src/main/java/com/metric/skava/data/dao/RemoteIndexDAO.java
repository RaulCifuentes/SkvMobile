package com.metric.skava.data.dao;

import com.metric.skava.calculator.model.Index;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteIndexDAO {

    public List<Index> getAllIndexes() throws DAOException;


}

