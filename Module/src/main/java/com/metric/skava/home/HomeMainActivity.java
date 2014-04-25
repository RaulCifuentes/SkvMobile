package com.metric.skava.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dropbox.sync.android.DbxAccount;
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
import com.metric.skava.assessment.activity.AssessmentsListActivity;
import com.metric.skava.authentication.LoginMainActivity;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.dropbox.datastore.DatastoreHelper;
import com.metric.skava.data.dao.impl.sqllite.SkavaDBHelper;
import com.metric.skava.settings.activity.SettingsMainActivity;
import com.metric.skava.sync.activity.SyncMainActivity;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.dao.SyncLoggingDAOsqlLiteImpl;
import com.metric.skava.sync.helper.SyncAgent;
import com.metric.skava.sync.model.SyncLogEntry;

import java.util.Date;

public class HomeMainActivity extends AbstractNavDrawerActivity {

    private SyncAgent syncAgent;
    private boolean callbackCompleted = false;
    private boolean dropboxNeverCalled = true;
    private boolean assertNeverCalled = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (navController == null) {
//            navController = NavigationController.getInstance();
//        }
        if (savedInstanceState == null) {
            setUp();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nav_drawer_main_layout_content_frame, new MainFragment())
                    .commit();
        }

        if (syncAgent.isAlreadyLinked() && dropboxNeverCalled){
            onDropboxReady();
        }

//        if (callbackCompleted && dropboxNeverCalled){
//            onDropboxReady();
//        }
        if (assertNeverCalled){
            assertDataAvailable();
        }
    }


    private void onDropboxReady() {
        try {
            if (syncAgent.shouldUpdate()) {
                DAOFactory daoFactory = DAOFactory.getInstance(this);
                SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
                if (syncAgent.downloadGlobalData()) {
                    SyncLogEntry newSyncLogEntry = new SyncLogEntry(new Date(), SyncLogEntry.Domain.GLOBAL, SyncLogEntry.Source.DROPBOX, SyncLogEntry.Status.SUCCESS);
                    syncLoggingDAO.saveSyncLogEntry(newSyncLogEntry);
                }
                if (syncAgent.downloadNonSpecificData()) {
                    SyncLogEntry newSyncLogEntry = new SyncLogEntry(new Date(), SyncLogEntry.Domain.NON_USER_SPECIFIC, SyncLogEntry.Source.DROPBOX, SyncLogEntry.Status.SUCCESS);
                    syncLoggingDAO.saveSyncLogEntry(newSyncLogEntry);
                }
            }
            dropboxNeverCalled = false;
        } catch (DAOException daoe) {
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void onDropboxFailed() {
        Toast.makeText(this, "Dropbox cancelled.", Toast.LENGTH_LONG).show();
        Log.d(SkavaConstants.LOG, "Dropbox cancelled.");
    }

    public void insertGlobalEmergencyData() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getInstance(this);
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        SQLiteDatabase dbConn = ((SyncLoggingDAOsqlLiteImpl) syncLoggingDAO).getDBConnection();
        SkavaDBHelper.insertDefaultData(dbConn);
        SyncLogEntry newSyncLogEntry = new SyncLogEntry(new Date(), SyncLogEntry.Domain.GLOBAL, SyncLogEntry.Source.DEFAULT, SyncLogEntry.Status.SUCCESS);
        syncLoggingDAO.saveSyncLogEntry(newSyncLogEntry);
    }

    public void insertNonSpecificEmergencyData() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getInstance(this);
        SyncLoggingDAO syncLoggingDAO = daoFactory.getSyncLoggingDAO();
        SQLiteDatabase dbConn = ((SyncLoggingDAOsqlLiteImpl) syncLoggingDAO).getDBConnection();
        SkavaDBHelper.insertDefaultData(dbConn);
        SyncLogEntry newSyncLogEntry = new SyncLogEntry(new Date(), SyncLogEntry.Domain.NON_USER_SPECIFIC, SyncLogEntry.Source.DEFAULT, SyncLogEntry.Status.SUCCESS);
        syncLoggingDAO.saveSyncLogEntry(newSyncLogEntry);
    }



    private void assertDataAvailable() {

        SyncLogEntry lastGlobalData = getSkavaContext().getSyncMetadata().getGlobal();
        SyncLogEntry lastNonSpecificData = getSkavaContext().getSyncMetadata().getGlobal();

        if (lastGlobalData == null) {
            try {
                insertGlobalEmergencyData();
                Log.d(SkavaConstants.LOG, "Using emergency data. No internet nor local data. Sorry !!");
                Toast.makeText(this, "Using emergency data. No internet nor local data. Sorry ", Toast.LENGTH_LONG).show();
            } catch(DAOException daoe){
                Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(SkavaConstants.LOG, daoe.getMessage());
            }
        } else {
            if (lastGlobalData.getSource().equals(SyncLogEntry.Source.DROPBOX)) {
                Log.d(SkavaConstants.LOG, "Using global data from the last succeeded sync data on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastGlobalData.getSyncDate()));
                Toast.makeText(this, "Using global data from the last succeeded sync data on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastGlobalData.getSyncDate()), Toast.LENGTH_LONG).show();
            }
            if (lastNonSpecificData.getSource().equals(SyncLogEntry.Source.DEFAULT)) {
                Log.d(SkavaConstants.LOG, "Operating on previous emergency data created on  " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastGlobalData.getSyncDate()));
                Toast.makeText(this, "Operating on previous emergency data created on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastGlobalData.getSyncDate()), Toast.LENGTH_LONG).show();
            }
        }

        if (lastNonSpecificData == null) {
            try {
                insertNonSpecificEmergencyData();
                Log.d(SkavaConstants.LOG, "Using emergency data. No internet nor local data. Sorry !!" );
                Toast.makeText(this, "Using emergency data. No internet nor local data. Sorry ", Toast.LENGTH_LONG).show();
            } catch(DAOException daoe){
                Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(SkavaConstants.LOG, daoe.getMessage());
            }
        } else {
            if (lastNonSpecificData.getSource().equals(SyncLogEntry.Source.DROPBOX)) {
                Log.d(SkavaConstants.LOG, "Using non specific data from the last succeeded sync data on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastNonSpecificData.getSyncDate()));
                Toast.makeText(this, "Using non specific data from the last succeeded sync data on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastNonSpecificData.getSyncDate()), Toast.LENGTH_LONG).show();
            }
            if (lastNonSpecificData.getSource().equals(SyncLogEntry.Source.DEFAULT)) {
                Log.d(SkavaConstants.LOG, "Operating on previous emergency data created on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastNonSpecificData.getSyncDate()));
                Toast.makeText(this, "Operating on previous emergency datacreated on " + DateDisplayFormat.getFormattedDate(DateDisplayFormat.DATE_TIME, lastNonSpecificData.getSyncDate()) , Toast.LENGTH_LONG).show();
            }
        }

        assertNeverCalled = false;
    }


    private void setUp() {
        if (isNetworkAvailable()) {
            //Check the syncronization status
            try {
                syncAgent = SyncAgent.getInstance(this, this);
            } catch (DAOException daoe) {
                Log.e(SkavaConstants.LOG, daoe.getMessage());
                Toast.makeText(this, daoe.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }





    @Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
        NavDrawerItem[] menu = new NavDrawerItem[]{
                NavMenuSection.create(100, "Skava Apps"),

                NavMenuItem.create(110, getString(R.string.login_label),
                        "ic_menu_copy_holo_dark", true, true, this),

                NavMenuSection.create(200, "General"),

                NavMenuItem.create(210, getString(R.string.about_label), "ic_action_overflow", true,
                        true, this)
        };

        User loggedUser = getSkavaContext().getLoggedUser();
        if (loggedUser != null) {
            Role geologist = new Role("GEOLOGIST", "Geologist");
            Role admin = new Role("ADMINISTRATOR", "Administrator");
            if (loggedUser.hasRole(geologist) || loggedUser.hasRole(admin)) {
                menu = new NavDrawerItem[]{
                        NavMenuSection.create(100, "Skava Apps"),
                        NavMenuItem.create(190, "Face Mappings",
                                "ic_menu_copy_holo_dark", true, true, this),
//                        NavMenuItem.create(199, "Test Dropbox Sync",
//                                "ic_menu_copy_holo_dark", true, true, this),
                        NavMenuSection.create(200, "General"),
                        NavMenuItem.create(210, getString(R.string.about_label), "ic_action_overflow", true,
                                true, this)};
            }
        }

        NavDrawerActivityConfiguration navDrawerConfig = new NavDrawerActivityConfiguration();
        navDrawerConfig.setMainLayout(R.layout.nav_drawer_main_layout);
        navDrawerConfig.setDrawerLayoutId(R.id.nav_drawer_main_layout_drawer_layout);
        navDrawerConfig.setLeftDrawerId(R.id.nav_drawer_main_layout_left_drawer);
        navDrawerConfig.setNavItems(menu);
        navDrawerConfig.setDrawerShadow(R.drawable.drawer_shadow);
        navDrawerConfig.setDrawerOpenDesc(R.string.drawer_open);
        navDrawerConfig.setDrawerCloseDesc(R.string.drawer_close);
        navDrawerConfig.setBaseAdapter(new NavDrawerAdapter(this, R.layout.nav_drawer_item, menu));
        navDrawerConfig.setDrawerIcon(R.drawable.apptheme_ic_navigation_drawer);
        return navDrawerConfig;
    }

    @Override
    protected void onNavItemSelected(int id) {
        Intent intent;
        switch (id) {

            case 110: // "Login"
                intent = new Intent(this, LoginMainActivity.class);
                intent.putExtra(LoginMainActivity.EXTRA_EMAIL, "geologist@skava.cl");
                startActivity(intent);
                break;

            case 190: //
                intent = new Intent(this, AssessmentsListActivity.class);
                startActivity(intent);
                break;

            case 199: //
                intent = new Intent(this, SyncMainActivity.class);
                startActivity(intent);
                break;

            case 205: // "Settings"
                intent = new Intent(this, SettingsMainActivity.class);
                startActivity(intent);
                break;


            case 210: // "About"
                intent = new Intent(this, AboutMainActivity.class);
                startActivity(intent);
                break;

        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DatastoreHelper.REQUEST_LINK_TO_DROPBOX) {
            if (resultCode == Activity.RESULT_OK) {
                // ... Now go on using the linked account information.
                callbackCompleted = true;
                DbxAccount accountQueLLego = null;
                syncAgent.getDatastoreHelper().setAccount(accountQueLLego);
            } else {
                // ... Link failed or was cancelled by the user.
                onDropboxFailed();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //    @Override
//    public void onBackPressed() {
//        // See bug:
//        // http://stackoverflow.com/questions/13418436/android-4-2-back-stack-behaviour-with-nested-fragments/14030872#14030872
//        // If the fragment exists and has some back-stack entry
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment currentFragment = fm
//                .findFragmentById(R.id.nav_drawer_main_layout_content_frame);
//        if (currentFragment != null
//                && currentFragment.getChildFragmentManager()
//                .getBackStackEntryCount() > 0) {
//            // Get the fragment fragment manager - and pop the backstack
//            currentFragment.getChildFragmentManager().popBackStack();
//        }
//        // Else, nothing in the direct fragment back stack
//        else {
//            if (!NavigationController.HOME_FRAGMENT_TAG.equals(currentFragment
//                    .getTag())) {
//                this.navController.goHomeFragment(this);
//            } else {
//                super.onBackPressed();
//            }
//        }
//    }


}
