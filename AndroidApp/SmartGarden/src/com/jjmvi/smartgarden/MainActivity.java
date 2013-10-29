package com.jjmvi.smartgarden;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity
{
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mNavListTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mTitle = mDrawerTitle = getTitle();
		mNavListTitles = getResources().getStringArray(R.array.nav_list_titles_array);
//		mNavListTitles = getResources().getStringArray(R.array.planets_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mNavListTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
				R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close)
		{
			public void onDrawerOpened(View drawerview)
			{
				getSupportActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerClosed(View view)
			{
				getSupportActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) selectItem(0);
		
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu)
	{
		return super.onCreateOptionsMenu(menu);
	}

	@Override
  public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item)
  {
  	int itemId = item.getItemId();
  	switch (itemId) {
  	  case android.R.id.home: 
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
          mDrawerLayout.closeDrawer(mDrawerList);
        } else {
          mDrawerLayout.openDrawer(mDrawerList);
        }
        break;
      }
	  return super.onOptionsItemSelected(item);
  }

  private class DrawerItemClickListener implements ListView.OnItemClickListener
  {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id)
    {
      selectItem(index);
    }
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState)
  {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig)
  {
    super.onConfigurationChanged(newConfig);
    // Pass any configuration change to the drawer toggles
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  // Drawer index selection
  private void selectItem(int index)
  {
    switch (index) {
      case 0:
        getSupportFragmentManager().beginTransaction()
          .add(R.id.content, PageSlidingTabStripFragment.newInstance(), PageSlidingTabStripFragment.TAG).commit();
        break;
      default:
        // Get a new fragment for the index
        SherlockFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ItemFragment.ARG_ITEM_NUMBER, index);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
        break;
    }
    // Close the drawer when we switch to a new page
    mDrawerLayout.closeDrawer(mDrawerList);
  }

}
