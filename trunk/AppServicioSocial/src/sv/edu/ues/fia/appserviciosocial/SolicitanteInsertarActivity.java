package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SolicitanteInsertarActivity extends Activity {

	private EditText txtNombre,txtTelefono,txtEmail,txtNitInstitucion,txtCargo;
	private ControlBD auxiliar;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;
		 
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitante_insertar);
		txtNombre = (EditText) findViewById(R.id.editNombre);
		txtEmail= (EditText) findViewById(R.id.editCorreo);
		txtTelefono = (EditText) findViewById(R.id.editTelefono);
		txtNitInstitucion = (EditText) findViewById(R.id.editNitInstitucionSolicitante);
		txtCargo = (EditText) findViewById(R.id.editcargo);
		auxiliar = new ControlBD(this);
		

		//sonidos
	         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
	         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
	         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solicitante_insertar, menu);
		return true;
	}
	
	
	public void insertarSolicitante(View v){
		String nombre = txtNombre.getText().toString(),
			   telefono = txtTelefono.getText().toString(),
			   email = txtEmail.getText().toString(),
			   nitInstitucion = txtNitInstitucion.getText().toString(),
			   idCargo = txtCargo.getText().toString();
		
		try{
			if ( !vacio(nombre,"Nombre") && !vacio(telefono,"telefono")  &&!vacio(email,"E-mail")
			   && !vacio(nitInstitucion,"NIT instituci�n") && !vacio(idCargo,"ID Cargo"))
				if ( telefono.length() == 8)
					if ( nitInstitucion.length() == 14){
					   auxiliar.abrir();
					   Institucion institucion = auxiliar.consultarInstitucion(nitInstitucion);
					   if (institucion == null)
						   Toast.makeText(this, "Instituci�n no existe", Toast.LENGTH_LONG).show();
					   else{						   
						   String idInstitucion = institucion.getIdInstitucion();						   
						   ArrayList<Cargo> datos=auxiliar.consultarCargo(idCargo, 0);
						   if (datos != null ) {
						     Cargo cargo=datos.get(0);						   
						     idCargo = Integer.toString(cargo.getIdCargo());
						   				  
							 if ( idCargo == null)
								   Toast.makeText(this, "ID cargo no existe", Toast.LENGTH_LONG).show();   
							 else{						   
								 Solicitante solicitante = new Solicitante(idInstitucion, idCargo, nombre, telefono, email);					   
								 String idAsignado = auxiliar.insertar(solicitante);
								 //sonido
								 soundPool.play(exito, 1, 1, 1, 0, 1);
								 Toast.makeText(this, "Registro insertado", Toast.LENGTH_SHORT).show();						   
								 Toast.makeText(this, "ID asignado a solicitante : "+idAsignado, Toast.LENGTH_LONG).show();
								   
							 }
						   }else  {
							   //sonido
							   soundPool.play(fracaso, 1, 1, 1, 0, 1);
							   Toast.makeText(this, "Cago no existe : "+idCargo, Toast.LENGTH_LONG).show();
						   }
			
					   }					   
					   auxiliar.cerrar();
					}else
						Toast.makeText(this, "NIT no v�lido ", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(this, "telefono no v�lido ", Toast.LENGTH_LONG).show();  
					
				}catch(Exception e){
					soundPool.play(fracaso, 1, 1, 1, 0, 1);
				   Toast.makeText(this, "Error "+e, Toast.LENGTH_LONG).show();			   
			   }
	}
	
	private boolean vacio(String campo,String nombreCampo){
		if (campo.matches("")){
			Toast.makeText(this, nombreCampo + " esta vacio ", Toast.LENGTH_LONG).show();
			return true;
		}else
			return false;
	}
}
