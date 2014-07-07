package com.metric.skava.app.navdrawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.metric.skava.app.activity.SkavaFragmentActivity;

public abstract class AbstractNavDrawerActivity extends SkavaFragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private ListView mDrawerList;
    private FrameLayout mContentFrameLayout;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private NavDrawerActivityConfiguration navConf;

	private int lastItemChecked = 0;

	protected abstract NavDrawerActivityConfiguration getNavDrawerConfiguration();

	protected abstract void onNavItemSelected(int id);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setupTheDrawer();
        if (savedInstanceState == null) {
            mTitle = mDrawerTitle = getTitle();
        } else {
            mTitle = savedInstanceState.getCharSequence("title");
            mDrawerTitle = savedInstanceState.getCharSequence("drawerTitle");
            lastItemChecked = savedInstanceState.getInt("lastItemChecked");
            setTitle(mTitle);
        }
	}

    protected void setupTheDrawer() {
        navConf = getNavDrawerConfiguration();

        setContentView(navConf.getMainLayout());

        mDrawerLayout = (DrawerLayout) findViewById(navConf.getDrawerLayoutId());
        mDrawerList = (ListView) findViewById(navConf.getLeftDrawerId());
        mDrawerList.setAdapter(navConf.getBaseAdapter());
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mContentFrameLayout = (FrameLayout)findViewById(navConf.getContentFrameId());

        this.initDrawerShadow();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

//        ImageView backgroundImage = (ImageView) mContentFrameLayout.findViewById(R.id.imageView);
//        backgroundImage.setVisibility(View.VISIBLE);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                navConf.getDrawerIcon(), navConf.getDrawerOpenDesc(),
                navConf.getDrawerCloseDesc()) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
//                ImageView backgroundImage = (ImageView) mContentFrameLayout.findViewById(R.id.imageView);
//                backgroundImage.setVisibility(View.VISIBLE);
                // setTitle(mDrawerTitle);
                ActivityCompat
                        .invalidateOptionsMenu(AbstractNavDrawerActivity.this);
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
//                ImageView backgroundImage = (ImageView) mContentFrameLayout.findViewById(R.id.imageView);
//                backgroundImage.setVisibility(View.GONE);
                // setTitle(mDrawerTitle);
                ActivityCompat
                        .invalidateOptionsMenu(AbstractNavDrawerActivity.this);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    protected void initDrawerShadow() {
		mDrawerLayout.setDrawerShadow(navConf.getDrawerShadow(),
				GravityCompat.START);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (navConf.getActionMenuItemsToHideWhenDrawerOpen() != null) {
			boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
			for (int iItem : navConf.getActionMenuItemsToHideWhenDrawerOpen()) {
				menu.findItem(iItem).setVisible(!drawerOpen);
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (this.mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
				this.mDrawerLayout.closeDrawer(this.mDrawerList);
			} else {
				this.mDrawerLayout.openDrawer(this.mDrawerList);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}

	protected ActionBarDrawerToggle getDrawerToggle() {
		return mDrawerToggle;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	public void selectItem(int position) {
		NavDrawerItem selectedItem = navConf.getNavItems()[position];

		this.onNavItemSelected(selectedItem.getId());

		if (selectedItem.isCheckable()) {
			mDrawerList.setItemChecked(position, true);
			lastItemChecked = position;
		} else {
			mDrawerList.setItemChecked(position, false);
			if (lastItemChecked != 0) {
				mDrawerList.setItemChecked(lastItemChecked, true);
			}
		}

		if (selectedItem.updateActionBarTitle()) {
			setTitle(selectedItem.getLabel());
		}

		if (this.mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		// SpannableString spanableTitle = new SpannableString(mTitle);
		// spanableTitle.setSpan(new CustomTypefaceSpan(getApplicationContext(),
		// "impact.ttf"), 0, spanableTitle.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(mTitle);
	}

	public void setTitleWithDrawerTitle() {
		setTitle(mDrawerTitle);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putCharSequence("title", this.mTitle);
		outState.putCharSequence("drawerTitle", this.mDrawerTitle);
		outState.putInt("lastItemChecked", this.lastItemChecked);
	}
}
