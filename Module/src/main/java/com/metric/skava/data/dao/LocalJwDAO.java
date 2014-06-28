package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalJwDAO {

    public List<Jw> getAllJws() throws DAOException;

    public Jw getJw(String groupCode, String code) throws DAOException;

    public Jw getJwByUniqueCode(String code) throws DAOException;

    public void saveJw(Jw jw) throws DAOException;

    public boolean deleteJw(String indexCode, String groupCode, String code);

    public int deleteAllJws();

    public Long countJw();
}
