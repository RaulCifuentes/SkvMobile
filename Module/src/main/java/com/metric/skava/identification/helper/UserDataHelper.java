package com.metric.skava.identification.helper;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.identification.model.UserDataDomain;

import java.util.List;

/**
 * Created by metricboy on 3/29/14.
 */
public class UserDataHelper {

    private DAOFactory daoFactory;
    private final LocalTunnelFaceDAO localTunnelFaceDAO;

    public UserDataHelper(SkavaContext skavaContext) throws DAOException {
        daoFactory = skavaContext.getDAOFactory();
        localTunnelFaceDAO = daoFactory.getLocalTunnelFaceDAO();
    }


    public UserDataDomain buildUserDataDomain(User user) throws DAOException {
        List<TunnelFace> faceList = localTunnelFaceDAO.getTunnelFacesByUser(user);
        UserDataDomain userDataDomain = new UserDataDomain(faceList) ;
        return userDataDomain;
    }




}
