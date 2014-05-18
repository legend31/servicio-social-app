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

public class InstitucionEliminarActivity extends Activity {

	private ControlBD auxiliar;
	private EditText txtNit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_institucion_eliminar);
		txtNit = (EditText) findViewById(R.id.editNitInstitucionEliminar);
		auxiliar = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.institucion_eliminar, menu);
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

	
	public void eliminarInstitucion(View v){
		String nit = txtNit.getText().toString();
		if(nit == null || nit.trim() == "" || nit.length() != 14)	{
			Toast.makeText(this, "NIT inválido", Toast.LENGTH_LONG).show();
			return;
		}		
		
		auxiliar.abrir();
		Institucion institucion = auxiliar.consultarInstitucion(nit);

		if (institucion != null){			
			auxiliar.eliminar(institucion);
			Toast.makeText(this, "Eliminación correcta", Toast.LENGTH_SHORT).show();
		}else
			Toast.makeText(this, "No existe institucion con NIT " + nit, Toast.LENGTH_SHORT).show();
		
		auxiliar.cerrar();
		
	}


}
