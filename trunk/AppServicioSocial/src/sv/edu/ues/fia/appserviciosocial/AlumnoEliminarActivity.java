package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlumnoEliminarActivity extends Activity {
	
	ControlBD auxiliar;
	EditText txtCarnet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno_eliminar);
		auxiliar = new ControlBD(this);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumno_eliminar, menu);
		return true;
	}
	
	public void eliminarAlumno(View v)
	{
		String carnet = txtCarnet.getText().toString();
		if(carnet == null || carnet.trim() == "" || carnet.length() != 7)
		{
			Toast.makeText(this, "Carnet inválido", Toast.LENGTH_LONG).show();
			return;
		}
		Alumno alumno=new Alumno();
		alumno.setCarnet(carnet);
		auxiliar.abrir();
		auxiliar.eliminar(alumno);
		auxiliar.cerrar();
		Toast.makeText(this, "Eliminación correcta", Toast.LENGTH_SHORT).show();
	}

}
