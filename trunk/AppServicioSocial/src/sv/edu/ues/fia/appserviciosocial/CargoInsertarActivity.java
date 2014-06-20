package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CargoInsertarActivity extends Activity {
	
	
	SoundPool soundPool;
	int exito;
	int fracaso;
	 
	
	ControlBD base;
	 
	   // private EditText txtIdEncargado;
	    private EditText edtNombre;
	    private EditText edtDescripcion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cargo_insertar);
		 base = new ControlBD(this);
         //txtIdEncargado = (EditText) findViewById(R.id.txtIdEncargadoEncargado);
         edtNombre = (EditText) findViewById(R.id.edtNombreCargo);
         edtDescripcion = (EditText) findViewById(R.id.edtDescripcionCargo);
         
    
         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
         
	}
	public void insertarCargo(View v)
    {
            //String id=txtIdEncargado.getText().toString();
            String nombre=edtNombre.getText().toString();
            String descripcion = edtDescripcion.getText().toString();
           
            String error = "";
            
           
            
            if(nombre == null || nombre.trim() == "" )
            {
                    error = "Nombre inválido";
            }
            
            if(descripcion == null || descripcion.trim() == "")
            {
                    error = "Descripción no Ingresada";
            }
           // if(txtIdEncargado.getText().toString()==null ||txtIdEncargado.getText().toString().trim()=="")
            //{
              //      error = "ID no Ingresado";
            //}
            //Avisando errores
            if(error != "")
            {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    soundPool.play(fracaso, 1, 1, 1, 0, 1);
                    return;
            }
            //Creando inserción
           Cargo cargo=new Cargo();
            //encargado.setIdEncargado(id);
            cargo.setNombre(nombre);
            cargo.setDescripcion(descripcion);
            
            
            base.abrir();
            String regInsertados=base.insertar(cargo);
            base.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
            
            if(regInsertados.length()<=20){
            	 soundPool.play(exito, 1, 1, 1, 0, 1);
            }
            else{
            soundPool.play(fracaso, 1, 1, 1, 0, 1);

            }
    }

	public void limpiarCargo(View v) {
        //txtIdEncargado.setText("");
        edtNombre.setText("");
        edtDescripcion.setText("");
        
}

}
