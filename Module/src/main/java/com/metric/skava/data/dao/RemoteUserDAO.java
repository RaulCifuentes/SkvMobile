package com.metric.skava.data.dao;

import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteUserDAO {


    public User getUserByCode(String code) throws DAOException;

    public List<User> getAllUsers() throws DAOException;

    public User getUserByEmail(String email) throws DAOException;


}
