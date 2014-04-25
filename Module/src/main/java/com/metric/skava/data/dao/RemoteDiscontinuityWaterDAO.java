package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.discontinuities.model.DiscontinuityWater;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteDiscontinuityWaterDAO {

    public List<DiscontinuityWater> getAllDiscontinuityWaters() throws DAOException;


}

