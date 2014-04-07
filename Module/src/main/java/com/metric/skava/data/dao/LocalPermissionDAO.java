package com.metric.skava.data.dao;

import com.metric.skava.app.data.IdentifiableEntity;
import com.metric.skava.app.model.Permission;
import com.metric.skava.app.model.User;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalPermissionDAO {

    public List<Permission> getAllPermissions() throws DAOException;

    public List<Permission> getPermissionsByUser(User user) throws DAOException;

    public List<User> getUsersByPermissionTarget(Permission.Action what, IdentifiableEntity where) throws DAOException;

    public void savePermission(Permission newEntity) throws DAOException;

    public boolean deletePermission(String code);

    public int deleteAllPermissions() throws DAOException;

}
