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

public class InstitucionEliminarActivity extends Activity {

	private ControlBD auxiliar;
	private EditText txtNit;
	//sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_institucion_eliminar);
		txtNit = (EditText) findViewById(R.id.editNitInstitucionEliminar);
		auxiliar = new ControlBD(this);

		//sonidos
	         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
	         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
	         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);


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
			soundPool.play(exito, 1, 1, 1, 0, 1);
		}else
			Toast.makeText(this, "No existe institucion con NIT " + nit, Toast.LENGTH_SHORT).show();
		soundPool.play(fracaso, 1, 1, 1, 0, 1);

		auxiliar.cerrar();
		
	}


}
