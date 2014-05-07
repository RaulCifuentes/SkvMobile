package com.metric.skava.data.dao;

import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalRockQualityDAO {

    public RockQuality getRockQualityByCode(String code) throws DAOException;

    public List<RockQuality> getAllRockQualities(RockQuality.AccordingTo accordingTo) throws DAOException;

    public void saveRockQuality(RockQuality newEntity) throws DAOException;

    public boolean deleteRockQuality(String code);

    public int deleteAllRockQualities();


}
