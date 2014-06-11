package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno_consultar);
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

}
