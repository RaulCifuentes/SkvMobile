package com.metric.skava.data.dao;

import com.metric.skava.calculator.model.Group;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteGroupDAO {


    public List<Group> getAllGroups() throws DAOException;


}

