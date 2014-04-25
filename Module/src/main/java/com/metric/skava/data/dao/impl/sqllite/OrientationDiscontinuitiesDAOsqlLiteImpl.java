package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

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

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public OrientationDiscontinuitiesDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public OrientationDiscontinuities getOrientationDiscontinuities(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{OrientationTable.INDEX_CODE_COLUMN, GROUP_CODE_COLUMN, CODE_COLUMN};
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
    public List<OrientationDiscontinuities> getAllOrientationDiscontinuities(int type) throws DAOException {
        //TODO hacer el lookup considerando el group que viene como OrientationDiscontinuities.SLOPES, etc

        Cursor cursor = getAllRecords(OrientationTable.MAPPED_INDEX_DATABASE_TABLE);
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
