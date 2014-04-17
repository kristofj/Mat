package com.j3nsen.hiof.mat.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j3nsen.hiof.mat.R;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout _drawerLayout;
	private ListView _drawerList;
	private ActionBarDrawerToggle _drawerToggle;

	private CharSequence _title;
	private CharSequence _drawerTitle;
	private String[] _navDrawerTitles;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initNavDrawer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	private void initNavDrawer() {
		_title = _drawerTitle = getTitle();
		_navDrawerTitles = getResources().getStringArray(R.array.navigation_array);
		_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		_drawerList = (ListView) findViewById(R.id.left_drawer);
		
		_drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		_drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, _navDrawerTitles));
		_drawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		_drawerToggle = new ActionBarDrawerToggle(
				this,
				_drawerLayout,
				R.drawable.ic_drawer,
				R.string.drawer_open,
				R.string.drawer_close);
		_drawerLayout.setDrawerListener(_drawerToggle);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(_drawerToggle.onOptionsItemSelected(item))
			return true;
		
		switch(item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);
		}	
	}
	
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		_drawerToggle.syncState();
	}

	
	private void navDrawerSelectedItem(int position) {
		_drawerLayout.closeDrawer(_drawerList);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			navDrawerSelectedItem(position);
		}
	}
}
