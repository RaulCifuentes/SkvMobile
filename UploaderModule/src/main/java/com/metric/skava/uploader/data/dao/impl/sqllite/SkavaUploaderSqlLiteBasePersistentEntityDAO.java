//package com.metric.skava.uploader.data.dao.impl.sqllite;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.metric.skava.uploader.data.dao.SkavaUploaderSqlLiteBaseDAO;
//import com.metric.skava.uploader.data.dao.exception.SkavaUploaderDAOException;
//
//import java.util.List;
//
///**
// * Created by metricboy on 3/12/14.
// */
//public abstract class SkavaUploaderSqlLiteBasePersistentEntityDAO<T> extends SkavaUploaderSqlLiteBaseDAO {
//
//
//    public SkavaUploaderSqlLiteBasePersistentEntityDAO(Context context) {
//        super(context);
//    }
//
//    protected abstract void savePersistentEntity(String tableName, T newSkavaEntity) throws SkavaUploaderDAOException;
//
//    protected abstract List<T> assemblePersistentEntities(Cursor cursor) throws SkavaUploaderDAOException;
//
//    public T getPersistentEntityByCandidateKey(String tableName, String candidateKeyColumn, String keyValue) throws SkavaUploaderDAOException {
//        Cursor cursor = getRecordsFilteredByColumn(tableName, candidateKeyColumn, keyValue, null);
//        List<T> list = assemblePersistentEntities(cursor);
//        if (list.isEmpty()) {
//            throw new SkavaUploaderDAOException("Entity not found. Table[ " + tableName + "] Column [ " + candidateKeyColumn + " ] Value [ " + keyValue + " ]");
//        }
//        if (list.size() > 1) {
//            throw new SkavaUploaderDAOException("Multiple records for same code. Table[ " + tableName + "] Column [ " + candidateKeyColumn + " ] Value [ " + keyValue + " ]");
//        }
//        cursor.close();
//        return list.get(0);
//    }
//
//    public List<T> getAllPersistentEntities(String tableName) throws SkavaUploaderDAOException {
//        Cursor cursor = getAllRecords(tableName);
//        List<T> list = assemblePersistentEntities(cursor);
//        cursor.close();
//        return list;
//    }
//
//    public int deleteAllPersistentEntities(String tableName) {
//        // Specify a where clause that determines which row(s) to delete.
//        // Specify where arguments as necessary.
//        String where = null;
//        String[] whereArgs = null;
//        // Delete the rows that match the where clause.
//        SQLiteDatabase db = getDBConnection();
//        return db.delete(tableName, where, whereArgs);
//    }
//
//    public int deletePersistentEntitiesFilteredByColumn(String tableName, String columnName, Object columnValue) {
//        // Specify a where clause that determines which row(s) to delete.
//        // Specify where arguments as necessary.
//        String where = null;
//        String whereArgs[] = null;
//        if (columnValue != null) {
//            // Specify the where clause that will limit our results.
//            where = columnName + "=?";
//            if (columnValue instanceof String) {
//                whereArgs = new String[]{(String) columnValue};
//            } else if (columnValue instanceof Long) {
//                whereArgs = new String[]{columnValue.toString()};
//            }
//        }
//        // Delete the rows that match the where clause.
//        SQLiteDatabase db = getDBConnection();
//        return db.delete(tableName, where, whereArgs);
//    }
//
//    public int deletePersistentEntitiesFilteredByColumns(String tableName, String[] columnNames, Object[] columnValues) {
//        if (columnNames.length == columnValues.length) {
//            if (columnNames.length == 1) {
//                return deletePersistentEntitiesFilteredByColumn(tableName, columnNames[0], columnValues[0]);
//            } else {
//                String firstWhere = columnNames[0] + "=?";
//                String finalCriteria = firstWhere;
//                for (int i = 1; i < columnNames.length - 1; i++) {
//                    finalCriteria += " AND " + columnNames[i] + "=?  ";
//                }
//                String lastWhere = columnNames[columnNames.length - 1] + "=?";
//                finalCriteria += " AND " + lastWhere;
//
//                String[] result_columns = null; // this brings me all columns (select *)
//                String where = finalCriteria;
//                String whereArgs[] = new String[columnValues.length];
//                for (int i = 0; i < columnValues.length; i++) {
//                    Object columnValue = columnValues[i];
//                    if (columnValues[i] instanceof String) {
//                        whereArgs[i] = (String) columnValues[i];
//                    } else if (columnValues[i] instanceof Long) {
//                        whereArgs[i] = ((Long) columnValues[i]).toString();
//                    }
//                }
//                return getDBConnection().delete(tableName, where, whereArgs);
//            }
//        }
//        return -1;
//    }
//
//}
