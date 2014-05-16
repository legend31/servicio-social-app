package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
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
		/*
		Object [] datos = new Object[10];
		datos[0] = "Nombre:";
		datos[1] = "Rodrigo Valle";
		datos[2] = "Teléfono:";
		datos[3] = "22724569";
		datos[4] = "DUI:";
		datos[5] = "032568402";
		datos[6] = "NIT:";
		datos[7] = "06142906937897";
		datos[8] = "E-mail:";
		datos[9] = "rodrigoahv@yahoo.es";
		String info="";
		//Llenando tabla
		ArrayAdapter<Object> adaptador =
		        new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, datos);
		gdvTabla.setAdapter(adaptador);
		*/
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
		AsignacionProyecto asignacion = auxiliar.consultarAsignacionProyecto(texto, tipo);
		auxiliar.cerrar();
		if(asignacion == null)
		{
			gdvTabla.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "Asignación de proyecto no encontrado", Toast.LENGTH_LONG).show();
			return;
		}
		else{
			/*String [] datos = new String[10];
			datos[0] = "Nombre:";
			datos[1] = alumno.getNombre();
			datos[2] = "Teléfono:";
			datos[3] = alumno.getTelefono();
			datos[4] = "DUI:";
			datos[5] = alumno.getDui();
			datos[6] = "NIT:";
			datos[7] = alumno.getNit();
			datos[8] = "E-mail:";
			datos[9] = alumno.getEmail();
			
			//Llenando tabla
			lblDatos.setVisibility(View.VISIBLE);
			ArrayAdapter<String> adaptador =
			        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
			gdvTabla.setAdapter(adaptador);*/
		}
		
	}

}
