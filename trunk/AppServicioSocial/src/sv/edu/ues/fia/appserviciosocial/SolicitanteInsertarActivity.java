package sv.edu.ues.fia.appserviciosocial;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SolicitanteInsertarActivity extends Activity {

	private EditText txtNombre, txtTelefono, txtEmail, txtNitInstitucion,
			txtCargo;

	private static int TAKE_PICTURE = 1;
	private String name = "";
	private Uri file;
	private ImageView image;
	private File photo;
	private String path;

	private ControlBD auxiliar;
	// sonidos
	SoundPool soundPool;
	int exito;
	int fracaso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitante_insertar);
		txtNombre = (EditText) findViewById(R.id.editNombre);
		txtEmail = (EditText) findViewById(R.id.editCorreo);
		txtTelefono = (EditText) findViewById(R.id.editTelefono);
		txtNitInstitucion = (EditText) findViewById(R.id.editNitInstitucionSolicitante);
		txtCargo = (EditText) findViewById(R.id.editcargo);
		image = (ImageView)findViewById(R.id.mainimage);
		auxiliar = new ControlBD(this);

		// sonidos
		soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
		exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
		fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solicitante_insertar, menu);
		return true;
	}

	public void insertarSolicitante(View v) {
		String nombre = txtNombre.getText().toString(), telefono = txtTelefono
				.getText().toString(), email = txtEmail.getText().toString(), nitInstitucion = txtNitInstitucion
				.getText().toString(), idCargo = txtCargo.getText().toString();

		try {
			if (!vacio(nombre, "Nombre") && !vacio(telefono, "telefono")
					&& !vacio(email, "E-mail")
					&& !vacio(nitInstitucion, "NIT institución")
					&& !vacio(idCargo, "ID Cargo"))
				if (telefono.length() == 8)
					if (nitInstitucion.length() == 14) {
						auxiliar.abrir();
						Institucion institucion = auxiliar
								.consultarInstitucion(nitInstitucion);
						if (institucion == null){
							Toast.makeText(this, "Institución no existe",
									Toast.LENGTH_LONG).show();
						soundPool.play(fracaso, 1, 1, 1, 0, 1);
						}
						else {
							String idInstitucion = institucion
									.getIdInstitucion();
							ArrayList<Cargo> datos = auxiliar.consultarCargo(
									idCargo, 0);
							if (datos != null) {
								Cargo cargo = datos.get(0);
								idCargo = Integer.toString(cargo.getIdCargo());

								if (idCargo == null){
									Toast.makeText(this, "ID cargo no existe",
											Toast.LENGTH_LONG).show();
								soundPool.play(fracaso, 1, 1, 1, 0, 1);}
								else {
									Solicitante solicitante = new Solicitante(
											idInstitucion, idCargo, nombre,
											telefono, email,path);
									String idAsignado = auxiliar
											.insertar(solicitante);
									// sonido
									soundPool.play(exito, 1, 1, 1, 0, 1);
									Toast.makeText(this, "Registro insertado",
											Toast.LENGTH_SHORT).show();
									Toast.makeText(
											this,
											"ID asignado a solicitante : "
													+ idAsignado,
											Toast.LENGTH_LONG).show();

								}
							} else {
								// sonido
								soundPool.play(fracaso, 1, 1, 1, 0, 1);
								Toast.makeText(this,
										"Cago no existe : " + idCargo,
										Toast.LENGTH_LONG).show();
							}

						}
						auxiliar.cerrar();
					} else
						Toast.makeText(this, "NIT no válido ",
								Toast.LENGTH_LONG).show();
				else
					Toast.makeText(this, "telefono no válido ",
							Toast.LENGTH_LONG).show();

		} catch (Exception e) {
			soundPool.play(fracaso, 1, 1, 1, 0, 1);
			Toast.makeText(this, "Error " + e, Toast.LENGTH_LONG).show();
		}
	}

	private boolean vacio(String campo, String nombreCampo) {
		if (campo.matches("")) {
			Toast.makeText(this, nombreCampo + " esta vacio ",
					Toast.LENGTH_LONG).show();
			return true;
		} else
			return false;
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
	}
}
