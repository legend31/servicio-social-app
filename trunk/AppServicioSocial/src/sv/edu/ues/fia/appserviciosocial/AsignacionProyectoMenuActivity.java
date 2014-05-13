package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AsignacionProyectoMenuActivity extends TabActivity {
	
	private String[] titulos;
    private DrawerLayout NavDrawerLayout;
    private ListView NavList;
    private ArrayList<Item_objct> NavItms;
    private TypedArray NavIcons;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    NavigationAdapter NavAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_asignacion_proyecto);
			
		//Crea las cuatro pesta�as de menu y monta adentro las activities
			Resources res = getResources();
			TabHost pesta�as=getTabHost();
			TabHost.TabSpec spec;
			
			spec = pesta�as.newTabSpec("Insertar");
			spec.setIndicator("",res.getDrawable(R.drawable.nuevo));
			Intent insertarIntent = new Intent(this,AsignacionProyectoInsertarActivity.class);
			spec.setContent(insertarIntent);
			pesta�as.addTab(spec);
			
			spec = pesta�as.newTabSpec("Consultar");
			spec.setIndicator("",res.getDrawable(R.drawable.consultar));
			Intent consultarIntent = new Intent(this,AsignacionProyectoConsultarActivity.class);
			spec.setContent(consultarIntent);
			pesta�as.addTab(spec);
			
			spec = pesta�as.newTabSpec("Actualizar");
			spec.setIndicator("",res.getDrawable(R.drawable.actualizar));
			Intent actualizarIntent = new Intent(this,AsignacionProyectoActualizarActivity.class);
			spec.setContent(actualizarIntent);
			pesta�as.addTab(spec);
			
			spec = pesta�as.newTabSpec("Eliminar");
			spec.setIndicator("",res.getDrawable(R.drawable.delete));
			Intent eliminarIntent = new Intent(this,AsignacionProyectoEliminarActivity.class);
			spec.setContent(eliminarIntent);
			pesta�as.addTab(spec);
			
			//Drawer Layout
			this.NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			//Lista
	        NavList = (ListView) findViewById(R.id.lista);
	        //Declaramos el header el cual sera el layout de header.xml
	        View header = getLayoutInflater().inflate(R.layout.header, null);
	        //Establecemos header
	        NavList.addHeaderView(header);
			//Tomamos listado  de imgs desde drawable
	        NavIcons = getResources().obtainTypedArray(R.array.navigation_iconos);			
			//Tomamos listado  de titulos desde el string-array de los recursos @string/nav_options
	        titulos = getResources().getStringArray(R.array.nav_options);
	        //Listado de titulos de barra de navegacion
	        NavItms = new ArrayList<Item_objct>();
	        //Agregamos objetos Item_objct al array
	        NavItms.add(new Item_objct(titulos[0], NavIcons.getResourceId(0, -1)));
	        NavItms.add(new Item_objct(titulos[1], NavIcons.getResourceId(1, -1)));
	        NavItms.add(new Item_objct(titulos[2], NavIcons.getResourceId(2, -1)));
	        NavItms.add(new Item_objct(titulos[3], NavIcons.getResourceId(3, -1)));
	        NavItms.add(new Item_objct(titulos[4], NavIcons.getResourceId(4, -1)));
	        NavItms.add(new Item_objct(titulos[5], NavIcons.getResourceId(5, -1)));
	        NavItms.add(new Item_objct(titulos[6], NavIcons.getResourceId(6, -1)));
	        NavItms.add(new Item_objct(titulos[7], NavIcons.getResourceId(7, -1)));
	        NavItms.add(new Item_objct(titulos[8], NavIcons.getResourceId(8, -1)));
	        NavItms.add(new Item_objct(titulos[9], NavIcons.getResourceId(9, -1)));
	      //Declaramos y seteamos nuestrp adaptador al cual le pasamos el array con los titulos	       
	        NavAdapter= new NavigationAdapter(this,NavItms);
	        NavList.setAdapter(NavAdapter);	
	        //Siempre vamos a mostrar el mismo titulo
	        
	      //Siempre vamos a mostrar el mismo titulo
	        mTitle = mDrawerTitle = getTitle();
	        
	        //Declaramos el mDrawerToggle y las imgs a utilizar
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                NavDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* Icono de navegacion*/
	                R.string.title_activity_menu_alumno,  /* "open drawer" description */
	                R.string.hello_world  /* "close drawer" description */
	                ) {

	            /** Called when a drawer has settled in a completely closed state. */
	            public void onDrawerClosed(View view) {
	            	//Log.e("Cerrado completo", "!!");
	            }

	            /** Called when a drawer has settled in a completely open state. */
	            public void onDrawerOpened(View drawerView) {
	              //  Log.e("Apertura completa", "!!");
	            }
	        };	        
	        // 
	        // Establecemos que mDrawerToggle declarado anteriormente sea el DrawerListener
	        //mDrawerToggle.setDrawerIndicatorEnabled(true);
	        NavDrawerLayout.setDrawerListener(mDrawerToggle);
	        //Establecemos que el ActionBar muestre el Boton Home
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	        //Establecemos la accion al clickear sobre cualquier item del menu.
	        //De la misma forma que hariamos en una app comun con un listview.
	        NavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
	            	abrirActivity(position);
	            	NavDrawerLayout.closeDrawer(NavList);
	            }
	        });
	        
	}

	private void abrirActivity(int posicion){
		switch (posicion) {
		case 1:
			startActivity(new Intent(this, AlumnoMenuActivity.class));
		break;
		case 2:
			startActivity(new Intent(this, AsignacionProyectoMenuActivity.class));
		break;
		case 3:
			startActivity(new Intent(this, AlumnoMenuActivity.class));
		break;
		case 4:
			startActivity(new Intent(this, AlumnoMenuActivity.class));
		break;
		case 5:
			startActivity(new Intent(this, AlumnoMenuActivity.class));
		break;
		case 6:
			startActivity(new Intent(this, AlumnoMenuActivity.class));
		break;
		default:
			break;
		}
	}
	
	@Override
	 protected void onPostCreate(Bundle savedInstanceState) {
	 super.onPostCreate(savedInstanceState);
	 mDrawerToggle.syncState();
	 }
	
	@Override
    public void onConfigurationChanged(Configuration newConfig) {
        // Called by the system when the device configuration changes while your
        // activity is running
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
