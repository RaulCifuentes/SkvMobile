package com.metric.skava.data.dao.impl.sqllite.helper;

import android.content.Context;
import android.database.Cursor;

import com.metric.skava.app.database.utils.CursorUtils;
import com.metric.skava.calculator.barton.model.Ja;
import com.metric.skava.calculator.barton.model.Jn;
import com.metric.skava.calculator.barton.model.Jr;
import com.metric.skava.calculator.barton.model.Jw;
import com.metric.skava.calculator.barton.model.SRF;
import com.metric.skava.calculator.rmr.model.Aperture;
import com.metric.skava.calculator.rmr.model.Groundwater;
import com.metric.skava.calculator.rmr.model.Infilling;
import com.metric.skava.calculator.rmr.model.OrientationDiscontinuities;
import com.metric.skava.calculator.rmr.model.Persistence;
import com.metric.skava.calculator.rmr.model.Roughness;
import com.metric.skava.calculator.rmr.model.Spacing;
import com.metric.skava.calculator.rmr.model.StrengthOfRock;
import com.metric.skava.calculator.rmr.model.Weathering;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.ApertureTable;
import com.metric.skava.data.dao.impl.sqllite.table.ESRTable;
import com.metric.skava.data.dao.impl.sqllite.table.GroundwaterTable;
import com.metric.skava.data.dao.impl.sqllite.table.InfillingTable;
import com.metric.skava.data.dao.impl.sqllite.table.JaTable;
import com.metric.skava.data.dao.impl.sqllite.table.JnTable;
import com.metric.skava.data.dao.impl.sqllite.table.JrTable;
import com.metric.skava.data.dao.impl.sqllite.table.JwTable;
import com.metric.skava.data.dao.impl.sqllite.table.OrientationTable;
import com.metric.skava.data.dao.impl.sqllite.table.PersistenceTable;
import com.metric.skava.data.dao.impl.sqllite.table.SRFTable;
import com.metric.skava.data.dao.impl.sqllite.table.SpacingTable;
import com.metric.skava.rocksupport.model.ESR;

/**
 * Created by metricboy on 3/18/14.
 */
public class MappedIndexInstanceBuilder4SqlLite {

    private final Context mContext;


    public MappedIndexInstanceBuilder4SqlLite(Context context) throws DAOException {
        this.mContext = context;
    }

    public Ja buildJaFromCursorRecord(Cursor cursor) throws DAOException {

        String groupCode = CursorUtils.getString(JaTable.GROUP_CODE_COLUMN, cursor);
        String jaCode = CursorUtils.getString(JaTable.CODE_COLUMN, cursor);
        String description = CursorUtils.getString(JaTable.CODE_COLUMN, cursor);
        Double value = CursorUtils.getDouble(JaTable.VALUE_COLUMN, cursor);

        Ja babyJa = new Ja(jaCode, description, value);

        if (groupCode.equalsIgnoreCase("a")){
            babyJa.setGroupType(Ja.a);
        }
        if (groupCode.equalsIgnoreCase("b")){
            babyJa.setGroupType(Ja.b);
        }
        if (groupCode.equalsIgnoreCase("c")){
            babyJa.setGroupType(Ja.c);
        }
        return babyJa;
    }


    public Jr buildJrFromCursorRecord(Cursor cursor) throws DAOException {

        String groupCode = CursorUtils.getString(JrTable.GROUP_CODE_COLUMN, cursor);
        String jrCode = CursorUtils.getString(JrTable.CODE_COLUMN, cursor);
        String description = CursorUtils.getString(JrTable.CODE_COLUMN, cursor);
        Double value = CursorUtils.getDouble(JrTable.VALUE_COLUMN, cursor);

        Jr babyJr = new Jr(jrCode, description, value);

        if (groupCode.equalsIgnoreCase("a")){
            babyJr.setGroupType(Jr.a);
        }
        if (groupCode.equalsIgnoreCase("b")){
            babyJr.setGroupType(Jr.b);
        }
        if (groupCode.equalsIgnoreCase("c")){
            babyJr.setGroupType(Jr.c);
        }
        return babyJr;
    }

    public Jn buildJnFromCursorRecord(Cursor cursor) throws DAOException {
        String jnCode = CursorUtils.getString(JnTable.CODE_COLUMN, cursor);
        String description = CursorUtils.getString(JnTable.CODE_COLUMN, cursor);
        Double value = CursorUtils.getDouble(JnTable.VALUE_COLUMN, cursor);

        Jn babyJn = new Jn(jnCode, description, value);
        return babyJn;
    }


    public Jw buildJwFromCursorRecord(Cursor cursor) throws DAOException {
        String jwCode = CursorUtils.getString(JwTable.CODE_COLUMN, cursor);
        String description = CursorUtils.getString(JwTable.CODE_COLUMN, cursor);
        Double value = CursorUtils.getDouble(JwTable.VALUE_COLUMN, cursor);

        Jw babyJw = new Jw(jwCode, description, value);
        return babyJw;
    }


