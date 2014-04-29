package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.data.dao.LocalOrientationDiscontinuitiesDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.OrientationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class OrientationDiscontinuitiesDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalOrientationDiscontinuitiesDAO {


    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public OrientationDiscontinuitiesDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public OrientationDiscontinuities getOrientationDiscontinuities(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{OrientationTable.INDEX_CODE_COLUMN, OrientationTable.GROUP_CODE_COLUMN, OrientationTable.CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(OrientationTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<OrientationDiscontinuities> list = assambleOrientationDiscontinuities(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<OrientationDiscontinuities> assambleOrientationDiscontinuities(Cursor cursor) throws DAOException {
        List<OrientationDiscontinuities> list = new ArrayList<OrientationDiscontinuities>();
        while (cursor.moveToNext()) {
            OrientationDiscontinuities newInstance = mappedIndexInstaceBuilder.buildOrientationDiscontinuitiesFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<OrientationDiscontinuities> getAllOrientationDiscontinuities(OrientationDiscontinuities.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(OrientationTable.MAPPED_INDEX_DATABASE_TABLE, OrientationTable.GROUP_CODE_COLUMN, group.name(), null );
        List<OrientationDiscontinuities> list = assambleOrientationDiscontinuities(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveOrientationDiscontinuities(OrientationDiscontinuities assessment) throws DAOException {

    }

    @Override
    public boolean deleteOrientationDiscontinuities(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllOrientationDiscontinuities() {
        return 0;
    }


}
