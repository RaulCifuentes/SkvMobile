package com.metric.skava.data.dao;

import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalUserDAO {


    public List<User> getAllUsers() throws DAOException;

    public User getUserByCode(String code) throws DAOException;

    public User getUserByEmail(String email) throws DAOException;

    public void saveUser(User newEntity) throws DAOException;

    public boolean deleteUser(String code) throws DAOException;

    public int deleteAllUsers() throws DAOException;


}
