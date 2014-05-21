package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuTablasActivity extends Activity implements OnClickListener{
	
	int tipoUsuario;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_tablas);
		
		findViewById(R.id.btnActualizar).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.btnSolicitante).setOnClickListener(this);
		findViewById(R.id.btnInstitucion).setOnClickListener(this);
		findViewById(R.id.botonBitacora).setOnClickListener(this);
		findViewById(R.id.btnEncargado).setOnClickListener(this);
		findViewById(R.id.btnTipoProyecto).setOnClickListener(this);
		findViewById(R.id.btnCargo).setOnClickListener(this);
		findViewById(R.id.botonTipoTrabajo).setOnClickListener(this);
		Bundle b = getIntent().getExtras();
		tipoUsuario = b.getInt("tipoUsuario");
		
	}
	
	public void tablaAlumno(View v)
	{
		startActivity(new Intent(this, AlumnoMenuActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = null;
		switch(v.getId())
		{
		
			case R.id.btnActualizar:
				i = new Intent(this, AlumnoMenuActivity.class);
				break;
			case R.id.button2:
				i = new Intent(this, AsignacionProyectoMenuActivity.class);
				break;
			case R.id.button3:
				i = new Intent(this, ProyectoMenuActivity.class);
				break;
			case R.id.btnSolicitante:
				i = new Intent(this, SolicitanteMenuActivity.class);
				break;
			case R.id.btnInstitucion:
				i = new Intent(this, InstitucionMenuActivity.class);
				break;
			case R.id.btnEncargado:
				i = new Intent(this, EncargadoMenuActivity.class);
				break;
			case R.id.btnCargo:
				i = new Intent(this, CargoMenuActivity.class);
				break;

			case R.id.botonBitacora:
				i = new Intent(this, BitacoraMenuActivity.class);
				break;
			case R.id.botonTipoTrabajo:
				i = new Intent(this, TipoTrabajoActivity.class);
				break;
			case R.id.btnTipoProyecto:
				i = new Intent(this, TipoProyectoMenuActivity.class);
			
			default:
		}
		i.putExtra("tipoUsuario", tipoUsuario);
		startActivity(i);
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}
}
