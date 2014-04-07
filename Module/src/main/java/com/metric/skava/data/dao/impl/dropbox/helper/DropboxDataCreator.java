//package com.metric.skava.data.dao.impl.dropbox.helper;
//
//import android.content.Context;
//
//import com.metric.skava.app.model.ExcavationProject;
//import com.metric.skava.app.model.Tunnel;
//import com.metric.skava.app.model.TunnelFace;
//import com.metric.skava.app.model.User;
//import com.metric.skava.data.dao.LocalExcavationProjectDAO;
//import com.metric.skava.data.dao.LocalTunnelDAO;
//import com.metric.skava.data.dao.LocalTunnelFaceDAO;
//import com.metric.skava.data.dao.LocalUserDAO;
//import com.metric.skava.data.dao.exception.DAOException;
//import com.metric.skava.data.dao.impl.dropbox.ExcavationProjectDAODropboxImpl;
//import com.metric.skava.data.dao.impl.dropbox.TunnelDAODropboxImpl;
//import com.metric.skava.data.dao.impl.dropbox.TunnelFaceDAODropboxImpl;
//import com.metric.skava.data.dao.impl.dropbox.UserDAODropboxImpl;
//
///**
// * Created by metricboy on 3/25/14.
// */
//public class DropboxDataCreator {
//
//    Context mContext;
//    LocalUserDAO userDAO;
//    LocalExcavationProjectDAO projectDAO;
//    LocalTunnelDAO tunnelDAO;
//    LocalTunnelFaceDAO faceDAO;
//
//    public DropboxDataCreator(Context context) throws DAOException {
//        this.mContext = context;
//        this.userDAO = new UserDAODropboxImpl(mContext);
//        this.projectDAO = new ExcavationProjectDAODropboxImpl(mContext);
//        this.tunnelDAO = new TunnelDAODropboxImpl(mContext);
//        this.faceDAO = new TunnelFaceDAODropboxImpl(mContext);
//    }
//
//
//    public void insertUsers() throws DAOException {
//        User newUser = new User("USR-S","Marcos Allende", "mallende@skava.cl", null);
//        userDAO.saveUser(newUser);
//        newUser = new User("USR-T","Marcos Allende", "mlazcano@skava.cl", null);
//        userDAO.saveUser(newUser);
//        newUser = new User("USR-W","Juan Luis Berrio", "jberrio@skava.cl", null);
//        userDAO.saveUser(newUser);
//    }
//
//
//    public void insertProjectsTunnelAndFaces() throws DAOException {
//        ExcavationProject newProject = new ExcavationProject("PRJ-S","Excavation Project S");
//        projectDAO.saveExcavationProject(newProject);
//
//        Tunnel newTunnel = new Tunnel("TNL-S","Tunnel S");
//        newTunnel.setProject(newProject);
//        tunnelDAO.saveTunnel(newTunnel);
//
//        newTunnel = new Tunnel("TNL-T","Tunnel T");
//        newTunnel.setProject(newProject);
//        tunnelDAO.saveTunnel(newTunnel);
//
//        TunnelFace newFace = new TunnelFace("TFC-N","Face North");
//        newFace.setTunnel(newTunnel);
//        faceDAO.saveTunnelFace(newFace);
//
//        newFace = new TunnelFace("TFC-S","Face South");
//        newFace.setTunnel(newTunnel);
//        faceDAO.saveTunnelFace(newFace);
//
//        /////////////
//        newProject = new ExcavationProject("PRJ-T","Excavation Project T");
//        projectDAO.saveExcavationProject(newProject);
//
//        newTunnel = new Tunnel("TNL-U","Tunnel U");
//        newTunnel.setProject(newProject);
//        tunnelDAO.saveTunnel(newTunnel);
//
//        newFace = new TunnelFace("TFC-N","Face North");
//        newFace.setTunnel(newTunnel);
//        faceDAO.saveTunnelFace(newFace);
//
//        newFace = new TunnelFace("TFC-S","Face South");
//        newFace.setTunnel(newTunnel);
//        faceDAO.saveTunnelFace(newFace);
//
//        ///////////////
//        newProject = new ExcavationProject("PRJ-W","Excavation Project W");
//        projectDAO.saveExcavationProject(newProject);
//
//        newTunnel = new Tunnel("TNL-W","Tunnel W");
//        newTunnel.setProject(newProject);
//        tunnelDAO.saveTunnel(newTunnel);
//
//        newFace = new TunnelFace("TFC-N","Face North");
//        newFace.setTunnel(newTunnel);
//        faceDAO.saveTunnelFace(newFace);
//
//        newFace = new TunnelFace("TFC-S","Face North");
//        newFace.setTunnel(newTunnel);
//        faceDAO.saveTunnelFace(newFace);
//
//    }
//
//
//
//
//}
