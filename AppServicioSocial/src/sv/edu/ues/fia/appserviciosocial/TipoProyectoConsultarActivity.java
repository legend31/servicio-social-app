package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TipoProyectoConsultarActivity extends Activity {

	ControlBD helper;
	EditText txtCodigoTipoProyecto;
	EditText txtNombre;
	EditText txtConsulta;
	String mensaje;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_proyecto_consultar);

		txtConsulta = (EditText) findViewById(R.id.txtConsultaTipoProyecto);
		
		txtCodigoTipoProyecto = (EditText) findViewById(R.id.txtCodigoTipoProyectoConsulta);
		txtNombre = (EditText) findViewById(R.id.txtConsultaNombreTipoProyecto);
		helper = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_proyecto_consultar, menu);
		return true;
	}

	public void consultarTipoProyecto(View v){
		
		String idTipoProyecto = txtConsulta.getText().toString();
		helper.abrir();
		TipoProyecto tipoProyecto = helper.consultarTipoProyecto(idTipoProyecto);
		helper.cerrar();
		if (tipoProyecto == null)
			Toast.makeText(
					this,
					"Tipo de Proyecto con ID " + idTipoProyecto+" no encontrado", Toast.LENGTH_LONG).show();
		else {
			
			txtNombre.setText(tipoProyecto.getNombre());
			txtCodigoTipoProyecto.setText(String.valueOf(tipoProyecto.getIdTipoProyecto()));
		}
	}

	public void limpiarTexto(View v) {
		
		txtCodigoTipoProyecto.setText("");
		txtNombre.setText("");

	}

}