    public Spacing buildSpacingFromCursorRecord(Cursor cursor) {
        Spacing babySpacing;
        String code = CursorUtils.getString(SpacingTable.CODE_COLUMN, cursor);
        String desc = CursorUtils.getString(SpacingTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(SpacingTable.VALUE_COLUMN, cursor);
        babySpacing = new Spacing(code, desc, value);
        return babySpacing;
    }

    public Persistence buildPersistenceFromCursorRecord(Cursor cursor) {
        Persistence babyPersistence;
        String code = CursorUtils.getString(PersistenceTable.CODE_COLUMN, cursor);
        String desc = CursorUtils.getString(PersistenceTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(PersistenceTable.VALUE_COLUMN, cursor);
        babyPersistence = new Persistence(code, desc, value);
        return babyPersistence;
    }

    public Aperture buildApertureFromCursorRecord(Cursor cursor) {
        Aperture babyAperture;
        String code = CursorUtils.getString(ApertureTable.CODE_COLUMN, cursor);
        String desc = CursorUtils.getString(ApertureTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(ApertureTable.VALUE_COLUMN, cursor);
        babyAperture = new Aperture(code, desc, value);
        return babyAperture;
    }

    public Infilling buildInfillingFromCursorRecord(Cursor cursor) {
        Infilling babyInfilling;
        String code = CursorUtils.getString(InfillingTable.CODE_COLUMN, cursor);
        String desc = CursorUtils.getString(InfillingTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(InfillingTable.VALUE_COLUMN, cursor);
        babyInfilling = new Infilling(code, desc, value);
        return babyInfilling;
    }


    public ESR buildESRFromCursorRecord(Cursor cursor) {
        String code = CursorUtils.getString(ESRTable.CODE_COLUMN, cursor);
        String description = CursorUtils.getString(ESRTable.CODE_COLUMN, cursor);
        Double value = CursorUtils.getDouble(ESRTable.VALUE_COLUMN, cursor);
        ESR babyESR = new ESR(code, description, value);
        return babyESR;
    }

    public SRF buildSrfFromCursorRecord(Cursor cursor) {
        String code = CursorUtils.getString(SRFTable.CODE_COLUMN, cursor);
        String description = CursorUtils.getString(SRFTable.CODE_COLUMN, cursor);
        Double value = CursorUtils.getDouble(SRFTable.VALUE_COLUMN, cursor);
        SRF babySRF = new SRF(code, description, value);
        return babySRF;
    }


    public OrientationDiscontinuities buildOrientationDiscontinuitiesFromCursorRecord(Cursor cursor) {
        OrientationDiscontinuities babyOrientationDiscontinuities;
        String code = CursorUtils.getString(OrientationTable.CODE_COLUMN, cursor);
        String desc = CursorUtils.getString(OrientationTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(OrientationTable.VALUE_COLUMN, cursor);
        babyOrientationDiscontinuities = new OrientationDiscontinuities(code, desc, value);
        return babyOrientationDiscontinuities;
    }

    public Roughness buildRoughnessFromCursorRecord(Cursor cursor) {
        Roughness babyRoughness;
        String code = CursorUtils.getString(OrientationTable.CODE_COLUMN, cursor);
        String desc = CursorUtils.getString(OrientationTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(OrientationTable.VALUE_COLUMN, cursor);
        babyRoughness = new Roughness(code, desc, value);
        return babyRoughness;
    }


    public Weathering buildWeatheringFromCursorRecord(Cursor cursor) {
        Weathering babyWeathering;
        String code = CursorUtils.getString(OrientationTable.CODE_COLUMN, cursor);
        String desc = CursorUtils.getString(OrientationTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(OrientationTable.VALUE_COLUMN, cursor);
        babyWeathering = new Weathering(code, desc, value);
        return babyWeathering;
    }

    public StrengthOfRock buildStrengthFromCursorRecord(Cursor cursor) {
        StrengthOfRock babyStrengthOfRock;
        String code = CursorUtils.getString(OrientationTable.CODE_COLUMN, cursor);
        String pointLoad = CursorUtils.getString(OrientationTable.DESCRIPTION_COLUMN, cursor);
        String uniaxial = null;
        Double value = CursorUtils.getDouble(OrientationTable.VALUE_COLUMN, cursor);
        babyStrengthOfRock = new StrengthOfRock(code, pointLoad, uniaxial, value);
        return babyStrengthOfRock;
    }

    public Groundwater buildGroundwaterFromCursorRecord(Cursor cursor) {
        //TODO Armar el groundwater (tal vez incorporar la logica de group en vez del array de categories)
        Groundwater babyGroundwater;
        String code = CursorUtils.getString(GroundwaterTable.CODE_COLUMN, cursor);
        String desc = CursorUtils.getString(GroundwaterTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(GroundwaterTable.VALUE_COLUMN, cursor);
        String inflow = null;
        String general = null;
        String jointPress = null;
        babyGroundwater = new Groundwater(code, inflow, jointPress, general, value);
        return babyGroundwater;
    }




}
