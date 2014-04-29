package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalJaDAO {


    public List<Ja> getAllJas(Ja.Group group) throws DAOException;

    public Ja getJa(String indexCode, String groupCode, String code) throws DAOException;

    public void saveJa(Ja jr) throws DAOException;

    public boolean deleteJa(String indexCode, String groupCode, String code);

    public int deleteAllJas();

}
