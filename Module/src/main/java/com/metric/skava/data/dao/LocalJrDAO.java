package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalJrDAO {


    public List<Jr> getAllJrs(Jr.Group group) throws DAOException;

    public Jr getJr(String indexCode, String groupCode, String code) throws DAOException;

    public void saveJr(Jr jr) throws DAOException;

    public boolean deleteJr(String indexCode, String groupCode, String code);

    public int deleteAllJrs();

}
