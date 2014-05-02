package sv.edu.ues.fia.appserviciosocial;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AlumnoMenuActivity extends TabActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_alumno);
		
		//Obtiene el actionbar y habilita el boton de navegacion hacia arriba
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//Crea las cuatro pestañas de menu y monta adentro las activities
		TabHost pestañas = getTabHost();
		TabSpec crear = pestañas.newTabSpec("Crear");
		crear.setIndicator("", getResources().getDrawable(R.drawable.nuevo));
		Intent primerIntent = new Intent(this, AlumnoInsertarActivity.class);
		crear.setContent(primerIntent);
		TabSpec consultar = pestañas.newTabSpec("Consultar");
		consultar.setIndicator("", getResources().getDrawable(R.drawable.consultar));
		Intent sIntent = new Intent(this, AlumnoConsultarActivity.class);
		consultar.setContent(sIntent);
		TabSpec actualizar = pestañas.newTabSpec("Actualizar");
		actualizar.setIndicator("", getResources().getDrawable(R.drawable.actualizar));
		Intent tIntent = new Intent(this, AlumnoActualizarActivity.class);
		actualizar.setContent(tIntent);
		TabSpec eliminar = pestañas.newTabSpec("Eliminar");
		eliminar.setIndicator("", getResources().getDrawable(R.drawable.delete));
		Intent cIntent = new Intent(this, AlumnoEliminarActivity.class);
		eliminar.setContent(cIntent);
		pestañas.addTab(crear);
		pestañas.addTab(consultar);
		pestañas.addTab(actualizar);
		pestañas.addTab(eliminar);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//Regresa con efecto de movimiento al activity con boton de navegacion de actionbar
	    if (item.getItemId() == android.R.id.home) {
	        finish();
	        overridePendingTransition(R.anim.right_in, R.anim.right_out);
	        return true;
	    }
	    return true;
	}

}
