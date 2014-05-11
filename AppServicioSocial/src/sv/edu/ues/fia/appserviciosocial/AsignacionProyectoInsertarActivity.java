package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AsignacionProyectoInsertarActivity extends Activity {

	EditText txtCarnet;
	EditText txtProyecto;
	ControlBD auxiliar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignacion_proyecto_insertar);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
		txtProyecto = (EditText) findViewById(R.id.txtProyecto);
		auxiliar = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignacion_proyecto_insertar, menu);
		return true;
	}

	public void insertarAsignacionProyecto(View v) {
		String carnet = txtCarnet.getText().toString();
		String proyecto = txtProyecto.getText().toString();
		String info = "";
		// Validando
		if (carnet == null || carnet.trim() == "") {
			info = "Carnet inválido";
		}
		if (proyecto == null || proyecto.trim() == "") {
			info = "Nombre inválido";
		}
		// Avisando errores
		if (info != "") {
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}
		// Creando inserción
		AsignacionProyecto asignacion = new AsignacionProyecto();
		asignacion.setCarnet(carnet);
		asignacion.setIdProyecto(proyecto);
		auxiliar.abrir();
		String regInsertados = auxiliar.insertar(asignacion);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}
}
