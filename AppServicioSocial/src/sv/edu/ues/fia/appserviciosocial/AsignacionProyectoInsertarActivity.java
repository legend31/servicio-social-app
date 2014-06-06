package sv.edu.ues.fia.appserviciosocial;

import java.text.SimpleDateFormat;
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
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class AsignacionProyectoInsertarActivity extends Activity {

	//sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;
	
	EditText txtCarnet;
	EditText txtProyecto;
	TextView txtFecha;
	Calendar calendario;
	ControlBD auxiliar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignacion_proyecto_insertar);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
		txtProyecto = (EditText) findViewById(R.id.txtProyecto);
		txtFecha = (TextView) findViewById(R.id.txtFecha);
		auxiliar = new ControlBD(this);
		
		//sonidos
		soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
        exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
        fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

		// Definición del botón calendar
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
		getMenuInflater().inflate(R.menu.asignacion_proyecto_insertar, menu);
		return true;
	}

	/**
	 * Abre la ventana modal
	 * 
	 * @param v
	 */
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(this.getFragmentManager(), "datePicker");
	}

	public void insertarAsignacionProyecto(View v) {
		String carnet = txtCarnet.getText().toString();
		String proyecto = txtProyecto.getText().toString();
		String fecha = "";
		try {
			String format = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
			fecha = sdf.format(calendario.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String info = "";
		// Validando
		if (carnet == null || carnet.trim() == "" || carnet.length() != 7) {
			info = "Carnet inválido";
		}
		if (proyecto == null || proyecto.trim() == "") {
			info = "Nombre inválido";
		}
		if (calendario == null || fecha == "") {
			info = "Fecha inválida";
		}
		// Avisando errores
		if (info != "") {
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}
		// Creando inserción
		AsignacionProyecto asignacion = new AsignacionProyecto();
		asignacion.setCarnet(carnet);
		asignacion.setIdProyecto(proyecto);
		asignacion.setFecha(fecha);
		auxiliar.abrir();
		String regInsertados = auxiliar.insertar(asignacion);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
		//sonidos
		   if(regInsertados.length()<=20){
		            	 soundPool.play(exito, 1, 1, 1, 0, 1);
		            }
		            else{
		            soundPool.play(fracaso, 1, 1, 1, 0, 1);

		            }
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Usar del defecto la fecha actual
			final Calendar c = Calendar.getInstance();
			try {
				// Si en algun momento se ha informado la fecha se recupera
				String format = "dd/MM/yyyy";
				SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
				c.setTime(sdf.parse(String.valueOf(txtFecha.getText())));
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
}
