package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityRelevance;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface LocalDiscontinuityRelevanceDAO {

    public DiscontinuityRelevance getDiscontinuityRelevanceByCode(String code) throws DAOException;

    public List<DiscontinuityRelevance> getAllDiscontinuityRelevances() throws DAOException;

    public void saveDiscontinuityRelevance(DiscontinuityRelevance newEntity) throws DAOException;

    public boolean deleteDiscontinuityRelevance(String code);

    public int deleteEmptyDiscontinuityRelevances(String tableName);

}

