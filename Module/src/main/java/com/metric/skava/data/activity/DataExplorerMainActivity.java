package com.metric.skava.data.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaActivity;
import com.metric.skava.app.model.Client;
import com.metric.skava.app.model.ExcavationProject;
import com.metric.skava.app.model.Role;
import com.metric.skava.app.model.Tunnel;
import com.metric.skava.app.model.TunnelFace;
import com.metric.skava.app.model.User;
import com.metric.skava.app.util.ViewUtils;
import com.metric.skava.data.adapter.ClientListViewAdapter;
import com.metric.skava.data.adapter.RoleListViewAdapter;
import com.metric.skava.data.adapter.SkavaEntityListViewAdapter;
import com.metric.skava.data.adapter.TunnelFaceListViewAdapter;
import com.metric.skava.data.adapter.TunnelListViewAdapter;
import com.metric.skava.data.adapter.UserListViewAdapter;
import com.metric.skava.data.dao.DAOFactory;
import com.metric.skava.data.dao.exception.DAOException;

import java.util.List;


public class DataExplorerMainActivity extends SkavaActivity {

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
    private SkavaEntityListViewAdapter projectListAdapter;
    private TunnelListViewAdapter tunnelListViewAdapter;
    private TunnelFaceListViewAdapter facesListViewAdapter;

//    private ListView sectionsListView;
//    private ListView methodsListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_main);
        daoFactory = DAOFactory.getInstance(this);
        setupClientsListView();
        setupRolesListView();
        setupUsersListView();
        setupProjectsListView();
        setupTunnelsListView();
        setupFacesListView();
    }

    private void setupClientsListView() {
        View usersHeaderView = getLayoutInflater().inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        clientsListView = (ListView) findViewById(R.id.listviewClients);
        final List<Client> listClients;
        try {
            listClients = daoFactory.getLocalClientDAO(DAOFactory.Flavour.SQLLITE).getAllClients();
            clientListViewAdapter = new ClientListViewAdapter(this,
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

    private void setupRolesListView() {
        View usersHeaderView = getLayoutInflater().inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        rolesListView = (ListView) findViewById(R.id.listviewRoles);
        final List<Role> listRoles;
        try {
            listRoles = daoFactory.getLocalRoleDAO(DAOFactory.Flavour.SQLLITE).getAllRoles();
            roleListViewAdapter = new RoleListViewAdapter(this,
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
    private void setupUsersListView() {
        View usersHeaderView = getLayoutInflater().inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        usersListView = (ListView) findViewById(R.id.listviewUsers);
        final List<User> listUsers;
        try {
            listUsers = daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE).getAllUsers();
            userListViewAdapter = new UserListViewAdapter(this,
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

    private void setupProjectsListView() {
        View usersHeaderView = getLayoutInflater().inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        projectsListView = (ListView) findViewById(R.id.listviewProjects);
        final List<ExcavationProject> listProjects;
        try {
            listProjects = daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE).getAllExcavationProjects();
            projectListAdapter = new SkavaEntityListViewAdapter(this,
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

    private void setupTunnelsListView() {
        View tunnelsHeaderView = getLayoutInflater().inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        tunnelsListView = (ListView) findViewById(R.id.listviewTunnels);
        final List<Tunnel> listTunnels;
        try {
            listTunnels = daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE).getAllTunnels();
            tunnelListViewAdapter = new TunnelListViewAdapter(this,
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


    private void setupFacesListView() {
        View facesHeaderView = getLayoutInflater().inflate(R.layout.calculator_two_column_list_view_header_checked_radio, null, false);
        facesListView = (ListView) findViewById(R.id.listviewFaces);
        final List<TunnelFace> listFaces;
        try {
            listFaces = daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE).getAllTunnelFaces();
            facesListViewAdapter = new TunnelFaceListViewAdapter(this,
                    R.layout.test_three_column_list_view_row, R.id.first_column_text_view, listFaces);

//            TextView firstTextView = (TextView) usersHeaderView.findViewById(R.id.first_column_text_view);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.test_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_clear_tables:
                try {
                    daoFactory.getLocalClientDAO(DAOFactory.Flavour.SQLLITE).deleteAllClients();
                    ((SkavaEntityListViewAdapter)clientListViewAdapter).notifyDataSetChanged();
                    daoFactory.getLocalRoleDAO(DAOFactory.Flavour.SQLLITE).deleteAllRoles();
                    ((SkavaEntityListViewAdapter)roleListViewAdapter).notifyDataSetChanged();
                    daoFactory.getLocalUserDAO(DAOFactory.Flavour.SQLLITE).deleteAllUsers();
                    ((SkavaEntityListViewAdapter)userListViewAdapter).notifyDataSetChanged();
                    daoFactory.getLocalExcavationProjectDAO(DAOFactory.Flavour.SQLLITE).deleteAllExcavationProjects();
                    ((SkavaEntityListViewAdapter)projectListAdapter).notifyDataSetChanged();
                    daoFactory.getLocalTunnelDAO(DAOFactory.Flavour.SQLLITE).deleteAllTunnels();
                    ((SkavaEntityListViewAdapter)tunnelListViewAdapter).notifyDataSetChanged();
                    daoFactory.getLocalTunnelFaceDAO(DAOFactory.Flavour.SQLLITE).deleteAllTunnelFaces();
                    ((SkavaEntityListViewAdapter)facesListViewAdapter).notifyDataSetChanged();
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                break;

        }
        return super.onOptionsItemSelected(menuItem);
    }
}
