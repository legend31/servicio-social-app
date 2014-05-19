package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;

public class CargoInsertarActivity extends Activity {
	 ControlBD base;
	   // private EditText txtIdEncargado;
	    private EditText edtNombre;
	    private EditText edtDescripcion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cargo_insertar);
		 base = new ControlBD(this);
         //txtIdEncargado = (EditText) findViewById(R.id.txtIdEncargadoEncargado);
         edtNombre = (EditText) findViewById(R.id.edtNombreCargo);
         edtDescripcion = (EditText) findViewById(R.id.edtDescripcionCargo);
         
	}
	public void insertarCargo(View v)
    {
            //String id=txtIdEncargado.getText().toString();
            String nombre=edtNombre.getText().toString();
            String descripcion = edtDescripcion.getText().toString();
           
            String error = "";
            
           
            
            if(nombre == null || nombre.trim() == "" )
            {
                    error = "Nombre inválido";
            }
            
            if(descripcion == null || descripcion.trim() == "")
            {
                    error = "Descripción no Ingresada";
            }
           // if(txtIdEncargado.getText().toString()==null ||txtIdEncargado.getText().toString().trim()=="")
            //{
              //      error = "ID no Ingresado";
            //}
            //Avisando errores
            if(error != "")
            {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    return;
            }
            //Creando inserción
           Cargo cargo=new Cargo();
            //encargado.setIdEncargado(id);
            cargo.setNombre(nombre);
            cargo.setDescripcion(descripcion);
            
            
            base.abrir();
            String regInsertados=base.insertar(cargo);
            base.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

	public void limpiarCargo(View v) {
        //txtIdEncargado.setText("");
        edtNombre.setText("");
        edtDescripcion.setText("");
        
}

}
