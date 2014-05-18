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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class InstitucionConsultarActivity extends Activity {
	
	private ControlBD auxiliar;
	private EditText txtNombre, txtNit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_institucion_consultar);
		txtNombre = (EditText) findViewById(R.id.editVNombreInstitucion);
		txtNit= (EditText) findViewById(R.id.editNitInstitucion);
		auxiliar = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.institucion_consultar, menu);
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

public void consultarInstitucion(View v) {
		txtNombre.setText("");
		String nit = txtNit.getText().toString();
		//Validando
		if(nit == null || nit.trim() == "" | nit.length() != 14){
			Toast.makeText(this, "NIT inválido", Toast.LENGTH_LONG).show();
			return;			
		}
		
		auxiliar.abrir();
		Institucion institucion= auxiliar.consultarInstitucion(nit);
		auxiliar.cerrar();
		
		if(institucion == null)	{
			/*lblDatos.setVisibility(View.INVISIBLE);
			gdvTabla.setVisibility(View.INVISIBLE);
			*/
			txtNombre.setText("");
			Toast.makeText(this, "Institucion con nit " + nit +
					" no encontrado", Toast.LENGTH_LONG).show();
			return;
		}else
			txtNombre.setText(institucion.getNombre());
		
		
	}


}
