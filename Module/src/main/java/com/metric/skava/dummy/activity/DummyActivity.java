package com.metric.skava.dummy.activity;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaActivity;
import com.metric.skava.dummy.adapter.SpinnerNavigationAdapter;
import com.metric.skava.dummy.model.DummySpinnerItem;

import java.util.ArrayList;

public class DummyActivity extends SkavaActivity implements OnNavigationListener {

	// action bar
	private ActionBar actionBar;

	// Title navigation Spinner data
	private ArrayList<DummySpinnerItem> navSpinner;

	// Navigation adapter
	private SpinnerNavigationAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dummy_main_activity);

		actionBar = getActionBar();

		// Hide the action bar title
		actionBar.setDisplayShowTitleEnabled(true);

		// Enabling Spinner dropdown navigation
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Spinner title navigation data
		navSpinner = new ArrayList<DummySpinnerItem>();
		navSpinner.add(new DummySpinnerItem("Local",
				R.drawable.ic_action_attach));
		navSpinner.add(new DummySpinnerItem("My Places",
				R.drawable.ic_action_call));
		navSpinner.add(new DummySpinnerItem("Checkins",
				R.drawable.ic_action_copy));
		navSpinner.add(new DummySpinnerItem("Latitude",
				R.drawable.ic_action_cut));

		// title drop down adapter
		adapter = new SpinnerNavigationAdapter(getApplicationContext(),
				navSpinner);

		// assigning the spinner navigation
		actionBar.setListNavigationCallbacks(adapter, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.dummy_main_menu, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Actionbar navigation item select listener
	 * */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// Action to be taken after selecting a spinner item
		return false;
	}

}
