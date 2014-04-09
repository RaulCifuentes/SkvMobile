package com.metric.skava.data.dao.impl.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.metric.skava.data.dao.exception.DAOException;

/**
 * Created by metricboy on 3/12/14.
 */
public abstract class SqlLiteBaseDAO {

    protected Context mContext;

    public Context getContext() {
        return mContext;
    }

    public SqlLiteBaseDAO(Context mContext) {
        this.mContext = mContext;
    }


    public SQLiteDatabase getDBConnection() {
        SkavaDatabase skavaDatabase = SkavaDatabase.getInstance(mContext);
        SkavaDBHelper skavaDBHelper = skavaDatabase.getSkavaDBHelper();
        SQLiteDatabase db = skavaDBHelper.getWritableDatabase();
        return db;
    }

    protected Cursor getAllRecords(String tableName) {
        return getRecordsFilteredByColumn(tableName, null, null, null);
    }

    protected Cursor getRecordsFilteredByColumn(String tableName, String columnName, Object columnValue, String orderBy) {
        String[] result_columns = null;
        String where = null;
        String whereArgs[] = null;
        if (columnValue != null) {
            // Specify the where clause that will limit our results.
            where = columnName + "=?";
            if (columnValue instanceof String){
                whereArgs = new String[]{(String)columnValue};
            } else if (columnValue instanceof Long){
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
                }
                else{
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
                        if (columnValues[i] instanceof String){
                            whereArgs[i] = (String) columnValues[i];
                        } else if (columnValues[i] instanceof Long){
                            whereArgs[i] =  ((Long) columnValues[i]).toString();
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

    public Long saveRecord(String tableName, String[] columnNames, Object[] columnValues) throws DAOException {
        // Create a new row of values to insert.
        if (columnNames.length == columnValues.length) {
            ContentValues newValues = new ContentValues();
            // Assign values for each row.
            for (int i = 0; i < columnNames.length; i++) {
                if (columnValues[i] instanceof String){
                    newValues.put(columnNames[i], (String) columnValues[i]);
                } else if (columnValues[i] instanceof Long){
                    newValues.put(columnNames[i], (Long) columnValues[i]);
                } else if (columnValues[i] instanceof Double){
                    newValues.put(columnNames[i], (Double) columnValues[i]);
                } else if (columnValues[i] instanceof Short){
                    newValues.put(columnNames[i], (Short) columnValues[i]);
                }
            }
            // [ ... Repeat for each column / value pair ... ]
            // Insert the row into your table
            SQLiteDatabase db = getDBConnection();
            //just in case newId is needed sometime in the future
//            long newId = db.insert(tableName, null, newValues);
            long newId = db.replace(tableName, null, newValues);
            return newId;
        } else {
            throw new DAOException("Criteria names[] and values[] must have the same number of elements.");
        }

    }


}
