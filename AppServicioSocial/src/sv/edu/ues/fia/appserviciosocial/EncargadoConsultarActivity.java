package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

public class EncargadoConsultarActivity extends Activity implements
		OnItemSelectedListener {
	ControlBD base;
	EditText txtBusqueda;
	int seleccion;
	int indicador;
	int largoCadena;
	ArrayList<EncargadoServicioSocial> datos;
	ListView li;
	private TableLayout tablaDeDatos;
	private EditText edtNombre;
	private EditText edtIdEncargado;
	private EditText edtTelefono;
	private EditText edtFacultad;
	private EditText edtEscuela;
	private EditText edtEmail;
	private Button btnAtras;
	private Button btnAdelante;
	EncargadoServicioSocial encargado;
	ImageView image;
	int cantidad;
	// sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;
	
	//servicios
	private String urlExterno = "http://hv11002pdm115.hostei.com/serviciosweb/consultar_encargado.php";
	static List<EncargadoServicioSocial> listaEncargados;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encargado_consultar);

		base = new ControlBD(this);
		txtBusqueda = (EditText) findViewById(R.id.edtBuscarEncargado);

		// los datos que se mostraran
		tablaDeDatos = (TableLayout) findViewById(R.id.TablaDeDatosEncargado);
		tablaDeDatos.setVisibility(View.INVISIBLE);
		btnAtras = (Button) findViewById(R.id.btnAtras);
		btnAtras.setVisibility(View.INVISIBLE);
		btnAdelante = (Button) findViewById(R.id.btnAdelante);
		btnAdelante.setVisibility(View.INVISIBLE);

		edtIdEncargado = (EditText) findViewById(R.id.edtIdEncargado);
		edtNombre = (EditText) findViewById(R.id.edtNombreEncargado);
		edtEmail = (EditText) findViewById(R.id.edtEmailEncargado);
		edtTelefono = (EditText) findViewById(R.id.edtTelefonoEncargado);

		edtFacultad = (EditText) findViewById(R.id.edtFacultadEncargado);
		edtEscuela = (EditText) findViewById(R.id.edtEscuelaEncargado);

		image = (ImageView) findViewById(R.id.mainimage);
		image.setVisibility(View.INVISIBLE);
		

		// spinner
		Spinner spinner = (Spinner) findViewById(R.id.spinnerEncargado);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.camposArrayEncargado,
				android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);

		// sonidos
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
		fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
		
		
		//servicio
		// Código para permitir la conexión a internet en el mismo hilo
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// Toast.makeText(parent.getContext(), "Selecciono la opcion: " + pos,
		// Toast.LENGTH_SHORT).show();
		seleccion = pos;

	}

	public void consultarEncargado(View v) {

		String busqueda = txtBusqueda.getText().toString();
		// Validando
		if (busqueda == null || busqueda.trim() == "") {
			Toast.makeText(this, "No se ingreso informacion para la busqueda.",
					Toast.LENGTH_LONG).show();
			tablaDeDatos.setVisibility(View.INVISIBLE);
			
			image.setVisibility(View.INVISIBLE);

			btnAtras.setVisibility(View.INVISIBLE);

			btnAdelante.setVisibility(View.INVISIBLE);

			return;

		}

		base.abrir();
		datos = base.consultarEncargadoServicioSocial(busqueda, seleccion);
		base.cerrar();
		if (datos == null) {
			Toast.makeText(this, "Registro " + busqueda + " no encontrado",
					Toast.LENGTH_LONG).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			tablaDeDatos.setVisibility(View.INVISIBLE);
			
			image.setVisibility(View.INVISIBLE);

			btnAtras.setVisibility(View.INVISIBLE);

			btnAdelante.setVisibility(View.INVISIBLE);

			return;
		}

		else {

			Toast.makeText(this, "Se encontraron" + datos.size() + "registro",
					Toast.LENGTH_LONG).show();
			soundPool.play(exito, 1, 1, 1, 0, 1);
			cantidad = datos.size() - 1;

			indicador = 0;
			mostrarDatos();

		}

	}

	public void adelante(View v) {
		indicador = indicador + 1;
		mostrarDatos();
	}

	public void atras(View v) {
		indicador = indicador - 1;
		mostrarDatos();
	}

	public void mostrarDatos() {

		encargado = datos.get(indicador);
		tablaDeDatos.setVisibility(View.VISIBLE);
		image.setVisibility(View.VISIBLE);
		// btnActualizar.setVisibility(View.VISIBLE);
		edtIdEncargado.setText(Integer.toString(encargado.getIdEncargado()));
		// Toast.makeText(this, "Su id es" +encargado.getIdEncargado()
		// +"registro", Toast.LENGTH_LONG).show();
		edtNombre.setText(encargado.getNombre());
		edtEmail.setText(encargado.getEmail());
		edtTelefono.setText(encargado.getTelefono());
		edtFacultad.setText(encargado.getFacultad());
		edtEscuela.setText(encargado.getEscuela());
		String path = encargado.getPath();
		image.setImageBitmap(BitmapFactory.decodeFile(path));
		if (path.equalsIgnoreCase("")) {
			image.setImageResource(R.drawable.anonimo);
		} else {
			image.setImageBitmap(BitmapFactory.decodeFile(path));
		}

		// mostrar los botones de navegacion
		if (indicador == 0 && cantidad != indicador) {
			btnAtras.setVisibility(View.INVISIBLE);
			btnAdelante.setVisibility(View.VISIBLE);
			return;
		}
		if (indicador == 0 && cantidad == indicador) {
			btnAtras.setVisibility(View.INVISIBLE);
			btnAdelante.setVisibility(View.INVISIBLE);
			return;
		}
		if (indicador != 0 && cantidad != indicador) {
			btnAtras.setVisibility(View.VISIBLE);
			btnAdelante.setVisibility(View.VISIBLE);
			return;
		}
		if (indicador != 0 && cantidad == indicador) {
			btnAtras.setVisibility(View.VISIBLE);
			btnAdelante.setVisibility(View.INVISIBLE);
			return;
		}

	}

	public void onNothingSelected(AdapterView<?> arg0) {

	}
	
	
	public void actualizarServidorEncargado(View v) {
		
	
		
		base.abrir();
		if(listaEncargados != null)
			listaEncargados.clear();
		String ultimaFecha= base.obtenerFechaActualizacion("encargado");
		Log.v("fecha de SQLite", ultimaFecha);
		String url = urlExterno + "?fecha=" + ultimaFecha;
		Log.v("URL", url);

		
		String encargadosExternos = ControladorServicio.obtenerRespuestaPeticion(url, this);
		Log.v("JSON", encargadosExternos);

	
		try {
			listaEncargados = ControladorServicio.obtenerEncargado(encargadosExternos, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int i=0; i < listaEncargados.size();i++){
			if(!listaEncargados.get(i).getPath().equals(""))
			{
				ControladorServicio.descargarImagen(listaEncargados.get(i).getPath(), this);
				listaEncargados.get(i).setPath("/storage/sdcard/" + listaEncargados.get(i).getPath());
			}
			listaEncargados.get(i).setEnviado("true");
			String respuesta = base.insertar(listaEncargados.get(i));
			Log.v("guardar",respuesta);
			Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
			
		}
		//Guardar la nueva fecha de actualización
		base.establecerFechaActualizacion("encargado");
		base.cerrar();
		
	}

}
