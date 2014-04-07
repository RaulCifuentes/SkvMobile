package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityType;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteDiscontinuityTypeDAO {

    public List<DiscontinuityType> getAllDiscontinuityTypes() throws DAOException;


}

