package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteSyncAcknowlegeDAO {

    public void deleteAcknowledges(String assessmentCode) throws DAOException;

    public void deleteAllAcknowledges() throws DAOException;


}
