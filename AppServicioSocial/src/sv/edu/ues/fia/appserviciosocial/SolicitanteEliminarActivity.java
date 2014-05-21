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

public class SolicitanteEliminarActivity extends Activity {
	
	private EditText txtIdSolicitante;
	private ControlBD auxiliar;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitante_eliminar);
		txtIdSolicitante = (EditText) findViewById(R.id.editIdSolicitanteEliminar);
		auxiliar = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solicitanten_eliminar, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	public void eliminarSolicitante(View v){
		String id = txtIdSolicitante.getText().toString();
		if(id == null || id.matches("") )	{
			Toast.makeText(this, "id inválido", Toast.LENGTH_LONG).show();
			return;
		}		
		
		auxiliar.abrir();
		Solicitante solicitante = auxiliar.consultarSolicitante(id);
		

		if (solicitante != null){			
			auxiliar.eliminar(solicitante);
			Toast.makeText(this, "Eliminación correcta", Toast.LENGTH_SHORT).show();
		}else
			Toast.makeText(this, "No existe solicitante con ID " + id, Toast.LENGTH_SHORT).show();
		
		auxiliar.cerrar();
		
	}
}
