package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteWeatheringDAO {

    public List<Weathering> getAllWeatherings() throws DAOException;


}
