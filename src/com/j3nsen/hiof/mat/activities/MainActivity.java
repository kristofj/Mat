package com.j3nsen.hiof.mat.activities;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.j3nsen.hiof.mat.R;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout _drawerLayout;
	private ListView _drawerList;
	private ActionBarDrawerToggle _drawerToggle;

	private CharSequence _title;
	private CharSequence _drawerTitle;
	private String[] _categoryTitles;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initiateNavDrawer();
	}

	private void initiateNavDrawer() {
		_title = _drawerTitle = getTitle();
		_categoryTitles = getResources().getStringArray(R.array.category_array);
		_drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		_drawerList = (ListView) findViewById(R.id.left_drawer);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		//_drawerToggle = new ActionBarDrawerToggle(this, _drawerLayout, R.drawable)
	}
}
