package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SolicitanteEliminarActivity extends Activity {
	
	private EditText txtIdSolicitante;
	private ControlBD auxiliar;
	//sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;
	@Override

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitante_eliminar);
		txtIdSolicitante = (EditText) findViewById(R.id.editIdSolicitanteEliminar);
		auxiliar = new ControlBD(this);
		//sonidos
        soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
        exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
        fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

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
			soundPool.play(exito, 1, 1, 1, 0, 1);
			Toast.makeText(this, "Eliminación correcta", Toast.LENGTH_SHORT).show();
			soundPool.play(exito, 1, 1, 1, 0, 1);
		}else
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			Toast.makeText(this, "No existe solicitante con ID " + id, Toast.LENGTH_SHORT).show();
		
		auxiliar.cerrar();
		
	}
}
