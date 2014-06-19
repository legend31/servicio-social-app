package sv.edu.ues.fia.appserviciosocial;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TipoProyectoInsertarActivity extends Activity {
	ControlBD helper;
	EditText txtTipoProyecto;
	EditText txtNombreTipoProyecto;
	String mensaje;
	String urlExterno = "http://hv11002pdm115.hostei.com/serviciosweb/insertar_tipo_proyecto.php";

	// sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_proyecto_insertar);
		// txtTipoProyecto = (EditText)findViewById(R.id.txtTipoProyectoCodigo);
		txtNombreTipoProyecto = (EditText) findViewById(R.id.txtNombreTipoProyecto);
		helper = new ControlBD(this);
		// sonidos
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
		fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_proyecto_insertar, menu);
		return true;

	}

	public void insertarTipoProyecto(View v) {
		mensaje = "";
		// String codeTipoProyecto = txtTipoProyecto.getText().toString();
		String nombre = txtNombreTipoProyecto.getText().toString();
		if (nombre.equals("") || nombre == null) {
			mensaje = "Nombre Invalido. Intente de nuevo";
			// Toast.makeText(this,"Nombre Invalido. Intente de nuevo",Toast.LENGTH_SHORT).show();
		}
		if (mensaje != "") {
			Toast.makeText(this, "Nombre Invalido. Intente de nuevo",
					Toast.LENGTH_SHORT).show();
			return;
		}
		TipoProyecto tipoProyecto = new TipoProyecto();
		tipoProyecto.setNombre(nombre);
		//MODIFICANDO
		tipoProyecto.setEnviado("false");
		helper.abrir();
		String regInsertados = helper.insertar(tipoProyecto);
		helper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
		// sonidos
		if (regInsertados.length() <= 25) {
			soundPool.play(exito, 1, 1, 1, 0, 1);
		} else {
			soundPool.play(fracaso, 1, 1, 1, 0, 1);

		}

	}

	public void limpiar(View v) {

	}

	public void insertarServidor(View v) {
		// Aqui se deben buscar todos los registros que tengan enviado=false
		// y se enviaran al servidor y se pondrán enviado=true
		helper.abrir();
		ArrayList<TipoProyecto> tipoProyectosAEnviar = helper
				.consultarTipoProyectoNoEnviado();

		int idTipoProyecto;
		String nombre = "";

		Date fecha = new Date();
		fecha = Calendar.getInstance().getTime();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String actualizado = formato.format(fecha);

		if (tipoProyectosAEnviar != null) {
			for (int i = 0; i < tipoProyectosAEnviar.size(); i++) {
				idTipoProyecto = tipoProyectosAEnviar.get(i).getIdTipoProyecto();
				nombre = tipoProyectosAEnviar.get(i).getNombre();

				// Inserción en el servidor PHP
				String url = urlExterno + "?idtipoproyecto=" + idTipoProyecto
						+"&nombre=" + URLEncoder.encode(nombre)
						+ "&fechaact=" + URLEncoder.encode(actualizado);
				Log.v("la url de php", url);
				ControladorServicio.insertarObjeto(url, this);

				helper.establecerTipoProyectoEnviado(String.valueOf(idTipoProyecto));
			}
		}
		helper.cerrar();
	}

}
