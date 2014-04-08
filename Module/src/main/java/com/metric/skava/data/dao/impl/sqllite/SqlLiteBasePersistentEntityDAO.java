package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * Created by metricboy on 3/12/14.
 */
public abstract class SqlLiteBasePersistentEntityDAO<T> extends SqlLiteBaseDAO {


    public SqlLiteBasePersistentEntityDAO(Context context) {
        super(context);
    }

    protected abstract void savePersistentEntity(String tableName, T newSkavaEntity) throws DAOException;

    protected abstract List<T> assemblePersistentEntities(Cursor cursor) throws DAOException;

    public T getPersistentEntityByCandidateKey(String tableName, String candidateKeyColumn, String keyValue) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(tableName, candidateKeyColumn, keyValue, null);
        List<T> list = assemblePersistentEntities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. Table[ " + tableName + "] Column [ " + candidateKeyColumn + " ] Value [ " + keyValue + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. Table[ " + tableName + "] Column [ " + candidateKeyColumn + " ] Value [ " + keyValue + " ]");
        }
        cursor.close();
        return list.get(0);
    }

    public List<T> getAllPersistentEntities(String tableName) throws DAOException {
        Cursor cursor = getAllRecords(tableName);
        List<T> list = assemblePersistentEntities(cursor);
        cursor.close();
        return list;
    }

    public int deleteAllPersistentEntities(String tableName) {
        // Specify a where clause that determines which row(s) to delete.
        // Specify where arguments as necessary.
        String where = null;
        String[] whereArgs = null;
        // Delete the rows that match the where clause.
        SQLiteDatabase db = getDBConnection();
        return db.delete(tableName, where, whereArgs);
    }


}
