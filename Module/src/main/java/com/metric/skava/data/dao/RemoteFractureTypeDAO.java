package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rockmass.model.FractureType;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteFractureTypeDAO {

    public List<FractureType> getAllFractureTypes() throws DAOException;


}

