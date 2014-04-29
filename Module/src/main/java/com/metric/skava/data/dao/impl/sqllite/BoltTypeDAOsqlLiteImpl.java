package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalBoltTypeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.BoltTypeTable;
import com.metric.skava.instructions.model.BoltType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class BoltTypeDAOsqlLiteImpl extends SqlLiteBaseIdentifiableEntityDAO<BoltType> implements LocalBoltTypeDAO {

    public BoltTypeDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }



    @Override
    protected List<BoltType> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<BoltType> list = new ArrayList<BoltType>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(BoltTypeTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(BoltTypeTable.NAME_COLUMN, cursor);
            BoltType newInstance = new BoltType(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public BoltType getBoltTypeByCode(String code) throws DAOException {
        BoltType entity = getIdentifiableEntityByCode(BoltTypeTable.BOLT_DATABASE_TABLE, code);
        return entity;
    }

    @Override
    public List<BoltType> getAllBoltTypes() throws DAOException {
        List<BoltType> list = getAllPersistentEntities(BoltTypeTable.BOLT_DATABASE_TABLE);
        return list;
    }

    @Override
    public void saveBoltType(BoltType newEntity) throws DAOException {
        savePersistentEntity(BoltTypeTable.BOLT_DATABASE_TABLE, newEntity );
    }

    @Override
    protected void savePersistentEntity(String tableName, BoltType newSkavaEntity) throws DAOException {

    }

    @Override
    public boolean deleteBoltType(String code) {
        return false;
    }

    @Override
    public int deleteAllBoltTypes() {
        return 0;
    }


}
