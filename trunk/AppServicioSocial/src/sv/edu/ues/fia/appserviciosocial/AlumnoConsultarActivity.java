package sv.edu.ues.fia.appserviciosocial;

import java.util.List;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AlumnoConsultarActivity extends Activity {

	ControlBD auxiliar;
	EditText txtCarnet;
	GridView gdvTabla;
	TextView lblDatos;
	ImageView image;
	// sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;
	
	private String urlExterno = "http://hv11002pdm115.hostei.com/serviciosweb/consultar_alumno.php";
	private String urlLocal = "http://10.0.2.2:8080/AppServicioSocial/webresources/sv.edu.ues.fia.appserviciosocial.entidad.alumno/by";
	static List<Alumno> listaAlumnos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno_consultar);
		
		// Código para permitir la conexión a internet en el mismo hilo
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);

		auxiliar = new ControlBD(this);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
		gdvTabla = (GridView) findViewById(R.id.gdvTabla);
		lblDatos = (TextView) findViewById(R.id.lblDatos);
		image = (ImageView) findViewById(R.id.mainimage);
		gdvTabla.setVisibility(View.INVISIBLE);
		lblDatos.setVisibility(View.INVISIBLE);
		// sonidos
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
		fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumno_consultar, menu);
		return true;
	}

	public void consultarAlumno(View v) {

		String carnet = txtCarnet.getText().toString();
		// Validando
		if (carnet == null || carnet.trim() == "" | carnet.length() != 7) {
			Toast.makeText(this, "Carnet inválido", Toast.LENGTH_LONG).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;

		}
		auxiliar.abrir();
		Alumno alumno = auxiliar.consultarAlumno(carnet);
		auxiliar.cerrar();
		if (alumno == null) {
			lblDatos.setVisibility(View.INVISIBLE);
			gdvTabla.setVisibility(View.INVISIBLE);
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			Toast.makeText(this,
					"Alumno con carnet " + carnet + " no encontrado",
					Toast.LENGTH_LONG).show();

			return;
		} else {
			soundPool.play(exito, 1, 1, 1, 0, 1);
			String[] datos = new String[10];
			datos[0] = "Nombre:";
			datos[1] = alumno.getNombre();
			datos[2] = "Teléfono:";
			datos[3] = alumno.getTelefono();
			datos[4] = "DUI:";
			datos[5] = alumno.getDui();
			datos[6] = "NIT:";
			datos[7] = alumno.getNit();
			datos[8] = "E-mail:";
			datos[9] = alumno.getEmail();
			String path = alumno.getPath();

			// Llenando tabla
			ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, datos);
			gdvTabla.setAdapter(adaptador);
			lblDatos.setVisibility(View.VISIBLE);
			gdvTabla.setVisibility(View.VISIBLE);

			if (path.equalsIgnoreCase("")) {
				image.setImageResource(R.drawable.anonimo);
			} else
				image.setImageBitmap(BitmapFactory.decodeFile(path));
		}

	}

	
	public void actualizarServidor(View v) {
		auxiliar.abrir();
		if(listaAlumnos != null)
		listaAlumnos.clear();
		String ultimaFecha = auxiliar.obtenerFechaActualizacion("alumno");
		Log.v("fecha de SQLite", ultimaFecha);
		String url = urlExterno + "?fecha=" + ultimaFecha;
		Log.v("URL", url);
		String alumnosExternos = ControladorServicio.obtenerRespuestaPeticion(url, this);
		Log.v("JSON", alumnosExternos);
		try {
			listaAlumnos = ControladorServicio.obtenerAlumno(alumnosExternos, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0; i < listaAlumnos.size();i++){
			if(!listaAlumnos.get(i).getPath().equals(""))
			{
				ControladorServicio.descargarImagen(listaAlumnos.get(i).getPath(), this);
				listaAlumnos.get(i).setPath("/storage/sdcard/" + listaAlumnos.get(i).getPath());
			}
			listaAlumnos.get(i).setEnviado("true");
			String respuesta = auxiliar.insertar(listaAlumnos.get(i));
			Log.v("guardar",respuesta);
			Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
			soundPool.play(exito, 1, 1, 1, 0, 1);
		}
		//Guardar la nueva fecha de actualización
		auxiliar.establecerFechaActualizacion("alumno");
		auxiliar.cerrar();
	}
	
	public void actualizarServidorJava(View v)
	{
		auxiliar.abrir();
		if(listaAlumnos != null)
		listaAlumnos.clear();
		String ultimaFecha = auxiliar.obtenerFechaActualizacion("alumno");
		Log.v("fecha de SQLite", ultimaFecha);
		String url = urlLocal + "?fecha=" + ultimaFecha;
		Log.v("URL", url);
		String alumnosExternos = ControladorServicio.obtenerRespuestaPeticion(url, this);
		Log.v("JSON", alumnosExternos);
		try {
			listaAlumnos = ControladorServicio.obtenerAlumno(alumnosExternos, this);
			Log.v("listaAlumnos", listaAlumnos+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0; i < listaAlumnos.size();i++){
			/*if(!listaAlumnos.get(i).getPath().equals(""))
			{
				ControladorServicio.descargarImagen(listaAlumnos.get(i).getPath(), this);
				listaAlumnos.get(i).setPath("/storage/sdcard/" + listaAlumnos.get(i).getPath());
			}*/
			listaAlumnos.get(i).setEnviado("true");
			String respuesta = auxiliar.insertar(listaAlumnos.get(i));
			Log.v("guardar",respuesta);
			Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
			soundPool.play(exito, 1, 1, 1, 0, 1);
		}
		//Guardar la nueva fecha de actualización
		auxiliar.establecerFechaActualizacion("alumno");
		auxiliar.cerrar();
	}
	public void print(View v){
		Printer p = new Printer(this);
		p.printDocument();
	}
}
