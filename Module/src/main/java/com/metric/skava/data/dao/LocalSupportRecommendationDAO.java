package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.SupportRecommendation;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalSupportRecommendationDAO {

    public SupportRecommendation getSupportRecommendation(String assessmentCode) throws DAOException;

    public void saveSupportRecommendation(String assessmentCode, SupportRecommendation SupportRecommendation) throws DAOException;

    public boolean deleteSupportRecommendation(String assessmentCode) throws DAOException;

    public int deleteAllSupportRecommendations() throws DAOException;


}
