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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProyectoInsertarActivity extends Activity {

	EditText idProyecto;
	EditText idSolicitante;
	EditText idTipoProyecto;
	EditText idEncargado;
	EditText nombre;
	ControlBD helper;
	Button btnIngresar;
	String info = "";
	// sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;
	private String urlExterno = "http://hv11002pdm115.hostei.com/serviciosweb/insertar_proyecto.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Código para permitir la conexión a internet en el mismo hilo
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		helper = new ControlBD(this);
		setContentView(R.layout.activity_proyecto_insertar);
		idSolicitante = (EditText) findViewById(R.id.editCodeSolicitante);
		idTipoProyecto = (EditText) findViewById(R.id.editTipoProyecto);
		idEncargado = (EditText) findViewById(R.id.editCodeEncargadoProyecto);
		nombre = (EditText) findViewById(R.id.editNombreProyecto);
		btnIngresar = (Button) findViewById(R.id.btnIngresarProyecto);

		// sonidos
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
		fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.proyecto_insertar, menu);
		return true;
	}

	public void insertarProyecto(View v) {

		int codeSolicitante = Integer.parseInt(idSolicitante.getText()
				.toString());
		int codeTipoProyecto = Integer.parseInt(idTipoProyecto.getText()
				.toString());
		int codeEncargado = Integer.parseInt(idEncargado.getText().toString());
		String name = nombre.getText().toString();

		if (name == null || name.equalsIgnoreCase("")) {
			info = "Nombre del Proyecto inválido";
		}

		if (info != "") {
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}

		Proyecto proyecto = new Proyecto();

		proyecto.setIdSolicitante(codeSolicitante);
		proyecto.setIdTipoProyecto(codeTipoProyecto);
		proyecto.setIdEncargado(codeEncargado);
		proyecto.setNombre(name);
		proyecto.setEnviado("false");

		helper.abrir();
		String regInsertados = helper.insertar(proyecto);
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
		// idProyecto.setText("");
		idEncargado.setText("");
		idTipoProyecto.setText("");
		nombre.setText("");
		idSolicitante.setText("");
	}

	public void insertarServidor(View v) {
		// Aqui se deben buscar todos los registros que tengan enviado=false
		// y se enviaran al servidor y se pondrán enviado=true
		helper.abrir();
		ArrayList<Proyecto> proyectosAEnviar = helper
				.consultarProyectoNoEnviado();

		int idProyecto;
		int idSolicitante;
		int idTipoProyecto;
		int idEncargado;
		String nombre = "";

		Date fecha = new Date();
		fecha = Calendar.getInstance().getTime();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String actualizado = formato.format(fecha);

		if (proyectosAEnviar != null) {
			for (int i = 0; i < proyectosAEnviar.size(); i++) {
				idProyecto = proyectosAEnviar.get(i).getIdProyecto();
				idSolicitante = proyectosAEnviar.get(i).getIdSolicitante();
				idTipoProyecto = proyectosAEnviar.get(i).getIdTipoProyecto();
				idEncargado = proyectosAEnviar.get(i).getIdEncargado();
				nombre = proyectosAEnviar.get(i).getNombre();

				// Inserción en el servidor PHP
				String url = urlExterno + "?idproyecto=" + idProyecto
						+ "&idsolicitante=" + idSolicitante
						+ "&idtipoproyecto=" + idTipoProyecto + "&idencargado="
						+ idEncargado + "&nombre=" + URLEncoder.encode(nombre)
						+ "&fechaact=" + URLEncoder.encode(actualizado);
				Log.v("la url de php", url);
				ControladorServicio.insertarObjeto(url, this);

				helper.establecerProyectoEnviado(String.valueOf(idProyecto));
			}
		}
		helper.cerrar();
	}
}
