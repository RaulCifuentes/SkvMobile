package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
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

    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;


    public JrDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Jr getJr(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{JrTable.INDEX_CODE_COLUMN, JrTable.GROUP_CODE_COLUMN, JrTable.CODE_COLUMN};
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
    public List<Jr> getAllJrs(Jr.Group group) throws DAOException {
//        Cursor cursor = getAllRecords(JrTable.MAPPED_INDEX_DATABASE_TABLE);
//        String groupAsString = null;
//        switch (group) {
//            case a:
//                groupAsString = "a";
//                break;
//            case b:
//                groupAsString = "b";
//                break;
//            case c:
//                groupAsString = "c";
//                break;
//        }
        Cursor cursor = getRecordsFilteredByColumn(JrTable.MAPPED_INDEX_DATABASE_TABLE, JrTable.GROUP_CODE_COLUMN, group.name(), null);
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
