package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalJnDAO {


    public List<Jn> getAllJns() throws DAOException;

    public Jn getJn(String indexCode, String groupCode, String code) throws DAOException;

    public void saveJn(Jn jn) throws DAOException;

    public boolean deleteJn(String indexCode, String groupCode, String code);

    public int deleteAllJns();

}
