package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;


public class EncargadoActualizarActivity extends Activity {
	  private TableLayout tablaDeDatos;
      private EditText edtIdEncargado;
      private EditText edtNombre;
      private EditText edtTelefono;
      private EditText edtFacultad;
      private EditText edtEscuela;
      private EditText edtEmail;
      private Button btnActualizar;
      private ControlBD base;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encargado_actualizar);
		
		 tablaDeDatos = (TableLayout) findViewById(R.id.TablaDeDatosEncargado);
         tablaDeDatos.setVisibility(View.INVISIBLE);
         btnActualizar = (Button) findViewById(R.id.btnActualizar);
         btnActualizar.setVisibility(View.INVISIBLE);
         
         edtIdEncargado= (EditText) findViewById(R.id.edtActualizarEncargado);
         edtNombre = (EditText) findViewById(R.id.edtNombreEncargado);
        edtEmail = (EditText) findViewById(R.id.edtEmailEncargado);
         edtTelefono = (EditText) findViewById(R.id.edtTelefonoEncargado);
        
         edtFacultad= (EditText) findViewById(R.id.edtFacultadEncargado);
         edtEscuela = (EditText) findViewById(R.id.edtEscuelaEncargado);
         base = new ControlBD(this);
	}

	public void consultarEncargadoActualizar(View v)
    {
            String busqueda = edtIdEncargado.getText().toString();
            //Validando
            if(busqueda == null || busqueda.trim() == "")
            {
                    Toast.makeText(this, "Carnet inválido", Toast.LENGTH_LONG).show();
                    return;
            } 
            base.abrir();
            ArrayList<EncargadoServicioSocial> datos = base.consultarEncargadoServicioSocial(busqueda, 1);
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
               EncargadoServicioSocial encargado=new EncargadoServicioSocial();
                encargado=datos.get(0);
            	tablaDeDatos.setVisibility(View.VISIBLE);
                    btnActualizar.setVisibility(View.VISIBLE);
                    edtNombre.setText(encargado.getNombre());
                    edtEmail.setText(encargado.getEmail());
                    edtTelefono.setText(encargado.getTelefono());
                    edtFacultad.setText(encargado.getFacultad());
                    edtEscuela.setText(encargado.getEscuela());
            }
    }
	

}
