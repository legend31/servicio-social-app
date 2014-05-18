package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class InstitucionInsertarActivity extends Activity {
	private EditText txtNombreInstitucion,
					 txtNitInstitucion;
	private ControlBD auxiliar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_institucion_insertar);
		auxiliar = new ControlBD(this);
		txtNombreInstitucion = (EditText) findViewById(R.id.editNombreInstitucion);
		txtNitInstitucion = (EditText) findViewById(R.id.editNitInstitucion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.institucion_insertar, menu);
		return true;
	}

		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public void insertarInstitucion(View v)	{		
		String nombre=txtNombreInstitucion.getText().toString();		
		String nit = txtNitInstitucion.getText().toString();
		String info ="";
		//Validando		
		if(nombre == null || nombre.trim() == "")		{
			info = "Nombre inválido";
		}
		
		if(nit == null || nit.trim() == "" || nit.length() != 14) {
			info = "NIT inválido";
		}
		//Avisando errores
		if(info != "")		{
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}
		//Creando inserción
		Institucion institucion=new Institucion(nombre,nit);		
		auxiliar.abrir();
		String regInsertados=auxiliar.insertar(institucion);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}
	

}
