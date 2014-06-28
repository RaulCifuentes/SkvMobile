package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.Coverage;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalCoverageDAO {

    public Coverage getCoverageByCode(String code) throws DAOException;

    public List<Coverage> getAllCoverages() throws DAOException;

    public void saveCoverage(Coverage newEntity) throws DAOException;

    public boolean deleteCoverage(String code);

    public int deleteAllCoverages();

    public Long countCoverages();
}
