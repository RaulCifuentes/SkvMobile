package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.data.IdentifiableEntity;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.SkavaEntityTable;

/**
 * Created by metricboy on 3/12/14.
 */
public abstract class SqlLiteBaseIdentifiableEntityDAO<T extends IdentifiableEntity> extends SqlLiteBasePersistentEntityDAO<T> {


    public SqlLiteBaseIdentifiableEntityDAO(Context context, SkavaContext skavaContext) {
     super(context, skavaContext);
    }


    private Cursor getRecordsByCode(String tableName, String code) {
        return getRecordsFilteredByColumn(tableName, SkavaEntityTable.CODE_COLUMN, code, null);
    }


    public T getIdentifiableEntityByCode(String tableName, String code) throws DAOException {
        return getPersistentEntityByCandidateKey(tableName, SkavaEntityTable.CODE_COLUMN, code);
    }


    public boolean deleteIdentifiableEntity(String tableName, String code) {
        // Specify a where clause that determines which row(s) to delete.
        // Specify where arguments as necessary.
        String where = SkavaEntityTable.CODE_COLUMN + "=?";
        String[] whereArgs = new String[]{code};
        // Delete the rows that match the where clause.
        SQLiteDatabase db = getDBConnection();
        int howMany = db.delete(tableName, where, whereArgs);
        return (howMany==1);
    }


}
