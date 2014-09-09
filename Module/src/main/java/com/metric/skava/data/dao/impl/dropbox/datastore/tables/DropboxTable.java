package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public interface DropboxTable {

    public List<DbxRecord> findAll() throws DAOException;

    public DbxRecord findRecordByCode(String code) throws DAOException;

    public DbxRecord findRecordByCandidateKey(String candidateKeyColumn, String candidateKeyValue) throws DAOException;

    public String persist(DbxFields fields) throws DAOException;

    public void delete(DbxRecord dbxRecord);


}
