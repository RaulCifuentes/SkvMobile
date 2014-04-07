package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.BoltType;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteBoltTypeDAO {

    public List<BoltType> getAllBoltTypes() throws DAOException;



}
