package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteSupportRequirementDAO {

    public List<SupportRequirement> getAllSupportRequirements() throws DAOException;


}
