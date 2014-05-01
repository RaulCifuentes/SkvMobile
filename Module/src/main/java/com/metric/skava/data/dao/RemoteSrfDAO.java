package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteSrfDAO {

    public List<SRF> getAllSrfs() throws DAOException;

}
