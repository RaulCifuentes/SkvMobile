package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.data.dao.LocalStrengthDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.StrengthTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class StrengthDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalStrengthDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public StrengthDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public StrengthOfRock getStrength(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{StrengthTable.INDEX_CODE_COLUMN, StrengthTable.GROUP_CODE_COLUMN, StrengthTable.CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(StrengthTable.MAPPED_INDEX_DATABASE_TABLE, names, values, null);
        List<StrengthOfRock> list = assambleStrengths(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: " + groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<StrengthOfRock> assambleStrengths(Cursor cursor) throws DAOException {
        List<StrengthOfRock> list = new ArrayList<StrengthOfRock>();
        while (cursor.moveToNext()) {
            StrengthOfRock newInstance = mappedIndexInstaceBuilder.buildStrengthFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<StrengthOfRock> getAllStrengths(StrengthOfRock.Group group) throws DAOException {
        Cursor cursor = getRecordsFilteredByColumn(StrengthTable.MAPPED_INDEX_DATABASE_TABLE, StrengthTable.GROUP_CODE_COLUMN, group.name(), null);
        List<StrengthOfRock> list = assambleStrengths(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveStrength(StrengthOfRock assessment) throws DAOException {

    }

    @Override
    public boolean deleteStrength(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllStrengths() {
        return 0;
    }


}
