package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rocksupport.model.ESR;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalEsrDAO {

    public static final String INDEX_CODE_COLUMN = "INDEX_CODE";
    public static final String GROUP_CODE_COLUMN = "GROUP_CODE";
    public static final String CODE_COLUMN = "CODE";

    public static final String KEYWORDS_COLUMN = "KEYWORDS";
    public static final String DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String VALUE_COLUMN  = "VALUE";

    public List<ESR> getAllESRs() throws DAOException;

    public ESR getESR(String code) throws DAOException;

    public void saveESR(ESR jr) throws DAOException;

    public boolean deleteESR(String indexCode, String groupCode, String code);

    public int deleteAllESRs();

}
