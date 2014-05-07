package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rocksupport.model.ESR;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteEsrDAO {

    public List<ESR> getAllESRs() throws DAOException;


}
