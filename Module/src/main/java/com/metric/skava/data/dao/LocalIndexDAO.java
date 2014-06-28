package com.metric.skava.data.dao;

import com.metric.skava.calculator.model.Index;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalIndexDAO {

    public List<Index> getAllIndexes() throws DAOException;

    public Index getIndexByCode(String indexCode) throws DAOException;

    public void saveIndex(Index index) throws DAOException;

    public boolean deleteIndex(String indexCode);

    public int deleteAllIndexes();

    public Long countIndexes();
}
