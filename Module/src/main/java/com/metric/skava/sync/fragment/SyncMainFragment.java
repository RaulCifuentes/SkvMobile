package com.metric.skava.sync.fragment;

/**
 * Created by metricboy on 3/7/14.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.fragment.SkavaFragment;
import com.metric.skava.app.model.Client;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.SkavaConstants;
import com.metric.skava.app.util.ViewUtils;
import com.metric.skava.data.adapter.ClientListViewAdapter;
import com.metric.skava.data.adapter.ProjectListViewAdapter;
import com.metric.skava.data.adapter.RoleListViewAdapter;
import com.metric.skava.data.adapter.TunnelFaceListViewAdapter;
import com.metric.skava.data.adapter.TunnelListViewAdapter;
import com.metric.skava.data.adapter.UserListViewAdapter;
import com.metric.skava.data.dao.LocalClientDAO;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.LocalExcavationProjectDAO;
import com.metric.skava.data.dao.LocalUserDAO;
import com.metric.skava.data.dao.LocalRoleDAO;
import com.metric.skava.data.dao.LocalTunnelDAO;
import com.metric.skava.data.dao.LocalTunnelFaceDAO;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SyncMainFragment extends SkavaFragment {

    private DAOFactory daoFactory;

    private ListView rolesListView;
    private ListView clientsListView;
    private ListView usersListView;
    private ListView projectsListView;
    private ListView facesListView;
    private ListView tunnelsListView;
    private ClientListViewAdapter clientListViewAdapter;
    private RoleListViewAdapter roleListViewAdapter;
    private UserListViewAdapter userListViewAdapter;
    private ProjectListViewAdapter projectListAdapter;
    private TunnelListViewAdapter tunnelListViewAdapter;
    private TunnelFaceListViewAdapter facesListViewAdapter;
    private LayoutInflater mInflater;
    private LocalClientDAO clientsDAO;
    private LocalTunnelFaceDAO facesDAO;
    private LocalRoleDAO rolesDAO;
    private LocalTunnelDAO tunnelsDAO;
    private LocalExcavationProjectDAO projectsDAO;
    private LocalUserDAO usersDAO;

    public SyncMainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daoFactory = DAOFactory.getInstance(getActivity());
        try {
            clientsDAO = daoFactory.getLocalClientDAO(DAOFactory.Flavour.SQLLITE);
            rolesDAO = daoFactory.getLocalRoleDAO(DAOFactory.Flavour.SQLLITE);
            usersDAO = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE);
            projectsDAO = daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE);
            tunnelsDAO = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE);
            facesDAO = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE);
        } catch (DAOException e) {
            Log.e(SkavaConstants.LOG, e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.sync_main_fragment, container, false);
        mInflater = inflater;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClientsListView(mInflater);
        setupRolesListView(mInflater);
        setupUsersListView(mInflater);
        setupProjectsListView(mInflater);
        setupTunnelsListView(mInflater);
        setupFacesListView(mInflater);
    }

    private void setupClientsListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        clientsListView = (ListView) getView().findViewById(R.id.listviewClients);
        final List<Client> listClients;
        try {
            listClients = daoFactory.getLocalClientDAO(DAOFactory.Flavour.SQLLITE).getAllClients();
            clientListViewAdapter = new ClientListViewAdapter(getActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listClients);

//            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
            TextView secondTextView = (TextView) usersHeaderView.findViewById(R.id.second_column_text_view);
            secondTextView.setText("Clients");

            clientsListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = clientsListView.getHeaderViewsCount();
            clientsListView.setAdapter(clientListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(clientsListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void setupRolesListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        rolesListView = (ListView) getView().findViewById(R.id.listviewRoles);
        final List<Role> listRoles;
        try {
            listRoles = daoFactory.getLocalRoleDAO(DAOFactory.Flavour.SQLLITE).getAllRoles();
            roleListViewAdapter = new RoleListViewAdapter(getActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listRoles);

//            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
            TextView secondTextView = (TextView) usersHeaderView.findViewById(R.id.second_column_text_view);
            secondTextView.setText("Roles");

            rolesListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = rolesListView.getHeaderViewsCount();
            rolesListView.setAdapter(roleListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(rolesListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    //summarized table
    private void setupUsersListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        usersListView = (ListView) getView().findViewById(R.id.listviewUsers);
        final List<User> listUsers;
        try {
            listUsers = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE).getAllUsers();
            userListViewAdapter = new UserListViewAdapter(getActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listUsers);

//            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
            TextView secondTextView = (TextView) usersHeaderView.findViewById(R.id.second_column_text_view);
            secondTextView.setText("Users");

            usersListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = usersListView.getHeaderViewsCount();
            usersListView.setAdapter(userListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(usersListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void setupProjectsListView(LayoutInflater inflater) {
        View usersHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        projectsListView = (ListView) getView().findViewById(R.id.listviewProjects);
        final List<ExcavationProject> listProjects;
        try {
            listProjects = daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE).getAllExcavationProjects();
            projectListAdapter = new ProjectListViewAdapter(getActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listProjects);

//            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
            TextView secondTextView = (TextView) usersHeaderView.findViewById(R.id.second_column_text_view);
            secondTextView.setText("Projects");

            projectsListView.addHeaderView(usersHeaderView, null, false);
            final int numberOfHeaders = projectsListView.getHeaderViewsCount();
            projectsListView.setAdapter(projectListAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(projectsListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    private void setupTunnelsListView(LayoutInflater inflater) {
        View tunnelsHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        tunnelsListView = (ListView) getView().findViewById(R.id.listviewTunnels);
        final List<Tunnel> listTunnels;
        try {
            listTunnels = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE).getAllTunnels();
            tunnelListViewAdapter = new TunnelListViewAdapter(getActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listTunnels);

//            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
            TextView secondTextView = (TextView) tunnelsHeaderView.findViewById(R.id.second_column_text_view);
            secondTextView.setText("Tunnels");

            tunnelsListView.addHeaderView(tunnelsHeaderView, null, false);
            final int numberOfHeaders = tunnelsListView.getHeaderViewsCount();
            tunnelsListView.setAdapter(tunnelListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(tunnelsListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    private void setupFacesListView(LayoutInflater inflater) {
        View facesHeaderView = inflater.inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        facesListView = (ListView) getView().findViewById(R.id.listviewFaces);
        final List<TunnelFace> listFaces;
        try {
            listFaces = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE).getAllTunnelFaces();
            facesListViewAdapter = new TunnelFaceListViewAdapter(getActivity(),
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listFaces);

//            TextView firstTextView = (TextView) usersHeaderView.getView().findViewById(R.id.first_column_text_view);
            TextView secondTextView = (TextView) facesHeaderView.findViewById(R.id.second_column_text_view);
            secondTextView.setText("Faces");

            facesListView.addHeaderView(facesHeaderView, null, false);
            final int numberOfHeaders = facesListView.getHeaderViewsCount();
            facesListView.setAdapter(facesListViewAdapter);

            //This method is necessary only to use a ListView inside a ScrollView
            ViewUtils.adjustListViewHeightBasedOnChildren(facesListView);

        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_clear_tables:
                try {
                    clientsDAO.deleteAllClients();

                    rolesDAO.deleteAllRoles();

                    usersDAO.deleteAllUsers();

                    projectsDAO.deleteAllExcavationProjects();

                    tunnelsDAO.deleteAllTunnels();

                    facesDAO.deleteAllTunnelFaces();

                    refreshListViews();

                } catch (DAOException e) {
                    Log.d(SkavaConstants.LOG, e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
                }
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void refreshListViews() throws DAOException {
        clientListViewAdapter.clear();
        clientListViewAdapter.addAll(clientsDAO.getAllClients());
        clientListViewAdapter.notifyDataSetChanged();

        roleListViewAdapter.clear();
        roleListViewAdapter.addAll(rolesDAO.getAllRoles());
        roleListViewAdapter.notifyDataSetChanged();

        userListViewAdapter.clear();
        userListViewAdapter.addAll(usersDAO.getAllUsers());
        userListViewAdapter.notifyDataSetChanged();

        projectListAdapter.clear();
        projectListAdapter.addAll(projectsDAO.getAllExcavationProjects());
        projectListAdapter.notifyDataSetChanged();

        tunnelListViewAdapter.clear();
        tunnelListViewAdapter.addAll(tunnelsDAO.getAllTunnels());
        tunnelListViewAdapter.notifyDataSetChanged();

        facesListViewAdapter.clear();
        facesListViewAdapter.addAll(facesDAO.getAllTunnelFaces());
        facesListViewAdapter.notifyDataSetChanged();
    }
}