package com.metric.skava.data.dao;

import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface LocalTunnelDAO {

//    public List<Tunnel> getTunnelsByProject(ExcavationProject project) throws DAOException;

//    public List<Tunnel> getTunnelsByProject(ExcavationProject project, User user) throws DAOException;

//    public List<Tunnel> getTunnelsByUser(String environment, User user) throws DAOException;

    public Tunnel getTunnelByUniqueCode (String code) throws DAOException;

//    public Tunnel getTunnelByCode(String projectCode, String code) throws DAOException;

    public List<Tunnel> getAllTunnels() throws DAOException;

    public void saveTunnel(Tunnel newEntity) throws DAOException;

//    public boolean deleteTunnel(String code);

    public int deleteAllTunnels() throws DAOException;

    public Long countTunnels();

}

