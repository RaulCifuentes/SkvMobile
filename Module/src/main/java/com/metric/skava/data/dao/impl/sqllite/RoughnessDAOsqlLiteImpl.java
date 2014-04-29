package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.data.dao.LocalRoughnessDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.RoughnessTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class RoughnessDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalRoughnessDAO {

    private Context mContext;
    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;

    public Context getContext() {
        return mContext;
    }

    public RoughnessDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mContext = context;
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Roughness getRoughness(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{RoughnessTable.INDEX_CODE_COLUMN, RoughnessTable.GROUP_CODE_COLUMN, RoughnessTable.CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Roughness> list = assambleRoughnesss(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Roughness> assambleRoughnesss(Cursor cursor) throws DAOException {
        List<Roughness> list = new ArrayList<Roughness>();
        while (cursor.moveToNext()) {
            Roughness newInstance = mappedIndexInstaceBuilder.buildRoughnessFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Roughness> getAllRoughnesses() throws DAOException {
        Cursor cursor = getAllRecords(RoughnessTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Roughness> list = assambleRoughnesss(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveRoughness(Roughness assessment) throws DAOException {

    }

    @Override
    public boolean deleteRoughness(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllRoughnesses() {
        return 0;
    }


}
