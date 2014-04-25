package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.data.dao.LocalPersistenceDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.PersistenceTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class PersistenceDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalPersistenceDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public PersistenceDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Persistence getPersistence(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{PersistenceTable.INDEX_CODE_COLUMN, GROUP_CODE_COLUMN, CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(PersistenceTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Persistence> list = assamblePersistences(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Persistence> assamblePersistences(Cursor cursor) throws DAOException {
        List<Persistence> list = new ArrayList<Persistence>();
        while (cursor.moveToNext()) {
            Persistence newInstance = mappedIndexInstaceBuilder.buildPersistenceFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Persistence> getAllPersistences() throws DAOException {
        Cursor cursor = getAllRecords(PersistenceTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Persistence> list = assamblePersistences(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void savePersistence(Persistence assessment) throws DAOException {

    }

    @Override
    public boolean deletePersistence(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllPersistences() {
        return 0;
    }


}
