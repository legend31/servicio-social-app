package sv.edu.ues.fia.appserviciosocial;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class TipoTrabajoInsertar extends Activity {

	//Variables globales
	ControlBD auxiliar;
	
	private EditText editText2;
	private EditText editText3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_trabajo_insertar);
		//Relacionar variables globales con los controles del layout
		
		editText2=(EditText) findViewById(R.id.editText2);
		editText3=(EditText) findViewById(R.id.editText3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_trabajo_insertar, menu);
		return true;
	}
	
	public void insertarTipoTrabajo(View v){
		//crear nuevas variables con los valores del layout
		String info = "";
		String nombre=editText2.getText().toString();
		String valor=editText3.getText().toString();
		//validacion
		if(nombre == null || nombre.trim() == "" )
		{
			info = "Debe introducir el nombre";
		}
		if(valor == null || valor.trim() == "" )
		{
			info = "Debe introducir el valor";
		}
		//Avisando errores
		if(info != "")
		{
			Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
			return;
		}
		//Realizar la insercion en la base de datos
		//Creacion del objeto
		TipoTrabajo objTipoTrabajo= new TipoTrabajo();
		//llenar el objeto
		objTipoTrabajo.setNombre(nombre);
		objTipoTrabajo.setValor(valor);
		//realizar la inserción
		auxiliar.abrir();
		String regInsertados=auxiliar.insertar(objTipoTrabajo);
		auxiliar.cerrar();
		Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
		
	}

}
