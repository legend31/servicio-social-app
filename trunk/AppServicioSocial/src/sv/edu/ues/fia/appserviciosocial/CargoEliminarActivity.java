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


public class CargoEliminarActivity extends Activity {
	private TableLayout tablaDeDatos;
    private EditText edtIdCargo;
    private EditText edtNombre;
    private EditText edtDescripcion;
   
    private Button btnEliminar;
    private ControlBD base;
  //sonidos
  	SoundPool soundPool;
  	int exito;
  	int fracaso;
  	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cargo_eliminar);
		
		tablaDeDatos = (TableLayout) findViewById(R.id.TablaDeDatosCargo);
		tablaDeDatos.setVisibility(View.INVISIBLE);
        btnEliminar = (Button) findViewById(R.id.btnEliminarCargo);
        btnEliminar.setVisibility(View.INVISIBLE);
        
        edtIdCargo= (EditText) findViewById(R.id.edtIdCargo);
        edtNombre = (EditText) findViewById(R.id.edtNombreCargo);
       edtDescripcion = (EditText) findViewById(R.id.edtDescripcionCargo);
       base = new ControlBD(this);
   	//sonidos
       soundPool = new SoundPool( 2, AudioManager.STREAM_MUSIC , 0);
       exito = soundPool.load(getApplicationContext(), R.raw.sonido, 0);
       fracaso = soundPool.load(getApplicationContext(), R.raw.sonido2, 0);
       
	}

	public void consultarCargoEliminar(View v)
    {
            String busqueda = edtIdCargo.getText().toString();
            //Validando
            if(busqueda == null || busqueda.trim() == "")
            {
                    Toast.makeText(this, "Carnet inválido", Toast.LENGTH_LONG).show();
                    return;
            } 
            base.abrir();
            ArrayList<Cargo> datos = base.consultarCargo(busqueda, 0);
          
            base.cerrar();
            if(datos == null)
            	//if(encargado == null)
            {
                    tablaDeDatos.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Cargo con ID " +busqueda +" no encontrado", Toast.LENGTH_LONG).show();
                    soundPool.play(fracaso, 1, 1, 1, 0, 1);
                    return;
            }
            else{
               Cargo cargo=new Cargo();
                cargo=datos.get(0);
            	tablaDeDatos.setVisibility(View.VISIBLE);
                    btnEliminar.setVisibility(View.VISIBLE);
                    edtNombre.setText(cargo.getNombre());
                    edtDescripcion.setText(cargo.getDescripcion());
                 
            }
    }

	 public void eliminarCargo(View v)
     {
             int idCargo= Integer.parseInt(edtIdCargo.getText().toString());
             if(edtIdCargo.getText().toString() == null || edtIdCargo.getText().toString().trim() == "" )
             {
                     Toast.makeText(this, "Valor invalido", Toast.LENGTH_LONG).show();
                     return;
             }
             Cargo cargo=new Cargo();
             cargo.setIdCargo(idCargo);
             base.abrir();
             base.eliminar(cargo);
             base.cerrar();
             Toast.makeText(this, "Eliminación correcta", Toast.LENGTH_SHORT).show();
             soundPool.play(exito, 1, 1, 1, 0, 1);
     }


}
