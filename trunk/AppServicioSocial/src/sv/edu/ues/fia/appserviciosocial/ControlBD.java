package sv.edu.ues.fia.appserviciosocial;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBD {

	private static final String[] camposAlumno = new String[] { "carnet",
			"nombre", "telefono", "dui", "nit", "email" };
	private static final String[] camposAsignacionProyecto = new String[] {
			"carnet", "idProyecto", "fecha" };
	private static final String[] camposBitacora = new String[] { "idBitacora",
			"carnet", "idProyecto", "idTipoTrabajo", "fecha", "descripcion" };
	private static final String[] camposEncargadoServicioSocial = new String[] {
			"idEncargado", "idEscuela", "nombre", "email", "telefono" };
	private static final String[] camposEscuela = new String[] { "idEscuela",
			"idEncargado", "nombre", "facultad" };
	private static final String[] camposInstitucion = new String[] {
			"idInstitucion", "nombre", "nit" };
	private static final String[] camposProyecto = new String[] { "idProyecto",
			"idSolicitante", "idTipoProyecto", "idEncargado", "nombre" };
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
				// lo del script de powerdesigner
				db.execSQL("create table ALUMNO (CARNET VARCHAR(7) not null primary key, NOMBRE VARCHAR(100) not null, "
						+ "TELEFONO VARCHAR(8) not null, DUI VARCHAR(10) not null, NIT VARCHAR(17) not null, EMAIL VARCHAR(50));");
				db.execSQL("create table BITACORA ( ID INTEGER not null primary key autoincrement, IDTIPOTRABAJO INTEGER not null, CARNET VARCHAR(7) "
						+ "not null, IDPROYECTO INTEGER not null, FECHA DATE not null, DESCRIPCION VARCHAR(250) not null, "
						+ "constraint FK_BITACORA_COMPONE_PROYECTO foreign key (IDPROYECTO) references PROYECTO (IDPROYECTO),constraint "
						+ "FK_BITACORA_SE_CLASIF_TIPOTRAB foreign key (IDTIPOTRABAJO)references TIPOTRABAJO (IDTIPOTRABAJO),constraint "
						+ "FK_BITACORA_TIENE_ALUMNO foreign key (CARNET)references ALUMNO (CARNET));");
				db.execSQL("create table CARGO ( IDCARGO INTEGER not null primary key autoincrement,NOMBRE VARCHAR(100),DESCRIPCION VARCHAR(250) );");
				db.execSQL("create table ENCARGADOSERVICIOSOCIAL ( IDENCARGADO INTEGER not null primary key autoincrement, NOMBRE VARCHAR(100) not null,"
						+ " EMAIL VARCHAR(50),TELEFONO VARCHAR(8) not null, FACULTAD VARCHAR(100),ESCUELA CHAR(100));");
				db.execSQL("create table INSTITUCION ( IDINSTITUCION INTEGER not null primary key autoincrement, NOMBRE VARCHAR(100) not null, NIT VARCHAR(17) not null);");
				db.execSQL("create table PROYECTO ( IDPROYECTO INTEGER not null primary key autoincrement, IDSOLICITANTE INTEGER not null, IDTIPOPROYECTO INTEGER "
						+ "not null, IDENCARGADO INTEGER not null, NOMBRE VARCHAR(100) not null, constraint FK_PROYECTO_ADMINISTR_ENCARGAD foreign key "
						+ "(IDENCARGADO) references ENCARGADOSERVICIOSOCIAL (IDENCARGADO), constraint FK_PROYECTO_SUPERVISA_SOLICITA foreign key "
						+ "(IDSOLICITANTE) references SOLICITANTE (IDSOLICITANTE), constraint FK_PROYECTO_TIENE_UN_TIPOPROY foreign key (IDTIPOPROYECTO) "
						+ "references TIPOPROYECTO (IDTIPOPROYECTO) );");
				db.execSQL("create table SOLICITANTE ( IDSOLICITANTE INTEGER not null primary key autoincrement, IDINSTITUCION INTEGER not null, IDCARGO INTEGER not"
						+ " null, NOMBRE VARCHAR(100), TELEFONO VARCHAR(8), CORREO_ELECTRONICO   CHAR(100), constraint FK_SOLICITA_CARGO_SOL_CARGO foreign "
						+ "key (IDCARGO) references CARGO (IDCARGO), constraint FK_SOLICITA_PERTENECE_INSTITUC foreign key (IDINSTITUCION) references "
						+ "INSTITUCION (IDINSTITUCION));");
				db.execSQL("create table TIPOPROYECTO ( IDTIPOPROYECTO INTEGER not null primary key autoincrement, NOMBRE VARCHAR(100) not null );");
				db.execSQL("create table TIPOTRABAJO ( IDTIPOTRABAJO INTEGER not null primary key autoincrement, NOMBRE VARCHAR(100) not null, VALOR FLOAT not null );");
				db.execSQL("create table ASIGNACIONPROYECTO ( CARNET VARCHAR(7) not null, IDPROYECTO INTEGER not null, FECHA DATE, PRIMARY KEY(carnet,idproyecto)"
						+ " CONSTRAINT fk_asignacionproyecto_proyecto FOREIGN KEY (idproyecto) REFERENCES proyecto(idproyecto) ON DELETE RESTRICT, CONSTRAINT "
						+ "fk_asignacionproyecto_alumno FOREIGN KEY (carnet) REFERENCES alumno(carnet) ON DELETE RESTRICT );");
				//triggers
				db.execSQL("CREATE TRIGGER fk_solicitante_cargo BEFORE INSERT ON solicitante FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idcargo FROM cargo" +
						" WHERE idcargo = NEW.idcargo) IS NULL) THEN RAISE(ABORT, 'No existe cargo') END; END;");
				db.execSQL("CREATE TRIGGER fk_solicitante_institucion BEFORE INSERT ON solicitante FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idinstitucion"
						+" FROM institucion WHERE idinstitucion = NEW.idinstitucion) IS NULL) THEN RAISE(ABORT, 'No existe institucion') END; END;");
				db.execSQL("CREATE TRIGGER fk_proyecto_encargado BEFORE INSERT ON proyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idencargado FROM "
						+"encargadoserviciosocial WHERE idencargado = NEW.idencargado) IS NULL) THEN RAISE(ABORT, 'No existe encargado') END; END;");
				db.execSQL("CREATE TRIGGER fk_proyecto_solicitante BEFORE INSERT ON proyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idsolicitante FROM "
						+"solicitante WHERE idsolicitante = NEW.idsolicitante) IS NULL) THEN RAISE(ABORT, 'No existe solicitante') END; END;");
				db.execSQL("CREATE TRIGGER fk_proyecto_tipoproyecto BEFORE INSERT ON proyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idtipoproyecto FROM"
						+" tipoproyecto WHERE idtipoproyecto = NEW.idtipoproyecto) IS NULL) THEN RAISE(ABORT, 'No existe tipo de proyecto') END; END;");
				db.execSQL("CREATE TRIGGER fk_asignacionproyecto_proyecto BEFORE INSERT ON asignacionproyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT "
						+"idproyecto FROM proyecto WHERE idproyecto = NEW.idproyecto) IS NULL) THEN RAISE(ABORT, 'No existe proyecto') END; END;");
				db.execSQL("CREATE TRIGGER fk_asignacionproyecto_alumno BEFORE INSERT ON asignacionproyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT " 
						+"carnet FROM alumno WHERE carnet = NEW.carnet) IS NULL) THEN RAISE(ABORT, 'No existe alumno') END; END;");
				db.execSQL("CREATE TRIGGER fk_bitacora_tipotrabajo BEFORE INSERT ON bitacora FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idtipotrabajo FROM "
						+"tipotrabajo WHERE idtipotrabajo = NEW.idtipotrabajo) IS NULL) THEN RAISE(ABORT, 'No existe tipo de trabajo') END; END;");
				db.execSQL("CREATE TRIGGER fk_bitacora_proyecto BEFORE INSERT ON bitacora FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idproyecto FROM " +
						"proyecto WHERE idproyecto = NEW.idproyecto) IS NULL) THEN RAISE(ABORT, 'No existe proyecto') END; END;");
				db.execSQL("CREATE TRIGGER fk_bitacora_alumno BEFORE INSERT ON bitacora FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT carnet FROM alumno " +
						"WHERE carnet = NEW.carnet) IS NULL) THEN RAISE(ABORT, 'No existe alumno') END; END;");
				// inserciones
				db.execSQL("insert into alumno values('FG12098', 'Pedro Fuentes',   '23456781', '033206621', '06142307906731', 'pedro@yahoo.es');");
				db.execSQL("insert into alumno values('MJ10458', 'Luis Martinez',   '22378781', '033673420', '06132307901231', 'luis@yahoo.com');");
				db.execSQL("insert into alumno values('QS11457', 'Juan Quevedo',    '23456896', '033209871', '09232307904531', 'juan@gmail.es');");
				db.execSQL("insert into alumno values('SA09027', 'Ricardo Sanchez', '23451231', '033207823', '08122307901931', 'ricardo@hotmail.es');");
				db.execSQL("insert into institucion values(null, 'Instituci�n 1', '06142506921232');");
				db.execSQL("insert into institucion values(null, 'Instituci�n 2', '02142509871232');");
				db.execSQL("insert into institucion values(null, 'Instituci�n 3', '06172506924567');");
				db.execSQL("insert into cargo values(null, 'Presidente', 'Puesto mas alto');");
				db.execSQL("insert into cargo values(null, 'Jefe de informatica', 'Puesto intermedio');");
				db.execSQL("insert into solicitante values (null, 2, 1, 'Juan Peraza', '27845689', 'peraza@info.org');");
				db.execSQL("insert into solicitante values (null, 1, 2, 'Mario Luigi', '27856239', 'mario@emq.info');");
				db.execSQL("insert into tipoproyecto values (null, 'Gubernamental');");
				db.execSQL("insert into tipoproyecto values (null, 'Social');");
				db.execSQL("insert into tipoproyecto values (null, 'Privado');");
				db.execSQL("insert into tipotrabajo values(null, 'Programaci�n', 12.50);");
				db.execSQL("insert into tipotrabajo values(null, 'Dise�o', 32.18);");
				db.execSQL("insert into tipotrabajo values(null, 'Analisis', 27.40);");
				db.execSQL("insert into encargadoserviciosocial values(null, 'Esteban Gonzalez', 'gonzalez@ues.edu.sv', '23458512', 'Econom�a', 'Econom�a');");
				db.execSQL("insert into encargadoserviciosocial values(null, 'Sebastian Dominguez', 'dominguezez@ues.edu.sv', '23453421', 'medicina', 'Medicina');");
				db.execSQL("insert into encargadoserviciosocial values(null, 'Kevin Funes', 'funes@ues.edu.sv', '23454100', 'Agronom�a', 'Veterinaria');");
				db.execSQL("insert into encargadoserviciosocial values(null, 'Julio Campos', 'campos@ues.edu.sv', '23450074', 'Ingenieria y arquitectura', 'Ingenieria Industrial');");
				db.execSQL("insert into proyecto values(null, 2, 2, 3, 'Cuidado de perrros');");
				db.execSQL("insert into proyecto values(null, 1, 3, 2, 'Atenci�n a personas');");
				db.execSQL("insert into proyecto values(null, 2, 1, 1, 'Consultoria contable');");
				db.execSQL("insert into proyecto values(null, 1, 3, 4, 'Revision de maquila');");
				db.execSQL("insert into asignacionproyecto values ('FG12098', 1, date('2014-06-29'));");
				db.execSQL("insert into asignacionproyecto values ('MJ10458', 2, date('2014-05-12'));");
				db.execSQL("insert into asignacionproyecto values ('QS11457', 3, date('2014-02-04'));");
				db.execSQL("insert into asignacionproyecto values ('SA09027', 4, date('2014-08-22'));");
				db.execSQL("insert into bitacora values(1, 1, 'FG12098', 1, date('2014-06-29'), 'Introduccion de objetivos');");
				db.execSQL("insert into bitacora values(null, 2, 'FG12098', 1, date('2014-07-02'), 'Dise�ando cosas');");
				db.execSQL("insert into bitacora values(null, 2, 'MJ10458', 3, date('2013-11-18'), 'Presentacion de resultados');");
				db.execSQL("insert into bitacora values(null, 3, 'MJ10458', 3, date('2013-11-20'), 'Avance literal');");
				
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
		String mensaje = "";
		long contador = 0;
		ContentValues valoresAlumno = new ContentValues();
		valoresAlumno.put("carnet", alumno.getCarnet());
		valoresAlumno.put("nombre", alumno.getNombre());
		valoresAlumno.put("telefono", alumno.getTelefono());
		valoresAlumno.put("dui", alumno.getDui());
		valoresAlumno.put("nit", alumno.getNit());
		valoresAlumno.put("email", alumno.getEmail());
		contador = db.insert("alumno", null, valoresAlumno);

		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			mensaje = "Registro ingresado";
		}
		return mensaje;
	}

	public String insertar(AsignacionProyecto asignacion) {
		String mensaje = "";
		long contador = 0;
		ContentValues valoresAsignacion = new ContentValues();
		valoresAsignacion.put("carnet", asignacion.getCarnet());
		valoresAsignacion.put("idproyecto", asignacion.getIdProyecto());
		valoresAsignacion.put("fecha", asignacion.getFecha());
		contador = db.insert("asignacionproyecto", null, valoresAsignacion);
		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			mensaje = "Registro ingresado";
		}
		return mensaje;
	}

	public String insertar(Bitacora bitacora) {
		return null;
	}

	public String insertar(EncargadoServicioSocial encargado) {
		String mensaje = "";
		long contador = 0;
		ContentValues valoresEncargado = new ContentValues();
		valoresEncargado.putNull("idencargado");
		valoresEncargado.put("nombre", encargado.getNombre());
		valoresEncargado.put("email", encargado.getEmail());
		valoresEncargado.put("telefono", encargado.getTelefono());
		valoresEncargado.put("facultad", encargado.getFacultad());
		valoresEncargado.put("escuela", encargado.getEscuela());

		contador = db.insert("encargadoserviciosocial", null, valoresEncargado);
		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			mensaje = "Registro ingresado";
		}
		return mensaje;
	}

	public String insertar(Escuela escuela) {
		return null;
	}

	public String insertar(Institucion institucion) {
		return null;
	}

	public String insertar(Proyecto proyecto) {
		String mensaje = "";
		long contador = 0;

		ContentValues values = new ContentValues();
		if (verificarIntegridad(proyecto, 1)) {
			values.putNull("idproyecto");
			// coloca un null en el valor autoincremental
			// vendr�a siendo igual a insert into proyect(codigoProyecto,...)
			// values(null, ...);
			values.put("idsolicitante", proyecto.getIdSolicitante());
			values.put("idtipoproyecto", proyecto.getIdTipoProyecto());
			values.put("idencargado", proyecto.getIdEncargado());
			values.put("nombre", proyecto.getNombre());
			contador = db.insert("proyecto", null, values);
		}

		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			mensaje = "Registro ingresado " + contador;
		}
		return mensaje;
	}

	public String insertar(Solicitante solicitante) {
		return null;
	}

	public String insertar(TipoProyecto tipoProyecto) {
		String mensaje = "";
		long contador = 0;
		// String values[]={tipoProyecto.getNombre()};
		Cursor cursor = db.query("tipoproyecto", camposTipoProyecto, "nombre='"
				+ tipoProyecto.getNombre() + "'", null, null, null, null, null);
		if (cursor.moveToFirst()) {
			return mensaje = "Registro ya almacenado en la Base de Datos";

		}
		ContentValues content = new ContentValues();
		content.putNull("idtipoproyecto");
		content.put("nombre", tipoProyecto.getNombre());
		contador = db.insert("tipoproyecto", null, content);

		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			mensaje = "Registro ingresado " + contador;
		}
		return mensaje;
	}

	public String insertar(TipoTrabajo tipoTrabajo) {
		return null;
	}

	// Actualizaciones

	public String actualizar(Alumno alumno) {
		String[] id = { alumno.getCarnet() };
		ContentValues cv = new ContentValues();
		cv.put("nombre", alumno.getNombre());
		cv.put("telefono", alumno.getTelefono());
		cv.put("dui", alumno.getDui());
		cv.put("nit", alumno.getNit());
		cv.put("email", alumno.getEmail());
		db.update("alumno", cv, "carnet = ?", id);
		return "Registro Actualizado Correctamente";
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
		if (verificarIntegridad(proyecto, 2)) {
			String[] id = { String.valueOf(proyecto.getIdProyecto()) };
			ContentValues cv = new ContentValues();

			cv.put("idproyecto", proyecto.getIdProyecto());
			cv.put("idsolicitante", proyecto.getIdSolicitante());
			cv.put("idtipoproyecto", proyecto.getIdTipoProyecto());
			cv.put("idencargado", proyecto.getIdEncargado());
			cv.put("nombre", proyecto.getNombre());
			db.update("proyecto", cv, "idproyecto = ?", id);
			return "Registro Actualizado Correctamente";
		} else
			return "Registro no Existente";
	}

	public String actualizar(Solicitante solicitante) {
		return null;
	}

	public String actualizar(TipoProyecto tipoProyecto) {

		if (verificarIntegridad(tipoProyecto, 3)) {
			String[] id = { String.valueOf(tipoProyecto.getIdTipoProyecto()) };
			ContentValues cv = new ContentValues();

			cv.put("idtipoproyecto", tipoProyecto.getIdTipoProyecto());
			cv.put("nombre", tipoProyecto.getNombre());
			db.update("tipoproyecto", cv, "idtipoproyecto = ?", id);
			return "Registro Actualizado Correctamente";
		}

		else
			return "Registro no Existente";

	}

	public String actualizar(TipoTrabajo tipoTrabajo) {
		return null;
	}

	// Eliminaciones

	public String eliminar(Alumno alumno) {
		String regAfectados = "";
		int contador = 0;
		contador += db.delete("alumno", "carnet='" + alumno.getCarnet() + "'",
				null);
		regAfectados += contador;
		return regAfectados;
	}

	public String eliminar(AsignacionProyecto asignacion, int tipo) {
		String regAfectados = "filas afectadas= ";
		int contador = 0;
		String id[] = null;
		switch (tipo) {
		// Se envio en carnet
		case 1:
			id[0] = String.valueOf(asignacion.getCarnet());
			contador += db.delete("asignacionproyecto", "carnet=?", id);
			break;
		// Se envio el idProyecto
		case 2:
			id[0] = String.valueOf(asignacion.getIdProyecto());
			contador += db.delete("asignacionproyecto", "idproyecto=?", id);
			break;
		}
		regAfectados += contador;
		return regAfectados;
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
		String regAfectados = "filas afectadas= ";
		String id[] = { String.valueOf(proyecto.getIdProyecto()) };
		int contador = 0;
		contador += db.delete("proyecto", "idproyecto=?", id);
		regAfectados += contador;
		return regAfectados;

	}

	public String eliminar(Solicitante solicitante) {
		return null;
	}

	public String eliminar(TipoProyecto tipoProyecto) {
		String regAfectados = "filas afectadas= ";
		int contador = 0;
		/*si regresa true es q existe tipoproyecto como fk en proyecto y lo eliminara antes
		 * caso contrario solo eliminara tipoproyecto de su respectiva tabla
		 * 
		 */
		if(verificarIntegridad(tipoProyecto, 4)){
			contador+=db.delete("proyecto","idtipoproyecto='" + tipoProyecto.getIdTipoProyecto() + "'", null);
		}
		contador+=db.delete("tipoproyecto","idtipoproyecto='" + tipoProyecto.getIdTipoProyecto() + "'", null);
		regAfectados+=contador;
		return regAfectados;
	
	}

	public String eliminar(TipoTrabajo tipoTrabajo) {
		return null;
	}

	// Consultas

	public Alumno consultarAlumno(String carnet) {
		String[] id = { carnet };
		Cursor cursor = db.query("alumno", camposAlumno, "carnet = ?", id,
				null, null, null);
		if (cursor.moveToFirst()) {
			Alumno alumno = new Alumno();
			alumno.setCarnet(cursor.getString(0));
			alumno.setNombre(cursor.getString(1));
			alumno.setTelefono(cursor.getString(2));
			alumno.setDui(cursor.getString(3));
			alumno.setNit(cursor.getString(4));
			alumno.setEmail(cursor.getString(5));
			return alumno;
		} else {
			return null;
		}
	}

	public AsignacionProyecto consultarAsignacionProyecto(String parametro,
			int tipo) {
		String[] id = { parametro };
		Cursor cursor = null;
		switch (tipo) {
		case 1:
			cursor = db.query("asignacionproyecto", camposAsignacionProyecto,
					"carnet = ?", id, null, null, null);
			break;
		case 2:
			cursor = db.query("asignacionproyecto", camposAsignacionProyecto,
					"idproyecto = ?", id, null, null, null);
			break;
		}
		if (cursor.moveToFirst()) {
			AsignacionProyecto asignacion = new AsignacionProyecto();
			asignacion.setCarnet(cursor.getString(0));
			asignacion.setIdProyecto(cursor.getString(1));
			asignacion.setFecha(cursor.getString(2));
			return asignacion;
		} else {
			return null;
		}
	}

	public Bitacora consultarBitacora(String fecha) {
		return null;
	}

	public ArrayList<EncargadoServicioSocial> consultarEncargadoServicioSocial(
			String busqueda, int seleccion) {
		String[] id = { busqueda };
		int opcion = seleccion;
		Cursor cursor;
		ArrayList<EncargadoServicioSocial> lista = new ArrayList<EncargadoServicioSocial>();
		switch (opcion) {

		// Se eligio id
		case 0:
			cursor = db.query("encargadoserviciosocial",
					camposEncargadoServicioSocial, "idEncargado = ?", id, null,
					null, null);
			break;
		// se eligio nombre
		case 1:

			cursor = db.query("encargadoserviciosocial",
					camposEncargadoServicioSocial, "nombre= ?", id, null, null,
					null);
			break;
		// Se eligio email
		case 2:
			cursor = db.query("encargadoserviciosocial",
					camposEncargadoServicioSocial, "email= ?", id, null, null,
					null);
			break;
		// se eligio telefono
		case 3:
			cursor = db.query("encargadoserviciosocial",
					camposEncargadoServicioSocial, "telefono= ?", id, null,
					null, null);
			break;
		// se eligio facultad
		case 4:
			cursor = db.query("encargadoserviciosocial",
					camposEncargadoServicioSocial, "facultad= ?", id, null,
					null, null);
			break;
		// se eligio escuela
		case 5:
			cursor = db.query("encargadoserviciosocial",
					camposEncargadoServicioSocial, "escuela = ?", id, null,
					null, null);
		default:
			cursor = db.query("encargadoserviciosocial",
					camposEncargadoServicioSocial, "idEncargado = ?", id, null,
					null, null);
		}
		// Cursor cursor =
		// db.query("proyecto",camposProyecto,id+" LIKE '%"+nombreProyecto+"%'",null,null,null,null,null);
		// if(cursor!=null){
		if (cursor.moveToFirst()) {
			do {
				EncargadoServicioSocial encargado = new EncargadoServicioSocial();

				encargado.setIdEncargado(cursor.getInt(0));
				encargado.setNombre(cursor.getString(1));
				encargado.setEmail(cursor.getString(2));
				encargado.setTelefono(cursor.getString(3));
				encargado.setFacultad(cursor.getString(4));
				encargado.setEscuela(cursor.getString(5));
				lista.add(encargado);
			} while (cursor.moveToNext());
			return lista;

		} else {
			return null;
		}

	}

	public Escuela consultarEscuela(String idEscuela) {
		return null;
	}

	public Institucion consultarInstitucion(String idInstitucion) {
		return null;
	}

	public Proyecto consultarProyecto(String codigoProyecto) {

		String[] id = { codigoProyecto };
		// String id="nombreProyecto";
		Cursor cursor = db.query("proyecto", camposProyecto, "idproyecto = ?",
				id, null, null, null);
		// Cursor cursor =
		// db.query("proyecto",camposProyecto,id+" LIKE '%"+nombreProyecto+"%'",null,null,null,null,null);
		if (cursor.moveToFirst()) {
			Proyecto proyecto = new Proyecto();
			proyecto.setIdProyecto(cursor.getInt(0));
			proyecto.setIdSolicitante(cursor.getInt(1));
			proyecto.setIdTipoProyecto(cursor.getInt(2));
			proyecto.setIdEncargado(cursor.getInt(3));
			proyecto.setNombre(cursor.getString(4));

			return proyecto;
		} else {
			return null;
		}

	}

	public Solicitante consultarSolicitante(String idSolicitante) {
		return null;
	}

	public TipoProyecto consultarTipoProyecto(String idTipoProyecto) {
		String[] id = { idTipoProyecto };
		// String id="nombreProyecto";
		Cursor cursor = db.query("tipoproyecto", camposTipoProyecto,
				"idtipoproyecto = ?", id, null, null, null);
		// Cursor cursor =
		// db.query("proyecto",camposProyecto,id+" LIKE '%"+nombreProyecto+"%'",null,null,null,null,null);
		if (cursor.moveToFirst()) {
			TipoProyecto tipoProyecto = new TipoProyecto();
			tipoProyecto.setIdTipoProyecto(cursor.getInt(0));
			tipoProyecto.setNombre(cursor.getString(1));

			return tipoProyecto;
		} else {
			return null;
		}

	}

	public TipoTrabajo consultarTipoTrabajo(String idTipoTrabajo) {
		return null;
	}

	// Llenado de base de datos para datos iniciales en caso los necesite
	public String llenarBD() {
		return null;
	}

	private boolean verificarIntegridad(Object dato, int relacion)
			throws SQLException {
		switch (relacion) {

		case 1: {
			// verificar que al insertar proyecto exista IdSolicitante e
			// IDTipoProyecto
			Proyecto proyecto = (Proyecto) dato;
			String[] id1 = { String.valueOf(proyecto.getIdSolicitante()) };
			String[] id2 = { String.valueOf(proyecto.getIdTipoProyecto()) };
			String[] id3 = { String.valueOf(proyecto.getIdEncargado()) };
			abrir();
			Cursor cursor1 = db.query("solicitante", null, "idsolicitante = ?",
					id1, null, null, null);
			Cursor cursor2 = db.query("tipoproyecto", null,
					"idtipoproyecto = ?", id2, null, null, null);
			Cursor cursor3 = db.query("encargado", null,
					"idencargado = ?", id3, null, null, null);
			if (cursor1.moveToFirst() && cursor2.moveToFirst()&&cursor3.moveToFirst()) {
				// Se encontraron datos
				return true;
			}
			return false;
		}

		case 2: {
			// verificar que al modificar nota exista carnet del alumno, el
			// codigo de materia y el ciclo
			Proyecto proyecto = (Proyecto) dato;
			String[] ids = { String.valueOf(proyecto.getIdProyecto()) };
			abrir();
			Cursor c = db.query("proyecto", null, "idproyecto = ?", ids, null,
					null, null);
			if (c.moveToFirst()) {
				// Se encontraron datos
				return true;
			}
			return false;
		}

		case 3: {
			// verificar que existe el tipoProyecto al actualizar
			TipoProyecto tipoProyecto = (TipoProyecto) dato;
			String[] id = { String.valueOf(tipoProyecto.getIdTipoProyecto()) };
			abrir();
			Cursor cursor = db.query("tipoproyecto", camposTipoProyecto, "idtipoproyecto=?",
					id, null, null, null);
			if (cursor.moveToFirst()) {
				return true;
			}
			else
				return false;
		}
		
		case 4:
		{
			//verifica que existe el tipoProyecto antes de eliminar
			//lo q hace es q verifica si en proyecto esta como fk tipoproyecto de ser 
			//asi regresara true y en el metodo de eliminar se eliminara ese registro
			//de la tabla proyecto 
			TipoProyecto tipoProyecto = (TipoProyecto)dato;
			String id[] = { String.valueOf(tipoProyecto.getIdTipoProyecto()) };
			abrir();
			Cursor cursor = db.query(true,"proyecto", new String[]{"idtipoproyecto"},"idtipoproyecto='" + tipoProyecto.getIdTipoProyecto() + "'", null, null,
					null, null, null);
			if(cursor.moveToFirst())
				return true;
			else
				return false;
		}	
		default:
			return false;
		}
	}// fin verificacion integridad
}// fin ControlDB