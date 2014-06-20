package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InstitucionInsertarActivity extends Activity {
	private EditText txtNombreInstitucion,
					 txtNitInstitucion,
					 editText1,
					 editText2;
	private ControlBD auxiliar;
	
	//sonidos
		SoundPool soundPool;
		int exito;
		int fracaso;
		LocationManager locationManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_institucion_insertar);
		auxiliar = new ControlBD(this);
		txtNombreInstitucion = (EditText) findViewById(R.id.editNombreInstitucion);
		txtNitInstitucion = (EditText) findViewById(R.id.editNitInstitucion);
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
        

	}//fin constructor

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.institucion_insertar, menu);
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

	
	public void insertarInstitucion(View v)	{		
		String nombre=txtNombreInstitucion.getText().toString();		
		String nit = txtNitInstitucion.getText().toString();
		String latitud = editText1.getText().toString();
		String longitud =editText2.getText().toString();
		String info ="";
		//Validando		
		if(nombre == null || nombre.trim() == "")		{
			info = "Nombre inválido";
		}
		
		if(nit == null || nit.trim() == "" || nit.length() != 14) {
			info = "NIT inválido";
		}
		//Avisando errores
		if(info != "")		{
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}
		//Creando inserción
		Institucion institucion=new Institucion(nombre,nit);
		institucion.setLatitud(latitud);
		institucion.setLongitud(longitud);
		auxiliar.abrir();
		String regInsertados=auxiliar.insertar(institucion);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
		
		//sonidos
		   if(regInsertados.length()<=20){
		            	 soundPool.play(exito, 1, 1, 1, 0, 1);
		            }
		            else{
		            soundPool.play(fracaso, 1, 1, 1, 0, 1);

		            }
	}
	

}
