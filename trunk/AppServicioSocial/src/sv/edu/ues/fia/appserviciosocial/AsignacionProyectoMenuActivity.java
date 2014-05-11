package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AsignacionProyectoMenuActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_asignacion_proyecto);
		
		//Obtiene el actionbar y habilita el boton de navegacion hacia arriba
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
				
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
