package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalPersistenceDAO {


    public List<Persistence> getAllPersistences() throws DAOException;

    public Persistence getPersistence(String groupCode, String code) throws DAOException;

    public Persistence getPersistenceByUniqueCode(String persistenceCode) throws DAOException;

    public void savePersistence(Persistence persistence) throws DAOException;

    public boolean deletePersistence( String groupCode, String code);


    public int deleteAllPersistences();
}
