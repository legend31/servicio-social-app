package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Build;

public class SolicitanteConsultarActivity extends Activity {

	private EditText txtId, txtNombre, txtTelefono, txtEmail, txtInstitucion,
			txtIdCargo;
	private ControlBD auxiliar;
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solicitante_consultar);

		txtId = (EditText) findViewById(R.id.editIdSolicitante);
		txtNombre = (EditText) findViewById(R.id.edit_Nombre);
		txtTelefono = (EditText) findViewById(R.id.edit_Telefono);
		txtEmail = (EditText) findViewById(R.id.edit_email);
		txtInstitucion = (EditText) findViewById(R.id.edit_institucion);
		txtIdCargo = (EditText) findViewById(R.id.editCargo);
		image = (ImageView) findViewById(R.id.mainImageSolicitante);
		auxiliar = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.solicitante_consultar, menu);
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

	public void consultarSolicitante(View v) {
		txtNombre.setText("");
		txtTelefono.setText("");
		txtInstitucion.setText("");
		txtIdCargo.setText("");
		txtEmail.setText("");

		String id = txtId.getText().toString();
		// Validando
		if (id == null || id.matches("")) {
			Toast.makeText(this, "ID inválido", Toast.LENGTH_LONG).show();
			return;
		}

		auxiliar.abrir();

		Solicitante solicitante = auxiliar.consultarSolicitante(id);

		if (solicitante == null) {

			txtNombre.setText("");
			Toast.makeText(this, "Solicitante con id " + id + " no encontrado",
					Toast.LENGTH_LONG).show();
			return;
		} else {

			Institucion institucion = auxiliar
					.consultarInstitucionById(solicitante.getIdInstitucion());

			txtNombre.setText(solicitante.getNombre());
			txtTelefono.setText(solicitante.getTelefono());
			txtEmail.setText(solicitante.getCorreo());
			txtIdCargo.setText(solicitante.getIdCargo());
			String path = solicitante.getPath();
			image.setImageBitmap(BitmapFactory.decodeFile(path));
			
			if (institucion != null)
				txtInstitucion.setText(institucion.getNombre());
			else
				Toast.makeText(
						this,
						"No se encontro institución "
								+ solicitante.getIdInstitucion(),
						Toast.LENGTH_LONG).show();

		}

		auxiliar.cerrar();
	}

}
