package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteJaDAO {

    public List<Ja> getAllJas() throws DAOException;

}
