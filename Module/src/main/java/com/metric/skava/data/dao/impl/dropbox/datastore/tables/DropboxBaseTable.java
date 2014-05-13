package com.metric.skava.data.dao.impl.dropbox.datastore.tables;

import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFields;
import com.dropbox.sync.android.DbxRecord;
import com.dropbox.sync.android.DbxTable;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.Collections;
import java.util.Comparator;
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


    public List<DbxRecord> findRecordsByCriteria(String[] names, String[] values) throws DAOException {
        List<DbxRecord> resultSet;
        try {
            DbxFields criteria = new DbxFields();
            if (names.length == values.length) {
                for (int i = 0; i < names.length; i++) {
                    criteria.set(names[i], values[i]);
                }
                resultSet = getBaseDropboxTable().query(criteria).asList();
                if (shouldSortByOrdinalColumn()) {
                    Collections.sort(resultSet, new Comparator<DbxRecord>() {
                        @Override
                        public int compare(DbxRecord lhs, DbxRecord rhs) {
                            return ((Long)lhs.getLong("ParameterOrder")).compareTo((Long)rhs.getLong("ParameterOrder"));
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
