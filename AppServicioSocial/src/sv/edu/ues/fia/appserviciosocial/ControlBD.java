package sv.edu.ues.fia.appserviciosocial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBD {

	private static final String[] camposAlumno = new String[] { "carnet","nombre", "telefono", "dui", "nit", "email" };
	private static final String[] camposAsignacionProyecto = new String[] {"idProyecto", "fecha", "carnet" };
	private static final String[] camposBitacora = new String[] { "fecha","descripcion" };
	private static final String[] camposEncargadoServicioSocial = new String[] {"idEncargado", "nombre", "email", "telefono" };
	private static final String[] camposEscuela = new String[] { "idEscuela","nombre", "facultad" };
	private static final String[] camposInstitucion = new String[] {"idInstitucion", "nombre", "nit" };
	private static final String[] camposProyecto = new String[] { "idProyecto","nombre" };
	private static final String[] camposSolicitante = new String[] {"idSolicitante", "nombre", "cargo" };
	private static final String[] camposTipoProyecto = new String[] {"idTipoProyecto", "nombre" };
	private static final String[] camposTipoTrabajo = new String[] {"idTipoTrabajo", "nombre", "valor" };
	private DatabaseHelper DBHelper;
	private SQLiteDatabase database;

	public ControlBD(Context contexto) {
		DBHelper = new DatabaseHelper(contexto);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String BASE_DATOS = "servicioSocial.s3db";
		private static final int VERSION = 1;

		public DatabaseHelper(Context contexto) {
			super(contexto, BASE_DATOS, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			try {
				// db.execSQL("CREATE TABLE alumno(carnet VARCHAR(7) NOT NULL PRIMARY KEY,nombre VARCHAR(30),apellido VARCHAR(30),sexo VARCHAR(1),matganadas INTEGER);");
				// db.execSQL("CREATE TABLE materia(codmateria VARCHAR(6) NOT NULL PRIMARY KEY,nommateria VARCHAR(30),unidadesval VARCHAR(1));");
				// db.execSQL("CREATE TABLE nota(carnet VARCHAR(7) NOT NULL ,codmateria VARCHAR(6) NOT NULL ,ciclo VARCHAR(5) ,notafinal REAL ,PRIMARY KEY(carnet,codmateria,ciclo));");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int antiguaVersion,
				int nuevaVersion) {
			// TODO Auto-generated method stub
		}
	}

	public void abrir() throws SQLException {
		database = DBHelper.getWritableDatabase();
		return;
	}

	public void cerrar() {
		DBHelper.close();
	}

	// Metodos de control de base de datos

	// Inserciones

	public String insertar(Alumno alumno) {
		return null;
	}

	public String insertar(AsignacionProyecto asignacion) {
		return null;
	}

	public String insertar(Bitacora bitacora) {
		return null;
	}

	public String insertar(EncargadoServicioSocial encargado) {
		return null;
	}

	public String insertar(Escuela escuela) {
		return null;
	}

	public String insertar(Institucion institucion) {
		return null;
	}

	public String insertar(Proyecto proyecto) {
		return null;
	}

	public String insertar(Solicitante solicitante) {
		return null;
	}

	public String insertar(TipoProyecto tipoProyecto) {
		return null;
	}

	public String insertar(TipoTrabajo tipoTrabajo) {
		return null;
	}

	// Actualizaciones
	public String actualizar(Alumno alumno) {
		return null;
	}

	public String actualizar(AsignacionProyecto asignacion) {
		return null;
	}

	public String actualizar(Bitacora bitacora) {
		return null;
	}

	public String actualizar(EncargadoServicioSocial encargado) {
		return null;
	}

	public String actualizar(Escuela escuela) {
		return null;
	}

	public String actualizar(Institucion institucion) {
		return null;
	}

	public String actualizar(Proyecto proyecto) {
		return null;
	}

	public String actualizar(Solicitante solicitante) {
		return null;
	}

	public String actualizar(TipoProyecto tipoProyecto) {
		return null;
	}

	public String actualizar(TipoTrabajo tipoTrabajo) {
		return null;
	}

	// Eliminaciones
	public String eliminar(Alumno alumno) {
		return null;
	}

	public String eliminar(AsignacionProyecto asignacion) {
		return null;
	}

	public String eliminar(Bitacora bitacora) {
		return null;
	}

	public String eliminar(EncargadoServicioSocial encargado) {
		return null;
	}

	public String eliminar(Escuela escuela) {
		return null;
	}

	public String eliminar(Institucion institucion) {
		return null;
	}

	public String eliminar(Proyecto proyecto) {
		return null;
	}

	public String eliminar(Solicitante solicitante) {
		return null;
	}

	public String eliminar(TipoProyecto tipoProyecto) {
		return null;
	}

	public String eliminar(TipoTrabajo tipoTrabajo) {
		return null;
	}

	// Consultas
	public Alumno consultarAlumno(String carnet) {
		return null;
	}

	public AsignacionProyecto consultarAsignacionProyecto()// No sé que
															// parametros
															// llevaria
	{
		return null;
	}

	public Bitacora consultarBitacora(String fecha) {
		return null;
	}

	public EncargadoServicioSocial consultarEncargadoServicioSocial(
			String idEncargado) {
		return null;
	}

	public Escuela consultarEscuela(String idEscuela) {
		return null;
	}

	public Institucion consultarInstitucion(String idInstitucion) {
		return null;
	}

	public Proyecto consultarProyecto(String idProyecto) {
		return null;
	}

	public Solicitante consultarSolicitante(String idSolicitante) {
		return null;
	}

	public TipoProyecto consultarTipoProyecto(String idTipoProyecto) {
		return null;
	}

	public TipoTrabajo consultarTipoTrabajo(String idTipoTrabajo) {
		return null;
	}

	//Llenado de base de datos para datos iniciales en caso los necesite
	public String llenarBDCarnet() {
		return null;
	}

}
