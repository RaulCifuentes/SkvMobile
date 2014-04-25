package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.data.dao.LocalJnDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.JnTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class JnDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalJnDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public JnDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Jn getJn(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{JnTable.INDEX_CODE_COLUMN, GROUP_CODE_COLUMN, CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(JnTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Jn> list = assambleJns(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Jn> assambleJns(Cursor cursor) throws DAOException {
        List<Jn> list = new ArrayList<Jn>();
        while (cursor.moveToNext()) {
            Jn newInstance = mappedIndexInstaceBuilder.buildJnFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Jn> getAllJns() throws DAOException {
        Cursor cursor = getAllRecords(JnTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Jn> list = assambleJns(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveJn(Jn assessment) throws DAOException {

    }

    @Override
    public boolean deleteJn(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllJns() {
        return 0;
    }


}
