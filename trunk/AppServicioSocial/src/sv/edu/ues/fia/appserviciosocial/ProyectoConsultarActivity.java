package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProyectoConsultarActivity extends Activity {

	EditText editNombre, editCodigoProyecto, editTipoProyecto, editEncargado,
			editSolicitante;
	EditText editIdProyecto;
	EditText editNumeroProyectos;
	ControlBD helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proyecto_consultar);

		helper = new ControlBD(this);

		editIdProyecto = (EditText) findViewById(R.id.editConsultaProyecto);
		
		editNombre = (EditText) findViewById(R.id.editConsultaNombreProyecto);
		editCodigoProyecto = (EditText) findViewById(R.id.editConsultaCodigoProyecto);
		editTipoProyecto = (EditText) findViewById(R.id.editConsultaTipoProyecto);
		editEncargado = (EditText) findViewById(R.id.editConsultaCodigoEncargadoProyecto);
		editSolicitante = (EditText) findViewById(R.id.editConsultaCodigoSolicitante);
		editNumeroProyectos = (EditText)findViewById(R.id.editConsultaNumeroProyectos);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.proyecto_consultar, menu);
		return true;
	}

	public void consultarProyecto(View v) {
		helper.abrir();
		Proyecto proyecto = helper.consultarProyecto(editIdProyecto.getText().toString());
		helper.cerrar();
		if (proyecto == null)
			Toast.makeText(
					this,
					"Proyecto con ID " + editIdProyecto.getText().toString()
							+ " no encontrado", Toast.LENGTH_LONG).show();
		else {
			editNombre.setText(proyecto.getNombre());
			editCodigoProyecto
					.setText(String.valueOf(proyecto.getIdProyecto()));
			editTipoProyecto.setText(String.valueOf(proyecto
					.getIdTipoProyecto()));
			editEncargado.setText(String.valueOf(proyecto.getIdEncargado()));
			editSolicitante
					.setText(String.valueOf(proyecto.getIdSolicitante()));
			editNumeroProyectos.setText(String.valueOf(proyecto.getNumeroProyectos()));
		}

	}

	public void limpiarTexto(View v) {
		editNombre.setText("");
		editCodigoProyecto.setText("");
		editTipoProyecto.setText("");
		editEncargado.setText("");
		editSolicitante.setText("");
	}

}
