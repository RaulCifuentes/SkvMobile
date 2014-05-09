package com.metric.skava.data.dao.impl.dropbox;

import android.content.Context;

import com.dropbox.sync.android.DbxDatastoreStatus;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxRecord;
import com.metric.skava.app.context.SkavaContext;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.data.dao.LocalArchTypeDAO;
import com.metric.skava.data.dao.LocalBoltTypeDAO;
import com.metric.skava.data.dao.LocalCoverageDAO;
import com.metric.skava.data.dao.LocalMeshTypeDAO;
import com.metric.skava.data.dao.LocalRockQualityDAO;
import com.metric.skava.data.dao.LocalShotcreteTypeDAO;
import com.metric.skava.data.dao.LocalSupportPatternTypeDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.RemoteSupportRequirementDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.tables.SupportRequirementDropboxTable;
import com.metric.skava.instructions.model.ArchType;
import com.metric.skava.instructions.model.BoltType;
import com.metric.skava.instructions.model.Coverage;
import com.metric.skava.instructions.model.MeshType;
import com.metric.skava.instructions.model.ShotcreteType;
import com.metric.skava.instructions.model.SupportPattern;
import com.metric.skava.instructions.model.SupportPatternType;
import com.metric.skava.rocksupport.model.SupportRequirement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by metricboy on 3/7/14.
 */
public class SupportRequirementDAODropboxImpl extends DropBoxBaseDAO implements RemoteSupportRequirementDAO {

    private SupportRequirementDropboxTable mSupportRequirementTable;
    private LocalTunnelDAO tunnelDAO;
    private LocalBoltTypeDAO boltTypeDAO;
    private LocalSupportPatternTypeDAO supportPatternTypeDAO;
    private LocalRockQualityDAO rockQualityDAO;
    private LocalShotcreteTypeDAO shotcreteTypeDAO;
    private LocalMeshTypeDAO meshTypeDAO;
    private LocalCoverageDAO coverageDAO;
    private LocalArchTypeDAO archTypeDAO;



    public SupportRequirementDAODropboxImpl(Context context, SkavaContext skavaContext) throws DAOException {
        super(context, skavaContext);
        this.mSupportRequirementTable = new SupportRequirementDropboxTable(getDatastore());
        this.tunnelDAO = getDAOFactory().getLocalTunnelDAO();
        this.boltTypeDAO = getDAOFactory().getLocalBoltTypeDAO();
        this.supportPatternTypeDAO = getDAOFactory().getLocalSupportPatternTypeDAO();
        this.rockQualityDAO = getDAOFactory().getLocalRockQualityDAO();
        this.shotcreteTypeDAO = getDAOFactory().getLocalShotcreteTypeDAO();
        this.meshTypeDAO = getDAOFactory().getLocalMeshTypeDAO();
        this.coverageDAO = getDAOFactory().getLocalCoverageDAO();
        this.archTypeDAO = getDAOFactory().getLocalArchTypeDAO();

    }


    @Override
    public List<SupportRequirement> getAllSupportRequirements() throws DAOException {
        try {
            DbxDatastoreStatus status = getDatastore().getSyncStatus();
            if (status.hasIncoming || status.isDownloading) {
                getDatastore().sync();
            }
            List<SupportRequirement> listSupportRequirements = new ArrayList<SupportRequirement>();
            List<DbxRecord> supporRequirementsList = mSupportRequirementTable.findAll();
            for (DbxRecord currentSupportRequirementRecord : supporRequirementsList) {
                String codigo = currentSupportRequirementRecord.getString("SupportId");
                String tunnelCode = currentSupportRequirementRecord.getString("TUNNEL_CODE");
                //TODO The name of this Rock classification column To be defined ..
                String name = "PENDING";
                Double lowerQ = 0d;
                Double upperQ = 100d;
//                Double lowerQ = currentSupportRequirementRecord.getDouble("Q_LOWER_BOUNDARY");
//                Double upperQ = currentSupportRequirementRecord.getDouble("Q_UPPER_BOUNDARY");
//                String name = currentSupportRequirementRecord.getString("NAME");
                String boltTypeCode = currentSupportRequirementRecord.getString("BOLT_TYPE_CODE");
                Double boltDiameter = currentSupportRequirementRecord.getDouble("BOLT_DIAMETER");
                Double boltLength = currentSupportRequirementRecord.getDouble("BOLT_LENGTH");
                String wallPatternTypeCode = currentSupportRequirementRecord.getString("PatternWallType");
                Double wallDx = currentSupportRequirementRecord.getDouble("PatternWall_dx");
                Double wallDy = currentSupportRequirementRecord.getDouble("PatternWall_dy");
                String roofPatternTypeCode = currentSupportRequirementRecord.getString("PatternRoofType");
                Double roofDx = currentSupportRequirementRecord.getDouble("PatternRoof_dx");
                Double roofDy = currentSupportRequirementRecord.getDouble("PatternRoof_dy");
                String shotcreteTypeCode = currentSupportRequirementRecord.getString("SHOTCRETE_TYPE");
                Double thickness = currentSupportRequirementRecord.getDouble("THICKNESS");
                String meshTypeCode = currentSupportRequirementRecord.getString("MESH_TYPE");
                String coverageCode = currentSupportRequirementRecord.getString("COVERAGE");
                String archTypeCode = currentSupportRequirementRecord.getString("ARCH_TYPE");
                Double separation = currentSupportRequirementRecord.getDouble("SEPARATION");

                Tunnel tunnel = tunnelDAO.getTunnelByUniqueCode(tunnelCode);

                BoltType boltType = boltTypeDAO.getBoltTypeByCode(boltTypeCode);
                SupportPatternType roofPatternType = supportPatternTypeDAO.getSupportPatternTypeByCode(roofPatternTypeCode);
                SupportPatternType wallPatternType = supportPatternTypeDAO.getSupportPatternTypeByCode(wallPatternTypeCode);
                SupportPattern roofPattern = new SupportPattern(roofPatternType, roofDx, roofDy);
                SupportPattern wallPattern = new SupportPattern(wallPatternType, wallDx, wallDy);
                ShotcreteType shotcreteType = shotcreteTypeDAO.getShotcreteTypeByCode(shotcreteTypeCode);
                MeshType meshType = meshTypeDAO.getMeshTypeByCode(meshTypeCode);
                Coverage coverage = coverageDAO.getCoverageByCode(coverageCode);
                ArchType archType = archTypeDAO.getArchTypeByCode(archTypeCode);

                SupportRequirement newSupportRequirement = new SupportRequirement(tunnel, codigo, name);
                newSupportRequirement.setqBartonLowerBoundary(lowerQ);
                newSupportRequirement.setqBartonUpperBoundary(upperQ);
                newSupportRequirement.setBoltType(boltType);
                newSupportRequirement.setDiameter(boltDiameter);
                newSupportRequirement.setLength(boltLength);
                newSupportRequirement.setRoofPattern(roofPattern);
                newSupportRequirement.setWallPattern(wallPattern);
                newSupportRequirement.setShotcreteType(shotcreteType);
                newSupportRequirement.setThickness(thickness);
                newSupportRequirement.setMeshType(meshType);
                newSupportRequirement.setCoverage(coverage);
                newSupportRequirement.setArchType(archType);
                newSupportRequirement.setSeparation(separation);

                listSupportRequirements.add(newSupportRequirement);
            }
            return listSupportRequirements;
        } catch (DbxException e) {
            throw new DAOException(e);
        }
    }


}
