package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityType;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface LocalDiscontinuityTypeDAO {

    public DiscontinuityType getDiscontinuityTypeByCode(String code) throws DAOException;

    public List<DiscontinuityType> getAllDiscontinuityTypes() throws DAOException;

    public void saveDiscontinuityType(DiscontinuityType newEntity) throws DAOException;

    public boolean deleteDiscontinuityType(String code);

    public int deleteAllDiscontinuityTypes();

}

