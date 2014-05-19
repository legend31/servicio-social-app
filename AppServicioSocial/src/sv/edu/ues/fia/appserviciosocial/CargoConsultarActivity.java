package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;

public class CargoConsultarActivity extends Activity implements OnItemSelectedListener{
	 ControlBD base;
	 EditText txtBusqueda;
	 int seleccion;
	 int indicador;
	 int largoCadena;
	 ArrayList<Cargo> datos;
	 ListView li;
	 private TableLayout tablaDeDatos;
	 private EditText edtNombre;
	 private EditText edtIdCargo;
    private EditText edtDescripcion;
  
    private Button btnAtras;
    private Button btnAdelante;
    Cargo cargo;
    int cantidad;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cargo_consultar);
		
		base = new ControlBD(this);
        txtBusqueda= (EditText) findViewById(R.id.edtBuscarCargo);
       
        //los datos que se mostraran
        tablaDeDatos = (TableLayout) findViewById(R.id.TablaDeDatosCargo);
        tablaDeDatos.setVisibility(View.INVISIBLE);
        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnAtras.setVisibility(View.INVISIBLE);
        btnAdelante = (Button) findViewById(R.id.btnAdelante);
        btnAdelante.setVisibility(View.INVISIBLE);
      
       edtIdCargo = (EditText) findViewById(R.id.edtIdCargo);
        edtNombre = (EditText) findViewById(R.id.edtNombreCargo);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcionCargo);
        
         
         
		//spinner
		Spinner spinner=(Spinner) findViewById(R.id.spinnerCargo);
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource
				(this, R.array.camposArrayCargo, android.R.layout.simple_spinner_item);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
	}


	public void onItemSelected (AdapterView<?> parent, View view, int pos, long id){
		//Toast.makeText(parent.getContext(), "Selecciono la opcion: " + pos, Toast.LENGTH_SHORT).show();
		seleccion=pos;
		
		
	}
	
	
	 public void consultarCargo(View v) {
		 
         
         String  busqueda = txtBusqueda.getText().toString();
         //Validando
         if(busqueda == null || busqueda.trim() == "" )
         {
                 Toast.makeText(this, "No se ingreso informacion para la busqueda.", Toast.LENGTH_LONG).show();
                 tablaDeDatos.setVisibility(View.INVISIBLE);
               
                 btnAtras.setVisibility(View.INVISIBLE);
               
                 btnAdelante.setVisibility(View.INVISIBLE);
                 
                 return;
                 
                 
         }
         
         base.abrir();
         datos= base.consultarCargo(busqueda, seleccion);
       base.cerrar();
         if(datos == null)
         {
                 Toast.makeText(this, "Registro " +busqueda +" no encontrado", Toast.LENGTH_LONG).show();
                 tablaDeDatos.setVisibility(View.INVISIBLE);
                 
                 btnAtras.setVisibility(View.INVISIBLE);
               
                 btnAdelante.setVisibility(View.INVISIBLE);
                 
                 return;
         }
         
         else{
        	
        	 Toast.makeText(this, "Se encontraron" +datos.size() +"registro", Toast.LENGTH_LONG).show();
        	 cantidad=datos.size() - 1;
        	// li=(ListView)findViewById(R.id.listView1);
        	 //ArrayAdapter<EncargadoServicioSocial> adaptador=new ArrayAdapter<EncargadoServicioSocial> (this,android.R.layout.simple_list_item_1, datos);
        				
        	//li.setAdapter(adaptador);
        	 //llenado de lista con una concatenacion de dos datos significantes de la tabla, puede ser el id junto con el nombre
        	 //al pulsar abre un activity
indicador=0;
        	 mostrarDatos();
                 
         } 

         
 }
	
	 
	 public void adelante(View v){
    	 indicador=indicador+1;
    	 mostrarDatos();
     }
     
	 public void atras(View v){
		 indicador=indicador-1;
    	 mostrarDatos();
     }
     
	 
	public void mostrarDatos(){
		
		
        cargo=datos.get(indicador);
    	tablaDeDatos.setVisibility(View.VISIBLE);
           // btnActualizar.setVisibility(View.VISIBLE);
    	edtIdCargo.setText(Integer.toString(cargo.getIdCargo()));
    	 //Toast.makeText(this, "Su id es" +encargado.getIdEncargado() +"registro", Toast.LENGTH_LONG).show();
            edtNombre.setText(cargo.getNombre());
            edtDescripcion.setText(cargo.getDescripcion());
         
             
            //mostrar los botones de navegacion
        if(indicador==0 && cantidad!=indicador){
        	btnAtras.setVisibility(View.INVISIBLE);
        	btnAdelante.setVisibility(View.VISIBLE);
        	return;
        }
        if(indicador==0 && cantidad==indicador){
        	btnAtras.setVisibility(View.INVISIBLE);
        	btnAdelante.setVisibility(View.INVISIBLE);
        	return;
        }
        if(indicador!=0 && cantidad!=indicador){
        	btnAtras.setVisibility(View.VISIBLE);
        	btnAdelante.setVisibility(View.VISIBLE);
        	return;
        }
        if(indicador!=0 && cantidad==indicador){
        	btnAtras.setVisibility(View.VISIBLE);
        	btnAdelante.setVisibility(View.INVISIBLE);
        	return;
        }
        	
            
	}
	
public void onNothingSelected(AdapterView<?> arg0){
	
}
	

}
