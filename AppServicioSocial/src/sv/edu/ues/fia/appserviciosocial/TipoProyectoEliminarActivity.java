package sv.edu.ues.fia.appserviciosocial;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TipoProyectoEliminarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_proyecto_eliminar);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_proyecto_eliminar, menu);
		return true;
	}

}
