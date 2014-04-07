package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.data.dao.RemoteExcavationSectionDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SectionDropboxTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class ExcavationSectionDAODropboxImpl extends DropBoxBaseDAO implements RemoteExcavationSectionDAO {

    private SectionDropboxTable mSectionsTable;

    public ExcavationSectionDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mSectionsTable = new SectionDropboxTable(getDatastore());
    }


    @Override
    public List<ExcavationSection> getAllExcavationSections() throws DAOException {
        List<ExcavationSection> listSections = new ArrayList<ExcavationSection>();
        List<DbxRecord> recordList = mSectionsTable.findAll();
        for (DbxRecord currentDbxRecord : recordList) {
            String codigo = currentDbxRecord.getString("code");
            String nombre = currentDbxRecord.getString("name");
            ExcavationSection newSection = new ExcavationSection(codigo, nombre);
            listSections.add(newSection);
        }
        return listSections;
    }

//
//    @Override
//    public ExcavationSection getExcavationSectionByCode(String code) throws DAOException {
//        DbxRecord projectRecord = mSectionsTable.findRecordByCode(code);
//        String codigo = projectRecord.getString("code");
//        String nombre = projectRecord.getString("name");
//        ExcavationSection section = new ExcavationSection(codigo, nombre);
//        return section;
//    }


}
