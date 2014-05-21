package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class SolicitanteInsertarActivity extends Activity {

	private EditText txtNombre,txtTelefono,txtEmail,txtNitInstitucion,txtCargo;
	private ControlBD auxiliar;
	
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
								 String idAsignado = auxiliar.insertar(solicitante);
								 Toast.makeText(this, "Registro insertado", Toast.LENGTH_SHORT).show();						   
								 Toast.makeText(this, "ID asignado a solicitante : "+idAsignado, Toast.LENGTH_LONG).show();
								   
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
