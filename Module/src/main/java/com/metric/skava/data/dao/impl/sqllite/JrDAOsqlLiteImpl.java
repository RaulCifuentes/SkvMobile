package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.data.dao.LocalJrDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.JrTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class JrDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalJrDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public JrDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Jr getJr(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{JrTable.INDEX_CODE_COLUMN, GROUP_CODE_COLUMN, CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(JrTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Jr> list = assambleJrs(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Jr> assambleJrs(Cursor cursor) throws DAOException {
        List<Jr> list = new ArrayList<Jr>();
        while (cursor.moveToNext()) {
            Jr newInstance = mappedIndexInstaceBuilder.buildJrFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Jr> getAllJrs(int type) throws DAOException {
        Cursor cursor = getAllRecords(JrTable.MAPPED_INDEX_DATABASE_TABLE);
        String groupAsString = null;
        switch (type) {
            case Jr.a:
                groupAsString = "a";
                break;
            case Jr.b:
                groupAsString = "b";
                break;
            case Jr.c:
                groupAsString = "c";
                break;
        }
        getRecordsFilteredByColumn(JrTable.MAPPED_INDEX_DATABASE_TABLE, JrTable.GROUP_CODE_COLUMN, groupAsString, null);
        List<Jr> list = assambleJrs(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveJr(Jr assessment) throws DAOException {

    }

    @Override
    public boolean deleteJr(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllJrs() {
        return 0;
    }


}
