package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rocksupport.model.ESR;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalEsrDAO {

    public List<ESR> getAllESRs(ESR.Group group) throws DAOException;

    public ESR getESR(String code) throws DAOException;

    public void saveESR(ESR esr) throws DAOException;

    public boolean deleteESR(String indexCode, String groupCode, String code);

    public int deleteAllESRs();

}
