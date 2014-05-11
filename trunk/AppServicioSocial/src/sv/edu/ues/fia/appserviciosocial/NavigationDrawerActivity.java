package sv.edu.ues.fia.appserviciosocial;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

public class NavigationDrawerActivity extends Activity {

	private String activities[] = { "AlumnoMenuActivity",
			"ProyectoMenuActivity" };
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mTablasTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_navigation_drawer);

        mTitle = mDrawerTitle = getTitle();
       // mTablasTitles = getResources().getStringArray(R.array.tablas);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
/*        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mTablasTitles));*/
        
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, mTablasTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
       
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//getMenuInflater().inflate(R.menu.proyecto_insertar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       return true;
    }

    
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
		String nombreValue = activities[position];
		try {
			Class<?> clase = Class.forName("sv.edu.ues.fia.appserviciosocial."+nombreValue);
			Intent intent = new Intent(this, clase);
			this.startActivity(intent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mTablasTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);

	}

   
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
}	
	/*ListView drawer = (ListView) findViewById(R.id.left_drawer);
	DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	String[] tablas = getResources().getStringArray(R.array.tablas);
	private CharSequence titulo;
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence drawerTitle;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation_drawer);
		
		 titulo = drawerTitle = getTitle();
		 drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		 drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) 
		 {
			 
	            public void onDrawerClosed(View view) {
	                super.onDrawerClosed(view);
	                getActionBar().setTitle(titulo);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }

	            
	            public void onDrawerOpened(View drawerView) {
	                super.onDrawerOpened(drawerView);
	                getActionBar().setTitle(drawerTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        }; 
	        
	        drawerLayout.setDrawerListener(drawerToggle);
		 
			 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, tablas);
		drawer.setAdapter(adapter);
		drawer.setOnItemClickListener(new DrawerItemClickListener());
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this,                 //host Activity 
                drawerLayout,         // DrawerLayout object 
                R.drawable.ic_drawer,  //nav drawer icon to replace 'Up' caret 
                R.string.drawer_open,  // "open drawer" description 
                R.string.drawer_close  // "close drawer" description 
                ) {

            // Called when a drawer has settled in a completely closed state. 
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(titulo);
            }

            // Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(drawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true); 
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

		
	
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawer);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener{
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id)
		{
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		String nombreValue = activities[position];
		try {
			Class<?> clase = Class.forName("sv.edu.ues.fia.appserviciosocial."+nombreValue);
			Intent intent = new Intent(NavigationDrawerActivity.this, clase);
			this.startActivity(intent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Highlight the selected item, update the title, and close the drawer
		drawer.setItemChecked(position, true);
		setTitle(tablas[position]);
		drawerLayout.closeDrawer(drawer);

	}

	@Override
	public void setTitle(CharSequence title) {
		titulo = title;
		getActionBar().setTitle(titulo);
	}
}
*/