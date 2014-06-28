package com.metric.skava.data.dao;

import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface LocalTunnelFaceDAO {

    List<TunnelFace> getTunnelFacesByTunnel(Tunnel tunnel) throws DAOException;

    List<TunnelFace> getTunnelFacesByTunnel(Tunnel tunnel, User user) throws DAOException;

    List<TunnelFace> getTunnelFacesByUser(User user) throws DAOException;

    public TunnelFace getTunnelFaceByCode(String code) throws DAOException;

    public List<TunnelFace> getAllTunnelFaces() throws DAOException;

    public void saveTunnelFace(TunnelFace newEntity) throws DAOException;

    public boolean deleteTunnelFace(String code);

    public int deleteAllTunnelFaces() throws DAOException;

    public Long countFaces();
}

