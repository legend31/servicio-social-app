package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PantallaInicialActivity extends Activity {

	private GridView lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listado);

		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
		datos.add(new Lista_entrada(R.drawable.estudiante, "Alumno"));
		datos.add(new Lista_entrada(R.drawable.proyectos, "Proyecto"));
		datos.add(new Lista_entrada(R.drawable.institucion, "Institucion"));
		datos.add(new Lista_entrada(R.drawable.encargado,
				"Encargado Servicio Social"));
		datos.add(new Lista_entrada(R.drawable.cargo, "Cargo"));
		datos.add(new Lista_entrada(R.drawable.bitacora, "Bitacora"));
		datos.add(new Lista_entrada(R.drawable.solicitante, "Solicitante"));

		lista = (GridView) findViewById(R.id.ListView_listado);
		lista.setAdapter(new Lista_Adaptador(this, R.layout.entrada, datos) {
			@Override
			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
					TextView texto_superior_entrada = (TextView)view.findViewById(R.id.textView_superior);
					if (texto_superior_entrada != null)
						texto_superior_entrada
								.setText(((Lista_entrada) entrada)
										.getTextoEncima());

					//TextView texto_inferior_entrada = (TextView) view
						//	.findViewById(R.id.textView_inferior);
					//if (texto_inferior_entrada != null)
						//texto_inferior_entrada
							//	.setText(((Lista_entrada) entrada)
								//		.get_textoDebajo());

					ImageView imagen_entrada = (ImageView) view
							.findViewById(R.id.imageView_imagen);
					if (imagen_entrada != null)
						imagen_entrada
								.setImageResource(((Lista_entrada) entrada)
										.getIdImagen());
				}
			}
		});

		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> pariente, View view,int posicion, long id) {
				Lista_entrada elegido = (Lista_entrada) pariente
						.getItemAtPosition(posicion);

				CharSequence texto = "Seleccionado: "
						+ elegido.getTextoEncima();
				Toast toast = Toast.makeText(PantallaInicialActivity.this,
						texto, Toast.LENGTH_LONG);
				toast.show();
			}
		});

	}

}
