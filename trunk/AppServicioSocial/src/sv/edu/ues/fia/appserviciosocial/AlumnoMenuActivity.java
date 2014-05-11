package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;
import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AlumnoMenuActivity extends TabActivity {
	
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
		setContentView(R.layout.activity_menu_alumno);
		
		//Obtiene el actionbar y habilita el boton de navegacion hacia arriba
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		
		//Crea las cuatro pesta�as de menu y monta adentro las activities
		TabHost pesta�as = getTabHost();
		TabSpec crear = pesta�as.newTabSpec("Crear");
		crear.setIndicator("", getResources().getDrawable(R.drawable.nuevo));
		Intent primerIntent = new Intent(this, AlumnoInsertarActivity.class);
		crear.setContent(primerIntent);
		TabSpec consultar = pesta�as.newTabSpec("Consultar");
		consultar.setIndicator("", getResources().getDrawable(R.drawable.consultar));
		Intent sIntent = new Intent(this, AlumnoConsultarActivity.class);
		consultar.setContent(sIntent);
		TabSpec actualizar = pesta�as.newTabSpec("Actualizar");
		actualizar.setIndicator("", getResources().getDrawable(R.drawable.actualizar));
		Intent tIntent = new Intent(this, AlumnoActualizarActivity.class);
		actualizar.setContent(tIntent);
		TabSpec eliminar = pesta�as.newTabSpec("Eliminar");
		eliminar.setIndicator("", getResources().getDrawable(R.drawable.delete));
		Intent cIntent = new Intent(this, AlumnoEliminarActivity.class);
		eliminar.setContent(cIntent);
		pesta�as.addTab(crear);
		pesta�as.addTab(consultar);
		pesta�as.addTab(actualizar);
		pesta�as.addTab(eliminar);
		
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
        //Perfil	      
        NavItms.add(new Item_objct(titulos[0], NavIcons.getResourceId(0, -1)));
        //Favoritos
        NavItms.add(new Item_objct(titulos[1], NavIcons.getResourceId(1, -1)));
        //Eventos
        NavItms.add(new Item_objct(titulos[2], NavIcons.getResourceId(2, -1)));
        //Lugares
        NavItms.add(new Item_objct(titulos[3], NavIcons.getResourceId(3, -1)));
        //Etiquetas
        NavItms.add(new Item_objct(titulos[4], NavIcons.getResourceId(4, -1)));
        //Configuracion
        NavItms.add(new Item_objct(titulos[5], NavIcons.getResourceId(5, -1)));
        //Share
        NavItms.add(new Item_objct(titulos[6], NavIcons.getResourceId(6, -1)));
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
        
        // Establecemos que mDrawerToggle declarado anteriormente sea el DrawerListener
        NavDrawerLayout.setDrawerListener(mDrawerToggle);
        //Establecemos que el ActionBar muestre el Boton Home
        getActionBar().setDisplayHomeAsUpEnabled(true);

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

}
