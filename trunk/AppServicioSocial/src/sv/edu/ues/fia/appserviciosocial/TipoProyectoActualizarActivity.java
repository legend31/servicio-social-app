package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

public class TipoProyectoActualizarActivity extends Activity {
	
	ControlBD helper;
	EditText txtTipoProyecto;
	EditText txtNombre;
	EditText txtIdTipoProyecto;
	TableLayout tabla;
	Button btnActualizarTipoProyecto;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;
		 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_proyecto_actualizar);
		helper = new ControlBD(this);
		tabla = (TableLayout)findViewById(R.id.TablaDeDatosTipoProyecto);
		tabla.setVisibility(View.INVISIBLE);
		btnActualizarTipoProyecto = (Button)findViewById(R.id.btnActualizarTipoProyecto);
		btnActualizarTipoProyecto.setVisibility(View.INVISIBLE);
		txtTipoProyecto = (EditText)findViewById(R.id.txtIdTipoProyecto);
		txtNombre = (EditText)findViewById(R.id.txtNombreTipoProyect);
		txtIdTipoProyecto = (EditText)findViewById(R.id.txtCodigoTipoProyecto1);

		//sonidos
	         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
	         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
	         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_proyecto_actualizar, menu);
		return true;
	}
	
	public void consultarTipoProyecto(View v){
		
		helper.abrir();
		TipoProyecto tipoProyecto = helper.consultarTipoProyecto(txtTipoProyecto.getText().toString());
		helper.cerrar();
		if(tipoProyecto == null)
		{
			tabla.setVisibility(View.INVISIBLE);
			btnActualizarTipoProyecto.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "Tipo Proyecto con ID " +txtTipoProyecto.getText().toString() +" no encontrado", Toast.LENGTH_LONG).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;
		}
		else{
			tabla.setVisibility(View.VISIBLE);
			btnActualizarTipoProyecto.setVisibility(View.VISIBLE);
			txtNombre.setText(tipoProyecto.getNombre());
			txtIdTipoProyecto.setText(String.valueOf(tipoProyecto.getIdTipoProyecto()));
		}
		
		
	}
	
	public void actualizarTipoProyecto(View v){
		
		String nombre=txtNombre.getText().toString();
		String codeTipoProyect=txtIdTipoProyecto.getText().toString();
		String info="";
		
		if(nombre == null || nombre.equals(""))
		{
			info = "Nombre inválido";
		}

		if(info != "")
		{
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}

		//Creando inserción
		TipoProyecto tipoProyecto = new TipoProyecto();
		tipoProyecto.setIdTipoProyecto(Integer.valueOf(codeTipoProyect));
		tipoProyecto.setNombre(nombre);
		
		helper.abrir();
		String regInsertados=helper.actualizar(tipoProyecto);
		helper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
		//sonidos
		   if(regInsertados.length()>25){
		            	 soundPool.play(exito, 1, 1, 1, 0, 1);
		            }
		            else{
		            soundPool.play(fracaso, 1, 1, 1, 0, 1);

		            }
	}

}
