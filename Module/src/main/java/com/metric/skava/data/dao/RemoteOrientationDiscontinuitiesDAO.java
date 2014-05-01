package com.metric.skava.data.dao;

import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteOrientationDiscontinuitiesDAO {

    public List<OrientationDiscontinuities> getAllOrientationsDiscontinuities() throws DAOException;


}
