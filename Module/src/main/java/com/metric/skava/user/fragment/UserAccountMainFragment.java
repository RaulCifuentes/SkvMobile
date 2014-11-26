package com.metric.skava.user.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.adapter.SkavaEntityAdapter;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.ViewUtils;
import com.metric.skava.data.adapter.TunnelFaceListViewAdapter;
import com.metric.skava.data.dao.exception.DAOException;
import com.metric.skava.identification.helper.UserDataHelper;
import com.metric.skava.identification.model.UserDataDomain;

import java.util.List;

/**
 * Created by metricboy on 11/21/14.
 */
public class UserAccountMainFragment extends SkavaFragment implements AdapterView.OnItemSelectedListener {

    private LayoutInflater mInflater;

    private UserDataDomain mUserDataDomain;
    private String mEnvironment;

    private Spinner projectSpinner;
    private int projectSpinnerLastPosition;
    private SkavaEntityAdapter projectAdapter;
    private List<ExcavationProject> projectList;
    private ExcavationProject selectedProject;

    private Spinner tunnelSpinner;
    private int tunnelSpinnerLastPosition;
    private SkavaEntityAdapter tunnelAdapter;
    private List<Tunnel> tunnelList;
    private Tunnel selectedTunnel;

    private TunnelFaceListViewAdapter facesListViewAdapter;
    private List<TunnelFace> facesList;
    private ListView facesListView;

    private View mSyncingStatusView;
    private TextView mSyncingStatusMessageView;
    private ProgressBar mSyncingStatusProgressCircle;


    public UserAccountMainFragment() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mEnvironment = getSkavaActivity().getTargetEnvironment();
            UserDataHelper userDataHelper = new UserDataHelper(getSkavaContext());
            mUserDataDomain = userDataHelper.buildUserDataDomain(mEnvironment, getSkavaContext().getLoggedUser());

            tunnelSpinnerLastPosition = -1;
            projectSpinnerLastPosition = -1;

            projectList = mUserDataDomain.getProjects();
            projectList.add(new ExcavationProject("HINT", "Select one project ...", null));

            projectAdapter = new SkavaEntityAdapter<ExcavationProject>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, projectList);
            // Specify the layout to use when the list of choices appears
            projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } catch (DAOException daoe) {
            daoe.printStackTrace();
            Toast.makeText(getActivity(), daoe.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(SkavaConstants.LOG, daoe.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_account_main_fragment, container, false);
        // ***************** PROJECT ******************
        projectSpinner = (Spinner) rootView.findViewById(R.id.user_account_project_spinner);
        projectSpinner.setAdapter(projectAdapter);
        //if this is the first time the spinner get configured set selection to the hint
        //this is necessary in order to onItemSelected proper functioning
        if (projectSpinnerLastPosition == -1) {
            projectSpinnerLastPosition = projectSpinner.getAdapter().getCount() - 1;
        }
        //first time selectedProject will be null, after that we can extract it from the selectedProject
        //The user may have not complete the face selection and leaves the fragment so
        //ExcavationProject project = getCurrentAssessment().getProject() wont work here
        //That's why is better to ese the selectedProject variable instead
        if (selectedProject != null) {
            projectSpinner.setSelection(projectAdapter.getPosition(selectedProject));
        } else {
            projectSpinner.setSelection(projectAdapter.getCount() - 1); //display hint
        }
        projectSpinner.setOnItemSelectedListener(this);

        // ***************** TUNNEL ******************
        tunnelSpinner = (Spinner) rootView.findViewById(R.id.user_account_tunnel_spinner);
        //tunnel adapter remains null until a project is selected
        if (selectedTunnel != null) {
            tunnelSpinner.setAdapter(tunnelAdapter);
            tunnelSpinner.setSelection(tunnelSpinnerLastPosition);
        }
        tunnelSpinner.setOnItemSelectedListener(this);

        mInflater = inflater;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSyncingStatusView = view.findViewById(R.id.syncing_status);
        mSyncingStatusProgressCircle = (ProgressBar) view.findViewById(R.id.syncing_status_progress_circle);
        mSyncingStatusMessageView = (TextView) view.findViewById(R.id.syncing_status_message);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == projectSpinner) {
            if (position != projectSpinnerLastPosition) {
                selectedProject = (ExcavationProject) parent.getItemAtPosition(position);
                tunnelAdapter = prepareTunnelAdapter(selectedProject);
                tunnelSpinner.setAdapter(tunnelAdapter);
                if (selectedTunnel != null) {
                    if (tunnelAdapter.getPosition(selectedTunnel) != -1) {
                        tunnelSpinner.setSelection(tunnelAdapter.getPosition(selectedTunnel));
                    } else {
                        tunnelSpinner.setSelection(tunnelAdapter.getCount() - 1);
                    }
                } else {
                    tunnelSpinner.setSelection(tunnelAdapter.getCount() - 1);
                }
                projectSpinnerLastPosition = position;
            }
        }

        if (parent == tunnelSpinner) {
            if (position != tunnelSpinnerLastPosition) {
                selectedTunnel = (Tunnel) parent.getItemAtPosition(position);
                tunnelSpinnerLastPosition = position;
                refreshFacesListView(mInflater);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private SkavaEntityAdapter prepareTunnelAdapter(ExcavationProject project) {
        tunnelList = mUserDataDomain.getTunnels(project);
        tunnelList.add(new Tunnel(null, "HINT", "Select a tunnel ...", null));
        tunnelAdapter = new SkavaEntityAdapter<Tunnel>(getActivity(), android.R.layout.simple_spinner_item, android.R.id.text1, tunnelList);
        // Specify the layout to use when the list of choices appears
        tunnelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (tunnelSpinnerLastPosition == -1) {
            tunnelSpinnerLastPosition = tunnelAdapter.getCount() - 1;
        }
        return tunnelAdapter;
    }


    private void refreshFacesListView(LayoutInflater inflater) {
        facesListView = (ListView) getView().findViewById(R.id.listview_faces_granted_to_user);
        final int numberOfHeaders = facesListView.getHeaderViewsCount();
        if (numberOfHeaders == 0){
            View facesHeaderView = inflater.inflate(R.layout.face_dto_list_header, null, false);
            facesListView.addHeaderView(facesHeaderView, null, false);
        }
        facesList = mUserDataDomain.getFaces(selectedTunnel);
        facesListViewAdapter = new TunnelFaceListViewAdapter(getActivity(), R.layout.face_dto_list_item, R.id.first_column_text_view, facesList);
        facesListView.setAdapter(facesListViewAdapter);
        //This method is necessary only to use a ListView inside a ScrollView
        ViewUtils.adjustListViewHeightBasedOnChildren(facesListView);
//            daoe.printStackTrace();
//            Log.e(SkavaConstants.LOG, daoe.getMessage());
//            BugSenseHandler.sendException(daoe);
//            //Adding alert message to inform exception occurred
//            final String finalTextToShow = daoe.getMessage();
//            DialogFragment theDialog = new DialogFragment() {
//                @Override
//                public Dialog onCreateDialog(Bundle savedInstanceState) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setTitle("Error assembling local assessments ::");
//                    builder.setMessage(finalTextToShow).setPositiveButton("OK", null);
//                    // Create the AlertDialog object and return it
//                    return builder.create();
//                }
//            };
//            theDialog.show(getSkavaActivity().getSupportFragmentManager(), "assertUserDataDialog");


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

}




