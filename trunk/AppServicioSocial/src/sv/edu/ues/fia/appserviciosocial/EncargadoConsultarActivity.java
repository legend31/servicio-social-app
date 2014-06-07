package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

		image = (ImageView) findViewById(R.id.mainImageEncargado);

		// spinner
		Spinner spinner = (Spinner) findViewById(R.id.spinnerEncargado);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.camposArrayEncargado,
				android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
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
			tablaDeDatos.setVisibility(View.INVISIBLE);

			btnAtras.setVisibility(View.INVISIBLE);

			btnAdelante.setVisibility(View.INVISIBLE);

			return;
		}

		else {

			Toast.makeText(this, "Se encontraron" + datos.size() + "registro",
					Toast.LENGTH_LONG).show();
			cantidad = datos.size() - 1;
			// li=(ListView)findViewById(R.id.listView1);
			// ArrayAdapter<EncargadoServicioSocial> adaptador=new
			// ArrayAdapter<EncargadoServicioSocial>
			// (this,android.R.layout.simple_list_item_1, datos);

			// li.setAdapter(adaptador);
			// llenado de lista con una concatenacion de dos datos significantes
			// de la tabla, puede ser el id junto con el nombre
			// al pulsar abre un activity
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

}
