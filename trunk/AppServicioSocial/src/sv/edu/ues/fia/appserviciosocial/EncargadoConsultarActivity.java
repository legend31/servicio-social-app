package sv.edu.ues.fia.appserviciosocial;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;


public class EncargadoConsultarActivity extends Activity implements OnItemSelectedListener {
	 ControlBD base;
	 EditText txtBusqueda;
	 int seleccion;
	 int largoCadena;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encargado_consultar);
		
		base = new ControlBD(this);
        txtBusqueda= (EditText) findViewById(R.id.edtBuscarEncargado);
       
		
		//spinner
		Spinner spinner=(Spinner) findViewById(R.id.spinnerEncargado);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource
				(this, R.array.camposArrayEncargado, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}


	public void onItemSelected (AdapterView<?> parent, View view, int pos, long id){
		Toast.makeText(parent.getContext(), "Selecciono la opcion: " + pos, Toast.LENGTH_SHORT).show();
		seleccion=pos;
		
	}
	
	
	 public void consultarEncargado(View v) {
         
         String  busqueda = txtBusqueda.getText().toString();
         //Validando
         if(busqueda == null || busqueda.trim() == "" )
         {
                 Toast.makeText(this, "No se ingreso informacion para la busqueda.", Toast.LENGTH_LONG).show();
                 return;
                 
         }
         base.abrir();
         EncargadoServicioSocial encargado = base.consultarEncargadoServicioSocial(busqueda, seleccion);
       base.cerrar();
         if(encargado == null)
         {
                 Toast.makeText(this, "Registro " +busqueda +" no encontrado", Toast.LENGTH_LONG).show();
                 return;
         }
         
         else{
        	 Toast.makeText(this, "Registro " +busqueda +"encontrado", Toast.LENGTH_LONG).show();
        	 //llenado de lista con una concatenacion de dos datos significantes de la tabla, puede ser el id junto con el nombre
        	 //al pulsar abre un activity
        	 /*
                 String [] datos = new String[10];
                 datos[0] = "Nombre:";
                 datos[1] = alumno.getNombre();
                 datos[2] = "Teléfono:";
                 datos[3] = alumno.getTelefono();
                 datos[4] = "DUI:";
                 datos[5] = alumno.getDui();
                 datos[6] = "NIT:";
                 datos[7] = alumno.getNit();
                 datos[8] = "E-mail:";
                 datos[9] = alumno.getEmail();
                 
                 //Llenando tabla
                 ArrayAdapter<String> adaptador =
                         new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
                 gdvTabla.setAdapter(adaptador);
                 lblDatos.setVisibility(View.VISIBLE);
                 gdvTabla.setVisibility(View.VISIBLE);
                 */
         }
         
 }
	
	
	
public void onNothingSelected(AdapterView<?> arg0){
	
}
	

}
