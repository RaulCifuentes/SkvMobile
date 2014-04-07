package com.metric.skava.data.dao.impl.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.metric.skava.app.data.SkavaEntity;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.SkavaEntityTable;

/**
 * Created by metricboy on 3/12/14.
 */
public abstract class SqlLiteBaseEntityDAO<T extends SkavaEntity> extends SqlLiteBaseIdentifiableEntityDAO<T> {


    public SqlLiteBaseEntityDAO(Context context) {
        super(context);
    }

    public void saveSkavaEntity(String tableName, T newSkavaEntity) throws DAOException {
        String[] columns = new String[]{SkavaEntityTable.CODE_COLUMN, SkavaEntityTable.NAME_COLUMN};
        String[] values = new String[]{newSkavaEntity.getCode(), newSkavaEntity.getName()};
        saveRecord(tableName, columns, values);
    }


    public void updateSkavaEntityName(String tableName, T skavaEntity) {
        // Create the updated row Content Values.
        ContentValues updatedValues = new ContentValues();

        // Assign values for each row.
        updatedValues.put(SkavaEntityTable.NAME_COLUMN, skavaEntity.getName());
        // [ ... Repeat for each column to update ... ]

        // Specify a where clause the defines which rows should be
        // updated. Specify where arguments as necessary.
        String where = SkavaEntityTable.CODE_COLUMN + "=?";
        String whereArgs[] = {skavaEntity.getCode()};

        // Update the row with the specified index with the new values.
        SQLiteDatabase db = getDBConnection();
        db.update(tableName, updatedValues, where, whereArgs);
    }


}
