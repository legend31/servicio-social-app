package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class AlumnoConsultarActivity extends Activity {

	ControlBD auxiliar;
	EditText txtCarnet;
	GridView gdvTabla;
	TextView lblDatos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno_consultar);
		auxiliar = new ControlBD(this);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
		gdvTabla = (GridView) findViewById(R.id.gdvTabla);
		lblDatos = (TextView) findViewById(R.id.lblDatos);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumno_consultar, menu);
		return true;
	}
	
	public void consultarAlumno(View v) {
		
		String carnet = txtCarnet.getText().toString();
		String info;
		//Validando
		if(carnet == null || carnet.trim() == "")
		{
			info = "Carnet inválido";
			Toast.makeText(this, info, Toast.LENGTH_LONG).show();
			return;
			
		}
		auxiliar.abrir();
		Alumno alumno = auxiliar.consultarAlumno(carnet);
		auxiliar.cerrar();
		if(alumno == null)
		{
			Toast.makeText(this, "Alumno con carnet " +carnet +" no encontrado", Toast.LENGTH_LONG).show();
			return;
		}
		else{
			String [] datos = new String[10];
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
			gdvTabla.setAdapter(adaptador);
		}
		
	}

}
