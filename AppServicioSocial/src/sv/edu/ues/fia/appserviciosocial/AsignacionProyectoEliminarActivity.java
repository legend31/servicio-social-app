package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AsignacionProyectoEliminarActivity extends Activity {
	
	EditText txtBusqueda;
	EditText txtResultado;
	EditText txtFecha;
	Calendar calendario;
	ControlBD auxiliar;
	RadioGroup radioGrupo;
	Button btnAdelante;
	Button btnAtras;
	int tamañoArray = 0, index = 0, tipo = 0;
	ArrayList<AsignacionProyecto> asignaciones;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignacion_proyecto_eliminar);
		txtBusqueda = (EditText) findViewById(R.id.txtBusqueda);
		txtResultado = (EditText) findViewById(R.id.txtResultado);
		txtFecha = (EditText) findViewById(R.id.txtFecha);
		radioGrupo = (RadioGroup) findViewById(R.id.radioGroup1);
		btnAdelante = (Button) findViewById(R.id.btnAdelante);
		btnAdelante.setVisibility(View.INVISIBLE);
		btnAtras = (Button) findViewById(R.id.btnAtras);
		btnAtras.setVisibility(View.INVISIBLE);
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
	
	public void consultarAsignacionProyecto(View v) {

		String info = "";
		String texto = txtBusqueda.getText().toString().trim();
		if (texto == null || texto == "" || texto.length() == 0) {
			switch (radioGrupo.getCheckedRadioButtonId()) {
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
		auxiliar.abrir();
		switch (radioGrupo.getCheckedRadioButtonId()) {
		case R.id.rbtAlumno:
			tipo = 1;
			break;
		case R.id.rbtProyecto:
			tipo = 2;
			break;
		}
		asignaciones = auxiliar.consultarAsignacionProyecto(texto, tipo);
		auxiliar.cerrar();
		if (asignaciones == null) {
			Toast.makeText(this, "Asignación de proyecto no encontrado",
					Toast.LENGTH_LONG).show();
			return;
		} else {
			index = 0;
			tamañoArray = asignaciones.size();
			if (tamañoArray > 0) {
				btnAdelante.setVisibility(View.VISIBLE);
			}
			mostrarDatos();
		}

	}
	
	public void mostrarDatos() {
		if (index >= 0 && index <= tamañoArray) {
			if (tipo == 1) {
				txtResultado.setText("Proyecto: "
						+ asignaciones.get(index).getIdProyecto());
			} else if (tipo == 2) {
				txtResultado.setText("Alumno: "
						+ asignaciones.get(index).getCarnet());
			}
			String fechaAux = asignaciones.get(index).getFecha();
			txtFecha.setText(fechaAux.substring(8)+"/"+fechaAux.substring(5, 7)+"/"+fechaAux.substring(0, 4));
		}
		if (index == tamañoArray - 1) {
			btnAdelante.setVisibility(View.INVISIBLE);
		} else {
			btnAdelante.setVisibility(View.VISIBLE);
		}
		if (index == 0) {
			btnAtras.setVisibility(View.INVISIBLE);
		} else {
			btnAtras.setVisibility(View.VISIBLE);
		}
	}

	public void adelante(View v) {
		index++;
		mostrarDatos();
	}

	public void atras(View v) {
		index--;
		mostrarDatos();
	}

	
	public void eliminarAsignacionProyecto(View v) {
		String info = "";
		String texto = txtBusqueda.getText().toString().trim();
		if (texto == null || texto == "" || texto.length() == 0) {
			switch (radioGrupo.getCheckedRadioButtonId()) {
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
		if(tipo == 1)
		{
			asignacion.setIdProyecto(txtResultado.getText().toString().substring(10));
			asignacion.setCarnet(texto);
		}
		else if(tipo == 2)
		{
			asignacion.setCarnet(txtResultado.getText().toString().substring(8));
			asignacion.setIdProyecto(texto);
		}
		auxiliar.abrir();
		String regInsertados=auxiliar.eliminar(asignacion);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}
}
