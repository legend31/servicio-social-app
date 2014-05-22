package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AccesoActivity extends Activity {
	
	EditText usuario;
	EditText password;
	ControlBD auxiliar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acceso);
		usuario = (EditText) findViewById(R.id.txfUsuario);
		password = (EditText) findViewById(R.id.txfPassword);
		auxiliar = new ControlBD(this);
	}
	
	public void ingresar(View v)
	{
		auxiliar.abrir();
		String tipo = auxiliar.verificarUsuario(usuario.getText().toString(), password.getText().toString());
		auxiliar.cerrar();
		if(tipo == null)
		{
			Toast.makeText(this, "Usuario inválido", Toast.LENGTH_SHORT).show();
		}
		else
		{
			int tipoUsuario = Integer.parseInt(tipo);
			//Intent menu = new Intent(this, MenuTablasActivity.class);
			Intent menu = new Intent(this, PantallaInicialActivity.class);
			menu.putExtra("tipoUsuario", tipoUsuario);
			startActivity(menu);
	        overridePendingTransition(R.anim.left_in, R.anim.left_out);
	        finish();
		}
	}


}
