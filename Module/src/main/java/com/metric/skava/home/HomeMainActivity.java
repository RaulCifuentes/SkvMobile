package com.metric.skava.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxFileSystem;
import com.metric.skava.R;
import com.metric.skava.about.activity.AboutMainActivity;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.User;
import com.metric.skava.app.navdrawer.AbstractNavDrawerActivity;
import com.metric.skava.app.navdrawer.NavDrawerActivityConfiguration;
import com.metric.skava.app.navdrawer.NavDrawerAdapter;
import com.metric.skava.app.navdrawer.NavDrawerItem;
import com.metric.skava.app.navdrawer.NavMenuItem;
import com.metric.skava.app.navdrawer.NavMenuSection;
import com.metric.skava.app.util.DateDisplayFormat;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.assessment.activity.AssessmentsListActivity;
import com.metric.skava.authentication.LoginMainActivity;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.settings.activity.SettingsMainActivity;
import com.metric.skava.sync.activity.SyncMainActivity;
import com.metric.skava.sync.exception.SyncDataFailedException;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncLogEntry;
import com.metric.skava.sync.model.SyncStatus;

import java.util.ArrayList;
import java.util.List;

public class HomeMainActivity extends AbstractNavDrawerActivity {


    private static final int NAV_MENU_SKAVA_SECTION_ID = 10;
    private static final int NAV_MENU_LOGIN_ITEM_ID = 20;
    private static final int NAV_MENU_FACE_MAPPING_ITEM_ID = 30;
    private static final int NAV_MENU_ADMIN_SECTION_ID = 40;
    private static final int NAV_MENU_SYNC_ITEM_ID = 50;
    private static final int NAV_MENU_GENERAL_SECTION_ID = 60;
    private static final int NAV_MENU_SETTINGS_ITEM_ID = 70;
    private static final int NAV_MENU_ABOUT_ITEM_ID = 80;
    private static final int NAV_MENU_FILESYSTEM_ITEM_ID = 90;
    private static final int NAV_MENU_LOGOUT_ITEM_ID = 100;
    private boolean dropboxNeverCalled = true;
    private boolean assertNeverCalled = true;
    private DbxAccountManager mDbxAcctMgr;
    private DbxAccount mAccount;
    private DbxDatastoreManager mDatastoreManager;
    private DbxDatastore mDatastore;
    private DbxFileSystem mFileSystem;
    private MainFragment mHomeMainFragment;
    private static final String FRAGMENT_HOME_MAIN_TAG = "FRAGMENT_HOME_MAIN_TAG";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        super.setupTheDrawer();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nav_drawer_main_layout_content_frame, new MainFragment(), FRAGMENT_HOME_MAIN_TAG)
                    .commit();
        }
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHomeMainFragment = (MainFragment)
                getSupportFragmentManager().findFragmentByTag(FRAGMENT_HOME_MAIN_TAG);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupLinkToDropbox();
        //TODO Check if the execution returns here when onACtivityResult is triggered
        setupDataModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void setupLinkToDropbox() {
        if (dropboxNeverCalled && isNetworkAvailable()) {
            String message = getString(R.string.connecting_dropbox);
            showProgressBar(true, message, false);
            try {
                //Abrir el acount manager que Dropbox ofrece para esta app
                mDbxAcctMgr = DbxAccountManager.getInstance(this.getApplicationContext(), SkavaConstants.DROBOX_APP_KEY, SkavaConstants.DROBOX_APP_SECRET);
                //Connect y tener la referencia al account
                if (!mDbxAcctMgr.hasLinkedAccount()) {
                    mDbxAcctMgr.startLink(this, SkavaConstants.REQUEST_LINK_TO_DROPBOX);
                } else {
                    //Connect y tener la referencia al account
                    mAccount = mDbxAcctMgr.getLinkedAccount();
                    //Con eso obtener la lista de Datastores
                    mDatastoreManager = DbxDatastoreManager.forAccount(mAccount);
//                    Set<DbxDatastoreInfo> listDatastores = mDatastoreManager.listDatastores();
//                    for (DbxDatastoreInfo currDatastore : listDatastores) {
//                        String datastore = currDatastore.toString();
//                    }
//                    //Abrir el datastore
//                    mDatastore = mDatastoreManager.openDatastore(DatastoreHelper.APP_DATASTORE_NAME);
                    if (mDatastore == null) {
                        mDatastore = mDatastoreManager.openDefaultDatastore();
                    }
                    if (mFileSystem == null) {
                        mFileSystem = DbxFileSystem.forAccount(mAccount);
                    }
                    getSkavaContext().setDatastore(mDatastore);
                    getSkavaContext().setFileSystem(mFileSystem);
                }
            } catch (DbxException e) {
                e.printStackTrace();
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                showProgressBar(true, "Failed trying to connect to Dropbox.", false);
            }
            showProgressBar(false, "It does not matter, right?", false);
        }
    }


    private void setupDataModel() {
        if (shouldUpdateAutomatically()) {
            UpdateDataModelTask task = new UpdateDataModelTask();
            task.execute();
        } else {
            if (assertNeverCalled) {
                assertDataAvailable();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SkavaConstants.REQUEST_LINK_TO_DROPBOX) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    // ... Now go on using the linked account information.
                    //Connect y tener la referencia al account
                    mAccount = mDbxAcctMgr.getLinkedAccount();
                    //Con eso obtener la lista de Datastores
                    mDatastoreManager = DbxDatastoreManager.forAccount(mAccount);
//                    Set<DbxDatastoreInfo> listDatastores = mDatastoreManager.listDatastores();
//                    for (DbxDatastoreInfo currDatastore : listDatastores) {
//                        String datastore = currDatastore.toString();
//                    }
//                    //Abrir el datastore
//                    mDatastore = mDatastoreManager.openDatastore(DatastoreHelper.APP_DATASTORE_NAME);
                    if (mDatastore == null) {
                        mDatastore = mDatastoreManager.openDefaultDatastore();
                    }
                    if (mFileSystem == null) {
                        mFileSystem = DbxFileSystem.forAccount(mAccount);
                    }
                    getSkavaContext().setDatastore(mDatastore);
                    getSkavaContext().setFileSystem(mFileSystem);
                    //TODO Check if this is necessary or if the execution returns to the onPostCreate() when onACtivityResult is triggered
                    //setupDataModel();
                } catch (DbxException e) {
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            } else {
                // ... Link failed or was cancelled by the user.
                onDropboxFailed();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void onDropboxFailed() {
        Toast.makeText(this, "Dropbox linking cancelled by user or failed.", Toast.LENGTH_LONG).show();
        if (assertNeverCalled) {
            assertDataAvailable();
        }
        Log.d(SkavaConstants.LOG, "Dropbox linking cancelled by user or failed.");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatastore.isOpen()) {
            mDatastore.close();
        }
    }


    private void prepareSyncTraceTable() {
        SyncHelper syncHelper = getSyncHelper();
        try {
            syncHelper.clearSyncLoggingTable();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }


    /*This will run under an AyncTask*/
    private Long importRoles() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadRoles();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


    private Long importMethods() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadExcavationMethods();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importSections() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadExcavationSections();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


    private Long importDiscontinuityTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadDiscontinuityTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importRelevances() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadDiscontinuityRelevances();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importIndexes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadIndexes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importGroups() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadGroups();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importSpacings() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadSpacings();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importPersistences() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadPersistences();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importApertures() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadApertures();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importShapes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadShapes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importRoughnesses() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadRoughnesses();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importInfillings() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadInfillings();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importWeatherings() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadWeatherings();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importWaters() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadWaters();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importStrengths() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadStrengths();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importGroundwaters() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadGroundwaters();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importOrientations() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadOrientations();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importJns() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadJns();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importJrs() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadJrs();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importJas() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadJas();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importJws() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadJws();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importSRFs() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadSRFs();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importFractureTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadFractureTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importBoltTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadBoltTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importShotcreteTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadShotcreteTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importMeshTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadMeshTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importCoverages() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadCoverages();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importArchTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadArchTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importESRs() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadESRs();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importSupportPatternTypes() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadSupportPatternTypes();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importRockQualities() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadRockQualities();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    //************ USER RELATED DATA **************** /
    private Long importClients() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords = syncHelper.downloadClients();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importProjects() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadProjects();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


    private Long importTunnels() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadTunnels();

        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importSupportRequirements() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadSupportRequirements();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }


    private Long importTunnelFaces() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadTunnelFaces();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }

    private Long importUsers() throws SyncDataFailedException {
        Long totalRecords = 0L;
        SyncHelper syncHelper = getSyncHelper();
        try {
            totalRecords += syncHelper.downloadUsers();
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            throw new SyncDataFailedException(daoe);
        }
        return totalRecords;
    }



    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgressBar(final boolean show, String text, boolean longTime) {

        mHomeMainFragment.getSyncingStatusMessageView().setText(text);

        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = longTime ? getResources().getInteger(android.R.integer.config_longAnimTime) : getResources().getInteger(android.R.integer.config_shortAnimTime);
            mHomeMainFragment.getSyncingStatusView().setVisibility(View.VISIBLE);
            mHomeMainFragment.getSyncingStatusView().animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mHomeMainFragment.getSyncingStatusView().setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mHomeMainFragment.getSyncingStatusView().setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }


    private void assertDataAvailable() {

        SyncStatus lastSyncState = getSkavaContext().getSyncMetadata();

        if (lastSyncState == null || !lastSyncState.isSuccess()) {
            Log.d(SkavaConstants.LOG, "Skava Mobile needs to download a set of initial data. Please connect to Internet and link to Skava Dropbox account!!");
            Toast.makeText(this, "Skava Mobile needs to download a set an set of initial data. Please connect to Internet and link to Skava Dropbox account!!", Toast.LENGTH_LONG).show();
        } else {
            Log.d(SkavaConstants.LOG, "Using master data from the last succeeded sync data on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastSyncState.getLastExecution()));
            Toast.makeText(this, "Using master data from the last succeeded sync data on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastSyncState.getLastExecution()), Toast.LENGTH_LONG).show();
        }

        assertNeverCalled = false;
    }

    @Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {

        List<NavDrawerItem> menuAsList = new ArrayList<NavDrawerItem>();
        menuAsList.add(NavMenuSection.create(NAV_MENU_SKAVA_SECTION_ID, "Skava Apps"));
        menuAsList.add(NavMenuItem.create(NAV_MENU_LOGIN_ITEM_ID, getString(R.string.login_label), "ic_menu_copy_holo_dark", true, true, this));

        User loggedUser = getSkavaContext().getLoggedUser();
        if (loggedUser != null) {
            Role geologist = new Role("GEOLOGIST", "Geologist");
            Role admin = new Role("ADMINISTRATOR", "Administrator");
            Role metricAdmin = new Role("METRICADMIN", "MetricAdmin");
            if (loggedUser.hasRole(geologist) || loggedUser.hasRole(admin) || loggedUser.hasRole(metricAdmin)) {
                menuAsList.remove(1);
                menuAsList.add(NavMenuItem.create(NAV_MENU_FACE_MAPPING_ITEM_ID, "Face Mappings", "ic_menu_copy_holo_dark", true, true, this));
            }
            if (loggedUser.hasRole(admin) || loggedUser.hasRole(metricAdmin)) {
                menuAsList.add(NavMenuSection.create(NAV_MENU_ADMIN_SECTION_ID, "Admin"));
                menuAsList.add(NavMenuItem.create(NAV_MENU_FILESYSTEM_ITEM_ID, "Dropbox with files", "ic_menu_copy_holo_dark", true, true, this));
                menuAsList.add(NavMenuItem.create(NAV_MENU_SYNC_ITEM_ID, "Sync admin", "ic_menu_copy_holo_dark", true, true, this));
            }
        }
        menuAsList.add(NavMenuSection.create(NAV_MENU_GENERAL_SECTION_ID, "General"));
        menuAsList.add(NavMenuItem.create(NAV_MENU_SETTINGS_ITEM_ID, getString(R.string.settings_label), "ic_action_overflow", true, true, this));
        menuAsList.add(NavMenuItem.create(NAV_MENU_ABOUT_ITEM_ID, getString(R.string.about_label), "ic_action_overflow", true, true, this));
        if (loggedUser != null) {
            menuAsList.add(NavMenuItem.create(NAV_MENU_LOGOUT_ITEM_ID, getString(R.string.logout_label), "ic_action_overflow", true, true, this));
        }
        NavDrawerItem[] menuAsArray = menuAsList.toArray(new NavDrawerItem[]{});

        NavDrawerActivityConfiguration navDrawerConfig = new NavDrawerActivityConfiguration();
        navDrawerConfig.setMainLayout(R.layout.nav_drawer_main_layout);
        navDrawerConfig.setDrawerLayoutId(R.id.nav_drawer_main_layout_drawer_layout);
        navDrawerConfig.setLeftDrawerId(R.id.nav_drawer_main_layout_left_drawer);
        navDrawerConfig.setNavItems(menuAsArray);
        navDrawerConfig.setDrawerShadow(R.drawable.drawer_shadow);
        navDrawerConfig.setDrawerOpenDesc(R.string.drawer_open);
        navDrawerConfig.setDrawerCloseDesc(R.string.drawer_close);
        navDrawerConfig.setBaseAdapter(new NavDrawerAdapter(this, R.layout.nav_drawer_item, menuAsArray));
        navDrawerConfig.setDrawerIcon(R.drawable.apptheme_ic_navigation_drawer);
        return navDrawerConfig;
    }


    @Override
    protected void onNavItemSelected(int id) {
        Intent intent;
        switch (id) {
            case NAV_MENU_LOGIN_ITEM_ID:
                intent = new Intent(this, LoginMainActivity.class);
                intent.putExtra(LoginMainActivity.EXTRA_USERNAME, "matias.lazcano");
                startActivity(intent);
                break;
            case NAV_MENU_FACE_MAPPING_ITEM_ID:
                intent = new Intent(this, AssessmentsListActivity.class);
                startActivity(intent);
                break;
            case NAV_MENU_SYNC_ITEM_ID:
                intent = new Intent(this, SyncMainActivity.class);
                startActivity(intent);
                break;
            case NAV_MENU_SETTINGS_ITEM_ID:
                intent = new Intent(this, SettingsMainActivity.class);
                startActivity(intent);
                break;
            case NAV_MENU_ABOUT_ITEM_ID:
                intent = new Intent(this, AboutMainActivity.class);
                startActivity(intent);
                break;
            case NAV_MENU_LOGOUT_ITEM_ID:
                if (shouldUnlinkOnLogout()) {
                    if (mDbxAcctMgr != null && mDbxAcctMgr.hasLinkedAccount()) {
                        mDbxAcctMgr.unlink();
                    }
                }
                getSkavaContext().setLoggedUser(null);
                setupTheDrawer();
        }
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onClick(View v) {
        new UpdateDataModelTask().execute();
    }

    public class UpdateDataModelTask extends AsyncTask<Void, Long, Long> {

        private SyncLogEntry errorCondition;

        @Override

        protected Long doInBackground(Void... params) {
            Long numRecordsCreated = 0L;
            prepareSyncTraceTable();
            try {
                // **** IMPORT GENERAL DATA FIRST *** //
                numRecordsCreated += importRoles();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importMethods();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importSections();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importDiscontinuityTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importRelevances();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importIndexes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importGroups();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importSpacings();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importPersistences();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importApertures();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importShapes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importRoughnesses();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importInfillings();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importWeatherings();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importWaters();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importStrengths();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importGroundwaters();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importOrientations();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importJns();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importJrs();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importJas();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importJws();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importSRFs();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importFractureTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importBoltTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importShotcreteTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importMeshTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importCoverages();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importArchTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importESRs();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importSupportPatternTypes();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importRockQualities();
                publishProgress(numRecordsCreated);

                // **** IMPORT THEN USER RELATED DATA **** //
                numRecordsCreated += importClients();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importProjects();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importTunnels();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importSupportRequirements();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importTunnelFaces();
                publishProgress(numRecordsCreated);
                numRecordsCreated += importUsers();
                publishProgress(numRecordsCreated);

                //UPDATE SYNC EXECUTION STATUS ON CONTEXT
                getSkavaContext().getSyncMetadata().setSuccess(true);
                getSkavaContext().getSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());

            } catch (SyncDataFailedException e) {
                getSkavaContext().getSyncMetadata().setSuccess(false);
                getSkavaContext().getSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());
                errorCondition = e.getEntry();
            }
            return numRecordsCreated;
        }

        @Override
        protected void onPreExecute() {
            String message = getString(R.string.syncing_progress);
            Toast.makeText(HomeMainActivity.this, "Data load starting ...", Toast.LENGTH_LONG);
            showProgressBar(true, message, true);
        }

        @Override
        protected void onProgressUpdate(Long... progress) {
            //mostrar avance
            Long value = (Long) progress[0];
            showProgressBar(true, +value + " records imported so far.", false);
        }

        @Override
        protected void onPostExecute(Long result) {
            if (errorCondition != null) {
                Toast.makeText(HomeMainActivity.this, "Load data process has failed!!", Toast.LENGTH_LONG);
                mHomeMainFragment.getBackgroudImage().setVisibility(View.VISIBLE);
                showProgressBar(true, "Failed. " + result + " records imported.", true);
                AlertDialog.Builder messageBox = new AlertDialog.Builder(HomeMainActivity.this);
                messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
                messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
                messageBox.setCancelable(false);
                messageBox.setNeutralButton("OK", null);
                messageBox.show();
            } else {
                //mostrar que termino exitosamente
                dropboxNeverCalled = false;
                Toast.makeText(HomeMainActivity.this, "Load succesfully finished", Toast.LENGTH_LONG);
                mHomeMainFragment.getBackgroudImage().setVisibility(View.VISIBLE);
                showProgressBar(true, "Finished. " + result + " records imported.", true);
                showProgressBar(false, "Hiding me !!", false);
            }
        }

    }



}
