package sv.edu.ues.fia.appserviciosocial;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONObject;

import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.media.MediaScannerConnection;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class AlumnoInsertarActivity extends Activity {

	ControlBD auxiliar;
	private EditText txtCarnet;
	private EditText txtNombre;
	private EditText txtTelefono;
	private EditText txtDui;
	private EditText txtNit;
	private EditText txtEmail;
	private String path="";
	private String urlExterno = "http://hv11002pdm115.hostei.com/serviciosweb/insertar_alumno.php";
	private String urlLocal = "http://10.0.2.2:8080/AppServicioSocial/webresources/sv.edu.ues.fia.appserviciosocial.entidad.alumno/";
	//private String urlLocal = "http://168.243.8.13:8080/AppServicioSocial/webresources/sv.edu.ues.fia.appserviciosocial.entidad.alumno/";
	static List<Alumno> listaAlumnos;

	SoundPool soundPool;
	int exito;
	int fracaso;

	private static int TAKE_PICTURE = 1;
	private String name = "";
	private Uri file;
	private ImageView image;
	private File photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumno_insertar);

		// Código para permitir la conexión a internet en el mismo hilo
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		auxiliar = new ControlBD(this);
		txtCarnet = (EditText) findViewById(R.id.txtCarnet);
		txtNombre = (EditText) findViewById(R.id.txtNombre);
		txtTelefono = (EditText) findViewById(R.id.txtTelefono);
		txtDui = (EditText) findViewById(R.id.txtDui);
		txtNit = (EditText) findViewById(R.id.txtNit);
		txtEmail = (EditText) findViewById(R.id.txtEmail);

		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
		fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

		image = (ImageView) findViewById(R.id.mainimage);

		/*
		 * if(savedInstanceState != null)
		 * if(savedInstanceState.getString("Foto")!=null){
		 * image.setImageURI(Uri.parse(savedInstanceState.getString("Foto")));
		 * file = Uri.parse(savedInstanceState.getString("Foto")); }
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alumno_insertar, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	public void insertarAlumno(View v) {
		String carnet = txtCarnet.getText().toString();
		String nombre = txtNombre.getText().toString();
		String telefono = txtTelefono.getText().toString();
		String dui = txtDui.getText().toString();
		String nit = txtNit.getText().toString();
		String email = txtEmail.getText().toString();
		String info = "";
		String url;
		// Validando
		if (carnet == null || carnet.trim() == "" || carnet.length() != 7) {
			info = "Carnet inválido";
			
		}
		if (nombre == null || nombre.trim() == "") {
			info = "Nombre inválido";
			
		}
		if (telefono == null || telefono.trim() == "" || telefono.length() != 8) {
			info = "Teléfono inválido";
			
		}
		if (dui == null || dui.trim() == "" || dui.length() != 9) {
			info = "DUI inválido";
			
		}
		if (nit == null || nit.trim() == "" || nit.length() != 14) {
			info = "NIT inválido";
			
		}
		if (email == null
				|| email.trim() == ""
				|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
						.matches()) {
			info = "E-mail inválido";
			
		}
		// Avisando errores
		if (info != "") {
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;
		}
		// Creando inserción
		Alumno alumno = new Alumno();
		alumno.setCarnet(carnet);
		alumno.setNombre(nombre);
		alumno.setTelefono(telefono);
		alumno.setDui(dui);
		alumno.setNit(nit);
		alumno.setEmail(email);
		alumno.setPath(path);
		alumno.setEnviado("false");
		auxiliar.abrir();
		String regInsertados = auxiliar.insertar(alumno);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

		if (regInsertados.length() <= 20) {
			soundPool.play(exito, 1, 1, 1, 0, 1);
		} else {
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
		}

	}

	public void insertarServidor(View v) {
		// Aqui se deben buscar todos los registros que tengan enviado=false
		// y se enviaran al servidor y se pondrán enviado=true
		auxiliar.abrir();
		ArrayList<Alumno> alumnosAEnviar = auxiliar.consultarAlumnoNoEnviado();
		
		String carnet = "", nombre = "", telefono = "", dui = "", nit = "", email = "", path = "";
		Date fecha = new Date();
        fecha = Calendar.getInstance().getTime();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String actualizado = formato.format(fecha);
		if (alumnosAEnviar != null) {
			for (int i = 0; i < alumnosAEnviar.size(); i++) {
				carnet = alumnosAEnviar.get(i).getCarnet();
				nombre = alumnosAEnviar.get(i).getNombre();
				telefono = alumnosAEnviar.get(i).getTelefono();
				dui = alumnosAEnviar.get(i).getDui();
				nit = alumnosAEnviar.get(i).getNit();
				email = alumnosAEnviar.get(i).getEmail();
				path = alumnosAEnviar.get(i).getPath();
				// Inserción en el servidor PHP
				String url = urlExterno + "?carnet=" + carnet + "&nombre="
						+ URLEncoder.encode(nombre) + "&telefono=" + telefono+ "&dui=" + dui + "&nit=" 
						+ nit + "&email=" + email  + "&fechaact=" +URLEncoder.encode(actualizado)+ "&path=" + URLEncoder.encode(path);
				Log.v("la url de php", url);
				ControladorServicio.insertarObjeto(url, this);
				// Subida de foto al servidor
				if(!path.equals(""))
				{
					ControladorServicio.subirImagen(path, this);
				}
				auxiliar.establecerAlumnoEnviado(carnet);
			}
		}
		auxiliar.cerrar();
	}

	
	public void insertarServidorJava(View v) {
		// Aqui se deben buscar todos los registros que tengan enviado=false
		// y se enviaran al servidor y se pondrán enviado=true
		auxiliar.abrir();
		ArrayList<Alumno> alumnosAEnviar = auxiliar.consultarAlumnoNoEnviado();
		
		String carnet = "", nombre = "", telefono = "", dui = "", nit = "", email = "", path = "";
		Date fecha = new Date();
        fecha = Calendar.getInstance().getTime();
        SimpleDateFormat formato =  new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        String actualizado = formato.format(fecha);
		if (alumnosAEnviar != null) {
			for (int i = 0; i < alumnosAEnviar.size(); i++) {//25193645
				carnet = alumnosAEnviar.get(i).getCarnet();
				nombre = alumnosAEnviar.get(i).getNombre();
				telefono = alumnosAEnviar.get(i).getTelefono();
				dui = alumnosAEnviar.get(i).getDui();
				nit = alumnosAEnviar.get(i).getNit();
				email = alumnosAEnviar.get(i).getEmail();
				path = alumnosAEnviar.get(i).getPath();
				if(path.equals(""))
					path = " ";
				//Inserción en el servidor Glassfish
				
				JSONObject alumno = new JSONObject();
				try{
					alumno.put("carnet", carnet);
					alumno.put("nombre", nombre);
					alumno.put("telefono", telefono);
					alumno.put("dui", dui);
					alumno.put("nit", nit);
					alumno.put("email", email);
					alumno.put("path", path);
					alumno.put("fechaact", actualizado);
					try{ControladorServicio.insertarObjeto(urlLocal, alumno, this);}
					catch(Exception e){return;}
					auxiliar.establecerAlumnoEnviado(carnet);
				}catch(Exception e){
					Toast.makeText(this, "Error en los datos", Toast.LENGTH_LONG).show();
					soundPool.play(fracaso, 1, 1, 1, 0, 1);
				}
			}
		}
		auxiliar.cerrar();
	}
	
	public void Scan(View v) {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		startActivityForResult(intent, 0);
	}

	public void tomarFoto(View view) {
		// name = Environment.getExternalStorageDirectory() + "/foto.jpg";
		photo = new File(Environment.getExternalStorageDirectory(),
				String.valueOf(Calendar.getInstance().getTimeInMillis())
						+ ".jpg");
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		int code = TAKE_PICTURE;

		file = Uri.fromFile(photo);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
		startActivityForResult(intent, code);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String datos = intent.getStringExtra("SCAN_RESULT");
				// Poner los datos en los campos respectivos
				StringTokenizer tokens = new StringTokenizer(datos, ":;");
				int i = 0;
				while (tokens.hasMoreTokens()) {
					String elemento = tokens.nextToken();
					if (i == 0 && !elemento.equals("QRAlumnoID")) {
						Toast.makeText(
								this,
								"El código QR no corresponde a la funcionalidad de esta aplicación",
								Toast.LENGTH_SHORT).show();
						soundPool.play(fracaso, 1, 1, 1, 0, 1);
						return;
					}
					if (i == 2) {
						txtCarnet.setText(elemento);
					}
					if (i == 4) {
						txtNombre.setText(elemento);
					}
					if (i == 6) {
						txtTelefono.setText(elemento);
					}
					if (i == 8) {
						txtDui.setText(elemento);
					}
					if (i == 10) {
						txtNit.setText(elemento);
					}
					if (i == 12) {
						txtEmail.setText(elemento);
					}
					i++;
				}
			} else if (resultCode == RESULT_CANCELED) {
				// Si se cancelo la captura.
				Toast.makeText(this, "Se canceló la captura del código QR",
						Toast.LENGTH_SHORT).show();
				soundPool.play(fracaso, 1, 1, 1, 0, 1);
			}
		}

		if (requestCode == TAKE_PICTURE)
			if (resultCode == RESULT_OK) {
				image.setImageBitmap(BitmapFactory.decodeFile(photo
						.getAbsolutePath()));
				// esta direccion es la que se guarda en la BD
				path = photo.getAbsolutePath();

				// esta parte es para guardar la imagen en la galeria, pero
				// creo q no funciona REVISAR MAS TARDE
				new MediaScannerConnectionClient() {
					private MediaScannerConnection msc = null;
					{
						msc = new MediaScannerConnection(
								getApplicationContext(), this);
						msc.connect();
					}

					public void onMediaScannerConnected() {
						msc.scanFile(name, null);
					}

					public void onScanCompleted(String path, Uri uri) {
						msc.disconnect();
					}
				};
			} else
				Toast.makeText(getApplicationContext(), "fotografia no tomada",
						Toast.LENGTH_SHORT).show();
		soundPool.play(fracaso, 1, 1, 1, 0, 1);
	}
}
