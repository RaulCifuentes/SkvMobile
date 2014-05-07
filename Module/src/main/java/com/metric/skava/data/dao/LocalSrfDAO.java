package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalSrfDAO {


    public List<SRF> getAllSrfs(SRF.Group group) throws DAOException;

    public SRF getSrf(String groupCode, String code) throws DAOException;

    public SRF getSrfByUniqueCode(String code) throws DAOException;

    public void saveSrf(SRF jr) throws DAOException;

    public boolean deleteSrf(String groupCode, String code);

    public int deleteAllSrfs();

}
