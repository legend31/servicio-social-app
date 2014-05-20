package sv.edu.ues.fia.appserviciosocial;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class BitacoraActualizarActivity extends Activity {

	ControlBD auxiliar;
	EditText txtIdBitacora;	
	Button botonBuscar;
	EditText editText1;
	EditText editText2;
	EditText editText3;
	EditText editText4;
	EditText editText5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitacora_actualizar);
		auxiliar = new ControlBD(this);
		txtIdBitacora = (EditText) findViewById(R.id.txtIdBitacora);		
		botonBuscar = (Button) findViewById(R.id.botonBuscar);
		editText1= (EditText) findViewById(R.id.editText1);
		editText2= (EditText) findViewById(R.id.editText2);
		editText3= (EditText) findViewById(R.id.editText3);
		editText4= (EditText) findViewById(R.id.editText4);
		editText5= (EditText) findViewById(R.id.editText5);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bitacora_actualizar, menu);
		return true;
	}
	public void modificarBitacora(View v){
		String idBitacora = txtIdBitacora.getText().toString();
		String mensaje;
		String carnet=editText1.getText().toString();
		String idProyecto =editText2.getText().toString();
		String idTipoTrabajo = editText3.getText().toString();
		String fecha =editText4.getText().toString();
		String descripcion = editText5.getText().toString();
		String info = "";
		//Validando
		if(idBitacora == null || idBitacora.trim() == "")
		{
			Toast.makeText(this, "Id Bitacora inválido", Toast.LENGTH_LONG).show();
			return;
			
		}
		
		if(botonBuscar.getText().toString().trim().length()==6){
			
		auxiliar.abrir();
		Bitacora objBitacora =auxiliar.consultarBitacora(idBitacora);
		auxiliar.cerrar();
		if(objBitacora == null)
		{
			
			botonBuscar.setText("Buscar");
			Toast.makeText(this, "Bitacora no encontrado", Toast.LENGTH_LONG).show();
			return;
		}else{
			
		    
			editText1.setText(objBitacora.getCarnet());			
			editText2.setText(String.valueOf(objBitacora.getIdProyecto()));			
			editText3.setText(String.valueOf(objBitacora.getIdTipoTrabajo()));			
			editText4.setText(objBitacora.getFecha());			
			editText5.setText(objBitacora.getdescripcion());
			
			//Llenando tabla
			
			botonBuscar.setText("Modificar");
		     }
		}else{
			Bitacora objBitacora= new Bitacora();
			objBitacora.setId(Integer.parseInt(idBitacora));
			objBitacora.setCarnet(carnet);
			objBitacora.setIdProyecto(Integer.parseInt(idProyecto));
			objBitacora.setIdTipoTrabajo(Integer.parseInt(idTipoTrabajo));
			objBitacora.setFecha(fecha);
			objBitacora.setdescripcion(descripcion);
			auxiliar.abrir();
			mensaje=auxiliar.actualizar(objBitacora);
			auxiliar.cerrar();
			Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();			
			botonBuscar.setText("Buscar");
		}
		
	}

}
