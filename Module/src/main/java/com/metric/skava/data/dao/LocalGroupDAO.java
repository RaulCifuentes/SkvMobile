package com.metric.skava.data.dao;

import com.metric.skava.calculator.model.Group;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalGroupDAO {

    public List<Group> getAllGroups() throws DAOException;

    public Group getGroupByCode(String groupCode) throws DAOException;

    public void saveGroup(Group group) throws DAOException;

    public boolean deleteGroup(String groupCode);

    public int deleteAllGroups();

    public Long countGroups();
}
