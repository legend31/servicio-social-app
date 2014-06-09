package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlumnoEliminarActivity extends Activity {
	
	ControlBD auxiliar;
	EditText txtCarnet;
	//sonidos
		SoundPool soundPool;
		int exito;
	int fracaso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno_eliminar);
		auxiliar = new ControlBD(this);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
		//sonidos
        soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
        exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
       fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

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
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;
		}
		Alumno alumno=new Alumno();
		alumno.setCarnet(carnet);
		auxiliar.abrir();
		auxiliar.eliminar(alumno);
		auxiliar.cerrar();
		Toast.makeText(this, "Eliminación correcta", Toast.LENGTH_SHORT).show();
		soundPool.play(exito, 1, 1, 1, 0, 1);
	}

}
