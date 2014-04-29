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
import com.metric.skava.data.dao.impl.sqllite.table.RoughnessTable;
import com.metric.skava.data.dao.impl.sqllite.table.SRFTable;
import com.metric.skava.data.dao.impl.sqllite.table.SpacingTable;
import com.metric.skava.data.dao.impl.sqllite.table.StrengthTable;
import com.metric.skava.data.dao.impl.sqllite.table.WeatheringTable;
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
        String jaKey = CursorUtils.getString(JaTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JaTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(JaTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(JaTable.VALUE_COLUMN, cursor);

        Ja.Group group = null;
        if (Ja.Group.a.name().equalsIgnoreCase(groupCode)){
            group =  Ja.Group.a;
        }
        if (Ja.Group.b.name().equalsIgnoreCase(groupCode)){
            group =  Ja.Group.b;
        }
        if (Ja.Group.c.name().equalsIgnoreCase(groupCode)){
            group =  Ja.Group.c;
        }
        Ja babyJa = new Ja(group, jaCode, jaKey, shortDescription, description, value);
        return babyJa;
    }


    public Jr buildJrFromCursorRecord(Cursor cursor) throws DAOException {

        String groupCode = CursorUtils.getString(JrTable.GROUP_CODE_COLUMN, cursor);
        String jrCode = CursorUtils.getString(JrTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JrTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JrTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(JrTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(JrTable.VALUE_COLUMN, cursor);

        Jr.Group group = null;
        if (Jr.Group.a.name().equalsIgnoreCase(groupCode)) {
            group = Jr.Group.a;
        }
        if (Jr.Group.b.name().equalsIgnoreCase(groupCode)){
            group = Jr.Group.b;
        }
        if (Jr.Group.c.name().equalsIgnoreCase(groupCode)){
            group = Jr.Group.c;
        }

        Jr babyJr = new Jr(group, jrCode, key, shortDescription, description, value);
        return babyJr;
    }

    public Jn buildJnFromCursorRecord(Cursor cursor) throws DAOException {
        String jnCode = CursorUtils.getString(JnTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(JnTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(JnTable.VALUE_COLUMN, cursor);

        Jn babyJn = new Jn(jnCode, key, shortDescription, description, value);
        return babyJn;
    }


    public Jw buildJwFromCursorRecord(Cursor cursor) throws DAOException {
        String jwCode = CursorUtils.getString(JwTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JwTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JwTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(JwTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(JwTable.VALUE_COLUMN, cursor);

        Jw babyJw = new Jw(jwCode, key, shortDescription, description, value);
        return babyJw;
    }


    public Spacing buildSpacingFromCursorRecord(Cursor cursor) {
        Spacing babySpacing;
        String code = CursorUtils.getString(SpacingTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String desc = CursorUtils.getString(SpacingTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(SpacingTable.VALUE_COLUMN, cursor);
        babySpacing = new Spacing(code, key, shortDescription, desc, value);
        return babySpacing;
    }

    public Persistence buildPersistenceFromCursorRecord(Cursor cursor) {
        Persistence babyPersistence;
        String code = CursorUtils.getString(PersistenceTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String desc = CursorUtils.getString(PersistenceTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(PersistenceTable.VALUE_COLUMN, cursor);
        babyPersistence = new Persistence(code, key, shortDescription, desc, value);
        return babyPersistence;
    }

    public Aperture buildApertureFromCursorRecord(Cursor cursor) {
        Aperture babyAperture;
        String code = CursorUtils.getString(ApertureTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String desc = CursorUtils.getString(ApertureTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(ApertureTable.VALUE_COLUMN, cursor);
        babyAperture = new Aperture(code, key, shortDescription, desc, value);
        return babyAperture;
    }

    public Infilling buildInfillingFromCursorRecord(Cursor cursor) {
        Infilling babyInfilling;
        String code = CursorUtils.getString(InfillingTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String desc = CursorUtils.getString(InfillingTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(InfillingTable.VALUE_COLUMN, cursor);
        babyInfilling = new Infilling(code, key, shortDescription, desc, value);
        return babyInfilling;
    }


    public ESR buildESRFromCursorRecord(Cursor cursor) {
        String groupCode = CursorUtils.getString(JrTable.GROUP_CODE_COLUMN, cursor);
        String code = CursorUtils.getString(ESRTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(ESRTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(ESRTable.VALUE_COLUMN, cursor);
        ESR.Group group = null;
        if (ESR.Group.i.name().equalsIgnoreCase(groupCode)) {
            group = ESR.Group.i;
        }
        if (ESR.Group.ii.name().equalsIgnoreCase(groupCode)){
            group =  ESR.Group.ii;
        }
        ESR babyESR = new ESR(group, code, key, shortDescription, description, value);
        return babyESR;
    }

    public SRF buildSrfFromCursorRecord(Cursor cursor) {
        String groupCode = CursorUtils.getString(JrTable.GROUP_CODE_COLUMN, cursor);
        String code = CursorUtils.getString(SRFTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(ESRTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(SRFTable.VALUE_COLUMN, cursor);
        SRF.Group group = null;
        if (SRF.Group.a.name().equalsIgnoreCase(groupCode)) {
            group = SRF.Group.a;
        }
        if (SRF.Group.b.name().equalsIgnoreCase(groupCode)){
            group =  SRF.Group.b;
        }
        if (SRF.Group.c.name().equalsIgnoreCase(groupCode)){
            group =  SRF.Group.c;
        }
        if (SRF.Group.d.name().equalsIgnoreCase(groupCode)){
            group =  SRF.Group.d;
        }
        SRF babySRF = new SRF(group, code, key, shortDescription, description, value);
        return babySRF;
    }


    public OrientationDiscontinuities buildOrientationDiscontinuitiesFromCursorRecord(Cursor cursor) {
        OrientationDiscontinuities babyOrientationDiscontinuities;
        String code = CursorUtils.getString(OrientationTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(ESRTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(OrientationTable.VALUE_COLUMN, cursor);
        String groupCode = CursorUtils.getString(OrientationTable.GROUP_CODE_COLUMN, cursor);

        OrientationDiscontinuities.Group group = null;
        if (OrientationDiscontinuities.Group.TUNNELS_MINES.name().equalsIgnoreCase(groupCode)){
            group = OrientationDiscontinuities.Group.TUNNELS_MINES;
        }
        if (OrientationDiscontinuities.Group.FOUNDATIONS.name().equalsIgnoreCase(groupCode)){
            group = OrientationDiscontinuities.Group.FOUNDATIONS;
        }
        if (OrientationDiscontinuities.Group.SLOPES.name().equalsIgnoreCase(groupCode)){
            group = OrientationDiscontinuities.Group.SLOPES;
        }
        babyOrientationDiscontinuities = new OrientationDiscontinuities(group, code, key, shortDescription, description, value);
        return babyOrientationDiscontinuities;
    }

    public Roughness buildRoughnessFromCursorRecord(Cursor cursor) {
        Roughness babyRoughness;
        String code = CursorUtils.getString(RoughnessTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(ESRTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(RoughnessTable.VALUE_COLUMN, cursor);
        babyRoughness = new Roughness(code, key, shortDescription, description, value);
        return babyRoughness;
    }


    public Weathering buildWeatheringFromCursorRecord(Cursor cursor) {
        Weathering babyWeathering;
        String code = CursorUtils.getString(WeatheringTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(ESRTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(WeatheringTable.VALUE_COLUMN, cursor);
        babyWeathering = new Weathering(code, key, shortDescription, description, value);
        return babyWeathering;
    }

    public StrengthOfRock buildStrengthFromCursorRecord(Cursor cursor) {
        StrengthOfRock babyStrengthOfRock;
        String code = CursorUtils.getString(StrengthTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(ESRTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(StrengthTable.VALUE_COLUMN, cursor);
        String groupCode = CursorUtils.getString(StrengthTable.GROUP_CODE_COLUMN, cursor);

        StrengthOfRock.Group group = null;
        if (StrengthOfRock.Group.POINT_LOAD_KEY.name().equalsIgnoreCase(groupCode)){
            group = StrengthOfRock.Group.POINT_LOAD_KEY;
        }
        if (StrengthOfRock.Group.UNIAXIAL_KEY.name().equalsIgnoreCase(groupCode)){
            group = StrengthOfRock.Group.UNIAXIAL_KEY;
        }

        babyStrengthOfRock = new StrengthOfRock(group, code, key, shortDescription, description, value);
        return babyStrengthOfRock;
    }

    public Groundwater buildGroundwaterFromCursorRecord(Cursor cursor) {
        Groundwater babyGroundwater;
        String code = CursorUtils.getString(GroundwaterTable.CODE_COLUMN, cursor);
        String key = CursorUtils.getString(JnTable.KEY_COLUMN, cursor);
        String shortDescription = CursorUtils.getString(JnTable.SHORT_DESCRIPTION_COLUMN, cursor);
        String description = CursorUtils.getString(ESRTable.DESCRIPTION_COLUMN, cursor);
        Double value = CursorUtils.getDouble(GroundwaterTable.VALUE_COLUMN, cursor);
        String groupCode = CursorUtils.getString(OrientationTable.GROUP_CODE_COLUMN, cursor);

        Groundwater.Group group = null;
        if (Groundwater.Group.INFLOW_LENGHT.name().equalsIgnoreCase(groupCode)){
            group = Groundwater.Group.INFLOW_LENGHT;
        }
        if (Groundwater.Group.JOINT_PRESS_PRINCIPAL.name().equalsIgnoreCase(groupCode)){
            group = Groundwater.Group.JOINT_PRESS_PRINCIPAL;
        }
        if (Groundwater.Group.GENERAL_CONDITIONS.name().equalsIgnoreCase(groupCode)){
            group = Groundwater.Group.GENERAL_CONDITIONS;
        }
        babyGroundwater = new Groundwater(group, code, key, shortDescription, description, value);
        return babyGroundwater;
    }




}
