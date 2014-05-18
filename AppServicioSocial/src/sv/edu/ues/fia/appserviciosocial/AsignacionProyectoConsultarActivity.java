package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AsignacionProyectoConsultarActivity extends Activity {
	
	EditText txtBusqueda;
	GridView gdvTabla;
	RadioGroup radioGrupo;
	ControlBD auxiliar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignacion_proyecto_consultar);
		txtBusqueda = (EditText) findViewById(R.id.txtBusqueda);
		gdvTabla = (GridView) findViewById(R.id.gdvTabla);
		radioGrupo = (RadioGroup) findViewById(R.id.radioGroup1);
		auxiliar = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignacion_proyecto_consultar, menu);
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
		
		String info="";
		String texto = txtBusqueda.getText().toString().trim();
		Log.i("Hola", "valor de texto ->"+texto+"<-");
		if(texto == null || texto == "" || texto.length() == 0)
		{
			Log.i("Hola", "entre al if");
			switch(radioGrupo.getCheckedRadioButtonId())
			{
			case R.id.rbtAlumno:
				Log.i("Hola", "alumno");
				info = "Carnet inválido";
			break;
			case R.id.rbtProyecto:
				Log.i("Hola", "proyecto");
				info = "Proyecto inválido";
			break;
			}
			Toast.makeText(this, info, Toast.LENGTH_LONG).show();
			return;
		}//
		auxiliar.abrir();
		int tipo = 0;
		switch(radioGrupo.getCheckedRadioButtonId())
		{
		case R.id.rbtAlumno:
			tipo = 1;
		break;
		case R.id.rbtProyecto:
			tipo = 2;
		break;
		}
		Log.i("Hola", "antes de consultar");
		ArrayList<AsignacionProyecto> asignaciones = auxiliar.consultarAsignacionProyecto(texto, tipo);
		auxiliar.cerrar();
		if(asignaciones == null)
		{
			gdvTabla.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "Asignación de proyecto no encontrado", Toast.LENGTH_LONG).show();
			return;
		}
		else{
			List<String> datos= new ArrayList<String>();
			if(tipo ==1)
			{
				datos.add("Proyectos");
				datos.add("Fecha");
				for (AsignacionProyecto asignacion : asignaciones) {
					datos.add(asignacion.getIdProyecto());
					datos.add(asignacion.getFecha());
				}
			}
			else if(tipo == 2){
				datos.add("Alumnos");
				datos.add("Fecha");
				for (AsignacionProyecto asignacion : asignaciones) {
					datos.add(asignacion.getCarnet());
					datos.add(asignacion.getFecha());
				}
			}
			//Llenando tabla
			ArrayAdapter<String> adaptador = 
			        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
			gdvTabla.setAdapter(adaptador);
			gdvTabla.setVisibility(View.VISIBLE);
		}
		
	}

}
