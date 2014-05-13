package com.metric.skava.data.dao;

import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalSupportRequirementDAO {

    public SupportRequirement findSupportRequirement(Tunnel tunnel, Double qBarton) throws DAOException;

    public List<SupportRequirement> getAllSupportRequirements(Tunnel tunnel) throws DAOException;

    public SupportRequirement getSupportRequirement(String code) throws DAOException;

    public void saveSupportRequirement(SupportRequirement SupportRequirement) throws DAOException;

    public boolean deleteSupportRequirements(Tunnel tunnel);

    public boolean deleteSupportRequirement(String code);

    public int deleteAllSupportRequirements();


}
