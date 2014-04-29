package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalWeatheringDAO {


    public List<Weathering> getAllWeatherings() throws DAOException;

    public Weathering getWeathering(String indexCode, String groupCode, String code) throws DAOException;

    public void saveWeathering(Weathering weathering) throws DAOException;

    public boolean deleteWeathering(String indexCode, String groupCode, String code);

    public int deleteAllWeatherings();




}
