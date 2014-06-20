package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InstitucionActualizarActivity extends Activity {
	
	private ControlBD auxiliar;
	private EditText txtNit,txtNombre, txtNuevoNit, editText1,
	 editText2;
	private Button btnActulizar;
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;
		 
		LocationManager locationManager;
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
		editText1=(EditText) findViewById(R.id.editText1);
		editText2=(EditText) findViewById(R.id.edtNombrett);
		//sonidos
	         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
	         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
	         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
	       //obtenerDir.setOnClickListener(onClickDireccion);
	         locationManager = (LocationManager)
	         this.getSystemService(Context.LOCATION_SERVICE);
	         }
	         LocationListener locationListener = new LocationListener() {
	         public void onLocationChanged(Location location) {
	         // TODO Auto-generated method stub
	         	editText1.setText(String.valueOf(location.getLatitude()));
	         	editText2.setText(String.valueOf(location.getLongitude()));

	         }
	         public void onProviderDisabled(String provider) {
	         // TODO Auto-generated method stub
	         }
	         public void onProviderEnabled(String provider) {
	         // TODO Auto-generated method stub
	         }
	         public void onStatusChanged(String provider, int status, Bundle extras) {
	         // TODO Auto-generated method stub
	         }
	         };
	         @Override
	         public void onPause() {
	         super.onPause();
	         locationManager.removeUpdates(locationListener);
	         }
	         @Override
	         public void onResume() {
	         super.onResume();
	         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
	         0, locationListener);
	         locationManager.requestLocationUpdates(
	         LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
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
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
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
		String latitud = editText1.getText().toString();
		String longitud =editText2.getText().toString();
		if ( nombre.matches("")){
			Toast.makeText(this, "Nombre no válido",
					Toast.LENGTH_LONG).show();			
		}else							
			if ( nuevoNit.matches("")){						
				guardar(nombre, actualNit,actualNit,latitud,longitud);
			}else				
				if(nuevoNit.length() != 14)
					Toast.makeText(this, "Nuevo NIT no válido",
							Toast.LENGTH_LONG).show();
				else
					guardar(nombre,nuevoNit,actualNit,latitud,longitud);
	}
	
	private void guardar(String nombre, String nuevoNit,String actualNit, String latitud, String longitud){
		Institucion institucion = new Institucion( nombre, nuevoNit);
		institucion.setLatitud(latitud);
		institucion.setLongitud(longitud);
		institucion.setNitAnterior(actualNit);
		auxiliar.abrir();
		auxiliar.actualizar(institucion);
		Toast.makeText(this, "Registro actualizado correctamente",
		Toast.LENGTH_LONG).show();	
		soundPool.play(exito, 1, 1, 1, 0, 1);
		auxiliar.cerrar();			
	}

}
