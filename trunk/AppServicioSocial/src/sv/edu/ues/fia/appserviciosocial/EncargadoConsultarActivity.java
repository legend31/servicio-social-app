package sv.edu.ues.fia.appserviciosocial;


import java.util.ArrayList;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class EncargadoConsultarActivity extends Activity implements OnItemSelectedListener {
	 ControlBD base;
	 EditText txtBusqueda;
	 int seleccion;
	 int largoCadena;
	 ArrayList<EncargadoServicioSocial> datos;
	 ListView li;
	 

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
         //datos= base.consultarEncargadoServicioSocial(busqueda, seleccion);
       base.cerrar();
         if(datos == null)
         {
                 Toast.makeText(this, "Registro " +busqueda +" no encontrado", Toast.LENGTH_LONG).show();
                 return;
         }
         
         else{
        	 Toast.makeText(this, "Registro " +busqueda +"encontrado", Toast.LENGTH_LONG).show();
        	 
        	 li=(ListView)findViewById(R.id.listView1);
        	 ArrayAdapter<EncargadoServicioSocial> adaptador=new ArrayAdapter<EncargadoServicioSocial> (this,android.R.layout.simple_list_item_1, datos);
        				
        	li.setAdapter(adaptador);
        	 //llenado de lista con una concatenacion de dos datos significantes de la tabla, puede ser el id junto con el nombre
        	 //al pulsar abre un activity
        	
                 
         } 
         
 }
	
	
	
public void onNothingSelected(AdapterView<?> arg0){
	
}
	

}
