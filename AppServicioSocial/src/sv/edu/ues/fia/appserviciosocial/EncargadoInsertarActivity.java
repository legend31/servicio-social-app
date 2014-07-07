package sv.edu.ues.fia.appserviciosocial;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.SoundPool;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class EncargadoInsertarActivity extends Activity {

	ControlBD base;
	// private EditText txtIdEncargado;
	private EditText txtNombre;
	private EditText txtEmail;
	private EditText txtTelefono;
	private EditText txtFacultad;
	private EditText txtEscuela;

	// sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;

	private static int TAKE_PICTURE = 1;
	private String name = "";
	private Uri file;
	private ImageView image;
	private File photo;
	private String path="";
	
	//servicio
	private String urlExterno = "http://hv11002pdm115.hostei.com/serviciosweb/insertar_encargado.php";
	static List<EncargadoServicioSocial> listaEncargados;

	
	//java
	//private String urlLocal = "http://168.243.8.13:8080/AppServicioSocial/webresources/sv.edu.ues.fia.appserviciosocial.entidad.encargadoserviciosocial/";
	private String urlLocal = "http://10.0.2.2:8080/AppServicioSocial/webresources/sv.edu.ues.fia.appserviciosocial.entidad.encargadoserviciosocial/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encargado_insertar);

		base = new ControlBD(this);
		//servicio
		// Código para permitir la conexión a internet en el mismo hilo
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		txtNombre = (EditText) findViewById(R.id.txtNombreEncargado);
		txtEmail = (EditText) findViewById(R.id.txtEmailEncargado);
		txtTelefono = (EditText) findViewById(R.id.txtTelefonoEncargado);
		txtFacultad = (EditText) findViewById(R.id.txtFacultadEncargado);
		txtEscuela = (EditText) findViewById(R.id.txtEscuelaEncargado);

		// sonidos
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
		fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

		// imagenes
		image = (ImageView) findViewById(R.id.mainimage);
		
	
	}

	public void insertarEncargado(View v) {
		// String id=txtIdEncargado.getText().toString();
		String nombre = txtNombre.getText().toString();
		String email = txtEmail.getText().toString();
		String telefono = txtTelefono.getText().toString();
		String facultad = txtFacultad.getText().toString();
		String escuela = txtEscuela.getText().toString();
		String error = "";

		if (escuela == null || escuela.trim() == "") {
			error = "Escuela no Ingresada";
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
		}

		if (facultad == null || facultad.trim() == "") {
			error = "Facultad no Ingresada";
			
		}

		if (telefono == null || telefono.trim() == "" || telefono.length() != 8) {
			error = "Teléfono inválido";
			
		}
		if (email == null
				|| email.trim() == ""
				|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
						.matches()) {
			error = "E-mail inválido";
			
		}

		if (nombre == null || nombre.trim() == "") {
			error = "Nombre no Ingresado";
			
		}
		// if(txtIdEncargado.getText().toString()==null
		// ||txtIdEncargado.getText().toString().trim()=="")
		// {
		// error = "ID no Ingresado";
		// }
		// Avisando errores
		if (error != "") {
			Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			return;
		}
		// Creando inserción
		EncargadoServicioSocial encargado = new EncargadoServicioSocial();
		// encargado.setIdEncargado(id);
		encargado.setNombre(nombre);
		encargado.setEmail(email);
		encargado.setTelefono(telefono);
		encargado.setFacultad(facultad);
		encargado.setEscuela(escuela);
		encargado.setPath(path);
		encargado.setEnviado("false");

		base.abrir();
		String regInsertados = base.insertar(encargado);
		base.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

		// sonidos
		if (regInsertados.length() <= 20) {
			soundPool.play(exito, 1, 1, 1, 0, 1);
		} else {
			soundPool.play(fracaso, 1, 1, 1, 0, 1);

		}
	}

	public void limpiarEncargado(View v) {
		// txtIdEncargado.setText("");
		txtNombre.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		txtFacultad.setText("");
		txtEscuela.setText("");
		image.setImageDrawable(null);
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
		if (requestCode == TAKE_PICTURE)
			if (resultCode == RESULT_OK) {
				image.setImageBitmap(BitmapFactory.decodeFile(photo
						.getAbsolutePath()));
				// esta direccion es la que se guarda en la BD
				path = photo.getAbsolutePath();

				// esta parte es para guardar la imagen en la galeria
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

	
	
	//Servicio
	public void insertarServidorEncargado (View v) {
		
	
		
		base.abrir();
		ArrayList<EncargadoServicioSocial> encargadoAEnviar = base.consultarEncargadoNoEnviado();
		
		
		String  nombre = "", email = "", telefono = "" ,facultad = "", escuela = "", path = "";
		int idencargado;
		Date fecha = new Date();
        fecha = Calendar.getInstance().getTime();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String actualizado = formato.format(fecha);
        
        if (encargadoAEnviar != null) {
			for (int i = 0; i <  encargadoAEnviar.size(); i++) {
				idencargado=encargadoAEnviar.get(i).getIdEncargado();
				nombre =  encargadoAEnviar.get(i).getNombre();
				email =  encargadoAEnviar.get(i).getEmail();
				telefono =  encargadoAEnviar.get(i).getTelefono();
				facultad =  encargadoAEnviar.get(i).getFacultad();
				escuela =   encargadoAEnviar.get(i).getEscuela();
				path =  encargadoAEnviar.get(i).getPath();
				
				
				
				//Toast.makeText(this,idencargado + nombre + email + telefono + facultad + escuela + path , Toast.LENGTH_SHORT).show();
				// Inserción en el servidor PHP
			
				String url = urlExterno + "?idencargado=" + idencargado + "&nombre="
						+ URLEncoder.encode(nombre) + "&email=" + email  +"&telefono=" + telefono + "&facultad=" + facultad + "&escuela=" 
						+ escuela + "&fechaact=" +URLEncoder.encode(actualizado)+ "&path=" + URLEncoder.encode(path);
				 
				
				
				
				Log.v("la url de php", url);
				ControladorServicio.insertarObjeto(url, this);
				// Subida de foto al servidor
				
				if(!path.equals(""))
				{
					ControladorServicio.subirImagen(path, this);
				}
				
				base.establecerEncargadoEnviado(idencargado);
			}
		}
		base.cerrar();
		
	}
	
	
	
	
	
	
	public void insertarServidorJavaEncargado(View v) {
		
		base.abrir();
		ArrayList<EncargadoServicioSocial> encargadoAEnviar = base.consultarEncargadoNoEnviado();
		
		
		String  nombre = "", email = "", telefono = "" ,facultad = "", escuela = "", path = "";
		int idencargado;
		Date fecha = new Date();
        fecha = Calendar.getInstance().getTime();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        String actualizado = formato.format(fecha);
        
        if (encargadoAEnviar != null) {
			for (int i = 0; i <  encargadoAEnviar.size(); i++) {
				idencargado=encargadoAEnviar.get(i).getIdEncargado();
				nombre =  encargadoAEnviar.get(i).getNombre();
				email =  encargadoAEnviar.get(i).getEmail();
				telefono =  encargadoAEnviar.get(i).getTelefono();
				facultad =  encargadoAEnviar.get(i).getFacultad();
				escuela =   encargadoAEnviar.get(i).getEscuela();
				path =  encargadoAEnviar.get(i).getPath();
				
				if(path.equals(""))
					path = " ";
				
				//Inserción en el servidor Glassfish
				
				JSONObject encargado = new JSONObject();
				try{
					encargado.put("idencargado", idencargado);
					encargado.put("nombre", nombre);
					encargado.put("telefono", telefono);
					encargado.put("facultad", facultad);
					encargado.put("escuela", escuela);
					encargado.put("email", email);
					encargado.put("path", path);
					encargado.put("fechaact", actualizado);
					try{ControladorServicio.insertarObjeto(urlLocal, encargado, this);}
					catch(Exception e){return;}
					base.establecerEncargadoEnviado(idencargado);
				}catch(Exception e){
					Toast.makeText(this, "Error en los datos", Toast.LENGTH_LONG).show();
					soundPool.play(fracaso, 1, 1, 1, 0, 1);
				}
			}
		}
		base.cerrar();
	}
	
	
	
	
}
