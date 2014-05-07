package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalGroundwaterDAO {

    public List<Groundwater> getAllGroundwaters(Groundwater.Group group) throws DAOException;

    public Groundwater getGroundwater(String groupCode, String code) throws DAOException;

    public Groundwater getGroundwaterByUniqueCode( String code) throws DAOException;

    public void saveGroundwater(Groundwater persistence) throws DAOException;

    public boolean deleteGroundwater(String indexCode, String groupCode, String code);

    public int deleteAllGroundwaters();


}
