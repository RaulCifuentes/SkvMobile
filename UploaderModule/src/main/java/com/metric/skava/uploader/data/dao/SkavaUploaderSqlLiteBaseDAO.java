package com.metric.skava.uploader.data.dao;

/**
* Created by metricboy on 8/7/14.
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.metric.skava.uploader.data.dao.exception.SkavaUploaderDAOException;
import com.metric.skava.uploader.data.dao.impl.sqllite.SkavaUploaderDBHelper;
import com.metric.skava.uploader.data.dao.impl.sqllite.SkavaUploaderDatabase;


/**
* Created by metricboy on 3/12/14.
*/
public abstract class SkavaUploaderSqlLiteBaseDAO {

    protected Context mContext;
    private SQLiteDatabase mDatabase;

    public Context getContext() {
        return mContext;
    }


    public SkavaUploaderSqlLiteBaseDAO(Context mContext) {
        this.mContext = mContext;
    }

    public SQLiteDatabase getDBConnection() {
        SkavaUploaderDatabase skavaDatabase = SkavaUploaderDatabase.getInstance(mContext);
        SkavaUploaderDBHelper skavaDBHelper = skavaDatabase.getSkavaDBHelper();
        if (mDatabase == null ){
            mDatabase = skavaDBHelper.getWritableDatabase();
        }
        return mDatabase;
    }


    protected Cursor getRecordsFilteredByColumn(String tableName, String columnName, Object columnValue, String orderBy) {
        String[] result_columns = null;
        String where = null;
        String whereArgs[] = null;
        if (columnValue != null) {
            // Specify the where clause that will limit our results.
            where = columnName + "=?";
            if (columnValue instanceof String) {
                whereArgs = new String[]{(String) columnValue};
            } else if (columnValue instanceof Long) {
                whereArgs = new String[]{columnValue.toString()};
            }
        }
        // Replace these with valid SQL statements as necessary.
        String groupBy = null;
        String having = null;
        String order = orderBy;
        Cursor cursor = getDBConnection().query(tableName, result_columns, where,
                whereArgs, groupBy, having, order);
        return cursor;
    }

    protected Cursor getRecordsFilteredByColumns(String tableName, String[] columnNames, Object[] columnValues, String orderBy) {
        Cursor cursor = null;
        if (columnValues != null) {
            // Specify the where clause that will limit our results.
            if (columnNames.length == columnValues.length) {
                if (columnNames.length == 1) {
                    return getRecordsFilteredByColumn(tableName, columnNames[0], columnValues[0], orderBy);
                } else {
                    String firstWhere = columnNames[0] + "=?";
                    String finalCriteria = firstWhere;
                    for (int i = 1; i < columnNames.length - 1; i++) {
                        finalCriteria += " AND " + columnNames[i] + "=?  ";
                    }
                    String lastWhere = columnNames[columnNames.length - 1] + "=?";
                    finalCriteria += " AND " + lastWhere;

                    String[] result_columns = null; // this brings me all columns (select *)
                    String where = finalCriteria;
                    String whereArgs[] = new String[columnValues.length];
                    for (int i = 0; i < columnValues.length; i++) {
                        Object columnValue = columnValues[i];
                        if (columnValues[i] instanceof String) {
                            whereArgs[i] = (String) columnValues[i];
                        } else if (columnValues[i] instanceof Long) {
                            whereArgs[i] = ((Long) columnValues[i]).toString();
                        }
                    }
                    String groupBy = null;
                    String having = null;
                    String order = null;
                    cursor = getDBConnection().query(tableName, result_columns, where, whereArgs, groupBy, having, order);
                }
            }
        }
        return cursor;
    }

    public Long saveRecord(String tableName, String[] columnNames, Object[] columnValues) throws SkavaUploaderDAOException {
        // Create a new row of values to insert.
        if (columnNames.length == columnValues.length) {
            ContentValues newValues = new ContentValues();
            // Assign values for each row.
            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i];
                Object columnValue = columnValues[i];

                if (columnValue == null) {
                    newValues.put(columnName, (String) null);
                } else {
                    // ref.: Sugar ORM https://github.com/satyan/sugar/blob/master/library/src/com/orm/SugarRecord.java#L92
                    Class<?> columnType = columnValue.getClass();
                    if (columnType.equals(Short.class) || columnType.equals(short.class)) {
                        newValues.put(columnName, (Short) columnValue);
                    } else if (columnType.equals(Integer.class) || columnType.equals(int.class)) {
                        newValues.put(columnName, (Integer) columnValue);
                    } else if (columnType.equals(Long.class) || columnType.equals(long.class)) {
                        newValues.put(columnName, (Long) columnValue);
                    } else if (columnType.equals(Float.class) || columnType.equals(float.class)) {
                        newValues.put(columnName, (Float) columnValue);
                    } else if (columnType.equals(Double.class) || columnType.equals(double.class)) {
                        newValues.put(columnName, (Double) columnValue);
                    } else if (columnType.equals(Boolean.class) || columnType.equals(boolean.class)) {
                        newValues.put(columnName, (Boolean) columnValue);
                    } else {
                        newValues.put(columnName, String.valueOf(columnValue));
                    }
                }
            }
            // [ ... Repeat for each column / value pair ... ]
            // Insert the row into your table
            SQLiteDatabase db = getDBConnection();
            //just in case newId is needed sometime in the future
//            long newId = db.insert(tableName, null, newValues);
            long newId = db.replace(tableName, null, newValues);
            if (newId == -1 ) {
                String insertValues = "[ ";
                for (int i = 0; i < columnValues.length; i++) {
                    insertValues+= columnValues[i] + ", " ;
                }
                int lastComma = insertValues.lastIndexOf(",");
                insertValues = insertValues.substring(0, lastComma);
                insertValues += " ]";
                throw new SkavaUploaderDAOException("Insert on " + tableName + " failed when attempting to insert " + insertValues);
            }
            return newId;
        } else {
            throw new SkavaUploaderDAOException("Criteria names[] and values[] must have the same number of elements.");
        }

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
