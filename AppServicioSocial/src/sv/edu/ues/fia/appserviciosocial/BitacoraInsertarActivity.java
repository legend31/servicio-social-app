package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BitacoraInsertarActivity extends Activity {
	
	ControlBD auxiliar;
	private EditText editText1;
	private EditText editText2;
	private EditText editText3;
	private EditText editText4;
	private EditText editText5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitacora_insertar);
		auxiliar = new ControlBD(this);
		//Aqui se relacionan los view con las variables
		editText1=(EditText) findViewById(R.id.editText1);
		editText2=(EditText) findViewById(R.id.editText2);
		 editText3=(EditText) findViewById(R.id. editText3);
		 editText4=(EditText) findViewById(R.id.editText4);
		 editText5=(EditText) findViewById(R.id.editText5);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bitacora_insertar, menu);
		return true;
	}
public void insertarBitacora(View v){
	
	String carnet=editText1.getText().toString();
	String idProyecto =editText2.getText().toString();
	String idTipoTrabajo = editText3.getText().toString();
	String fecha =editText4.getText().toString();
	String descripcion = editText5.getText().toString();
	String info = "";
	//Validando
	if(carnet == null || carnet.trim() == "" || carnet.length() != 7)
	{
		info = "Carnet inválido";
	}
	if(idProyecto == null || idProyecto.trim() == "")
	{
		info = "idProyecto inválido";
	}
	if(idTipoTrabajo == null || idTipoTrabajo.trim() == "" )
	{
		info = "idTipoTrabajo inválido";
	}
	if(fecha == null || fecha.trim() == "" || fecha.length() != 10)
	{
		info = "fecha inválida";
	}
	if(descripcion == null || descripcion.trim() == "" )
	{
		info = "descripcion inválida";
	}
	
	//Avisando errores
	if(info != "")
	{
		Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
		return;
	}
	//Realizar la insercion en la base de datos
	Bitacora objBitacora= new Bitacora();
	objBitacora.setCarnet(carnet);
	objBitacora.setIdProyecto(Integer.parseInt(idProyecto));
	objBitacora.setIdTipoTrabajo(Integer.parseInt(idTipoTrabajo));
	objBitacora.setFecha(fecha);
	objBitacora.setdescripcion(descripcion);
	auxiliar.abrir();
	String regInsertados=auxiliar.insertar(objBitacora);
	auxiliar.cerrar();
	Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
}
	
}
