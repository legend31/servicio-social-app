package sv.edu.ues.fia.appserviciosocial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBD {

	private static final String[] camposAlumno = new String[] { "carnet",
			"idEncargado", "nombre", "telefono", "dui", "nit", "email" };
	private static final String[] camposAsignacionProyecto = new String[] {
			"idProyecto", "fecha", "carnet" };
	private static final String[] camposBitacora = new String[] { "idBitacora",
			"carnet", "idProyecto", "idTipoTrabajo", "fecha", "descripcion" };
	private static final String[] camposEncargadoServicioSocial = new String[] {
			"idEncargado", "idEscuela", "nombre", "email", "telefono" };
	private static final String[] camposEscuela = new String[] { "idEscuela",
			"idEncargado", "nombre", "facultad" };
	private static final String[] camposInstitucion = new String[] {
			"idInstitucion", "nombre", "nit" };
	private static final String[] camposProyecto = new String[] { "idProyecto",
			"idTipoProyecto", "idSolicitante", "idEncargado", "nombre" };
	private static final String[] camposSolicitante = new String[] {
			"idSolicitante", "idInstitucion", "nombre", "cargo" };
	private static final String[] camposTipoProyecto = new String[] {
			"idTipoProyecto", "nombre" };
	private static final String[] camposTipoTrabajo = new String[] {
			"idTipoTrabajo", "nombre", "valor" };
	private static final String[] camposCargo = new String[] { "idCargo",
			"nombre", "descripcion" };

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public ControlBD(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(ctx);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String Base_Datos = "BaseDeDatos.s3db";
		private static final int VERSION = 1;

		public DatabaseHelper(Context context) {
			super(context, Base_Datos, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {
				db.execSQL("CREATE TABLE alumno( carnet VARCHAR(27) NOT NULL PRIMARY KEY, nombre VARCHAR(100),telefono VARCHAR(8),dui VARCHAR(10)");
				db.execSQL("CREATE TABLE bitacora( id INTEGER NOT NULL PRIMARY KEY, carnet VARCHAR(7) NOT NULL,idTipoTrabajo INTEGER NOT NULL,carnet VARCHAR(7) NOT NULL,fecha DATE,descripcion VARCHAR(500)");
				db.execSQL("CREATE TABLE cargo( idCargo INTEGER NOT NULL PRIMARY KEY,nombre VARCHAR(100),descripcion VARCHAR(250)");
				db.execSQL("CREATE TABLE encargadoserviciosocial( idEncargado INTEGER NOT NULL PRIMARY KEY,nombre VARCHAR(100) NOT NULL,email VARCHAR(50),telefono VARCHAR(8),facultad VARCHAR(100), escuela VARCHAR(50)");
				db.execSQL("CREATE TABLE institucion( idinstitucion INTEGER NOT NULL PRIMARY KEY,nombre VARCHAR(100),nit VARCHAR(17)");
				db.execSQL("CREATE TABLE proyecto( idproyecto INTEGER NOT NULL PRIMARY KEY, idsolicitante INTEGER NOT NULL, idtipoproyecto INTEGER NOT NULL, idencargado INTEGER NOT NULL, nombre VARCHAR(100)");
				db.execSQL("CREATE TABLE solicitante( idsolicitante INTEGER NOT NULL PRIMARY KEY, idinstitucion INTEGER NOT NULL, idcargo INTEGER NOT NULL, nombre VARCHAR(100), telefono VARCHAR(8),correo_electronico VARCHAR(100)");
				db.execSQL("CREATE TABLE tipoproyecto( idtipoproyecto INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR(100)");
				db.execSQL("CREATE TABLE tipotrabajo( idtipotrabajo INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR(100) NOT NULL,valor REAL NOT NULL");
				db.execSQL("CREATE TABLE asignacionproyecto( carnet VARCHAR(7) NOT NULL, idproyecto INTEGER NOT NULL, fecha DATE, PRIMARY KEY(carnet,idproyecto)");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
	}

	// metodos de abrir y cerrar la base

	public void abrir() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return;
	}

	public void cerrar() {
		DBHelper.close();
	}
	
	// Metodos de control de base de datos

	// metodos de insercion en las tablas

	public String insertar(Alumno alumno) {
		String mensaje=""; 
		long contador=0; 
		ContentValues valoresAlumno = new ContentValues();
		valoresAlumno.put("carnet", alumno.getCarnet());
		valoresAlumno.put("nombre", alumno.getNombre());
		valoresAlumno.put("telefono", alumno.getTelefono());
		valoresAlumno.put("dui", alumno.getDui()); valoresAlumno.put("nit",alumno.getNit()); valoresAlumno.put("email", alumno.getEmail());
		contador=db.insert("alumno", null, valoresAlumno); 
		if(contador==-1|| contador==0) 
		{ 
			mensaje="Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
			} 
		else { mensaje="Registro ingresado"; } 
		return mensaje; 
		}
	  
	  public String insertar(AsignacionProyecto asignacion) { return null; }
	  
	  public String insertar(Bitacora bitacora) { return null; }
	  
	  public String insertar(EncargadoServicioSocial encargado) { return null;
	  }
	  
	  public String insertar(Escuela escuela) { return null; }
	  
	  public String insertar(Institucion institucion) { return null; }
	  
	  public String insertar(Proyecto proyecto) { return null; }
	  
	  public String insertar(Solicitante solicitante) { return null; }
	  
	  public String insertar(TipoProyecto tipoProyecto) { return null; }
	  
	  public String insertar(TipoTrabajo tipoTrabajo) { return null; }
	  

	  // Actualizaciones 
	  
	  public String actualizar(Alumno alumno) { return null;
	  }
	  
	  public String actualizar(AsignacionProyecto asignacion) { return null; }
	  
	  public String actualizar(Bitacora bitacora) { return null; }
	  
	  public String actualizar(EncargadoServicioSocial encargado) { return
	  null; }
	  
	  public String actualizar(Escuela escuela) { return null; }
	  
	  public String actualizar(Institucion institucion) { return null; }
	  
	  public String actualizar(Proyecto proyecto) { return null; }
	  
	  public String actualizar(Solicitante solicitante) { return null; }
	  
	  public String actualizar(TipoProyecto tipoProyecto) { return null; }
	  
	  public String actualizar(TipoTrabajo tipoTrabajo) { return null; }
	  
	  
	  // Eliminaciones 
	  
	  public String eliminar(Alumno alumno) 
	  { 
		  String regAfectados=""; 
		  int contador=0; 
		  contador+=db.delete("alumno","carnet='"+alumno.getCarnet()+"'", null); regAfectados+=contador; 
		  return regAfectados; 
		  }
	  
	  public String eliminar(AsignacionProyecto asignacion) { return null; }
	  
	  public String eliminar(Bitacora bitacora) { return null; }
	  
	  public String eliminar(EncargadoServicioSocial encargado) { return null;
	  }
	  
	  public String eliminar(Escuela escuela) { return null; }
	  
	  public String eliminar(Institucion institucion) { return null; }
	  
	  public String eliminar(Proyecto proyecto) { return null; }
	  
	  public String eliminar(Solicitante solicitante) { return null; }
	  
	  public String eliminar(TipoProyecto tipoProyecto) { return null; }
	  
	  public String eliminar(TipoTrabajo tipoTrabajo) { return null; }
	  
	  // Consultas 
	  
	  public Alumno consultarAlumno(String carnet) { 
		  String[] id = {carnet}; Cursor cursor = db.query("alumno", camposAlumno,"carnet = ?", id,null, null, null); 
		  if(cursor.moveToFirst()){ 
			  Alumno alumno = new Alumno(); 
			  alumno.setCarnet(cursor.getString(0));
			  alumno.setNombre(cursor.getString(1));
			  alumno.setTelefono(cursor.getString(2));
			  alumno.setDui(cursor.getString(3)); alumno.setNit(cursor.getString(4));
			  alumno.setEmail(cursor.getString(5)); return alumno; 
			  }
		  else{ return null;
			  } 
		  }
	  
	  public AsignacionProyecto consultarAsignacionProyecto(){return null;}// No s� que //
	  //parametros // llevaria { return null; }
	  
	  public Bitacora consultarBitacora(String fecha) { return null; }
	  
	  public EncargadoServicioSocial consultarEncargadoServicioSocial( String
	  idEncargado) { return null; }
	  
	  public Escuela consultarEscuela(String idEscuela) { return null; }
	  
	  public Institucion consultarInstitucion(String idInstitucion) { return
	  null; }
	  
	  public Proyecto consultarProyecto(String idProyecto) { return null; }
	  
	  public Solicitante consultarSolicitante(String idSolicitante) { return
	  null; }
	  
	  public TipoProyecto consultarTipoProyecto(String idTipoProyecto) { return
	  null; }
	  
	  public TipoTrabajo consultarTipoTrabajo(String idTipoTrabajo) { return
	  null; }
	  
	  //Llenado de base de datos para datos iniciales en caso los necesite
	  public String llenarBD() { return null; }
	 
}// fin ControlDB
