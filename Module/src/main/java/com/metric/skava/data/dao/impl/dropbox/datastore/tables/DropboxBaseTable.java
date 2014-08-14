package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by metricboy on 3/24/14.
 */
public abstract class DropboxBaseTable implements DropboxTable {

    protected DbxDatastore mDatastore;

    public DropboxBaseTable(DbxDatastore datastore) {
        this.mDatastore = datastore;
    }

    public abstract DbxTable getBaseDropboxTable();

    public abstract boolean shouldSortByOrdinalColumn();

    public abstract boolean shouldSortByKeyColumn();

    @Override
    public List<DbxRecord> findAll() throws DAOException {
        DbxTable.QueryResult results = null;
        try {
            results = getBaseDropboxTable().query();
        } catch (DbxException e) {
            throw new DAOException(e);
        }
        return results.asList();
    }

    @Override
    public DbxRecord findRecordByCode(String code) throws DAOException {
        DbxRecord assessmentRecord = null;
        try {
            DbxFields criteria = new DbxFields();
            criteria.set("code", code);
            DbxTable.QueryResult results = getBaseDropboxTable().query(criteria);
            if (results.hasResults()) {
                if (results.count() == 1) {
                    assessmentRecord = results.iterator().next();
                } else {
                    throw new DAOException("Multiple Assessment instances for same code: " + code);
                }
            }
        } catch (DbxException e) {
            throw new DAOException(e);
        }
        return assessmentRecord;
    }


    @Override
    public DbxRecord findRecordByCandidateKey(String candidateKeyColumn, String keyValue) throws DAOException {
        DbxRecord resultRecord = null;
        try {
            DbxFields criteria = new DbxFields();
            criteria.set(candidateKeyColumn, keyValue);
            DbxTable.QueryResult results = getBaseDropboxTable().query(criteria);
            if (results.hasResults()) {
                if (results.count() == 1) {
                    resultRecord = results.iterator().next();
                } else {
                    throw new DAOException("Multiple record instances for same code: " + keyValue);
                }
            }
        } catch (DbxException e) {
            throw new DAOException(e);
        }
        return resultRecord;
    }

    public DbxRecord findRecordByCandidateKey(String[] candidateKeyColumns, Object[] keyValues) throws DAOException {
        DbxRecord resultRecord = null;
        try {
            DbxFields criteria = new DbxFields();
            if (candidateKeyColumns.length == keyValues.length) {
                for (int i = 0; i < candidateKeyColumns.length; i++) {
                    if (keyValues[i] instanceof String){
                        criteria.set(candidateKeyColumns[i], (String)keyValues[i]);
                    }
                    if (keyValues[i] instanceof Boolean){
                        criteria.set(candidateKeyColumns[i], (Boolean)keyValues[i]);
                    }
                    if (keyValues[i] instanceof Long){
                        criteria.set(candidateKeyColumns[i], (Long)keyValues[i]);
                    }
                    if (keyValues[i] instanceof Integer){
                        criteria.set(candidateKeyColumns[i], (Integer)keyValues[i]);
                    }
                    if (keyValues[i] instanceof Double){
                        criteria.set(candidateKeyColumns[i], (Double)keyValues[i]);
                    }
                    if (keyValues[i] instanceof Date){
                        criteria.set(candidateKeyColumns[i], (Date)keyValues[i]);
                    }
                }

            DbxTable.QueryResult results = getBaseDropboxTable().query(criteria);
            if (results.hasResults()) {
                if (results.count() == 1) {
                    resultRecord = results.iterator().next();
                } else {
                    throw new DAOException("Multiple record instances for same code: " + candidateKeyColumns + "with values " + keyValues);
                }
            }
            }
        } catch (DbxException e) {
            throw new DAOException(e);
        }
        return resultRecord;
    }


    public List<DbxRecord> findRecordsByCriteria(String[] names, Object[] values) throws DAOException {
        List<DbxRecord> resultSet;
        try {
            DbxFields criteria = new DbxFields();
            if (names.length == values.length) {
                for (int i = 0; i < names.length; i++) {
                    if (values[i] instanceof String){
                        criteria.set(names[i], (String)values[i]);
                    }
                    if (values[i] instanceof Boolean){
                        criteria.set(names[i], (Boolean)values[i]);
                    }
                    if (values[i] instanceof Long){
                        criteria.set(names[i], (Long)values[i]);
                    }
                    if (values[i] instanceof Integer){
                        criteria.set(names[i], (Integer)values[i]);
                    }
                    if (values[i] instanceof Double){
                        criteria.set(names[i], (Double)values[i]);
                    }
                    if (values[i] instanceof Date){
                        criteria.set(names[i], (Date)values[i]);
                    }
                }
                resultSet = getBaseDropboxTable().query(criteria).asList();
                if (shouldSortByOrdinalColumn()) {
                    Collections.sort(resultSet, new Comparator<DbxRecord>() {
                        @Override
                        public int compare(DbxRecord lhs, DbxRecord rhs) {
                            return ((Long) lhs.getLong("ParameterOrder")).compareTo((Long) rhs.getLong("ParameterOrder"));
                        }
                    });
                }
                if (shouldSortByKeyColumn()) {
                    Collections.sort(resultSet, new Comparator<DbxRecord>() {
                        @Override
                        public int compare(DbxRecord lhs, DbxRecord rhs) {
                            return (lhs.getString("IndexCode")).compareTo(rhs.getString("IndexCode"));
                        }
                    });
                }

            } else {
                throw new DAOException("Criteria names[] and values[] must have the same number of elements.");
            }
        } catch (DbxException e) {
            throw new DAOException(e);
        }
        return resultSet;
    }


    public String persist(DbxFields fields) {
        DbxRecord dbxRecord = getBaseDropboxTable().insert(fields);
        return dbxRecord.getId();
    }

    public void delete(DbxRecord dbxRecord) {
        dbxRecord.deleteRecord();
    }

}
