package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityFamily;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalDiscontinuityFamilyDAO {

    public List<DiscontinuityFamily> getDiscontinuityFamilies(String assessmentCode) throws DAOException;

    public void saveDiscontinuityFamily(String assessmentCode, DiscontinuityFamily newEntity) throws DAOException;

    public boolean deleteDiscontinuityFamily(String assessmentCode, Integer number);

    public int deleteAllDiscontinuityFamilies(String assessmentCode) throws DAOException;

    public int deleteAllDiscontinuitiesFamilies() throws DAOException;

}
