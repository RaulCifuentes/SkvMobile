package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.ShotcreteType;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteShotcreteTypeDAO {


    public List<ShotcreteType> getAllShotcreteTypes() throws DAOException;



}
