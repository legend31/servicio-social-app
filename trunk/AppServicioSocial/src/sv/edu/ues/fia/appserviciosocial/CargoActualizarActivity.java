package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;
import android.app.Activity;


public class CargoActualizarActivity extends Activity {
	 private TableLayout tablaDeDatos;
     private EditText edtIdCargo;
     private EditText edtNombre;
     private EditText edtDescripcion;
   
     private Button btnActualizar;
     private ControlBD base;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cargo_actualizar);
		
		tablaDeDatos = (TableLayout) findViewById(R.id.TablaDeDatosCargo);
        tablaDeDatos.setVisibility(View.INVISIBLE);
        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setVisibility(View.INVISIBLE);
        
        edtIdCargo= (EditText) findViewById(R.id.edtIdCargo);
        edtNombre = (EditText) findViewById(R.id.edtNombreCargo);
       edtDescripcion= (EditText) findViewById(R.id.edtDescripcionCargo);
     
        base = new ControlBD(this);
	}

	public void consultarCargoActualizar(View v)
    {
            String busqueda = edtIdCargo.getText().toString();
            //Validando
            if(busqueda == null || busqueda.trim() == "")
            {
                    Toast.makeText(this, "No se ingreso informacion.", Toast.LENGTH_LONG).show();
                    return;
            } 
            
            base.abrir();
            ArrayList<Cargo> datos = base.consultarCargo(busqueda, 0);
            //EncargadoServicioSocial encargado = base.consultarEncargadoServicioSocial(busqueda);
            base.cerrar();
            if(datos == null)
            	//if(encargado == null)
            {
                    tablaDeDatos.setVisibility(View.INVISIBLE);
                    btnActualizar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Encargado con ID " +busqueda +" no encontrado", Toast.LENGTH_LONG).show();
                    return;
            }
            else{
              Cargo cargo=new Cargo();
                cargo=datos.get(0);
            	tablaDeDatos.setVisibility(View.VISIBLE);
                    btnActualizar.setVisibility(View.VISIBLE);
                    edtNombre.setText(cargo.getNombre());
                    edtDescripcion.setText(cargo.getDescripcion());
                   
            }
    }
	

	 public void actualizarCargo(View v)
     {
             int idCargo=Integer.parseInt(edtIdCargo.getText().toString());
             String nombre=edtNombre.getText().toString();
             String descripcion = edtDescripcion.getText().toString();
            
             String error= "";
         
             
             if(nombre == null || nombre.trim() == "")
             {
            	 error = "Nombre inválido";
             }
             if(descripcion == null || descripcion.trim() == "" )
             {
            	 error= "Descripcion inválida";
             }
            
             //Avisando errores
             if(error != "")
             {
                     Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                     return;
             }
             //Creando inserción
             Cargo cargo=new Cargo();
             cargo.setIdCargo(idCargo);
             cargo.setNombre(nombre);
             cargo.setDescripcion(descripcion);
             base.abrir();
             String regInsertados=base.actualizar(cargo);
             base.cerrar();
             Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
     }


}
