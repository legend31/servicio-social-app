package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EncargadoInsertarActivity extends Activity {
	

    ControlBD base;
   // private EditText txtIdEncargado;
    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtTelefono;
    private EditText txtFacultad;
    private EditText txtEscuela;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_encargado_insertar);
		
		  base = new ControlBD(this);
          //txtIdEncargado = (EditText) findViewById(R.id.txtIdEncargadoEncargado);
          txtNombre = (EditText) findViewById(R.id.txtNombreEncargado);
          txtEmail = (EditText) findViewById(R.id.txtEmailEncargado);
          txtTelefono = (EditText) findViewById(R.id.txtTelefonoEncargado);
          txtFacultad = (EditText) findViewById(R.id.txtFacultadEncargado);
          txtEscuela = (EditText) findViewById(R.id.txtEscuelaEncargado);
	}

	
	public void insertarEncargado(View v)
    {
            //String id=txtIdEncargado.getText().toString();
            String nombre=txtNombre.getText().toString();
            String email = txtEmail.getText().toString();
            String telefono = txtTelefono.getText().toString();
            String facultad= txtFacultad.getText().toString();
            String escuela= txtEscuela.getText().toString();
            String error = "";
            
           
            if(escuela == null || escuela.trim() == "")
            {
                    error= "Escuela Ingresado";
            }
         
            if(facultad == null || facultad.trim() == "")
            {
                    error= "Facultad Ingresado";
            }
           
            if(telefono == null || telefono.trim() == "" || telefono.length() != 8)
            {
                    error = "Teléfono inválido";
            }
            if(email == null || email.trim() == "" || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                    error = "E-mail inválido";
            }
            
            if(nombre == null || nombre.trim() == "")
            {
                    error = "Nombre no Ingresado";
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
           EncargadoServicioSocial encargado=new EncargadoServicioSocial();
            //encargado.setIdEncargado(id);
            encargado.setNombre(nombre);
            encargado.setEmail(email);
            encargado.setTelefono(telefono);
            encargado.setFacultad(facultad);
            encargado.setEscuela(escuela);
            
            base.abrir();
            String regInsertados=base.insertar(encargado);
            base.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }

	public void limpiarEncargado(View v) {
        //txtIdEncargado.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtFacultad.setText("");
        txtEscuela.setText("");
}

}
