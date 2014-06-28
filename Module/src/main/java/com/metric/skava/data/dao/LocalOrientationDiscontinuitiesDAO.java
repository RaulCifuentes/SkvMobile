package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface LocalOrientationDiscontinuitiesDAO {


    public List<OrientationDiscontinuities> getAllOrientationDiscontinuities(OrientationDiscontinuities.Group group) throws DAOException;

    public OrientationDiscontinuities getOrientationDiscontinuities(String groupCode, String code) throws DAOException;

    public OrientationDiscontinuities getOrientationDiscontinuitiesByUniqueCode(String code) throws DAOException;

    public void saveOrientationDiscontinuities(OrientationDiscontinuities orientationDiscontinuities) throws DAOException;

    public boolean deleteOrientationDiscontinuities(String indexCode, String groupCode, String code);

    public int deleteAllOrientationDiscontinuities();

    public Long countOrientation();
}
