package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MenuTablasActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_tablas);
		
		findViewById(R.id.btnActualizar).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.btnSolicitante).setOnClickListener(this);
		findViewById(R.id.btnInstitucion).setOnClickListener(this);
		//findViewById(R.id.btnEncargado).setOnClickListener(this);
		
	}
	
	public void tablaAlumno(View v)
	{
		startActivity(new Intent(this, AlumnoMenuActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.btnActualizar:
				startActivity(new Intent(this, AlumnoMenuActivity.class));
				break;
			case R.id.button2:
				startActivity(new Intent(this, AsignacionProyectoMenuActivity.class));
				break;
			case R.id.button3:
				startActivity(new Intent(this, ProyectoMenuActivity.class));
				break;
			case R.id.btnSolicitante:
				startActivity(new Intent(this, SolicitanteMenuActivity.class));
				break;
			case R.id.btnInstitucion:
				startActivity(new Intent(this, InstitucionMenuActivity.class));
				break;
			/*case R.id.btnEncargado:
				startActivity(new Intent(this, EncargadoMenuActivity.class));
				break;*/
			
			default:
		}
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
	}
}
