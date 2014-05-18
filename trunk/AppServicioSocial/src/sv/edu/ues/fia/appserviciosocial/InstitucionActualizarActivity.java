package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class InstitucionActualizarActivity extends Activity {
	
	private ControlBD auxiliar;
	private EditText txtNit,txtNombre, txtNuevoNit;
	private Button btnActulizar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_institucion_actualizar);
		auxiliar = new ControlBD(this);
		txtNit = (EditText) findViewById(R.id.editActNitInstitucion);
		txtNombre= (EditText) findViewById(R.id.editActNombreInstitucion);
		txtNuevoNit= (EditText) findViewById(R.id.editNuevoNit);
		btnActulizar = (Button) findViewById(R.id.btnActualizarInstitucion);
		btnActulizar.setEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.institucion_actualizar, menu);
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
	
	public void buscarInstitucion(View v){
		txtNombre.setText("");
		txtNuevoNit.setText("");
		String nit = txtNit.getText().toString();
		//Validando
		if(nit.matches("") || nit.length() != 14){
			Toast.makeText(this, "NIT inválido", Toast.LENGTH_LONG).show();
			return;			
		}
		
		auxiliar.abrir();
		Institucion institucion= auxiliar.consultarInstitucion(nit);
		auxiliar.cerrar();
		
		if(institucion == null)	{
			/*lblDatos.setVisibility(View.INVISIBLE);
			gdvTabla.setVisibility(View.INVISIBLE);
			*/
			txtNombre.setText("");
			Toast.makeText(this, "Institucion con nit " + nit +
					" no encontrado", Toast.LENGTH_LONG).show();
			btnActulizar.setEnabled(false);
			return;
		}else			
			txtNombre.setText(institucion.getNombre());
		
		btnActulizar.setEnabled(true);
	}

	public void actualizarInstitucion(View v){
		String nombre = txtNombre.getText().toString();
		String nuevoNit = txtNuevoNit.getText().toString();
		String actualNit = txtNit.getText().toString();
		
		if ( nombre.matches("")){
			Toast.makeText(this, "Nombre no válido",
					Toast.LENGTH_LONG).show();			
		}else							
			if ( nuevoNit.matches("")){						
				guardar(nombre, actualNit,actualNit);
			}else				
				if(nuevoNit.length() != 14)
					Toast.makeText(this, "Nuevo NIT no válido",
							Toast.LENGTH_LONG).show();
				else
					guardar(nombre,nuevoNit,actualNit);
	}
	
	private void guardar(String nombre, String nuevoNit,String actualNit){
		Institucion institucion = new Institucion( nombre, nuevoNit);
		institucion.setNitAnterior(actualNit);
		auxiliar.abrir();
		auxiliar.actualizar(institucion);
		Toast.makeText(this, "Registro actualizado correctamente",
		Toast.LENGTH_LONG).show();					
		auxiliar.cerrar();			
	}

}
