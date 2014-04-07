package com.metric.skava.identification.helper;

import android.content.Context;

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

    private Context mContext;
    private DAOFactory daoFactory;
    private final LocalTunnelFaceDAO localTunnelFaceDAO;

    public UserDataHelper(Context context) throws DAOException {
        mContext = context;
        daoFactory = DAOFactory.getInstance(mContext);
        localTunnelFaceDAO = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
    }


    public UserDataDomain buildUserDataDomain(User user) throws DAOException {
        List<TunnelFace> faceList = localTunnelFaceDAO.getTunnelFacesByUser(user);
        UserDataDomain userDataDomain = new UserDataDomain(faceList) ;
        return userDataDomain;
    }




}
