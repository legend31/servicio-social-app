package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AlumnoInsertarActivity extends Activity {
	
	
	ControlBD auxiliar;
	private EditText txtCarnet;
	private EditText txtNombre;
	private EditText txtTelefono;
	private EditText txtDui;
	private EditText txtNit;
	private EditText txtEmail;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno_insertar);
		auxiliar = new ControlBD(this);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
		txtNombre = (EditText) findViewById(R.id.txtNombre);
		txtTelefono = (EditText) findViewById(R.id.txtTelefono);
		txtDui = (EditText) findViewById(R.id.txtDui);
		txtNit = (EditText) findViewById(R.id.txtNit);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumno_insertar, menu);
		return true;
	}
	
	public void insertarAlumno(View v)
	{
		String carnet=txtCarnet.getText().toString();
		String nombre=txtNombre.getText().toString();
		String telefono = txtTelefono.getText().toString();
		String dui = txtDui.getText().toString();
		String nit = txtNit.getText().toString();
		String email = txtEmail.getText().toString();
		String info = "";
		//Validando
		if(carnet == null || carnet.trim() == "")
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
		try{
			Integer.parseInt(telefono);
		}catch(NumberFormatException ex){
			info = "Teléfono inválido";
		}
		if(dui == null || dui.trim() == "" || dui.length() != 9)
		{
			info = "DUI inválido";
		}
		try{
			Integer.parseInt(dui);
		}catch(NumberFormatException ex){
			info = "DUI inválido";
		}
		if(nit == null || nit.trim() == "" || nit.length() != 14)
		{
			info = "NIT inválido";
		}
		try{
			Integer.parseInt(nit);
		}catch(NumberFormatException ex){
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
		String regInsertados=auxiliar.insertar(alumno);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}

}
