package com.metric.skava.home.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxDatastore;
import com.dropbox.sync.android.DbxDatastoreInfo;
import com.dropbox.sync.android.DbxDatastoreManager;
import com.dropbox.sync.android.DbxException;
import com.metric.skava.R;
import com.metric.skava.about.activity.AboutMainActivity;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.User;
import com.metric.skava.app.navdrawer.AbstractNavDrawerActivity;
import com.metric.skava.app.navdrawer.NavDrawerActivityConfiguration;
import com.metric.skava.app.navdrawer.NavDrawerAdapter;
import com.metric.skava.app.navdrawer.NavDrawerItem;
import com.metric.skava.app.navdrawer.NavMenuItem;
import com.metric.skava.app.navdrawer.NavMenuSection;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.assessment.activity.AssessmentsListActivity;
import com.metric.skava.authentication.LoginMainActivity;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.expandedview.activity.TestAutocadMainActivity;
import com.metric.skava.home.fragment.MainFragment;
import com.metric.skava.settings.activity.SettingsMainActivity;
import com.metric.skava.sync.activity.DataManagementMainActivity;
import com.metric.skava.user.activity.UserAccountMainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HomeMainActivity extends AbstractNavDrawerActivity {

    private static final int NAV_MENU_SKAVA_SECTION_ID = 10;
    private static final int NAV_MENU_LOGIN_ITEM_ID = 20;
    public static final int NAV_MENU_ROCK_CLASSIFICATION_ITEM_ID = 25;
//    public static final int NAV_MENU_FACE_MAPPING_ITEM_ID = 30;
    private static final int NAV_MENU_ADMIN_SECTION_ID = 40;
    private static final int NAV_MENU_DATA_MANAGEMENT_ITEM_ID = 50;
    private static final int NAV_MENU_GENERAL_SECTION_ID = 60;
    private static final int NAV_MENU_USER_ACCOUNT = 70;
    private static final int NAV_MENU_SYNC_ITEM_ID = 80;
    private static final int NAV_MENU_SETTINGS_ITEM_ID = 90;
    private static final int NAV_MENU_ABOUT_ITEM_ID = 100;
    private static final int NAV_MENU_LOGOUT_ITEM_ID = 200;

    private static final int NAV_MENU_TEST_AUTOCAD_ID = 980;

    private static final String FRAGMENT_HOME_MAIN_TAG = "FRAGMENT_HOME_MAIN_TAG";

    private DbxAccountManager mDbxAcctMgr;
    private DbxAccount mAccount;
    private DbxDatastoreManager mDatastoreManager;
    private DbxDatastore mDatastore;
    private MainFragment mHomeMainFragment;

    //ITS SEEMS to reset each restarts so try with an inherited mmethod reading from the skava context
//    private boolean linkDropboxCompleted;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateAsString = sdf.format(SkavaUtils.getCurrentDate());
        Log.d(SkavaConstants.LOG, "********** onNewIntent ***** " + dateAsString);
        this.setupTheData();
        super.setupTheDrawer();
        invalidateOptionsMenu();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* How to avoid the start a new fresh app each time the launcher icon is pressed */
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            // Activity was brought to front and not created,
//            // Thus finishing this will get us to the last viewed activity
//            finish();
//            return;
//        }

//        String languageToLoad  = "es";
//        Locale locale = new Locale(languageToLoad);
//        Locale.setDefault(locale);
//        Configuration config = new Configuration();
//        config.locale = locale;
//        getBaseContext().getResources().updateConfiguration(config,
//                getBaseContext().getResources().getDisplayMetrics());


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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateAsString = sdf.format(SkavaUtils.getCurrentDate());
        Log.d(SkavaConstants.LOG, "********** onPostCreate ***** " + dateAsString);
        super.onPostCreate(savedInstanceState);
        this.setupTheData();
    }

    private void setupTheData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateAsString = sdf.format(SkavaUtils.getCurrentDate());
        Log.d(SkavaConstants.LOG, "********** setupTheData ***** " + dateAsString);
//        if (!linkDropboxCompleted) {
        if (!isLinkDropboxCompleted()) {
            setupLinkToDropbox();
        }

        if (shouldImportAppData()) {
            lackOfAppData = true;
        }
        if (shouldImportUserData()) {
            lackOfUserData = true;
        }
        if (assertAppDataNeverCalled) {
            try {
                assertAppDataAvailable();
            } catch (DAOException e) {
                BugSenseHandler.sendException(e);
                Log.e(SkavaConstants.LOG, e.getMessage());
                e.printStackTrace();
            }
        }
        //**
