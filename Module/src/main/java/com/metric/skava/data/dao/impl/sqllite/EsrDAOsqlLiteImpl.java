package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.data.dao.LocalEsrDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.ESRTable;
import com.metric.skava.rocksupport.model.ESR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class EsrDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalEsrDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public EsrDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public ESR getESR(String code) throws DAOException {
        String[] names = new String[]{ESRTable.INDEX_CODE_COLUMN, CODE_COLUMN};
        String[] values = new String[]{ESR.ESR_CODE, code};
        Cursor cursor = getRecordsFilteredByColumns(ESRTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<ESR> list = assambleESRs(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [ESR Code : " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<ESR> assambleESRs(Cursor cursor) throws DAOException {
        List<ESR> list = new ArrayList<ESR>();
        while (cursor.moveToNext()) {
            ESR newInstance = mappedIndexInstaceBuilder.buildESRFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<ESR> getAllESRs() throws DAOException {
        Cursor cursor = getAllRecords(ESRTable.MAPPED_INDEX_DATABASE_TABLE);
        List<ESR> list = assambleESRs(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveESR(ESR assessment) throws DAOException {

    }

    @Override
    public boolean deleteESR(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllESRs() {
        return 0;
    }


}
