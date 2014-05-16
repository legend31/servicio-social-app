package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

public class ProyectoActualizarActivity extends Activity {
	
	private TableLayout tabla;
	private EditText txtNombre;
	private EditText txtCodigoProyecto;
	private EditText txtTipoProyecto;
	private EditText txtEncargado;
	private EditText txtSolicitante;
	private EditText txtProyecto;
	private Button btnActualizar;
	private ControlBD helper;
	private Button btnConsultar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proyecto_actualizar);
		
		tabla = (TableLayout) findViewById(R.id.TablaDeDatosProyecto);
		tabla.setVisibility(View.INVISIBLE);
		btnActualizar = (Button) findViewById(R.id.btnActualizarProyecto);
		btnActualizar.setVisibility(View.INVISIBLE);
		txtProyecto = (EditText)findViewById(R.id.txtIDProyecto);
		txtNombre = (EditText)findViewById(R.id.txtNombreProyect);
		txtCodigoProyecto = (EditText) findViewById(R.id.txtCodigoProyecto);
		txtTipoProyecto = (EditText) findViewById(R.id.txtCodigoTipoProyecto);
		txtEncargado = (EditText) findViewById(R.id.txtEncargado);
		txtSolicitante = (EditText) findViewById(R.id.txtSolicitante);
		helper = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actualizar_proyecto, menu);
		return true;
	}
	

	public void consultarProyecto(View v)
	{
		
		helper.abrir();
		Proyecto proyecto = helper.consultarProyecto(txtProyecto.getText().toString());
		helper.cerrar();
		if(proyecto == null)
		{
			tabla.setVisibility(View.INVISIBLE);
			btnActualizar.setVisibility(View.INVISIBLE);
			Toast.makeText(this, "Proyecto con ID " +txtProyecto.getText().toString() +" no encontrado", Toast.LENGTH_LONG).show();
			return;
		}
		else{
			tabla.setVisibility(View.VISIBLE);
			btnActualizar.setVisibility(View.VISIBLE);
			txtNombre.setText(proyecto.getNombre());
			txtCodigoProyecto.setText(String.valueOf(proyecto.getIdProyecto()));
			txtTipoProyecto.setText(String.valueOf(proyecto.getIdTipoProyecto()));
			txtEncargado.setText(String.valueOf(proyecto.getIdEncargado()));
			txtSolicitante.setText(String.valueOf(proyecto.getIdSolicitante()));
		}
	}
	
	public void actualizarProyecto(View v)
	{
		String nombre=txtNombre.getText().toString();
		String codeProyect=txtCodigoProyecto.getText().toString();
		String codeTipoProyecto=txtTipoProyecto.getText().toString();
		String encargado = txtEncargado.getText().toString();
		String solicitante = txtSolicitante.getText().toString();
		String info = "";
		//Validando
		if(nombre == null || nombre.equals(""))
		{
			info = "Nombre inválido";
		}
		//Avisando errores
		if(info != "")
		{
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}
		//Creando inserción
		Proyecto proyecto = new Proyecto();
		proyecto.setIdProyecto(Integer.valueOf(codeProyect));
		proyecto.setNombre(nombre);
		proyecto.setIdTipoProyecto(Integer.valueOf(codeTipoProyecto));
		proyecto.setIdEncargado(Integer.valueOf(encargado));
		proyecto.setIdSolicitante(Integer.valueOf(solicitante));
		
		helper.abrir();
		String regInsertados=helper.actualizar(proyecto);
		helper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
	}


}
