package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TipoTrabajoInsertar extends Activity {

	//Variables globales
	ControlBD auxiliar;	
	private EditText editText2;
	private EditText editText3;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;
		 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_trabajo_insertar);
		//Relacionar variables globales con los controles del layout	
		auxiliar = new ControlBD(this);			
		editText2=(EditText) findViewById(R.id.edtNombrett);
		editText3=(EditText) findViewById(R.id.editText3);
		//sonidos
        soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
        exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
        fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_trabajo_insertar, menu);
		return true;
	}
	
	public void insertarTipoTrabajo(View v){

		//crear nuevas variables con los valores del layout
		String info = "";
		String nombre=editText2.getText().toString();
		String valor=editText3.getText().toString();
		//validacion
		if(nombre.length()==0 )
		{
			info = "Debe introducir el nombre";
		}
		if(valor.length()==0 )
		{
			info =info+ " Debe introducir el valor";
		}
		//Avisando errores
		if(info.length()!=0)
		{
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}
		info="Datos validados";
		Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
		//Realizar la insercion en la base de datos
		//Creacion del objeto
		TipoTrabajo objTipoTrabajo= new TipoTrabajo();
		//llenar el objeto
		objTipoTrabajo.setNombre(nombre);
		objTipoTrabajo.setValor(valor);
		//realizar la inserción
		auxiliar.abrir();
		String regInsertados=auxiliar.insertar(objTipoTrabajo);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
		//sonidos
		   if(regInsertados.length()<=25){
		            	 soundPool.play(exito, 1, 1, 1, 0, 1);
		            }
		            else{
		            soundPool.play(fracaso, 1, 1, 1, 0, 1);

		            }
	}

}
