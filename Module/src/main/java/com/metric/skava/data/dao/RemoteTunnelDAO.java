package com.metric.skava.data.dao;

import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface RemoteTunnelDAO {


    public List<Tunnel> getAllTunnels() throws DAOException;

    public Tunnel getTunnelByCode(String code) throws DAOException;

//    public List<Tunnel> getTunnelsByProject(ExcavationProject project) throws DAOException;
//
//    public List<Tunnel> getTunnelsByProject(ExcavationProject project, User user) throws DAOException;
//
//    public List<Tunnel> getTunnelsByUser(User user) throws DAOException;


}

