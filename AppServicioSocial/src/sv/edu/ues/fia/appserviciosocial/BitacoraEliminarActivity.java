package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class BitacoraEliminarActivity extends Activity {
	ControlBD auxiliar;
	EditText txtIdBitacora;
	GridView gdvTabla;
	TextView lblDatos;
	Button btnBuscar;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;
		 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitacora_eliminar);
		auxiliar = new ControlBD(this);
		txtIdBitacora = (EditText) findViewById(R.id.txtIdBitacora);
		gdvTabla = (GridView) findViewById(R.id.gdvTabla);
		lblDatos = (TextView) findViewById(R.id.lblDatos);
		btnBuscar = (Button) findViewById(R.id.btnBuscar);
		gdvTabla.setVisibility(View.INVISIBLE);
		lblDatos.setVisibility(View.INVISIBLE);
		
		//sonidos
        soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
        exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
        fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bitacora_eliminar, menu);
		return true;
	}
public void consultarBitacora(View v) {
		
		String idBitacora = txtIdBitacora.getText().toString();
		String mensaje;
		//Validando
		if(idBitacora == null || idBitacora.trim() == "")
		{
			Toast.makeText(this, "Id Bitacora inválido", Toast.LENGTH_LONG).show();
			return;
			
		}
		
		if(btnBuscar.getText().toString().trim().length()==6){
			
		auxiliar.abrir();
		Bitacora objBitacora =auxiliar.consultarBitacora(idBitacora);
		auxiliar.cerrar();
		if(objBitacora == null)
		{
			lblDatos.setVisibility(View.INVISIBLE);
			gdvTabla.setVisibility(View.INVISIBLE);
			btnBuscar.setText("Buscar");
			Toast.makeText(this, "Bitacora no encontrado", Toast.LENGTH_LONG).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;
		}
		else{
			String [] datos = new String[10];
			datos[0] = "Carnet:";
			datos[1] = objBitacora.getCarnet();
			datos[2] = "Id Proyecto";
			datos[3] =String.valueOf(objBitacora.getIdProyecto());
			datos[4] = "Id Tipo Trabajo:";
			datos[5] = String.valueOf(objBitacora.getIdTipoTrabajo());
			datos[6] = "Fecha:";
			datos[7] = objBitacora.getFecha();
			datos[8] = "Descripcion:";
			datos[9] = objBitacora.getdescripcion();
			
			//Llenando tabla
			ArrayAdapter<String> adaptador =
			        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
			gdvTabla.setAdapter(adaptador);
			lblDatos.setVisibility(View.VISIBLE);
			gdvTabla.setVisibility(View.VISIBLE);
			btnBuscar.setText("Eliminar");
		}
		}
		else
		{
			auxiliar.abrir();
			mensaje=auxiliar.eliminarBitacora(idBitacora);
			auxiliar.cerrar();
			Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
			soundPool.play(exito, 1, 1, 1, 0, 1);
			lblDatos.setVisibility(View.INVISIBLE);
			gdvTabla.setVisibility(View.INVISIBLE);
			btnBuscar.setText("Buscar");
		}
	}


}//fin de la clase
