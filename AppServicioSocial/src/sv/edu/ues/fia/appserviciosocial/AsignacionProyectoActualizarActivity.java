package sv.edu.ues.fia.appserviciosocial;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AsignacionProyectoActualizarActivity extends Activity {

	EditText txtBusqueda;
	EditText txtResultado;
	EditText txtFecha;
	Calendar calendario;
	ControlBD auxiliar;
	RadioGroup radioGrupo;
	Button btnAdelante;
	Button btnAtras;
	int tamañoArray = 0, index = 0, tipo = 0;
	ArrayList<AsignacionProyecto> asignaciones;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignacion_proyecto_actualizar);
		txtBusqueda = (EditText) findViewById(R.id.txtBusqueda);
		txtResultado = (EditText) findViewById(R.id.txtResultado);
		txtFecha = (EditText) findViewById(R.id.txtFecha);
		radioGrupo = (RadioGroup) findViewById(R.id.radioGroup1);
		btnAdelante = (Button) findViewById(R.id.btnAdelante);
		btnAdelante.setVisibility(View.INVISIBLE);
		btnAtras = (Button) findViewById(R.id.btnAtras);
		btnAtras.setVisibility(View.INVISIBLE);
		auxiliar = new ControlBD(this);


		//sonidos
	         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
	         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
	         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

		final Button btnOpenPopup = (Button) findViewById(R.id.btnFecha);
		btnOpenPopup.setOnClickListener(new Button.OnClickListener() {

			@Override
			/**
			 * Al pulsar sobre el boton se abre la ventana modal con el componente DatePicker
			 */
			public void onClick(View arg0) {
				showDatePickerDialog(arg0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignacion_proyecto_actualizar, menu);
		return true;
	}

	public void seleccion(View v) {
		switch (v.getId()) {
		case R.id.rbtAlumno:
			txtBusqueda.setHint("Alumno");
			break;
		case R.id.rbtProyecto:
			txtBusqueda.setHint("Proyecto");
			break;
		}
	}

	public void consultarAsignacionProyecto(View v) {

		String info = "";
		String texto = txtBusqueda.getText().toString().trim();
		if (texto == null || texto == "" || texto.length() == 0) {
			switch (radioGrupo.getCheckedRadioButtonId()) {
			case R.id.rbtAlumno:
				info = "Carnet inválido";
				break;
			case R.id.rbtProyecto:
				info = "Proyecto inválido";
				break;
			}
			Toast.makeText(this, info, Toast.LENGTH_LONG).show();
			return;
		}
		auxiliar.abrir();
		switch (radioGrupo.getCheckedRadioButtonId()) {
		case R.id.rbtAlumno:
			tipo = 1;
			break;
		case R.id.rbtProyecto:
			tipo = 2;
			break;
		}
		asignaciones = auxiliar.consultarAsignacionProyecto(texto, tipo);
		auxiliar.cerrar();
		if (asignaciones == null) {
			Toast.makeText(this, "Asignación de proyecto no encontrado",
					Toast.LENGTH_LONG).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;
		} else {
			index = 0;
			tamañoArray = asignaciones.size();
			if (tamañoArray > 0) {
				btnAdelante.setVisibility(View.VISIBLE);
			}
			mostrarDatos();
		}

	}

	public void actualizarAsignacionProyecto(View v) {
		String info = "";
		String texto = txtBusqueda.getText().toString().trim();
		if (texto == null || texto == "" || texto.length() == 0) {
			switch (radioGrupo.getCheckedRadioButtonId()) {
			case R.id.rbtAlumno:
				info = "Carnet inválido";
				break;
			case R.id.rbtProyecto:
				info = "Proyecto inválido";
				break;
			}
			Toast.makeText(this, info, Toast.LENGTH_LONG).show();
			return;
		}
		String fecha = "";
		try {
			String format = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
			fecha = sdf.format(calendario.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		AsignacionProyecto asignacion = new AsignacionProyecto();
		if(tipo == 1)
		{
			asignacion.setIdProyecto(txtResultado.getText().toString().substring(10));
			asignacion.setCarnet(texto);
		}
		else if(tipo == 2)
		{
			asignacion.setCarnet(txtResultado.getText().toString().substring(8));
			asignacion.setIdProyecto(texto);
		}
		asignacion.setFecha(fecha);
		auxiliar.abrir();
		String regInsertados=auxiliar.actualizar(asignacion);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
		//sonidos
		   if(regInsertados.length()>=25){
		            	 soundPool.play(exito, 1, 1, 1, 0, 1);
		            }
		            else{
		            soundPool.play(fracaso, 1, 1, 1, 0, 1);

		            }
	}

	public void mostrarDatos() {
		if (index >= 0 && index <= tamañoArray) {
			if (tipo == 1) {
				txtResultado.setText("Proyecto: "
						+ asignaciones.get(index).getIdProyecto());
			} else if (tipo == 2) {
				txtResultado.setText("Alumno: "
						+ asignaciones.get(index).getCarnet());
			}
			String fechaAux = asignaciones.get(index).getFecha();
			txtFecha.setText(fechaAux.substring(8)+"/"+fechaAux.substring(5, 7)+"/"+fechaAux.substring(0, 4));
		}
		if (index == tamañoArray - 1) {
			btnAdelante.setVisibility(View.INVISIBLE);
		} else {
			btnAdelante.setVisibility(View.VISIBLE);
		}
		if (index == 0) {
			btnAtras.setVisibility(View.INVISIBLE);
		} else {
			btnAtras.setVisibility(View.VISIBLE);
		}
	}

	public void adelante(View v) {
		index++;
		mostrarDatos();
	}

	public void atras(View v) {
		index--;
		mostrarDatos();
	}

	@SuppressLint("ValidFragment")
	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Usar del defecto la fecha actual
			final Calendar c = Calendar.getInstance();
			try {
				// Si en algun momento se ha informado la fecha se recupera
				String aux = txtFecha.getText().toString().trim();
				String format = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
				c.setTime(sdf.parse(String.valueOf(aux)));
			} catch (Exception e) {
				// Si falla utilizaremos la fecha actual
			}

			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of TimePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		/**
		 * Recupera el valor seleccionado en el componente DatePicker e inserta
		 * el valor en el TextView tvDate
		 * 
		 * @param view
		 * @param year
		 * @param month
		 * @param day
		 */
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			try {
				calendario = Calendar.getInstance();
				calendario.set(year, month, day);
				String format = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
				txtFecha.setText(sdf.format(calendario.getTime()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(this.getFragmentManager(), "datePicker");
	}
}
