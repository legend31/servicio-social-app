package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AsignacionProyectoActualizarActivity extends Activity {
	
	EditText txtBusqueda;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignacion_proyecto_actualizar);
		txtBusqueda = (EditText) findViewById(R.id.txtBusqueda);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignacion_proyecto_actualizar, menu);
		return true;
	}
	
	public void seleccion(View v)
	{
		switch(v.getId())
		{
		case R.id.rbtAlumno:
			txtBusqueda.setHint("Alumno");
		break;
		case R.id.rbtProyecto:
			txtBusqueda.setHint("Proyecto");
		break;
		}
	}

}
