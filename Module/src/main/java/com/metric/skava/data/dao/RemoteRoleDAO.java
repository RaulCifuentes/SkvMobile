package com.metric.skava.data.dao;

import com.metric.skava.app.model.Role;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteRoleDAO {


    public List<Role> getAllRoles() throws DAOException;


}
