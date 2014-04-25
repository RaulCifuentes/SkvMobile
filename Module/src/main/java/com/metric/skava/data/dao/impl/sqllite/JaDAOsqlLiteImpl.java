package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.data.dao.LocalJaDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.JaTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class JaDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalJaDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public JaDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Ja getJa(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{JaTable.INDEX_CODE_COLUMN, GROUP_CODE_COLUMN, CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(JaTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Ja> list = assambleJas(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Ja> assambleJas(Cursor cursor) throws DAOException {
        List<Ja> list = new ArrayList<Ja>();
        while (cursor.moveToNext()) {
            Ja newInstance = mappedIndexInstaceBuilder.buildJaFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Ja> getAllJas(int type) throws DAOException {
        //Ja.

        Cursor cursor = getAllRecords(JaTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Ja> list = assambleJas(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveJa(Ja assessment) throws DAOException {

    }

    @Override
    public boolean deleteJa(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllJas() {
        return 0;
    }


}