//        if (assertUserDataNeverCalled) {
//            try {
//                assertUserDataAvailable();
//            } catch (DAOException e) {
//                BugSenseHandler.sendException(e);
//                Log.e(SkavaConstants.LOG, e.getMessage());
//                e.printStackTrace();
//            }
//        }
        //**
        //Every thing is set up correctly? Show the Skava background image
        if (enoughDataAvailable) {
            mHomeMainFragment.getBackgroudImage().setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatastore != null && mDatastore.isOpen()) {
            mDatastore.close();
        }
    }

    private void setupLinkToDropbox() {
        String message = getString(R.string.connecting_dropbox);
        showProgressBar(true, message, false);
        try {
            //Abrir el acount manager que Dropbox ofrece para esta app
            mDbxAcctMgr = DbxAccountManager.getInstance(this.getApplicationContext(), SkavaConstants.DROPBOX_APP_KEY, SkavaConstants.DROPBOX_APP_SECRET);
            //Connect y tener la referencia al account
            if (!mDbxAcctMgr.hasLinkedAccount()) {
                if (isNetworkAvailable()) {
                    mDbxAcctMgr.startLink(this, SkavaConstants.REQUEST_LINK_TO_DROPBOX);
                } else {
                    //Alert user Internet is required
                    DialogFragment theDialog = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("There's no Internet connection available");
                            builder.setMessage("In order to link to Dropbox account Internet connection is required but not available");
                            builder.setPositiveButton("OK", null);
                            builder.setCancelable(false);
                            // Create the AlertDialog object and return it
                            return builder.create();
                        }
                    };
                }
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
//               linkDropboxCompleted = true;
                setLinkDropboxCompleted(true);

                getSkavaContext().setDatastore(mDatastore);
                getSkavaContext().getDatastore().addSyncStatusListener(this);
            }
        } catch (DbxException e) {
            BugSenseHandler.sendException(e);
            Log.e(SkavaConstants.LOG, e.getMessage());
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            showProgressBar(true, "Failed trying to connect to Dropbox.", false);
        }
        showProgressBar(false, "Hiding me !!", false);
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
                    Set<DbxDatastoreInfo> listDatastores = mDatastoreManager.listDatastores();
                    //listDatastores
                    if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.DEV_KEY)) {
                        for (DbxDatastoreInfo currDatastore : listDatastores) {
                            if (currDatastore.id.equalsIgnoreCase(SkavaConstants.DROPBOX_DS_DEV_NAME)){
                                mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_DEV_NAME);
                            }
                        }
                    } else if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.QA_KEY)) {
                        for (DbxDatastoreInfo currDatastore : listDatastores) {
                            if (currDatastore.id.equalsIgnoreCase(SkavaConstants.DROPBOX_DS_DEV_NAME)){
                                mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_QA_NAME);
                            }
                        }
                    } else if (getTargetEnvironment().equalsIgnoreCase(SkavaConstants.PROD_KEY)) {
                        for (DbxDatastoreInfo currDatastore : listDatastores) {
                            if (currDatastore.id.equalsIgnoreCase(SkavaConstants.DROPBOX_DS_DEV_NAME)){
                                mDatastore = mDatastoreManager.openDatastore(SkavaConstants.DROPBOX_DS_PROD_NAME);
                            }
                        }
                    }
                    if (mDatastore == null) {
                        mDatastore = mDatastoreManager.openDefaultDatastore();
                    }
