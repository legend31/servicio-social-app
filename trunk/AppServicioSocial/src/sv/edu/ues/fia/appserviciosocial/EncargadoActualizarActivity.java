package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
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
  	//sonidos
  	SoundPool soundPool;
  	int exito;
  	int fracaso;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encargado_actualizar);
		
		 tablaDeDatos = (TableLayout) findViewById(R.id.TablaDeDatosEncargado);
         tablaDeDatos.setVisibility(View.INVISIBLE);
         btnActualizar = (Button) findViewById(R.id.btnActualizar);
         btnActualizar.setVisibility(View.INVISIBLE);
         
         edtIdEncargado= (EditText) findViewById(R.id.edtIdEncargado);
         edtNombre = (EditText) findViewById(R.id.edtNombreEncargado);
        edtEmail = (EditText) findViewById(R.id.edtEmailEncargado);
         edtTelefono = (EditText) findViewById(R.id.edtTelefonoEncargado);
        
         edtFacultad= (EditText) findViewById(R.id.edtFacultadEncargado);
         edtEscuela = (EditText) findViewById(R.id.edtEscuelaEncargado);
         base = new ControlBD(this);
     	//sonidos
         soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
         exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
         fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);

	}

	public void consultarEncargadoActualizar(View v)
    {
            String busqueda = edtIdEncargado.getText().toString();
            //Validando
            if(busqueda == null || busqueda.trim() == "")
            {
                    Toast.makeText(this, "No se ingreso informacion.", Toast.LENGTH_LONG).show();
                    return;
            } 
            
            base.abrir();
            ArrayList<EncargadoServicioSocial> datos = base.consultarEncargadoServicioSocial(busqueda, 0);
            //EncargadoServicioSocial encargado = base.consultarEncargadoServicioSocial(busqueda);
            base.cerrar();
            if(datos == null)
            	//if(encargado == null)
            {
                    tablaDeDatos.setVisibility(View.INVISIBLE);
                    btnActualizar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Encargado con ID " +busqueda +" no encontrado", Toast.LENGTH_LONG).show();
                    soundPool.play(fracaso, 1, 1, 1, 0, 1);
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
	

	 public void actualizarEncargado(View v)
     {
             int idEncargado=Integer.parseInt(edtIdEncargado.getText().toString());
             String nombre=edtNombre.getText().toString();
             String email = edtEmail.getText().toString();
             String telefono = edtTelefono.getText().toString();
             String facultad = edtFacultad.getText().toString();
             String escuela = edtEscuela.getText().toString();
             String error= "";
             //Validando
             //if(edtIdEncargado.getText().toString()== null || edtIdEncargado.getText().toString().trim() == "")
             //{
              //      error = "Carnet inválido";
            // }
             
             if(nombre == null || nombre.trim() == "")
             {
            	 error = "Nombre inválido";
             }
             if(telefono == null || telefono.trim() == "" || telefono.length() != 8)
             {
            	 error= "Telefono inválido";
             }
             if(facultad == null || facultad.trim() == "" )
             {
            	 error = "Facultad no ingresada";
             }
             if(escuela == null || escuela.trim() == "")
             {
            	 error = "Encuela no ingresada";
             }
             if(email == null || email.trim() == "" || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
             {
            	 error = "E-mail inválido";
             }
             //Avisando errores
             if(error != "")
             {
                     Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                     return;
             }
             //Creando inserción
            EncargadoServicioSocial encargado= new EncargadoServicioSocial();
             encargado.setIdEncargado(idEncargado);
             encargado.setNombre(nombre);
             encargado.setEmail(email);
             encargado.setTelefono(telefono);
             encargado.setFacultad(facultad);
             encargado.setEscuela(escuela);
             base.abrir();
             String regInsertados=base.actualizar(encargado);
             base.cerrar();
             Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
             soundPool.play(exito, 1, 1, 1, 0, 1);
     }
}
