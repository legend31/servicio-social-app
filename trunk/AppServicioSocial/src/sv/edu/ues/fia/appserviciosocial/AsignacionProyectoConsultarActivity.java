package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AsignacionProyectoConsultarActivity extends Activity {
	
	EditText txtBusqueda;
	GridView gdvTabla;
	RadioGroup radioGrupo;
	ControlBD auxiliar;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asignacion_proyecto_consultar);
		txtBusqueda = (EditText) findViewById(R.id.txtBusqueda);
		gdvTabla = (GridView) findViewById(R.id.gdvTabla);
		radioGrupo = (RadioGroup) findViewById(R.id.radioGroup1);
		auxiliar = new ControlBD(this);
		//sonidos
        soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
        exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
        fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asignacion_proyecto_consultar, menu);
		return true;
	}

	public void seleccion(View v)
	{
		switch(v.getId())
		{
		case R.id.rbtAlumno:
			txtBusqueda.setHint("Alumno");
		break;
		case R.id.rbtProyecto:
			txtBusqueda.setHint("Proyecto");
		break;
		}
	}
	
		public void consultarAsignacionProyecto(View v) {
		
		String info="";
		String texto = txtBusqueda.getText().toString().trim();
		if(texto == null || texto == "" || texto.length() == 0)
		{
			switch(radioGrupo.getCheckedRadioButtonId())
			{
			case R.id.rbtAlumno:
				info = "Carnet inválido";
			break;
			case R.id.rbtProyecto:
				info = "Proyecto inválido";
			break;
			}
			Toast.makeText(this, info, Toast.LENGTH_LONG).show();
			return;
		}//
		auxiliar.abrir();
		int tipo = 0;
		switch(radioGrupo.getCheckedRadioButtonId())
		{
		case R.id.rbtAlumno:
			tipo = 1;
		break;
		case R.id.rbtProyecto:
			tipo = 2;
		break;
		}
		ArrayList<AsignacionProyecto> asignaciones = auxiliar.consultarAsignacionProyecto(texto, tipo);
		auxiliar.cerrar();
		if(asignaciones == null)
		{
			gdvTabla.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "Asignación de proyecto no encontrado", Toast.LENGTH_LONG).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;
		}
		else{
			soundPool.play(exito, 1, 1, 1, 0, 1);
			List<String> datos= new ArrayList<String>();
			if(tipo ==1)
			{
				datos.add("Proyectos");
				datos.add("Fecha");
				for (AsignacionProyecto asignacion : asignaciones) {
					datos.add(asignacion.getIdProyecto());
					String aux = asignacion.getFecha();
					datos.add(aux.substring(8)+"/"+aux.substring(5, 7)+"/"+aux.substring(0,4));
				}
			}
			else if(tipo == 2){
				datos.add("Alumnos");
				datos.add("Fecha");
				for (AsignacionProyecto asignacion : asignaciones) {
					datos.add(asignacion.getCarnet());
					String aux = asignacion.getFecha();
					datos.add(aux.substring(8)+"/"+aux.substring(5, 7)+"/"+aux.substring(0,4));
				}
			}
			//Llenando tabla
			ArrayAdapter<String> adaptador = 
			        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
			gdvTabla.setAdapter(adaptador);
			gdvTabla.setVisibility(View.VISIBLE);
		}
		
	}

}
