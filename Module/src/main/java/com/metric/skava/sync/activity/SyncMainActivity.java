package com.metric.skava.sync.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.model.Client;
import com.metric.skava.app.model.ExcavationMethod;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.ExcavationSection;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.LocalClientDAO;
import com.metric.skava.data.dao.LocalExcavationMethodDAO;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalExcavationSectionDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.RemoteClientDAO;
import com.metric.skava.data.dao.RemoteExcavationMethodDAO;
import com.metric.skava.data.dao.RemoteExcavationProjectDAO;
import com.metric.skava.data.dao.RemoteExcavationSectionDAO;
import com.metric.skava.data.dao.RemoteRoleDAO;
import com.metric.skava.data.dao.RemoteTunnelDAO;
import com.metric.skava.data.dao.RemoteTunnelFaceDAO;
import com.metric.skava.data.dao.RemoteUserDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;
import com.metric.skava.sync.fragment.SyncMainFragment;

import java.util.List;

public class SyncMainActivity extends SkavaFragmentActivity {

    public String FRAGMENT_TAG = "SYNC_FRAGMENT_TAG";

    private DAOFactory daoFactory;
    DatastoreHelper datastoreHelper;
    private SyncMainFragment syncFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_main_activity);
        daoFactory = DAOFactory.getInstance(this);
        datastoreHelper = DatastoreHelper.getInstance(this, this);
        try {
            datastoreHelper.wakeUp();
        }
        catch (DAOException daoe) {
            syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new SyncMainFragment(), FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sync_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_sync_clients:
                syncClients();
                break;
            case R.id.action_sync_roles:
                syncRoles();
                break;
            case R.id.action_sync_users:
                syncUsers();
                break;
            case R.id.action_sync_projects:
                syncProjects();
                break;
            case R.id.action_sync_tunnels:
                syncTunnels();
                break;
            case R.id.action_sync_faces:
                syncFaces();
                break;
            case R.id.action_sync_sections:
                syncSections();
                break;
            case R.id.action_sync_methods:
                syncMethods();
                break;
            case R.id.action_sync_all:
                syncAll();
                break;
        }
        try {
            syncFragment.refreshListViews();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
        return super.onOptionsItemSelected(item);
    }



    private void syncClients() {
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            /**Update the excavation methods data*/
            RemoteClientDAO dropBoxClientDAO = daoFactory.getRemoteClientDAO(DAOFactory.Flavour.DROPBOX);
            LocalClientDAO sqlLiteLocalClientDAO = daoFactory.getLocalClientDAO(DAOFactory.Flavour.SQLLITE);
            //Read from DropBox
            List<Client> downloadedClients = dropBoxClientDAO.getAllClients();
            for (Client downloadedClient : downloadedClients) {
                //Write into the SQLLite
                sqlLiteLocalClientDAO.saveClient(downloadedClient);
            }
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    private void syncRoles() {
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            /**Update the excavation methods data*/
            RemoteRoleDAO dropBoxLocalRoleDAO = daoFactory.getRemoteRoleDAO(DAOFactory.Flavour.DROPBOX);
            LocalRoleDAO sqlLiteLocalRoleDAO = daoFactory.getLocalRoleDAO(DAOFactory.Flavour.SQLLITE);
            //Read from DropBox
            List<Role> downloadedMethods = dropBoxLocalRoleDAO.getAllRoles();
            for (Role downloadedMethod : downloadedMethods) {
                //Write into the SQLLite
                sqlLiteLocalRoleDAO.saveRole(downloadedMethod);
            }
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    private void syncUsers() {
        /**Update the project data*/
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            RemoteUserDAO dropBoxUserDAO = daoFactory.getRemoteUserDAO(DAOFactory.Flavour.DROPBOX);
            LocalUserDAO sqlLiteLocalUserDAO = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE);
            //Read from DropBox
            List<User> downloadedUsers = dropBoxUserDAO.getAllUsers();
            for (User downloadedUser : downloadedUsers) {
                //Write into the SQLLite
                sqlLiteLocalUserDAO.saveUser(downloadedUser);
            }
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    private void syncProjects() {
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            RemoteExcavationProjectDAO dropBoxProjectDAO = daoFactory.getRemoteExcavationProjectDAO(DAOFactory.Flavour.DROPBOX);
            LocalExcavationProjectDAO sqlLiteProjectDAO = daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE);
            //Read from DropBox
            List<ExcavationProject> downloadedProjects = dropBoxProjectDAO.getAllExcavationProjects();
            for (ExcavationProject downloadedProject : downloadedProjects) {
                //Write into the SQLLite
                sqlLiteProjectDAO.saveExcavationProject(downloadedProject);
            }
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    private void syncTunnels() {
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            RemoteTunnelDAO dropBoxTunnelDAO = daoFactory.getRemoteTunnelDAO(DAOFactory.Flavour.DROPBOX);
            LocalTunnelDAO sqlLiteLocalTunnelDAO = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE);
            //Read from DropBox
            List<Tunnel> downloadedTunnels = dropBoxTunnelDAO.getAllTunnels();
            for (Tunnel downloadedTunnel : downloadedTunnels) {
                //Write into the SQLLite
                sqlLiteLocalTunnelDAO.saveTunnel(downloadedTunnel);
            }
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }


    private void syncFaces() {
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            RemoteTunnelFaceDAO dropBoxFaceDAO = daoFactory.getRemoteTunnelFaceDAO(DAOFactory.Flavour.DROPBOX);
            LocalTunnelFaceDAO sqlLiteFaceDAO = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
            //Read from DropBox
            List<TunnelFace> downloadedFaces = dropBoxFaceDAO.getAllTunnelFaces();
            for (TunnelFace downloadedFace : downloadedFaces) {
                //Write into the SQLLite
                sqlLiteFaceDAO.saveTunnelFace(downloadedFace);
            }
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    private void syncSections() {
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            /**Update the excavation sections data*/
            RemoteExcavationSectionDAO dropBoxSectionDAO = daoFactory.getRemoteExcavationSectionDAO(DAOFactory.Flavour.DROPBOX);
            LocalExcavationSectionDAO sqlLiteSectionDAO = daoFactory.getLocalExcavationSectionDAO(DAOFactory.Flavour.SQLLITE);
            //Read from DropBox
            List<ExcavationSection> downloadedSections = dropBoxSectionDAO.getAllExcavationSections();
            for (ExcavationSection downloadedSection : downloadedSections) {
                //Write into the SQLLite
                sqlLiteSectionDAO.saveExcavationSection(downloadedSection);
            }
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    private void syncMethods() {
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            /**Update the excavation methods data*/
            RemoteExcavationMethodDAO dropBoxMethodDAO = daoFactory.getRemoteExcavationMethodDAO(DAOFactory.Flavour.DROPBOX);
            LocalExcavationMethodDAO sqlLiteMethodDAO = daoFactory.getLocalExcavationMethodDAO(DAOFactory.Flavour.SQLLITE);
            //Read from DropBox
            List<ExcavationMethod> downloadedMethods = dropBoxMethodDAO.getAllExcavationMethods();
            for (ExcavationMethod downloadedMethod : downloadedMethods) {
                //Write into the SQLLite
                sqlLiteMethodDAO.saveExcavationMethod(downloadedMethod);
            }
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }


    private void syncAssessments() {
        SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        try {
            /**Upload the assessments pending to upload*/
            Assessment curAssessment = getCurrentAssessment();
            LocalAssessmentDAO assessmentDAO = daoFactory.getAssessmentDAO(DAOFactory.Flavour.DROPBOX);
            assessmentDAO.saveDraft(curAssessment);
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }


    private void syncAll() {
        //Sync dictionaries
        syncRoles();
        syncClients();
        syncUsers();
        syncProjects();
        syncTunnels();
        syncFaces();
//        syncAssessments();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        try {
            datastoreHelper.goSleep();
//        }
//        catch (DAOException daoe) {
//            SyncMainFragment syncFragment = (SyncMainFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//            syncFragment.showMessage("Error syncing :: " + daoe.getMessage());
//            daoe.printStackTrace();
//            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
//            Log.e(SkavaConstants.LOG, daoe.getMessage());
//        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DatastoreHelper.REQUEST_LINK_TO_DROPBOX) {
            if (resultCode == RESULT_OK) {
//                DatastoreHelper datastoreHelper = DatastoreHelper.getInstance(this);
//                //read info from the intent and set the account I guess
//                datastoreHelper.setAccount(null);
                //just to see if comes here
                Toast.makeText(this, "AssessmentStageListActivity :: onActivityResult", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Link to Dropbox failed.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
