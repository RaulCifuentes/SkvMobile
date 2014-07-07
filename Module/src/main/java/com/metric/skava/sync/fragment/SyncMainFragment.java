package com.metric.skava.sync.fragment;

/**
 * Created by metricboy on 3/7/14.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.metric.skava.R;
import com.metric.skava.app.SkavaApplication;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Assessment;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.SkavaUtils;
import com.metric.skava.app.util.ViewUtils;
import com.metric.skava.data.adapter.AssessmentListViewAdapter;
import com.metric.skava.data.adapter.RemoteAssessmentListViewAdapter;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalAssessmentDAO;
import com.metric.skava.data.dao.LocalDiscontinuityFamilyDAO;
import com.metric.skava.data.dao.LocalQCalculationDAO;
import com.metric.skava.data.dao.LocalRMRCalculationDAO;
import com.metric.skava.data.dao.LocalSupportRecommendationDAO;
import com.metric.skava.data.dao.RemoteAssessmentDAO;
import com.metric.skava.data.dao.RemoteSyncAcknowlegeDAO;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.data.dao.impl.sqllite.table.AssessmentTable;
import com.metric.skava.home.activity.HomeMainActivity;
import com.metric.skava.home.helper.ImportDataHelper;
import com.metric.skava.sync.dao.SyncLoggingDAO;
import com.metric.skava.sync.exception.SyncDataFailedException;
import com.metric.skava.sync.helper.SyncHelper;
import com.metric.skava.sync.model.SyncLogEntry;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SyncMainFragment extends SkavaFragment {

    private ListView localAssessmentsListView;
    private ListView remoteAssessmentsListView;

    private AssessmentListViewAdapter localAssessmentListViewAdapter;
    private RemoteAssessmentListViewAdapter remoteAssessmentListViewAdapter;

    private LayoutInflater mInflater;

    private LocalAssessmentDAO mLocalAsssessmentDAO;
    private LocalSupportRecommendationDAO mLocalRecommendationDAO;
    private LocalDiscontinuityFamilyDAO mLocalDiscontinuitiesFamilyDAO;
    private LocalRMRCalculationDAO mLocalRMRCalculationDAO;
    private LocalQCalculationDAO mLocalBartonCalculationDAO;
    private SyncLoggingDAO mSyncLoggingDAO;


    private RemoteAssessmentDAO mRemoteAsssessmentDAO;
    private RemoteSyncAcknowlegeDAO mRemoteSyncAcknowlegedDAO;

    private View mSyncingStatusView;
    private TextView mSyncingStatusMessageView;
    private ProgressBar mSyncingStatusProgressCircle;


    public SyncMainFragment() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mLocalAsssessmentDAO = getDAOFactory().getLocalAssessmentDAO();
            mLocalRecommendationDAO = getDAOFactory().getLocalSupportRecommendationDAO();
            mLocalDiscontinuitiesFamilyDAO = getDAOFactory().getLocalDiscontinuityFamilyDAO();
            mLocalRMRCalculationDAO = getDAOFactory().getLocalRMRCalculationDAO();
            mLocalBartonCalculationDAO = getDAOFactory().getLocalQCalculationDAO();
            mRemoteAsssessmentDAO = getDAOFactory().getRemoteAssessmentDAO(DAOFactory.Flavour.DROPBOX);
            mRemoteSyncAcknowlegedDAO = getDAOFactory().getRemoteSyncAcknowledgeDAO(DAOFactory.Flavour.DROPBOX);
            mSyncLoggingDAO = getDAOFactory().getSyncLoggingDAO();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sync_main_fragment, container, false);
        mInflater = inflater;
//        showAssessmentsListViews(false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSyncingStatusView = view.findViewById(R.id.syncing_status);
        mSyncingStatusProgressCircle = (ProgressBar) view.findViewById(R.id.syncing_status_progress_circle);
        mSyncingStatusMessageView = (TextView) view.findViewById(R.id.syncing_status_message);
        setupLocalAssessmentsListView(mInflater);
        setupRemoteAssessmentsListView(mInflater);
    }


    public View getSyncingStatusView() {
        return mSyncingStatusView;
    }

    public TextView getSyncingStatusMessageView() {
        return mSyncingStatusMessageView;
    }

    public ProgressBar getSyncingStatusProgressCircle() {
        return mSyncingStatusProgressCircle;
    }

    private void setupLocalAssessmentsListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.test_list_header, null, false);
        localAssessmentsListView = (ListView) getView().findViewById(R.id.listview_local_assessments);
        final List<Assessment> listAssessments;
        try {
            listAssessments = mLocalAsssessmentDAO.getAllAssessments(AssessmentTable.DATE_COLUMN);
            localAssessmentListViewAdapter = new AssessmentListViewAdapter(getSkavaActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listAssessments);

            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.headerText);
            firstTextView.setText("Local Mappings");

            localAssessmentsListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = localAssessmentsListView.getHeaderViewsCount();
            localAssessmentsListView.setAdapter(localAssessmentListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(localAssessmentsListView);

        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    private void setupRemoteAssessmentsListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.test_list_header, null, false);
        remoteAssessmentsListView = (ListView) getView().findViewById(R.id.listview_remote_assessments);
        final List<Assessment> listAssessments;
        try {
            listAssessments = mRemoteAsssessmentDAO.getAllAssessments();
            remoteAssessmentListViewAdapter = new RemoteAssessmentListViewAdapter(getSkavaActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listAssessments);

            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.headerText);
            firstTextView.setText("Remote Mappings");

            remoteAssessmentsListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = remoteAssessmentsListView.getHeaderViewsCount();
            remoteAssessmentsListView.setAdapter(remoteAssessmentListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(remoteAssessmentsListView);

        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_clear_local_assessments:
                try {
                    mLocalRecommendationDAO.deleteAllSupportRecommendations();
                    mLocalRMRCalculationDAO.deleteAllRMRCalculations();
                    mLocalBartonCalculationDAO.deleteAllQCalculations();
                    mLocalDiscontinuitiesFamilyDAO.deleteAllDiscontinuitiesFamilies();
                    mLocalAsssessmentDAO.deleteAllAssessments();
                    showAssessmentsListViews(true);
                    refreshListViews();
                } catch (DAOException daoe) {
                    BugSenseHandler.sendException(daoe);
                    daoe.printStackTrace();
                    Log.d(SkavaConstants.LOG, daoe.getMessage());
                    Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_clear_remote_assessments:
                try {
                    mRemoteAsssessmentDAO.deleteAllAssessments(true);
                    showAssessmentsListViews(true);
                    refreshListViews();
                } catch (DAOException daoe) {
                    BugSenseHandler.sendException(daoe);
                    daoe.printStackTrace();
                    Log.d(SkavaConstants.LOG, daoe.getMessage());
                    Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_import_app_data:
                showAssessmentsListViews(false);
                ImportAllAppDataModelTask fixedDataTask = new ImportAllAppDataModelTask();
                fixedDataTask.execute();
                break;
            case R.id.action_clear_app_data:
                showAssessmentsListViews(false);
                Long numDeleted = clearAllAppData();
                getSyncingStatusProgressCircle().setProgress(getSyncingStatusProgressCircle().getMax());
                showProgressBar(true,  numDeleted + " records deleted." , false);
                break;
            case R.id.action_import_user_data:
                showAssessmentsListViews(false);
                ImportAllUserDataModelTask dynamicDataTask = new ImportAllUserDataModelTask();
                dynamicDataTask.execute();
                break;
            case R.id.action_clear_user_data:
                showAssessmentsListViews(false);
                numDeleted = clearAllUserData();
                getSyncingStatusProgressCircle().setProgress(getSyncingStatusProgressCircle().getMax());
                showProgressBar(true,  numDeleted + " records deleted." , false);
                break;
            case R.id.action_clear_remote_sync_acknowledge:
                try {
                    mRemoteSyncAcknowlegedDAO.deleteAllAcknowledges();
                    showAssessmentsListViews(true);
                    refreshListViews();
                } catch (DAOException daoe) {
                    BugSenseHandler.sendException(daoe);
                    daoe.printStackTrace();
                    Log.d(SkavaConstants.LOG, daoe.getMessage());
                    Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_unlink_dropbox_account:
                showAssessmentsListViews(false);
                ((SkavaApplication) getActivity().getApplication()).setWantUnlinkDropboxAccount(true);
                restartApp();
                break;
            case R.id.action_clear_sync_data:
                showAssessmentsListViews(false);
                SharedPreferences persistenceBucket = getActivity().getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = persistenceBucket.edit();
                editor.clear();
                boolean successClearingPreferences = editor.commit();
                getSyncingStatusProgressCircle().setProgress(getSyncingStatusProgressCircle().getMax());
                if (successClearingPreferences) {
                    showProgressBar(true, "Success. Preferences are cleared", false);
                } else {
                    showProgressBar(true, "Failure. Preferences are not cleared", false);
                }
                break;
            case R.id.action_clear_assessment_traces:
                try {
                    mSyncLoggingDAO.deleteAllAssessmentSyncTraces();
                    showAssessmentsListViews(true);
                    refreshListViews();
                } catch (DAOException daoe) {
                    BugSenseHandler.sendException(daoe);
                    daoe.printStackTrace();
                    Log.d(SkavaConstants.LOG, daoe.getMessage());
                    Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void restartAppWontCloseEnough() {
        Intent i = getActivity().getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    private void restartApp() {
        Intent mStartActivity = new Intent(getActivity().getApplicationContext(), HomeMainActivity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }


    public void showAssessmentsListViews(boolean visible) {
        localAssessmentsListView.setVisibility(visible?View.VISIBLE:View.GONE);
        remoteAssessmentsListView.setVisibility(visible?View.VISIBLE:View.GONE);
    }


    public void refreshListViews() throws DAOException {
        if (localAssessmentListViewAdapter != null) {
            localAssessmentListViewAdapter.clear();
            localAssessmentListViewAdapter.addAll(mLocalAsssessmentDAO.getAllAssessments(AssessmentTable.DATE_COLUMN));
            localAssessmentListViewAdapter.notifyDataSetChanged();
        }
        if (remoteAssessmentListViewAdapter != null) {
            remoteAssessmentListViewAdapter.clear();
            remoteAssessmentListViewAdapter.addAll(mRemoteAsssessmentDAO.getAllAssessments());
            remoteAssessmentListViewAdapter.notifyDataSetChanged();
        }
    }

    private Long findOutNumberOfAllAppDataRecordsToImport() {
        SyncHelper syncHelper = getSkavaContext().getSyncHelper();
        try {
            return syncHelper.getAllAppDataRecordCount();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
        return null;
    }

    private Long findOutNumberOfAllUserDataRecordsToImport() {
        SyncHelper syncHelper = getSkavaContext().getSyncHelper();
        try {
            return syncHelper.getAllUserDataRecordCount();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            BugSenseHandler.sendException(daoe);
        }
        return null;
    }

    private Long clearAllUserData(){
        SyncHelper syncHelper = getSkavaContext().getSyncHelper();
        Long numRecordsAffected = 0L;
        try {
            numRecordsAffected = syncHelper.clearAllUserData();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            BugSenseHandler.sendException(daoe);
        }
        return numRecordsAffected;
    }

    private Long clearAllAppData(){
        SyncHelper syncHelper = getSkavaContext().getSyncHelper();
        Long numRecordsAffected = 0L;
        try {
            numRecordsAffected = syncHelper.clearAllAppData();
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
            BugSenseHandler.sendException(daoe);
        }
        return numRecordsAffected;
    }

    public class ImportAllAppDataModelTask extends AsyncTask<Void, Long, Long> {

        private SyncLogEntry errorCondition;
        private Long totalRecordsToImport;
        private ImportDataHelper importHelper;

        @Override
        protected void onPreExecute() {
            importHelper = new ImportDataHelper(getSkavaContext().getSyncHelper());
            String message = getString(R.string.syncing_app_data_progress);
            showProgressBar(true, message, true);
        }

        @Override
        protected Long doInBackground(Void... params) {
            Long numRecordsCreated = 0L;
//            prepareSyncTraceTable();
            try {
                //FIND OUT THE TOTAL NUMBER OF RECORDS
                totalRecordsToImport = findOutNumberOfAllAppDataRecordsToImport();
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
                BugSenseHandler.sendException(e);
                e.printStackTrace();
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
            if (errorCondition == null) {
                //mostrar que termino exitosamente
                showProgressBar(true, "Finished. " + result + " records imported.", true);
                saveAppDataSyncStatus(true);
            } else {
                saveAppDataSyncStatus(false);
                showProgressBar(true, "Failed after " + result + " records imported.", true);
                AlertDialog.Builder messageBox = new AlertDialog.Builder(getActivity());
                messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
                messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
                messageBox.setCancelable(false);
                messageBox.setNeutralButton("OK", null);
                messageBox.show();
                showProgressBar(false, "Hiding me !!", false);
            }
            // now that app data sync has finished call the eventual user data sync
//            setupUserDataModel();
        }
    }


    public class ImportAllUserDataModelTask extends AsyncTask<Void, Long, Long> {

        private SyncLogEntry errorCondition;
        private Long totalRecordsToImport;
        private ImportDataHelper importHelper;

        @Override
        protected void onPreExecute() {
            importHelper = new ImportDataHelper(getSkavaContext().getSyncHelper());
            String message = getString(R.string.syncing_user_data_progress);
            showProgressBar(true, message, true);
        }

        @Override
        protected Long doInBackground(Void... params) {
            Long numRecordsCreated = 0L;
            try {
                //FIND OUT THE TOTAL NUMBER OF RECORDS
                totalRecordsToImport = findOutNumberOfAllUserDataRecordsToImport();
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
                BugSenseHandler.sendException(e);
                e.printStackTrace();
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
            if (errorCondition == null) {
                //mostrar que termino exitosamente
                saveUserDataSyncStatus(true);
                showProgressBar(true, "Finished. " + result + " records imported.", true);
                showProgressBar(false, "Hiding me !!", false);
            } else {
                saveUserDataSyncStatus(false);
                showProgressBar(true, "Failed after " + result + " records imported.", true);
                AlertDialog.Builder messageBox = new AlertDialog.Builder(getActivity());
                messageBox.setTitle("Bad news with " + errorCondition.getSource().name() + " on " + errorCondition.getSyncDate().toString());
                messageBox.setMessage("Hey buddy, I was syncing " + errorCondition.getDomain().name() + ", but this issue arose : " + errorCondition.getMessage());
                messageBox.setCancelable(false);
                messageBox.setNeutralButton("OK", null);
                messageBox.show();
                showProgressBar(false, "Hiding me !!", false);
            }
        }
    }



    private void showProgressBar(final boolean show, String text, boolean longTime) {
        getSyncingStatusMessageView().setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = longTime ? getResources().getInteger(android.R.integer.config_longAnimTime) : getResources().getInteger(android.R.integer.config_shortAnimTime);
            getSyncingStatusView().setVisibility(View.VISIBLE);
            getSyncingStatusView().animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            getSyncingStatusView().setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    });
        } else {
            getSyncingStatusView().setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void saveUserDataSyncStatus(boolean success) {
        getSkavaContext().getUserDataSyncMetadata().setSuccess(success);
        getSkavaContext().getUserDataSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.user_data_last_sync_succeed), success);
        editor.putLong(getString(R.string.user_data_last_sync_date), SkavaUtils.getCurrentDate().getTime());
        editor.commit();
    }

    public void saveAppDataSyncStatus(boolean success) {
        getSkavaContext().getAppDataSyncMetadata().setSuccess(success);
        getSkavaContext().getAppDataSyncMetadata().setLastExecution(SkavaUtils.getCurrentDate());
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.persistence_bucket_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.app_data_last_sync_succeed), success);
        editor.putLong(getString(R.string.app_data_last_sync_date), SkavaUtils.getCurrentDate().getTime());
        editor.commit();
    }



}