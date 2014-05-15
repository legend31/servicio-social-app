package sv.edu.ues.fia.appserviciosocial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AccesoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acceso);
	}
	
	public void ingresar(View v)
	{
		startActivity(new Intent(this, MenuTablasActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
	}


}
