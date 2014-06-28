package com.metric.skava.data.dao;

import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 3/5/14.
 */
public interface RemoteMetadataDAO {

    public Long getAllAppDataRecordsCount() throws DAOException;

    public Long getAllUserDataRecordsCount() throws DAOException;

    public Long getRecordsCount(String[] dropboxTables) throws DAOException;
}
