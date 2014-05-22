package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class TipoTrabajoModificar extends Activity {
	//Variables globales
			ControlBD auxiliar;	
			private EditText editText1;
			private EditText editText2;
			private EditText editText3;
			private Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_trabajo_modificar);
		//Relacionar variables globales con los controles del layout	
		auxiliar = new ControlBD(this);	//constructor de la conexion bd
		editText1=(EditText) findViewById(R.id.editText1);
		editText2=(EditText) findViewById(R.id.editText2);
		editText3=(EditText) findViewById(R.id.editText3);
		button1=(Button) findViewById(R.id.button1);
		button1.setVisibility(View.INVISIBLE);
		editText2.setVisibility(View.INVISIBLE);
		editText3.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_trabajo_modificar, menu);
		return true;
	}
	public void consultarTipoTrabajo(View v){
		String idTipoTrabajo = editText1.getText().toString();
		if(idTipoTrabajo.length()==0)
		{
			Toast.makeText(this, "Id Tipo Trabajo inválido", Toast.LENGTH_LONG).show();
			return;
			
		}
		auxiliar.abrir();
		TipoTrabajo objTipoTrabajo=auxiliar.consultarTipoTrabajo(idTipoTrabajo);
		auxiliar.cerrar();
		if(objTipoTrabajo==null){
			Toast.makeText(this, "Tipo de Trabajo no encontrado", Toast.LENGTH_LONG).show();
			return;
		}else{
			editText2.setText(objTipoTrabajo.getNombre());
			editText3.setText(toString().valueOf(objTipoTrabajo.getValor()));
			button1.setVisibility(View.VISIBLE);
			editText2.setVisibility(View.VISIBLE);
			editText3.setVisibility(View.VISIBLE);
		}
		
	}

	public void modificarTipoTrabajo(View v){
		//crear nuevas variables con los valores del layout
				String info = "";
				String idTipoTrabajo=editText1.getText().toString();
				String nombre=editText2.getText().toString();
				String valor=editText3.getText().toString();
				//validacion
				if(nombre.length()==0 )
				{
					info = "Debe introducir el nombre";
				}
				if(valor.length()==0 )
				{
					info =info+ " Debe introducir el valor";
				}
				//Avisando errores
				if(info.length()!=0)
				{
					Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
					return;
				}
				info="Datos validados";
				Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
				//Realizar la insercion en la base de datos
				//Creacion del objeto
				TipoTrabajo objTipoTrabajo= new TipoTrabajo();
				//llenar el objeto
				objTipoTrabajo.setIdTipoTrabajo(idTipoTrabajo);
				objTipoTrabajo.setNombre(nombre);
				objTipoTrabajo.setValor(valor);
				//realizar la inserción
				auxiliar.abrir();
				String regInsertados=auxiliar.actualizar(objTipoTrabajo);
				auxiliar.cerrar();
				Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
				button1.setVisibility(View.INVISIBLE);
				editText2.setVisibility(View.INVISIBLE);
				editText3.setVisibility(View.INVISIBLE);
	}
}
