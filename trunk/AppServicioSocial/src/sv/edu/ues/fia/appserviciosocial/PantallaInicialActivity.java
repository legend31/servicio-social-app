package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaInicialActivity extends Activity implements
		OnItemClickListener {

	private GridView lista;
	private int tipoUsuario;
	private String[] actividades = { "AlumnoMenuActivity",
			"AsignacionProyectoMenuActivity", "BitacoraMenuActivity",
			"CargoMenuActivity", "EncargadoMenuActivity",
			"InstitucionMenuActivity", "SolicitanteMenuActivity",
			"ProyectoMenuActivity", "TipoProyectoMenuActivity",
			"TipoTrabajoMenuActivity" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listado);
		Bundle b = getIntent().getExtras();
		tipoUsuario = b.getInt("tipoUsuario");
		ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();

		if(tipoUsuario==1)
		{
			datos.add(new Lista_entrada(R.drawable.estudiante, "Alumno"));
			datos.add(new Lista_entrada(R.drawable.aproyectos,
					"Asignacion Proyectos"));
			datos.add(new Lista_entrada(R.drawable.bitacora, "Bitacora"));
			datos.add(new Lista_entrada(R.drawable.cargo, "Cargo"));
			datos.add(new Lista_entrada(R.drawable.encargado,
					"Encargado S.S"));
			datos.add(new Lista_entrada(R.drawable.institucion, "Institucion"));
			datos.add(new Lista_entrada(R.drawable.solicitante, "Solicitante"));
			datos.add(new Lista_entrada(R.drawable.proyecto, "Proyecto"));
			datos.add(new Lista_entrada(R.drawable.proyectos, "Tipo Proyecto"));
			datos.add(new Lista_entrada(R.drawable.trabajo, "Tipo Trabajo"));

			lista = (GridView) findViewById(R.id.ListView_listado);
			lista.setAdapter(new Lista_Adaptador(this, R.layout.entrada, datos) {
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
						TextView texto_superior_entrada = (TextView) view
								.findViewById(R.id.textView_superior);
						if (texto_superior_entrada != null)
							texto_superior_entrada
									.setText(((Lista_entrada) entrada)
											.getTextoEncima());

						ImageView imagen_entrada = (ImageView) view
								.findViewById(R.id.imageView_imagen);
						if (imagen_entrada != null)
							imagen_entrada
									.setImageResource(((Lista_entrada) entrada)
											.getIdImagen());
					}
				}
			});
		}//fin if
		else
			if(tipoUsuario==2){
				datos.add(new Lista_entrada(R.drawable.estudiante, "Alumno"));
				datos.add(new Lista_entrada(R.drawable.encargado,
						"Encargado S.S"));
				datos.add(new Lista_entrada(R.drawable.institucion, "Institucion"));
				datos.add(new Lista_entrada(R.drawable.proyecto, "Proyecto"));
				datos.add(new Lista_entrada(R.drawable.proyectos, "Tipo Proyecto"));
				lista = (GridView) findViewById(R.id.ListView_listado);
				lista.setAdapter(new Lista_Adaptador(this, R.layout.entrada, datos) {
					@Override
					public void onEntrada(Object entrada, View view) {
						if (entrada != null) {
							TextView texto_superior_entrada = (TextView) view
									.findViewById(R.id.textView_superior);
							if (texto_superior_entrada != null)
								texto_superior_entrada
										.setText(((Lista_entrada) entrada)
												.getTextoEncima());

							ImageView imagen_entrada = (ImageView) view
									.findViewById(R.id.imageView_imagen);
							if (imagen_entrada != null)
								imagen_entrada
										.setImageResource(((Lista_entrada) entrada)
												.getIdImagen());
						}
					}
				});
				
			}
		
		
		/*ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
		datos.add(new Lista_entrada(R.drawable.estudiante, "Alumno"));
		datos.add(new Lista_entrada(R.drawable.aproyectos,
				"Asignacion Proyectos"));
		datos.add(new Lista_entrada(R.drawable.bitacora, "Bitacora"));
		datos.add(new Lista_entrada(R.drawable.cargo, "Cargo"));
		datos.add(new Lista_entrada(R.drawable.encargado,
				"Encargado S.S"));
		datos.add(new Lista_entrada(R.drawable.institucion, "Institucion"));
		datos.add(new Lista_entrada(R.drawable.solicitante, "Solicitante"));
		datos.add(new Lista_entrada(R.drawable.proyecto, "Proyecto"));
		datos.add(new Lista_entrada(R.drawable.proyectos, "Tipo Proyecto"));
		datos.add(new Lista_entrada(R.drawable.trabajo, "Tipo Trabajo"));

		lista = (GridView) findViewById(R.id.ListView_listado);
		lista.setAdapter(new Lista_Adaptador(this, R.layout.entrada, datos) {
			@Override
			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
					TextView texto_superior_entrada = (TextView) view
							.findViewById(R.id.textView_superior);
					if (texto_superior_entrada != null)
						texto_superior_entrada
								.setText(((Lista_entrada) entrada)
										.getTextoEncima());

					ImageView imagen_entrada = (ImageView) view
							.findViewById(R.id.imageView_imagen);
					if (imagen_entrada != null)
						imagen_entrada
								.setImageResource(((Lista_entrada) entrada)
										.getIdImagen());
				}
			}
		});
*/

		lista.setOnItemClickListener(this);
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String nombreValues = actividades[position];
		try {
			Class<?> clase = Class.forName("sv.edu.ues.fia.appserviciosocial."
					+ nombreValues);
			Intent intent = new Intent(this, clase);
			intent.putExtra("tipoUsuario", tipoUsuario);
			startActivity(intent);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
