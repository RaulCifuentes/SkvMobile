package com.metric.skava.data.dao.impl.sqllite;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.data.dao.LocalJwDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.helper.MappedIndexInstanceBuilder4SqlLite;
import com.metric.skava.data.dao.impl.sqllite.table.JwTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/18/14.
 */
public class JwDAOsqlLiteImpl extends SqlLiteBaseDAO implements LocalJwDAO {

    private MappedIndexInstanceBuilder4SqlLite mappedIndexInstaceBuilder;


    public JwDAOsqlLiteImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        mappedIndexInstaceBuilder = new MappedIndexInstanceBuilder4SqlLite(mContext);
    }

    @Override
    public Jw getJw(String indexCode, String groupCode, String code) throws DAOException {
        String[] names = new String[]{JwTable.INDEX_CODE_COLUMN, JwTable.GROUP_CODE_COLUMN, JwTable.CODE_COLUMN};
        String[] values = new String[]{indexCode, groupCode, code};
        Cursor cursor = getRecordsFilteredByColumns(JwTable.MAPPED_INDEX_DATABASE_TABLE, names , values, null );
        List<Jw> list = assambleJws(cursor);
        if (list.isEmpty()) {
            throw new DAOException("Entity not found. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        if (list.size() > 1) {
            throw new DAOException("Multiple records for same code. [Index Code : " + indexCode + ", Group Code: "+ groupCode + ", Code: " + code + " ]");
        }
        cursor.close();
        return list.get(0);
    }


    protected List<Jw> assambleJws(Cursor cursor) throws DAOException {
        List<Jw> list = new ArrayList<Jw>();
        while (cursor.moveToNext()) {
            Jw newInstance = mappedIndexInstaceBuilder.buildJwFromCursorRecord(cursor);
            list.add(newInstance);
        }
        return list;
    }


    @Override
    public List<Jw> getAllJws() throws DAOException {
        Cursor cursor = getAllRecords(JwTable.MAPPED_INDEX_DATABASE_TABLE);
        List<Jw> list = assambleJws(cursor);
        cursor.close();
        return list;
    }


    @Override
    public void saveJw(Jw assessment) throws DAOException {

    }

    @Override
    public boolean deleteJw(String indexCode, String groupCode, String code) {
        return false;
    }

    @Override
    public int deleteAllJws() {
        return 0;
    }


}
