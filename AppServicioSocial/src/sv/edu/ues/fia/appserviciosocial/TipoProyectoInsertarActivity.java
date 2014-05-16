package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TipoProyectoInsertarActivity extends Activity {
	ControlBD helper;
	EditText txtTipoProyecto;
	EditText txtNombreTipoProyecto;
	String mensaje;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_proyecto_insertar);
		//txtTipoProyecto = (EditText)findViewById(R.id.txtTipoProyectoCodigo);
		txtNombreTipoProyecto = (EditText)findViewById(R.id.txtNombreTipoProyecto); 
		helper = new ControlBD(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_proyecto_insertar, menu);
		return true;
		
	}
	
	public void insertarTipoProyecto(View v)
	{
		mensaje="";
		//String codeTipoProyecto = txtTipoProyecto.getText().toString();
		String nombre = txtNombreTipoProyecto.getText().toString();
		if(nombre.equals("") || nombre==null){
			mensaje="Nombre Invalido. Intente de nuevo";
			//Toast.makeText(this,"Nombre Invalido. Intente de nuevo",Toast.LENGTH_SHORT).show();
		}
		if(mensaje!=""){
			Toast.makeText(this,"Nombre Invalido. Intente de nuevo",Toast.LENGTH_SHORT).show();
			return;
		}
		TipoProyecto tipoProyecto = new TipoProyecto();
		tipoProyecto.setNombre(nombre);
		helper.abrir();
		String regInsertados = helper.insertar(tipoProyecto);
		helper.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();

	}
	
	public void limpiar(View v){
		
		
	}

}
