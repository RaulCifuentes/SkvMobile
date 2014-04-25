package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalJrDAO {

    public static final String INDEX_CODE_COLUMN = "INDEX_CODE";
    public static final String GROUP_CODE_COLUMN = "GROUP_CODE";
    public static final String CODE_COLUMN = "CODE";

    public static final String KEYWORDS_COLUMN = "KEYWORDS";
    public static final String DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String VALUE_COLUMN  = "VALUE";

    public List<Jr> getAllJrs(int type) throws DAOException;

    public Jr getJr(String indexCode, String groupCode, String code) throws DAOException;

    public void saveJr(Jr jr) throws DAOException;

    public boolean deleteJr(String indexCode, String groupCode, String code);

    public int deleteAllJrs();

}
