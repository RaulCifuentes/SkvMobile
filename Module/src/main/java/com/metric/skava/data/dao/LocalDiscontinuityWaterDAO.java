package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface LocalDiscontinuityWaterDAO {

    public DiscontinuityWater getDiscontinuityWaterByCode(String code) throws DAOException;

    public List<DiscontinuityWater> getAllDiscontinuityWaters() throws DAOException;

    public void saveDiscontinuityWater(DiscontinuityWater newEntity) throws DAOException;

    public boolean deleteDiscontinuityWater(String code);

    public int deleteAllDiscontinuityWaters();

    public Long countDiscontinuityWaters();
}

