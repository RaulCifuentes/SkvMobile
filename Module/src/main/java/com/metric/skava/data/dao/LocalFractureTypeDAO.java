package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rockmass.model.FractureType;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface LocalFractureTypeDAO {

    public List<FractureType> getAllFractureTypes() throws DAOException;

    public FractureType getFractureTypeByCode(String code) throws DAOException;

    public void saveFractureType(FractureType newEntity) throws DAOException;

    public boolean deleteFractureType(String code);

    public int deleteAllFractureTypes();

    public Long countFractureTypes();
}

