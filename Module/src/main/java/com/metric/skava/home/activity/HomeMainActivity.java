package com.metric.skava.home.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import com.metric.skava.home.fragment.MainFragment;
import com.metric.skava.home.helper.ImportDataHelper;
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
//    private boolean dropboxNeverCalled = true;
    private boolean assertUserDataNeverCalled = true;
    private boolean assertAppDataNeverCalled = true;
    private DbxAccountManager mDbxAcctMgr;
    private DbxAccount mAccount;
    private DbxDatastoreManager mDatastoreManager;
    private DbxDatastore mDatastore;
    private DbxFileSystem mFileSystem;
    private MainFragment mHomeMainFragment;
    private static final String FRAGMENT_HOME_MAIN_TAG = "FRAGMENT_HOME_MAIN_TAG";
    private boolean linkDropboxCompleted;
    private boolean lackOfAppData;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        super.setupTheDrawer();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nav_drawer_main_layout_content_frame, new MainFragment(), FRAGMENT_HOME_MAIN_TAG)
                    .commit();
        }

//        Thread.getDefaultUncaughtExceptionHandler();
//        SkavaExceptionHandler handler = new SkavaExceptionHandler(this, getSupportFragmentManager());
//        Thread.setDefaultUncaughtExceptionHandler(handler);

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
        if (!linkDropboxCompleted){
            setupLinkToDropbox();
        }
        if (linkDropboxCompleted) {
            setupDataModelOnCascade();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void setupLinkToDropbox() {
        if (isNetworkAvailable()) {
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
                    //Abrir el datastore
                    if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.DEV_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_DEV_NAME);
                    } else if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.QA_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_QA_NAME);
                    } else if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.PROD_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_PROD_NAME);
                    }
                    if (mDatastore == null) {
                        mDatastore = mDatastoreManager.openDefaultDatastore();
                    }
                    if (mFileSystem == null) {
                        mFileSystem = DbxFileSystem.forAccount(mAccount);
                    }
                    linkDropboxCompleted = true;
                    getSkavaContext().setDatastore(mDatastore);
                    getSkavaContext().setFileSystem(mFileSystem);
                }
            } catch (DbxException e) {
                e.printStackTrace();
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                showProgressBar(true, "Failed trying to connect to Dropbox.", false);
            }
            showProgressBar(false, "Hiding me !!", false);
        } else {
            try {
                //Abrir el acount manager que Dropbox ofrece para esta app
                mDbxAcctMgr = DbxAccountManager.getInstance(this.getApplicationContext(), SkavaConstants.DROBOX_APP_KEY, SkavaConstants.DROBOX_APP_SECRET);
                //Connect y tener la referencia al account
                if (mDbxAcctMgr.hasLinkedAccount()) {
                    mAccount = mDbxAcctMgr.getLinkedAccount();
                    //Con eso obtener la lista de Datastores
                    mDatastoreManager = DbxDatastoreManager.forAccount(mAccount);
                    //Abrir el datastore
                    if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.DEV_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_DEV_NAME);
                    } else if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.QA_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_QA_NAME);
                    } else if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.PROD_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_PROD_NAME);
                    }
                    if (mDatastore == null) {
                        mDatastore = mDatastoreManager.openDefaultDatastore();
                    }
                    if (mFileSystem == null) {
                        mFileSystem = DbxFileSystem.forAccount(mAccount);
                    }
                    linkDropboxCompleted = true;
                    getSkavaContext().setDatastore(mDatastore);
                    getSkavaContext().setFileSystem(mFileSystem);
                }
            } catch (DbxException e) {
                e.printStackTrace();
                Log.e(SkavaConstants.LOG, e.getMessage());
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                showProgressBar(true, "Failed trying to link to Dropbox.", false);
            }
        }
    }


    private void setupDataModelOnCascade() {
        if (shouldImportAppData() && isNetworkAvailable()) {
            ImportAppDataModelTask fixedDataTask = new ImportAppDataModelTask();
            fixedDataTask.execute();
        } else {
            if (assertAppDataNeverCalled) {
                assertAppDataAvailable();
            }
            if (lackOfAppData){
                //do nothing
            } else {
                setupUserDataModel();
            }
        }
    }


    private void setupUserDataModel(){
        if (shouldImportUserData() && isNetworkAvailable()) {
            ImportUserDataModelTask dynamicDataTask = new ImportUserDataModelTask();
            dynamicDataTask.execute();
        } else {
            if (assertUserDataNeverCalled) {
                assertUserDataAvailable();
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
//                    //Abrir el datastore
                    if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.DEV_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_DEV_NAME);
                    } else if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.QA_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_QA_NAME);
                    } else if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.PROD_KEY)) {
                        mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_PROD_NAME);
                    }
                    if (mDatastore == null) {
                        mDatastore = mDatastoreManager.openDefaultDatastore();
                    }
                    if (mFileSystem == null) {
                        mFileSystem = DbxFileSystem.forAccount(mAccount);
                    }
                    linkDropboxCompleted = true;
                    getSkavaContext().setDatastore(mDatastore);
                    getSkavaContext().setFileSystem(mFileSystem);
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
        if (assertAppDataNeverCalled) {
            assertAppDataAvailable();
        }
        if (assertUserDataNeverCalled) {
            assertUserDataAvailable();
        }
        Log.d(SkavaConstants.LOG, "Dropbox linking cancelled by user or failed.");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFileSystem != null && !mFileSystem.isShutDown()) {
            mFileSystem.shutDown();
        }
        if (mDatastore != null && mDatastore.isOpen()) {
            mDatastore.close();
        }
    }


    private boolean areAppDataTablesEmpty() {
        boolean empty = false;
        SyncHelper syncHelper = getSyncHelper();
        try {
            empty = syncHelper.isESRTableEmpty();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
        return empty;
    }

    private Long findOutNumberOfAppDataRecordsToImport() {
        SyncHelper syncHelper = getSyncHelper();
        try {
            return syncHelper.getAppDataRecordCount();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
        return null;
    }


    private Long findOutNumberOfUserDataRecordsToImport() {
        SyncHelper syncHelper = getSyncHelper();
        try {
            return syncHelper.getUserDataRecordCount();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
        return null;
    }

    /*This will run under an AyncTask*/


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


    private void assertAppDataAvailable() {
        SyncStatus lastSyncState = getSkavaContext().getAppDataSyncMetadata();
        String alertTitle = null;
        if (!shouldImportAppData()) {
            alertTitle = "Your current settings states the app data should not be synced automatically on app start";
        }
        if (!isNetworkAvailable()) {
            alertTitle = "Currently you have no Internet connection";
        }
        //The sync status metadata is not enough to ensure app data is not empty, so
        // check at least one parameters table as example and check its not empty.
        // There is also a condition where tables are empty
        if (areAppDataTablesEmpty() || lastSyncState == null || !lastSyncState.isSuccess()) {
            final String textToShow = "Skava Mobile needs a minimal set of configuration data that is currently not available in your device. In order to download it ensure Internet is available !!";
            Log.d(SkavaConstants.LOG, textToShow);
            final String finalAlertTitle = alertTitle;
            DialogFragment theDialog = new DialogFragment() {
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(finalAlertTitle);
                    builder.setMessage(textToShow).setPositiveButton("OK", null);
                    // Create the AlertDialog object and return it
                    return builder.create();
                }
            };
            // Showing Alert Message
            theDialog.show(getSupportFragmentManager(), "assertAppDataDialog");
            lackOfAppData = true;
        } else {
            final String textToShow = "Using app data (master parameters) from the last succeeded sync execution on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastSyncState.getLastExecution());
            Log.d(SkavaConstants.LOG, textToShow);
            final String finalAlertTitle = alertTitle;
            DialogFragment theDialog = new DialogFragment() {
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(finalAlertTitle);
                    builder.setMessage(textToShow).setPositiveButton("OK", null);
                    // Create the AlertDialog object and return it
                    return builder.create();
                }
            };
            // Showing Alert Message
            //Dont bother the user with this
//            theDialog.show(getSupportFragmentManager(), "assertAppDataDialog");
        }
        assertAppDataNeverCalled = false;
    }

    private void assertUserDataAvailable() {
        SyncStatus lastSyncState = getSkavaContext().getUserDataSyncMetadata();
        String alertTitle = null;
        if (!shouldImportUserData()) {
            alertTitle = "Your current settings states the user data should not be synced automatically on app start";
        }
        if (!isNetworkAvailable()) {
            alertTitle = "Currently you have no Internet connection";
        }
        if (lastSyncState == null || !lastSyncState.isSuccess()) {
            final String textToShow = "Skava Mobile needs to download an initial set of user data. Please connect to Internet and link to Skava Dropbox account!!";
            Log.d(SkavaConstants.LOG, textToShow);
            final String finalAlertTitle = alertTitle;
            DialogFragment theDialog = new DialogFragment() {
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(finalAlertTitle);
                    builder.setMessage(textToShow).setPositiveButton("OK", null);
                    // Create the AlertDialog object and return it
                    return builder.create();
                }
            };
            // Showing Alert Message
            theDialog.show(getSupportFragmentManager(), "assertUserDataDialog");
        } else {
            final String textToShow = "Using user data (users, projects, tunnels, faces, etc) from the last succeeded sync execution on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastSyncState.getLastExecution());
            Log.d(SkavaConstants.LOG, textToShow );
            final String finalAlertTitle = alertTitle;
            DialogFragment theDialog = new DialogFragment() {
                @Override
                public Dialog onCreateDialog(Bundle savedInstanceState) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(finalAlertTitle);
                    builder.setMessage(textToShow).setPositiveButton("OK", null);
                    // Create the AlertDialog object and return it
                    return builder.create();
                }
            };
            // Showing Alert Message
            theDialog.show(getSupportFragmentManager(), "assertUserDataDialog");
        }
        assertUserDataNeverCalled = false;
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
                menuAsList.add(NavMenuItem.create(NAV_MENU_SYNC_ITEM_ID, "Data management", "ic_menu_copy_holo_dark", true, true, this));
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
                intent.putExtra(LoginMainActivity.EXTRA_USERNAME, "rcifuentes");
                intent.putExtra(LoginMainActivity.EXTRA_PASSWORD, "pepito");
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
                        linkDropboxCompleted = false;
                        mDbxAcctMgr.unlink();
                    }
                }
                getSkavaContext().setAssessment(null);
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

    public void saveUserDataSyncStatus(boolean success) {
        getSkavaContext().getUserDataSyncMetadata().setSuccess(success);
        getSkavaContext().getUserDataSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.settings_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.user_data_last_sync_succeed), success);
        editor.putLong(getString(R.string.user_data_last_sync_date), SkavaUtils.getCurrentDate().getTime());
        editor.commit();
    }

    public void saveAppDataSyncStatus(boolean success) {
        getSkavaContext().getAppDataSyncMetadata().setSuccess(success);
        getSkavaContext().getAppDataSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.settings_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.app_data_last_sync_succeed), success);
        editor.putLong(getString(R.string.app_data_last_sync_date), SkavaUtils.getCurrentDate().getTime());
        editor.commit();
    }


    public class ImportAppDataModelTask extends AsyncTask<Void, Long, Long> {

        private SyncLogEntry errorCondition;
        private Long totalRecordsToImport;
        private ImportDataHelper importHelper;

        @Override
        protected void onPreExecute() {
            importHelper = new ImportDataHelper(getSyncHelper());
            String message = getString(R.string.syncing_app_data_progress);
            showProgressBar(true, message, true);
        }

        @Override
        protected Long doInBackground(Void... params) {
            Long numRecordsCreated = 0L;
//            prepareSyncTraceTable();
            try {
                //FIND OUT THE TOTAL NUMBER OF RECORDS
                totalRecordsToImport = findOutNumberOfAppDataRecordsToImport();
                if (totalRecordsToImport > 0) {
                    // **** IMPORT GENERAL DATA FIRST *** //
                    numRecordsCreated += importHelper.importMethods();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importSections();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importDiscontinuityTypes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importRelevances();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importIndexes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importGroups();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importSpacings();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importPersistences();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importApertures();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importShapes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importRoughnesses();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importInfillings();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importWeatherings();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importWaters();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importStrengths();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importGroundwaters();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importOrientations();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importJns();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importJrs();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importJas();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importJws();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importSRFs();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importFractureTypes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importBoltTypes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importShotcreteTypes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importMeshTypes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importCoverages();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importArchTypes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importESRs();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importSupportPatternTypes();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importRockQualities();
                    publishProgress(numRecordsCreated);
                }
            } catch (SyncDataFailedException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                errorCondition = e.getEntry();
            }
            return numRecordsCreated;
        }


        @Override
        protected void onProgressUpdate(Long... progress) {
            //mostrar avance
            Long value = (Long) progress[0];
            showProgressBar(true, +value + " of " + totalRecordsToImport + " app data records imported so far.", false);
        }

        @Override
        protected void onPostExecute(Long result) {
            if (errorCondition != null) {
                saveAppDataSyncStatus(false);
                showProgressBar(true, "Failed after " + result + " records imported.", true);
                AlertDialog.Builder messageBox = new AlertDialog.Builder(HomeMainActivity.this);
                messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
                messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
                messageBox.setCancelable(false);
                messageBox.setNeutralButton("OK", null);
                messageBox.show();
                showProgressBar(false, "Hiding me !!", false);
            } else {
                //mostrar que termino exitosamente
                saveAppDataSyncStatus(true);
//                dropboxNeverCalled = false;
                mHomeMainFragment.getBackgroudImage().setVisibility(View.VISIBLE);
                showProgressBar(true, "Finished. " + result + " records imported.", true);
//                showProgressBar(false, "Hiding me !!", false);
            }
            // now that app data sync has finished call the eventual user data sync
            setupUserDataModel();
        }

    }


    public class ImportUserDataModelTask extends AsyncTask<Void, Long, Long> {

        private SyncLogEntry errorCondition;
        private Long totalRecordsToImport;
        private ImportDataHelper importHelper;

        @Override
        protected void onPreExecute() {
            importHelper = new ImportDataHelper(getSyncHelper());
            String message = getString(R.string.syncing_user_data_progress);
            showProgressBar(true, message, true);
        }

        @Override
        protected Long doInBackground(Void... params) {
            Long numRecordsCreated = 0L;
//            prepareSyncTraceTable();
            try {
                //FIND OUT THE TOTAL NUMBER OF RECORDS
                totalRecordsToImport = findOutNumberOfUserDataRecordsToImport();
                if (totalRecordsToImport > 0) {

                    // **** IMPORT THEN USER RELATED DATA **** //
                    numRecordsCreated += importHelper.importRoles();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importClients();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importProjects();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importTunnels();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importSupportRequirements();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importTunnelFaces();
                    publishProgress(numRecordsCreated);
                    numRecordsCreated += importHelper.importUsers();
                    publishProgress(numRecordsCreated);
                }
            } catch (SyncDataFailedException e) {
                Log.e(SkavaConstants.LOG, e.getMessage());
                errorCondition = e.getEntry();
            }
            return numRecordsCreated;
        }


        @Override
        protected void onProgressUpdate(Long... progress) {
            //mostrar avance
            Long value = (Long) progress[0];
            showProgressBar(true, +value + " of " + totalRecordsToImport + " user data records imported so far.", false);
        }

        @Override
        protected void onPostExecute(Long result) {
            if (errorCondition != null) {
                saveUserDataSyncStatus(false);
                showProgressBar(true, "Failed after " + result + " records imported.", true);
                AlertDialog.Builder messageBox = new AlertDialog.Builder(HomeMainActivity.this);
                messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
                messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
                messageBox.setCancelable(false);
                messageBox.setNeutralButton("OK", null);
                messageBox.show();
                showProgressBar(false, "Hiding me !!", false);
            } else {
                //mostrar que termino exitosamente
                saveUserDataSyncStatus(true);
//                dropboxNeverCalled = false;
                mHomeMainFragment.getBackgroudImage().setVisibility(View.VISIBLE);
                showProgressBar(true, "Finished. " + result + " records imported.", true);
                showProgressBar(false, "Hiding me !!", false);
            }
        }

    }


}
