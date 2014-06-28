package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.instructions.model.ShotcreteType;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalShotcreteTypeDAO {

    public ShotcreteType getShotcreteTypeByCode(String code) throws DAOException;

    public List<ShotcreteType> getAllShotcreteTypes() throws DAOException;

    public void saveShotcreteType(ShotcreteType newEntity) throws DAOException;

    public boolean deleteShotcreteType(String code);

    public int deleteAllShotcreteTypes();

    public Long countShotcreteTypes();
}
