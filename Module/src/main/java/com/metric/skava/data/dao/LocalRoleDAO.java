package com.metric.skava.data.dao;

import com.metric.skava.app.model.Role;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalRoleDAO {


    public List<Role> getAllRoles() throws DAOException;

    public Role getRoleByCode(String code) throws DAOException;

    public void saveRole(Role newEntity) throws DAOException;

    public boolean deleteRole(String code);

    public int deleteAllRoles() throws DAOException;

    public Long countRoles();
}
