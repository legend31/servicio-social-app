package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AsignacionProyectoEliminarActivity extends Activity {
	
	EditText txtBusqueda;
	RadioGroup radioGrupo;
	ControlBD auxiliar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignacion_proyecto_eliminar);
		txtBusqueda = (EditText) findViewById(R.id.txtBusqueda);
		radioGrupo = (RadioGroup) findViewById(R.id.radioGroup1);
		auxiliar = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignacion_proyecto_eliminar, menu);
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
	
	public void eliminarAsignacionProyecto(View v)
	{
		String info="";
		String texto = txtBusqueda.getText().toString();
		if(texto == null || texto == "")
		{
			switch(radioGrupo.getCheckedRadioButtonId())
			{
			case R.id.rbtAlumno:
				info = "Carnet inválido";
			break;
			case R.id.rbtProyecto:
				info = "Proyecto inválido";
			break;
			}
			Toast.makeText(this, info, Toast.LENGTH_LONG).show();
			return;
		}
		AsignacionProyecto asignacion = new AsignacionProyecto();
		auxiliar.abrir();
		switch(radioGrupo.getCheckedRadioButtonId())
		{
		case R.id.rbtAlumno:
			asignacion.setCarnet(texto);
			auxiliar.eliminar(asignacion, 1);
		case R.id.rbtProyecto:
			asignacion.setIdProyecto(texto);
			auxiliar.eliminar(asignacion, 2);
		break;
		}
		auxiliar.cerrar();
		Toast.makeText(this, "Eliminación correcta", Toast.LENGTH_SHORT).show();
	}
}
