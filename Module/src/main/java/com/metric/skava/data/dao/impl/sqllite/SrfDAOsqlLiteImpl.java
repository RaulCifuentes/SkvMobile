package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.data.dao.LocalSrfDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.SRFTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class SrfDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalSrfDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public SrfDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public SRF getSrf(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{SRFTable.INDEX_CODE_COLUMN, SRFTable.GROUP_CODE_COLUMN, SRFTable.CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(SRFTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<SRF> list = assambleSrfs(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<SRF> assambleSrfs(Cursor cursor) throws DAOException {
        List<SRF> list = new ArrayList<SRF>();
        while (cursor.moveToNext()) {
            SRF newInstance = mappedIndexInstaceBuilder.buildSrfFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<SRF> getAllSrfs(SRF.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(SRFTable.MAPPED_INDEX_DATABASE_TABLE, SRFTable.GROUP_CODE_COLUMN, group.name(), null);
        List<SRF> list = assambleSrfs(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveSrf(SRF assessment) throws DAOException {

    }

    @Override
    public boolean deleteSrf(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllSrfs() {
        return 0;
    }


}
