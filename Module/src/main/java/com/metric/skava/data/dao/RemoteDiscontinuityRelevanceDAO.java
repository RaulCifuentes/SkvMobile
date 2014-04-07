package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteDiscontinuityRelevanceDAO {

    public List<DiscontinuityRelevance> getAllDiscontinuityRelevances() throws DAOException;

}

