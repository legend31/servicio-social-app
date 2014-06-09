package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProyectoEliminarActivity extends Activity{

	EditText codigoProyecto;
	ControlBD helper;
	String mensaje = "";
	Button eliminar;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proyecto_eliminar);

		codigoProyecto = (EditText) findViewById(R.id.txtProyectoEliminar);
		eliminar = (Button)findViewById(R.id.btnEliminarProyecto);
		helper = new ControlBD(this);
		//sonidos
        soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
        exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
        fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.proyecto_eliminar, menu);
		
		return true;
	}
	
	public void eliminarProyecto(View v){
		mensaje = "";
		Proyecto proyecto = new Proyecto();
		proyecto.setIdProyecto(Integer.valueOf(codigoProyecto.getText().toString()));
		helper.abrir();
		mensaje = helper.eliminar(proyecto);
		helper.cerrar();
		Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
		soundPool.play(exito, 1, 1, 1, 0, 1);
	}
	
	}
