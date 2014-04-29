package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.data.dao.LocalDiscontinuityShapeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.DiscontinuityShapeTable;
import com.metric.skava.discontinuities.model.DiscontinuityShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/14/14.
 */
public class DiscontinuityShapeDAOsqlLiteImpl extends SqlLiteBaseEntityDAO<DiscontinuityShape> implements LocalDiscontinuityShapeDAO {

    public DiscontinuityShapeDAOsqlLiteImpl(Context context, SkavaContext skavaContext) {
        super(context, skavaContext);
    }


    @Override
    protected List<DiscontinuityShape> assemblePersistentEntities(Cursor cursor) throws DAOException {
        List<DiscontinuityShape> list = new ArrayList<DiscontinuityShape>();
        while (cursor.moveToNext()) {
            String code = CursorUtils.getString(DiscontinuityShapeTable.CODE_COLUMN, cursor);
            String name = CursorUtils.getString(DiscontinuityShapeTable.NAME_COLUMN, cursor);
            DiscontinuityShape newInstance = new DiscontinuityShape(code, name);
            list.add(newInstance);
        }
        return list;

    }


    @Override
    public DiscontinuityShape getDiscontinuityShapeByCode(String code) throws DAOException {
        //TODO check if this if is necessary
        // HACK: Para cuando se olviden de llenar el fracture
        if (!code.equals("HINT")) {
            DiscontinuityShape entity = getIdentifiableEntityByCode(DiscontinuityShapeTable.SHAPE_DATABASE_TABLE, code);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public List<DiscontinuityShape> getAllDiscontinuityShapes() throws DAOException {
        List<DiscontinuityShape> list = getAllPersistentEntities(DiscontinuityShapeTable.SHAPE_DATABASE_TABLE);
        return list;
    }

    @Override
    protected void savePersistentEntity(String tableName, DiscontinuityShape newSkavaEntity) throws DAOException {
        saveSkavaEntity(tableName, newSkavaEntity);
    }

    @Override
    public void saveDiscontinuityShape(DiscontinuityShape newEntity) throws DAOException {
        savePersistentEntity(DiscontinuityShapeTable.SHAPE_DATABASE_TABLE, newEntity);
    }

    @Override
    public boolean deleteDiscontinuityShape(String code) {
        return deleteIdentifiableEntity(DiscontinuityShapeTable.SHAPE_DATABASE_TABLE, code);
    }

    @Override
    public int deleteAllDiscontinuityShapes() {
        return deleteAllPersistentEntities(DiscontinuityShapeTable.SHAPE_DATABASE_TABLE);
    }

}
