package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class TipoTrabajoEliminar extends Activity {
	//Variables globales
			ControlBD auxiliar;	
			private EditText editText1;
			private EditText editText2;
			private EditText editText3;
			private Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_trabajo_eliminar);
		//Relacionar variables globales con los controles del layout	
		auxiliar = new ControlBD(this);	//constructor de la conexion bd
		editText1=(EditText) findViewById(R.id.editText1);
		editText2=(EditText) findViewById(R.id.editText2);
		editText3=(EditText) findViewById(R.id.editText3);
		button1=(Button) findViewById(R.id.btnQR);
		button1.setVisibility(View.INVISIBLE);
		editText2.setVisibility(View.INVISIBLE);
		editText3.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_trabajo_eliminar, menu);
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

	public void eliminarTipoTrabajo(View v){
		String mensaje;
		String idTipoTrabajo=editText1.getText().toString();
		TipoTrabajo objTipoTrabajo= new TipoTrabajo();
		objTipoTrabajo.setIdTipoTrabajo(idTipoTrabajo);
		auxiliar.abrir();
		mensaje=auxiliar.eliminar(objTipoTrabajo);
		auxiliar.cerrar();
		Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
		button1.setVisibility(View.INVISIBLE);
		editText2.setVisibility(View.INVISIBLE);
		editText3.setVisibility(View.INVISIBLE);
	}
}
