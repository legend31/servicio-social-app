package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SolicitanteActualizarActivity extends Activity {
	private EditText txtNombre,txtIdSolicitante,txtNitInstitucion, txtIdCargo,txtTelefono,txtEmail;
	private ControlBD auxiliar;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitante_actualizar);
		auxiliar = new ControlBD(this);
		txtIdSolicitante = (EditText)findViewById(R.id.editIdSolicitanteActualizar);
		txtNitInstitucion = (EditText)findViewById(R.id.editConsNitInstitucion);
		txtIdCargo = (EditText)findViewById(R.id.editIdCargo);
		txtTelefono= (EditText)findViewById(R.id.editTelefonoSolicitante);
		txtEmail = (EditText)findViewById(R.id.editCorreoSolicitante);
		txtNombre = (EditText)findViewById(R.id.editNombreSolicitante);

		//sonidos
	         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
	         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
	         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solicitante_actualizar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void buscarSolicitante(View v){
		txtNombre.setText("");
		txtEmail.setText("");
		txtIdCargo.setText("");
		txtNitInstitucion.setText("");
		txtNombre.setText("");
		txtTelefono.setText("");
		
		String id = txtIdSolicitante.getText().toString();
		//Validando
		if(id == null || id.matches("")){
			Toast.makeText(this, "ID inválido", Toast.LENGTH_LONG).show();
			return;			
		}
		
		auxiliar.abrir();
		
		Solicitante solicitante= auxiliar.consultarSolicitante(id);
		
		
		if(solicitante == null)	{
			
			txtNombre.setText("");
			Toast.makeText(this, "Solicitante con id " + id +
					" no encontrado", Toast.LENGTH_LONG).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;
		}else{
			
			Institucion institucion = auxiliar.
					consultarInstitucionById(solicitante.getIdInstitucion());
			
			txtNombre.setText(solicitante.getNombre());
			txtTelefono.setText(solicitante.getTelefono());
			txtEmail.setText(solicitante.getCorreo());
			txtIdCargo.setText(solicitante.getIdCargo());
			if (institucion != null)
				txtNitInstitucion.setText(institucion.getNit());
			else
				Toast.makeText(this, "No se encontro institución " + 
						solicitante.getIdInstitucion(), Toast.LENGTH_LONG).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
		}
		
		auxiliar.cerrar();
	
		
	}
	
	
	public void actualizarSolicitante(View v){
		String nombre = txtNombre.getText().toString(),
				   telefono = txtTelefono.getText().toString(),
				   email = txtEmail.getText().toString(),
				   nitInstitucion = txtNitInstitucion.getText().toString(),
				   idCargo = txtIdCargo.getText().toString();
			
			try{
				if ( !vacio(nombre,"Nombre") && !vacio(telefono,"telefono")  &&!vacio(email,"E-mail")
				   && !vacio(nitInstitucion,"NIT institución") && !vacio(idCargo,"ID Cargo"))
					if ( telefono.length() == 8)
						if ( nitInstitucion.length() == 14){
						   auxiliar.abrir();
						   Institucion institucion = auxiliar.consultarInstitucion(nitInstitucion);
						   if (institucion == null)
							   Toast.makeText(this, "Institución no existe", Toast.LENGTH_LONG).show();
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
									 solicitante.setIdSolicitante(txtIdSolicitante.getText().toString());
									 String idAsignado = auxiliar.actualizar(solicitante);
									 Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show();						   
									 soundPool.play(exito, 1, 1, 1, 0, 1); 
								 }
							   }else  {
								   Toast.makeText(this, "Cago no existe : "+idCargo, Toast.LENGTH_LONG).show();
							   }
				
						   }					   
						   auxiliar.cerrar();
						}else
							Toast.makeText(this, "NIT no válido ", Toast.LENGTH_LONG).show();
					else
						Toast.makeText(this, "telefono no válido ", Toast.LENGTH_LONG).show();  
						
					}catch(Exception e){
					   Toast.makeText(this, "Error "+e, Toast.LENGTH_LONG).show();
					   soundPool.play(fracaso, 1, 1, 1, 0, 1);
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
