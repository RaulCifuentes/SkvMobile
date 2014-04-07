package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.ArchType;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalArchTypeDAO {

    public ArchType getArchTypeByCode(String code) throws DAOException;

    public List<ArchType> getAllArchTypes() throws DAOException;

    public void saveArchType(ArchType newEntity) throws DAOException;

    public boolean deleteArchType(String code);

    public int deleteAllArchTypes();


}
