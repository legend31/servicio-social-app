package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

public class AlumnoActualizarActivity extends Activity {
	
	private TableLayout tablaDeDatos;
	private EditText txtCarnet;
	private EditText txtNombre;
	private EditText txtTelefono;
	private EditText txtDui;
	private EditText txtNit;
	private EditText txtEmail;
	private Button btnActualizar;
	private ControlBD auxiliar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno_actualizar);
		tablaDeDatos = (TableLayout) findViewById(R.id.TablaDeDatos);
		tablaDeDatos.setVisibility(View.INVISIBLE);
		btnActualizar = (Button) findViewById(R.id.btnActualizar);
		btnActualizar.setVisibility(View.INVISIBLE);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
		txtNombre = (EditText) findViewById(R.id.txtNombre);
		txtTelefono = (EditText) findViewById(R.id.txtTelefono);
		txtDui = (EditText) findViewById(R.id.txtDui);
		txtNit = (EditText) findViewById(R.id.txtNit);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		auxiliar = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumno_actualizar, menu);
		return true;
	}
	
	public void consultarAlumno(View v)
	{
		String carnet = txtCarnet.getText().toString();
		//Validando
		if(carnet == null || carnet.trim() == "" || carnet.length() != 7)
		{
			Toast.makeText(this, "Carnet inválido", Toast.LENGTH_LONG).show();
			return;
		}
		auxiliar.abrir();
		Alumno alumno = auxiliar.consultarAlumno(carnet);
		auxiliar.cerrar();
		if(alumno == null)
		{
			tablaDeDatos.setVisibility(View.INVISIBLE);
			btnActualizar.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "Alumno con carnet " +carnet +" no encontrado", Toast.LENGTH_LONG).show();
			return;
		}
		else{
			tablaDeDatos.setVisibility(View.VISIBLE);
			btnActualizar.setVisibility(View.VISIBLE);
			txtNombre.setText(alumno.getNombre());
			txtTelefono.setText(alumno.getTelefono());
			txtDui.setText(alumno.getDui());
			txtNit.setText(alumno.getNit());
			txtEmail.setText(alumno.getEmail());
		}
	}
	
	public void actualizarAlumno(View v)
	{
		String carnet=txtCarnet.getText().toString();
		String nombre=txtNombre.getText().toString();
		String telefono = txtTelefono.getText().toString();
		String dui = txtDui.getText().toString();
		String nit = txtNit.getText().toString();
		String email = txtEmail.getText().toString();
		String info = "";
		//Validando
		if(carnet == null || carnet.trim() == "" || carnet.length() != 7)
		{
			info = "Carnet inválido";
		}
		if(nombre == null || nombre.trim() == "")
		{
			info = "Nombre inválido";
		}
		if(telefono == null || telefono.trim() == "" || telefono.length() != 8)
		{
			info = "Teléfono inválido";
		}
		if(dui == null || dui.trim() == "" || dui.length() != 9)
		{
			info = "DUI inválido";
		}
		if(nit == null || nit.trim() == "" || nit.length() != 14)
		{
			info = "NIT inválido";
		}
		if(email == null || email.trim() == "" || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
		{
			info = "E-mail inválido";
		}
		//Avisando errores
		if(info != "")
		{
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}
		//Creando inserción
		Alumno alumno=new Alumno();
		alumno.setCarnet(carnet);
		alumno.setNombre(nombre);
		alumno.setTelefono(telefono);
		alumno.setDui(dui);
		alumno.setNit(nit);
		alumno.setEmail(email);
		auxiliar.abrir();
		String regInsertados=auxiliar.actualizar(alumno);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}

}
