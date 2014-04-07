package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.data.dao.LocalSpacingDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.SpacingTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class SpacingDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalSpacingDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public SpacingDAOsqlLiteImpl(Context context) throws DAOException {
        super(context);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Spacing getSpacing(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{SpacingTable.INDEX_CODE_COLUMN, GROUP_CODE_COLUMN, CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(SpacingTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Spacing> list = assambleSpacings(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Spacing> assambleSpacings(Cursor cursor) throws DAOException {
        List<Spacing> list = new ArrayList<Spacing>();
        while (cursor.moveToNext()) {
            Spacing newInstance = mappedIndexInstaceBuilder.buildSpacingFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Spacing> getAllSpacings() throws DAOException {
        Cursor cursor = getAllRecords(SpacingTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Spacing> list = assambleSpacings(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveSpacing(Spacing assessment) throws DAOException {

    }

    @Override
    public boolean deleteSpacing(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllSpacings() {
        return 0;
    }


}
