package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AccesoActivity extends Activity {
	
	EditText usuario;
	EditText password;
	ControlBD auxiliar;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;
		 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Codigo para ganar tiempo en el acceso en las pruebas
		//**************************************
		Intent menu = new Intent(this, PantallaInicialActivity.class);
		menu.putExtra("tipoUsuario", 1);
		startActivity(menu);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
        //**************************************
        
		setContentView(R.layout.activity_acceso);
		usuario = (EditText) findViewById(R.id.txfUsuario);
		password = (EditText) findViewById(R.id.txfPassword);
		auxiliar = new ControlBD(this);

		//sonidos
	         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
	         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
	         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
	}
	
	public void ingresar(View v)
	{
		/*auxiliar.abrir();
		String tipo = auxiliar.verificarUsuario(usuario.getText().toString(), password.getText().toString());
		auxiliar.cerrar();
		if(tipo == null)
		{
			Toast.makeText(this, "Usuario inválido", Toast.LENGTH_SHORT).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
		}
		else
		{
			soundPool.play(exito, 1, 1, 1, 0, 1);
			int tipoUsuario = Integer.parseInt(tipo);
			//Intent menu = new Intent(this, MenuTablasActivity.class);
			Intent menu = new Intent(this, PantallaInicialActivity.class);
			menu.putExtra("tipoUsuario", tipoUsuario);
			startActivity(menu);
	        overridePendingTransition(R.anim.left_in, R.anim.left_out);
	        finish();
		}*/
	}


}