//                    linkDropboxCompleted = true;
                    setLinkDropboxCompleted(true);
                    getSkavaContext().setDatastore(mDatastore);
                    ((SkavaApplication)getApplication()).setNeedImportAppData(true);
                    ((SkavaApplication)getApplication()).setNeedImportUserData(true);
                } catch (DbxException e) {
                    BugSenseHandler.sendException(e);
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
        Log.d(SkavaConstants.LOG, "Dropbox linking cancelled by user or failed.");
        if (assertAppDataNeverCalled) {
            try {
                assertAppDataAvailable();
            } catch (DAOException e) {
                BugSenseHandler.sendException(e);
                Log.e(SkavaConstants.LOG, e.getMessage());
                e.printStackTrace();
            }
        }
    }


    public void showProgressBar(final boolean show, String text, boolean longTime) {
        mHomeMainFragment.getSyncingStatusMessageView().setText(text);
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
            mHomeMainFragment.getSyncingStatusView().setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }



    @Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {

        List<NavDrawerItem> menuAsList = new ArrayList<NavDrawerItem>();
        menuAsList.add(NavMenuSection.create(NAV_MENU_SKAVA_SECTION_ID, "Skava Apps"));
        User loggedUser = getSkavaContext().getLoggedUser();
        if (!preventExecution) {
            menuAsList.add(NavMenuItem.create(NAV_MENU_LOGIN_ITEM_ID, getString(R.string.login_label), "ic_menu_copy_holo_dark", true, true, this));
            if (loggedUser != null) {
                Role geologist = new Role("GEOLOGIST", "Geologist");
                Role admin = new Role("ADMINISTRATOR", "Administrator");
                Role metricAdmin = new Role("METRICADMIN", "MetricAdmin");
                if (loggedUser.hasRole(geologist) || loggedUser.hasRole(admin) || loggedUser.hasRole(metricAdmin)) {
                    menuAsList.remove(1);
                    menuAsList.add(NavMenuItem.create(NAV_MENU_ROCK_CLASSIFICATION_ITEM_ID, getString(R.string.rock_classification_label) , "ic_menu_copy_holo_dark", true, true, this));
                }
                if (loggedUser.hasRole(admin) || loggedUser.hasRole(metricAdmin)) {
                    menuAsList.add(NavMenuSection.create(NAV_MENU_ADMIN_SECTION_ID, "Admin"));
                    menuAsList.add(NavMenuItem.create(NAV_MENU_DATA_MANAGEMENT_ITEM_ID, getString(R.string.data_management_label), "ic_menu_copy_holo_dark", true, true, this));
                }
                BugSenseHandler.setUserIdentifier(loggedUser.getName());
            }
        }
        menuAsList.add(NavMenuSection.create(NAV_MENU_GENERAL_SECTION_ID, "General"));
        if (loggedUser != null) {
            menuAsList.add(NavMenuItem.create(NAV_MENU_USER_ACCOUNT, getString(R.string.usr_account_label), "ic_action_overflow", true, true, this));
        }
        menuAsList.add(NavMenuItem.create(NAV_MENU_SYNC_ITEM_ID, getString(R.string.sync_label), "ic_action_overflow", true, true, this));
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
        navDrawerConfig.setContentFrameId(R.id.nav_drawer_main_layout_content_frame);
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
                //TODO comment this out to force login with another credentials
//                intent.putExtra(LoginMainActivity.EXTRA_USERNAME, "rcifuentes");
//                intent.putExtra(LoginMainActivity.EXTRA_PASSWORD, "pepito");
                startActivity(intent);
                break;
            case NAV_MENU_ROCK_CLASSIFICATION_ITEM_ID:
                intent = new Intent(this, AssessmentsListActivity.class);
                getSkavaContext().setOriginatorModule(SkavaApplication.Module.ROCK_CLASSIFICATION);
                intent.putExtra(SkavaConstants.SKAVA_APP_SRC, NAV_MENU_ROCK_CLASSIFICATION_ITEM_ID);
                startActivity(intent);
                break;
            case NAV_MENU_DATA_MANAGEMENT_ITEM_ID:
                intent = new Intent(this, DataManagementMainActivity.class);
                startActivity(intent);
                break;
            case NAV_MENU_USER_ACCOUNT:
                intent = new Intent(this, UserAccountMainActivity.class);
                startActivity(intent);
                break;
            case NAV_MENU_SYNC_ITEM_ID:
                //RAUL SYNC ON DEMAND
                syncOnDemand();
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
                        setLinkDropboxCompleted(false);
//                        linkDropboxCompleted = false;
                        mDbxAcctMgr.unlink();
                    }
                }
                getSkavaContext().setAssessment(null);
                getSkavaContext().setLoggedUser(null);
                setupTheDrawer();
                break;
            case NAV_MENU_TEST_AUTOCAD_ID:
                intent = new Intent(this, TestAutocadMainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void onPreExecuteImportAppData(){
        mHomeMainFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    public void onPreExecuteImportUserData(){
        mHomeMainFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    public void onPostExecuteImportAppData(boolean success, Long result){
        if (success) {
            //termino exitosamente
            lackOfAppData = false;
            if (shouldImportAppData()) {
                ((SkavaApplication) getApplication()).setNeedImportAppData(false);
            }
            preventExecution = false;
            saveAppDataSyncStatus(true);
            showProgressBar(true, "Finished. " + result + " records imported.", true);
            if (assertUserDataNeverCalled) {
                try {
                    assertUserDataAvailable();
                } catch (DAOException e) {
                    BugSenseHandler.sendException(e);
                    Log.e(SkavaConstants.LOG, e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            lackOfAppData = true;
            preventExecution = true;
            saveAppDataSyncStatus(false);
            showProgressBar(false, "Failed after " + result + " records imported.", true);
        }


        mHomeMainFragment.getBackgroudImage().setVisibility(View.VISIBLE);
    }

    public void onPostExecuteImportUserData(boolean success, Long result){
        mHomeMainFragment.getBackgroudImage().setVisibility(View.VISIBLE);
        if (success) {
            //mostrar que termino exitosamente
            lackOfUserData = false;
            if (shouldImportUserData()) {
                ((SkavaApplication) getApplication()).setNeedImportUserData(false);
            }
            preventExecution = false;
            saveUserDataSyncStatus(true);
            showProgressBar(false, "Finished. " + result + " records imported.", true);
        } else {
            lackOfUserData = true;
            preventExecution = true;
            saveUserDataSyncStatus(false);
            showProgressBar(false, "Failed after " + result + " records imported.", true);
        }
        //Now preventExecution flag is available so setup the drawer menu
        setupTheDrawer();
    }

}
