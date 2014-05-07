package com.metric.skava.data.dao;

import com.metric.skava.app.model.Tunnel;
import com.metric.skava.calculator.barton.model.RockQuality;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalSupportRequirementDAO {

    public SupportRequirement getSupportRequirementByTunnel(Tunnel tunnel, RockQuality quality) throws DAOException;

    public List<SupportRequirement> getAllSupportRequirements() throws DAOException;

    public void saveSupportRequirement(SupportRequirement SupportRequirement) throws DAOException;

    public boolean deleteSupportRequirementByTunnel(Tunnel tunnel);

    public int deleteAllSupportRequirements();


}
