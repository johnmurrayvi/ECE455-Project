package com.jjmvi.gardenmonitor;

// import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;


public class GardenMonitorActivity extends FragmentActivity
{
  protected String dataFeed = "http://69.1.47.239/";
  private TabsAdapter mTabsAdapter;
  private ViewPager mViewPager;

  private String[] tabColors = { "#378FFD", "#FF8700", "#5FD7FE", "#D50929", "#378F2A" };

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getActionBar().setTitle("Garden Monitor");

    mViewPager = (ViewPager) this.findViewById(R.id.view_pager);
    final ActionBar bar = this.getActionBar();
    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    GraphFragment humidityFrag = new GraphFragment();
    GraphFragment lightFrag = new GraphFragment();
    GraphFragment moistureFrag = new GraphFragment();
    GraphFragment temperatureFrag = new GraphFragment();

    
    // Get TabsAdapter and add fragments to the adapter as tabs
    mTabsAdapter = new TabsAdapter(this, mViewPager);

    Bundle hB = new Bundle();
    hB.putInt("position", 0);
    humidityFrag.setArguments(hB);
    mTabsAdapter.addTab(bar.newTab().setText("Humidity"), GraphFragment.class, hB, humidityFrag);

    Bundle lB = new Bundle();
    lB.putInt("position", 1);
    lightFrag.setArguments(lB);
    mTabsAdapter.addTab(bar.newTab().setText("Light"), GraphFragment.class, lB, lightFrag);

    Bundle mB = new Bundle();
    mB.putInt("position", 2);
    moistureFrag.setArguments(mB);
    mTabsAdapter.addTab(bar.newTab().setText("Moisture"), GraphFragment.class, mB, moistureFrag);

    Bundle tB = new Bundle();
    tB.putInt("position", 3);
    temperatureFrag.setArguments(tB);
    mTabsAdapter.addTab(bar.newTab().setText("Temperature"), GraphFragment.class, tB, temperatureFrag);

    mViewPager.setOffscreenPageLimit(mTabsAdapter.getCount() - 1);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
      super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState)
  {
      super.onRestoreInstanceState(savedInstanceState);
  }

  public static class TabsAdapter extends FragmentStatePagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener
  {
    private final FragmentActivity mContext;
    private final ActionBar mActionBar;
    private final ViewPager mViewPager;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    private final ArrayList<android.support.v4.app.Fragment> mFrag = new ArrayList<android.support.v4.app.Fragment>();

    static final class TabInfo
    {
      private final Class<?> clss;
      private final Bundle args;
      
      public TabInfo(Class<?> _class, Bundle _args)
      {
        clss = _class;
        args = _args;
      }
    }

    public TabsAdapter(FragmentActivity activity, ViewPager pager)
    {
      super(activity.getSupportFragmentManager());
      mContext = activity;
      mActionBar = activity.getActionBar();
      mViewPager = pager;
      mViewPager.setAdapter(this);
      mViewPager.setOnPageChangeListener(this);
    }

    public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args, android.support.v4.app.Fragment frag)
    {
      TabInfo info = new TabInfo(clss, args);
      tab.setTag(info);
      tab.setTabListener(this);
      mTabs.add(info);
      mFrag.add(frag);
      mActionBar.addTab(tab);
      notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
      // TODO Auto-generated method stub
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
      // TODO Auto-generated method stub
    }

    @Override
    public void onPageSelected(int position)
    {
      mActionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft)
    {
      // TODO Auto-generated method stub
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft)
    {
      Object tag = tab.getTag();
      for (int i = 0; i < mTabs.size(); i++) {
        if (mTabs.get(i) == tag) {
          mViewPager.setCurrentItem(i);
        }
      }
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft)
    {
      // TODO Auto-generated method stub
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position)
    {
      TabInfo info = mTabs.get(position);
      return mFrag.get(position);
    }

    @Override
    public int getCount()
    {
      return mTabs.size();
    }
  }


}
