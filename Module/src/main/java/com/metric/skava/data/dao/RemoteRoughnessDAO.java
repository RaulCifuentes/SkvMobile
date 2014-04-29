package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteRoughnessDAO {

    public List<Roughness> getAllRoughnesses() throws DAOException;


}
