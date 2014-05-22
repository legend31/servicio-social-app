package sv.edu.ues.fia.appserviciosocial;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ControlBD {

	private static final String[] camposAlumno = new String[] { "carnet",
			"nombre", "telefono", "dui", "nit", "email" };
	private static final String[] camposAsignacionProyecto = new String[] {
			"carnet", "idProyecto", "fecha" };
	private static final String[] camposBitacora = new String[] { "id",
			"carnet", "idProyecto", "idTipoTrabajo", "fecha", "descripcion" };
	private static final String[] camposEncargadoServicioSocial = new String[] {
			"idEncargado", "nombre", "email", "telefono", "facultad", "escuela"};
	private static final String[] camposEscuela = new String[] { "idEscuela",
			"idEncargado", "nombre", "facultad" };
	private static final String[] camposInstitucion = new String[] {
			"idInstitucion", "nombre", "nit" };
	private static final String[] camposProyecto = new String[] { "idProyecto",
			"idSolicitante", "idTipoProyecto", "idEncargado", "nombre" };
	private static final String[] camposSolicitante = new String[] {
			"idSolicitante", "idInstitucion","idCargo", "nombre", "telefono","correo_electronico" };
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
				db.execSQL("create table TIPOTRABAJO ( IDTIPOTRABAJO INTEGER not null primary key autoincrement, NOMBRE VARCHAR(100) not null, VALOR FLOAT  not null );");
				db.execSQL("create table ASIGNACIONPROYECTO ( CARNET VARCHAR(7) not null, IDPROYECTO INTEGER not null, FECHA DATE, PRIMARY KEY(carnet,idproyecto)"
						+ " CONSTRAINT fk_asignacionproyecto_proyecto FOREIGN KEY (idproyecto) REFERENCES proyecto(idproyecto) ON DELETE RESTRICT, CONSTRAINT "
						+ "fk_asignacionproyecto_alumno FOREIGN KEY (carnet) REFERENCES alumno(carnet) ON DELETE RESTRICT );");
				db.execSQL("create table usuarios (id INTEGER not null primary key autoincrement, usuario VARCHAR(50) not null, password VARCHAR(100) not null, tipo INTEGER not null);");
				// triggers
				db.execSQL("CREATE TRIGGER fk_solicitante_cargo BEFORE INSERT ON solicitante FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idcargo FROM cargo"
						+ " WHERE idcargo = NEW.idcargo) IS NULL) THEN RAISE(ABORT, 'No existe cargo') END; END;");
				db.execSQL("CREATE TRIGGER fk_solicitante_institucion BEFORE INSERT ON solicitante FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idinstitucion"
						+ " FROM institucion WHERE idinstitucion = NEW.idinstitucion) IS NULL) THEN RAISE(ABORT, 'No existe institucion') END; END;");
				// triggers para proyecto
				db.execSQL("CREATE TRIGGER fk_proyecto_encargado BEFORE INSERT ON proyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idencargado FROM "
						+ "encargadoserviciosocial WHERE idencargado = NEW.idencargado) IS NULL) THEN RAISE(ABORT, 'No existe encargado') END; END;");
				db.execSQL("CREATE TRIGGER fk_proyecto_solicitante BEFORE INSERT ON proyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idsolicitante FROM "
						+ "solicitante WHERE idsolicitante = NEW.idsolicitante) IS NULL) THEN RAISE(ABORT, 'No existe solicitante') END; END;");
				db.execSQL("CREATE TRIGGER fk_proyecto_tipoproyecto BEFORE INSERT ON proyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idtipoproyecto FROM"
						+ " tipoproyecto WHERE idtipoproyecto = NEW.idtipoproyecto) IS NULL) THEN RAISE(ABORT, 'No existe tipo de proyecto') END; END;");

				// triggers tipoProyecto
				//db.execSQL("CREATE TRIGGER TipoProyectoELiminar BEFORE DELETE ON TIPOPROYECTO FOR EACH ROW BEGIN DELETE FROM PROYECTO WHERE IDTIPOPROYECTO=OLD.IDTIPOPROYECTO; END");

				// triggers para asignacionProyecto
				db.execSQL("CREATE TRIGGER fk_asignacionproyecto_proyecto BEFORE INSERT ON asignacionproyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT "
						+ "idproyecto FROM proyecto WHERE idproyecto = NEW.idproyecto) IS NULL) THEN RAISE(ABORT, 'No existe proyecto') END; END;");
				db.execSQL("CREATE TRIGGER fk_asignacionproyecto_alumno BEFORE INSERT ON asignacionproyecto FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT "
						+ "carnet FROM alumno WHERE carnet = NEW.carnet) IS NULL) THEN RAISE(ABORT, 'No existe alumno') END; END;");
				db.execSQL("CREATE TRIGGER fk_bitacora_tipotrabajo BEFORE INSERT ON bitacora FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idtipotrabajo FROM "
						+ "tipotrabajo WHERE idtipotrabajo = NEW.idtipotrabajo) IS NULL) THEN RAISE(ABORT, 'No existe tipo de trabajo') END; END;");
				db.execSQL("CREATE TRIGGER fk_bitacora_proyecto BEFORE INSERT ON bitacora FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT idproyecto FROM "
						+ "proyecto WHERE idproyecto = NEW.idproyecto) IS NULL) THEN RAISE(ABORT, 'No existe proyecto') END; END;");
				db.execSQL("CREATE TRIGGER fk_bitacora_alumno BEFORE INSERT ON bitacora FOR EACH ROW BEGIN SELECT CASE WHEN ((SELECT carnet FROM alumno "
						+ "WHERE carnet = NEW.carnet) IS NULL) THEN RAISE(ABORT, 'No existe alumno') END; END;");
				// inserciones
				db.execSQL("insert into alumno values('FG12098', 'Pedro Fuentes',   '23456781', '033206621', '06142307906731', 'pedro@yahoo.es');");
				db.execSQL("insert into alumno values('MJ10458', 'Luis Martinez',   '22378781', '033673420', '06132307901231', 'luis@yahoo.com');");
				db.execSQL("insert into alumno values('QS11457', 'Juan Quevedo',    '23456896', '033209871', '09232307904531', 'juan@gmail.es');");
				db.execSQL("insert into alumno values('SA09027', 'Ricardo Sanchez', '23451231', '033207823', '08122307901931', 'ricardo@hotmail.es');");
				db.execSQL("insert into institucion values(null, 'Institución 1', '06142506921232');");
				db.execSQL("insert into institucion values(null, 'Institución 2', '02142509871232');");
				db.execSQL("insert into institucion values(null, 'Institución 3', '06172506924567');");
				db.execSQL("insert into cargo values(null, 'Presidente', 'Puesto mas alto');");
				db.execSQL("insert into cargo values(null, 'Jefe de informatica', 'Puesto intermedio');");
				db.execSQL("insert into solicitante values (null, 2, 1, 'Juan Peraza', '27845689', 'peraza@info.org');");
				db.execSQL("insert into solicitante values (null, 1, 2, 'Mario Luigi', '27856239', 'mario@emq.info');");
				db.execSQL("insert into tipoproyecto values (null, 'Gubernamental');");
				db.execSQL("insert into tipoproyecto values (null, 'Social');");
				db.execSQL("insert into tipoproyecto values (null, 'Privado');");
				db.execSQL("insert into tipotrabajo values(null, 'Programación', 12.50);");
				db.execSQL("insert into tipotrabajo values(null, 'Diseño', 32.18);");
				db.execSQL("insert into tipotrabajo values(null, 'Analisis', 27.40);");
				db.execSQL("insert into encargadoserviciosocial values(null, 'Esteban Gonzalez', 'gonzalez@ues.edu.sv', '23458512', 'Economía', 'Economía');");
				db.execSQL("insert into encargadoserviciosocial values(null, 'Sebastian Dominguez', 'dominguezez@ues.edu.sv', '23453421', 'medicina', 'Medicina');");
				db.execSQL("insert into encargadoserviciosocial values(null, 'Kevin Funes', 'funes@ues.edu.sv', '23454100', 'Agronomía', 'Veterinaria');");
				db.execSQL("insert into encargadoserviciosocial values(null, 'Julio Campos', 'campos@ues.edu.sv', '23450074', 'Ingenieria y arquitectura', 'Ingenieria Industrial');");
				db.execSQL("insert into proyecto values(null, 2, 2, 3, 'Cuidado de perrros');");
				db.execSQL("insert into proyecto values(null, 1, 3, 2, 'Atención a personas');");
				db.execSQL("insert into proyecto values(null, 2, 1, 1, 'Consultoria contable');");
				db.execSQL("insert into proyecto values(null, 1, 3, 4, 'Revision de maquila');");
				db.execSQL("insert into asignacionproyecto values ('FG12098', 1, date('2014-06-29'));");
				db.execSQL("insert into asignacionproyecto values ('MJ10458', 2, date('2014-05-12'));");
				db.execSQL("insert into asignacionproyecto values ('QS11457', 3, date('2014-02-04'));");
				db.execSQL("insert into asignacionproyecto values ('SA09027', 4, date('2014-08-22'));");
				db.execSQL("insert into bitacora values(1, 1, 'FG12098', 1, date('2014-06-29'), 'Introduccion de objetivos');");
				db.execSQL("insert into bitacora values(null, 2, 'FG12098', 1, date('2014-07-02'), 'Diseñando cosas');");
				db.execSQL("insert into bitacora values(null, 2, 'MJ10458', 3, date('2013-11-18'), 'Presentacion de resultados');");
				db.execSQL("insert into bitacora values(null, 3, 'MJ10458', 3, date('2013-11-20'), 'Avance literal');");
				db.execSQL("insert into usuarios values(null, 'admin', 'admin', 1);");
				db.execSQL("insert into usuarios values(null, 'alumno', 'alumno', 2);");

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
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
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
		try{
		contador = db.insertOrThrow("asignacionproyecto", null, valoresAsignacion);
		}catch(Exception integridad)
		{
			String ex = integridad.getMessage();
			return ex.substring(0, ex.lastIndexOf("(code 19)"));
		}
		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
		} else {
			mensaje = "Registro ingresado";
		}
		return mensaje;
	}

	public String insertar(Bitacora bitacora) {
		String mensaje = "";
		long contador = 0;
		ContentValues valoresBitacora = new ContentValues();
		valoresBitacora.putNull("id");
		valoresBitacora.put("carnet", bitacora.getCarnet());
		valoresBitacora.put("idProyecto", bitacora.getIdProyecto());
		valoresBitacora.put("idTipoTrabajo", bitacora.getIdTipoTrabajo());
		valoresBitacora.put("fecha",bitacora.getFecha());
		valoresBitacora.put("descripcion", bitacora.getdescripcion());
		try{
			contador = db.insert("bitacora", null, valoresBitacora);
			}catch(Exception integridad)
			{
				String ex = integridad.getMessage();
				return ex.substring(0, ex.lastIndexOf("(code 19)"));
			}
		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
		} else {
			mensaje = "Registro ingresado";
		}
		return mensaje;
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
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
		} else {
			mensaje = "Registro ingresado";
		}
		return mensaje;
	}

	public String insertar(Cargo cargo) {
		String mensaje = "";
		long contador = 0;
		ContentValues valoresCargo = new ContentValues();
		valoresCargo.putNull("idcargo");
		valoresCargo.put("nombre", cargo.getNombre());
		valoresCargo.put("descripcion", cargo.getDescripcion());
		
		contador = db.insert("cargo", null, valoresCargo);
		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
		} else {
			mensaje = "Registro ingresado";
		}
		return mensaje;
	}

	public String insertar(Institucion institucion) {
		String mensaje = "";
		Institucion institucionVerificando = 
				consultarInstitucion(institucion.getNit());
		//long contador = 0;
		ContentValues valoresInstitucion = new ContentValues();
		valoresInstitucion.putNull("idinstitucion");
		valoresInstitucion.put("nombre", institucion.getNombre());
		valoresInstitucion.put("nit", institucion.getNit());
		
		//if (contador == -1 || contador == 0) {
		if ( institucionVerificando != null )
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
		 else {
			//contador = db.insert("institucion", null, valoresInstitucion);
			db.insert("institucion", null, valoresInstitucion);
			mensaje = "Registro ingresado";
		}
		return mensaje;
	}

	public String insertar(Proyecto proyecto) {
		String mensaje = "";
		long contador = 0;

		ContentValues values = new ContentValues();
		// if (verificarIntegridad(proyecto, 1)) {

		values.putNull("idproyecto");
		values.put("idsolicitante", proyecto.getIdSolicitante());
		values.put("idtipoproyecto", proyecto.getIdTipoProyecto());
		values.put("idencargado", proyecto.getIdEncargado());
		values.put("nombre", proyecto.getNombre());
		contador = db.insert("proyecto", null, values);
		// }//este no xq ya tengo el trigger

		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro. Verificar inserción";
		} else {
			mensaje = "Registro ingresado " + contador;
		}
		return mensaje;
	}

	public String insertar(Solicitante solicitante) {
		String mensaje = "";
		long contador = 0;

		ContentValues values = new ContentValues();		

		values.putNull("idsolicitante");
		values.put("idinstitucion", solicitante.getIdInstitucion());
		values.put("idcargo", solicitante.getIdCargo());
		values.put("nombre", solicitante.getNombre());
		values.put("telefono", solicitante.getTelefono());
		values.put("correo_electronico", solicitante.getCorreo());		
		
		contador = db.insert("solicitante", null, values);
		/*
		if (contador == -1 || contador == 0) 
			mensaje = "Error al Insertar el registro. Verificar inserción";
		 else
			mensaje = "Registro ingresado " + contador;
		*/
		String [] arrayEmail = {solicitante.getCorreo()}; 
		Cursor cursor = db.query("solicitante",camposSolicitante,"correo_electronico = ?",
				arrayEmail,null,null,null);
		cursor.moveToFirst();		
		return cursor.getString(0);
	}

	public String insertar(TipoProyecto tipoProyecto) {
		String mensaje = "";
		long contador = 0;
		if (verificarIntegridad(tipoProyecto, 5)!=true) {
			ContentValues content = new ContentValues();
			content.putNull("idtipoproyecto");
			content.put("nombre", tipoProyecto.getNombre());
			contador = db.insert("tipoproyecto", null, content);
		}
		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro. Registro con el mismo nombre a uno anterior";
		} else {
			mensaje = "Registro ingresado " + contador;
		}
		return mensaje;
	}

	public String insertar(TipoTrabajo tipoTrabajo) {
		String mensaje = "";
		long contador = 0;
		 
		ContentValues valoresTipoTrabajo = new ContentValues();
		valoresTipoTrabajo.putNull("idTipoTrabajo");//autonumerico
		valoresTipoTrabajo.put("nombre", tipoTrabajo.getNombre());
		valoresTipoTrabajo.put("valor", tipoTrabajo.getValor());		
		try{
			contador = db.insert("tipotrabajo", null, valoresTipoTrabajo);
			}catch(Exception integridad)
			{
				String ex = integridad.getMessage();
				return ex.substring(0, ex.lastIndexOf("(code 19)"));
			}
		if (contador == -1 || contador == 0) {
			mensaje = "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
		} else {
			mensaje = "Registro ingresado";
		}
		return mensaje;	
		
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
		int contador =0;
		String[] id = { asignacion.getCarnet(), asignacion.getIdProyecto() };
		ContentValues cv = new ContentValues();
		cv.put("fecha", asignacion.getFecha());
		contador+=db.update("asignacionproyecto", cv, "carnet = ? AND idproyecto=?", id);
		if(contador == 0 || contador == -1)
		{
			return "Registro no actualizado";
		}
		return "Registro Actualizado Correctamente";
	}

	public String actualizar(Bitacora bitacora) {
		String mensaje = "";
		String[] id ={toString().valueOf(bitacora.getId())};
		long contador = 0;
		ContentValues valoresBitacora = new ContentValues();		
		valoresBitacora.put("carnet", bitacora.getCarnet());
		valoresBitacora.put("idProyecto", bitacora.getIdProyecto());
		valoresBitacora.put("idTipoTrabajo", bitacora.getIdTipoTrabajo());
		valoresBitacora.put("fecha",bitacora.getFecha());
		valoresBitacora.put("descripcion", bitacora.getdescripcion());
		try{
			contador = db.update("bitacora", valoresBitacora, "id=?", id );
			}catch(Exception integridad)
			{
				String ex = integridad.getMessage();
				return ex.substring(0, ex.lastIndexOf("(code 19)"));
			}
		if (contador == -1 || contador == 0) {
			mensaje = "Error al modificar el registro";
		} else {
			mensaje = "Registro modificado";
		}
		return mensaje;
	}

	public String actualizar(EncargadoServicioSocial encargado) {
		String[] id = { String.valueOf(encargado.getIdEncargado())};
		ContentValues cv = new ContentValues();
		cv.put("nombre", encargado.getNombre());
		cv.put("email", encargado.getEmail());
		cv.put("telefono", encargado.getTelefono());
		cv.put("facultad", encargado.getFacultad());
		cv.put("escuela", encargado.getEscuela());
		db.update("encargadoserviciosocial", cv, "idencargado = ?", id);
		return "Registro Actualizado Correctamente";
	}

	public String actualizar(Cargo cargo) {
		String[] id = { String.valueOf(cargo.getIdCargo())};
		ContentValues cv = new ContentValues();
		cv.put("nombre", cargo.getNombre());
		cv.put("descripcion", cargo.getDescripcion());
		
		db.update("cargo", cv, "idcargo = ?", id);
		return "Registro Actualizado Correctamente";
	}

	public String actualizar(Institucion institucion) {
		String[] nit = { institucion.getNitAnterior() };
		ContentValues cv = new ContentValues();
		cv.put("nombre", institucion.getNombre());		
		cv.put("nit", institucion.getNit());		
		db.update("institucion", cv, "nit = ?", nit);
		return "Registro Actualizado Correctamente";
	}

	public String actualizar(Proyecto proyecto) {
		if (verificarIntegridad(proyecto, 2)) {
			String[] id = { String.valueOf(proyecto.getIdProyecto()) };
			ContentValues cv = new ContentValues();
			long contador=0;

			cv.put("idproyecto", proyecto.getIdProyecto());
			cv.put("idsolicitante", proyecto.getIdSolicitante());
			cv.put("idtipoproyecto", proyecto.getIdTipoProyecto());
			cv.put("idencargado", proyecto.getIdEncargado());
			cv.put("nombre", proyecto.getNombre());
			contador+=db.update("proyecto", cv, "idproyecto = ?", id);
			if(contador==-1||contador==0)
				return "Registro No Actualizado.";
			else
				return "Registro Actualizado Correctamente.";
		} else
			return "Registro no Existente";
	}

	public String actualizar(Solicitante solicitante) {
		String[] id = { solicitante.getIdSolicitante()};
		ContentValues cv = new ContentValues();
		cv.put("nombre", solicitante.getNombre());
		cv.put("correo_electronico", solicitante.getCorreo());
		cv.put("telefono", solicitante.getTelefono());
		cv.put("idcargo", solicitante.getIdCargo());
		cv.put("idInstitucion", solicitante.getIdInstitucion());
		Log.i("DB","Se almacena: "
				+solicitante.getNombre()+" "
				+solicitante.getCorreo()+" "
				+solicitante.getTelefono()+" "
				+solicitante.getIdCargo()+" "
				+solicitante.getIdInstitucion()+" "+id);
		db.update("solicitante", cv, "idsolicitante = ?", id);
		return "Registro Actualizado Correctamente";
	}

	public String actualizar(TipoProyecto tipoProyecto) {
		long contador=0;

		if (verificarIntegridad(tipoProyecto, 3)) {
			String[] id = { String.valueOf(tipoProyecto.getIdTipoProyecto()) };
			ContentValues cv = new ContentValues();

			cv.put("idtipoproyecto", tipoProyecto.getIdTipoProyecto());
			cv.put("nombre", tipoProyecto.getNombre());
			contador+=db.update("tipoproyecto", cv, "idtipoproyecto = ?", id);
			if(contador==-1||contador==0)
				return "Registro No Actualizado.";
			else
				return "Registro Actualizado Correctamente.";
		} else
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

	public String eliminar(AsignacionProyecto asignacion) {
		String regAfectados = "filas afectadas= ";
		int contador = 0;
		String id[] = {asignacion.getCarnet(), asignacion.getIdProyecto()};
		contador += db.delete("asignacionproyecto", "carnet=? AND idproyecto=?", id);
		if(contador == 0 || contador == -1)
		{
			return "No se realizó eliminación";
		}
		regAfectados += contador;
		return regAfectados;
	}

	public String eliminarBitacora(String idBitacora) {
		String regAfectados = "filas afectadas= ";
		long contador = 0;
		/*
		 * si regresa true es q existe tipoproyecto como fk en proyecto y lo
		 * eliminara antes caso contrario solo eliminara tipoproyecto de su
		 * respectiva tabla
		 */
		contador += db.delete("bitacora","id='" + idBitacora + "'",null);
		regAfectados += contador;
		if (contador == 0 || contador == -1)
			return regAfectados;
		else
			return regAfectados;
	}

	public String eliminar(EncargadoServicioSocial encargado) {
		String regAfectados = "";
		int contador = 0;
		contador += db.delete("encargadoserviciosocial", "idencargado='" + encargado.getIdEncargado() + "'",
				null);
		regAfectados += contador;
		return regAfectados;
	}

	public String eliminar(Cargo cargo) {
		String regAfectados = "";
		int contador = 0;
		contador += db.delete("cargo", "idcargo='" + cargo.getIdCargo() + "'",
				null);
		regAfectados += contador;
		return regAfectados;
	}

	public String eliminar(Institucion institucion) {
		String regAfectados = "filas afectadas= ";
		String id[] = { String.valueOf(institucion.getNit()) };
		int contador = 0;
		contador += db.delete("institucion", "nit=?", id);
		regAfectados += contador;
		return regAfectados;
	}

	public String eliminar(Proyecto proyecto) {
		String regAfectados = "filas afectadas= ";
		int contador = 0;

		if (verificarIntegridad(proyecto, 2)) {
			String id[] = { String.valueOf(proyecto.getIdProyecto()) };
			contador += db.delete("proyecto", "idproyecto=?", id);
		}

		regAfectados += contador;
		return regAfectados;

	}

	public String eliminar(Solicitante solicitante) {
		String regAfectados = "filas afectadas= ";
		String id[] = { solicitante.getIdSolicitante() };
		int contador = 0;
		contador += db.delete("solicitante", "idSolicitante=?", id);
		
		regAfectados += contador;
		return regAfectados;
	}

	public String eliminar(TipoProyecto tipoProyecto) {
		String regAfectados = "filas afectadas= ";
		long contador = 0;
		/*
		 * si regresa true es q existe tipoproyecto como fk en proyecto y lo
		 * eliminara antes caso contrario solo eliminara tipoproyecto de su
		 * respectiva tabla
		 */
		if (verificarIntegridad(tipoProyecto, 4)) {
			contador += db.delete("proyecto","idtipoproyecto='"+ tipoProyecto.getIdTipoProyecto() + "'",null);
		}

		contador += db.delete("tipoproyecto","idtipoproyecto='" + tipoProyecto.getIdTipoProyecto() + "'",null);
		regAfectados += contador;
		if (contador == 0 || contador == -1)
			return regAfectados;
		else
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

	public ArrayList<AsignacionProyecto> consultarAsignacionProyecto(String parametro,int tipo) {
		ArrayList<AsignacionProyecto> asignaciones = new ArrayList<AsignacionProyecto>();
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
			do{
				AsignacionProyecto asignacion = new AsignacionProyecto();
				asignacion.setCarnet(cursor.getString(0));
				asignacion.setIdProyecto(cursor.getString(1));
				asignacion.setFecha(cursor.getString(2));
				asignaciones.add(asignacion);
			}while(cursor.moveToNext());
			return asignaciones;
		} else {
			return null;
		}
	}

	public Bitacora consultarBitacora(String parametro) {
		String[] id = { parametro };//parametro
		Cursor cursor = db.query("bitacora", camposBitacora,"id= ?", id,
				null, null, null);
		if (cursor.moveToFirst()) {
			Bitacora objBitacora = new Bitacora();
			objBitacora.setCarnet(cursor.getString(1));
			objBitacora.setIdProyecto(cursor.getInt(2));
			objBitacora.setIdTipoTrabajo(cursor.getInt(3));
			objBitacora.setFecha(cursor.getString(4));
			objBitacora.setdescripcion(cursor.getString(5));			
			return objBitacora;
		} else {
			return null;
		}
	}

	

	/////////Metodo para que regrese mas de un dato en la consulta  >>>>pendiente de arreglar<<<<<<<  ///////////
	
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

	//consultar cargo
	public ArrayList<Cargo> consultarCargo(String busqueda, int seleccion) {
		String[] id = { busqueda };
		int opcion = seleccion;
		Cursor cursor;
		ArrayList<Cargo> lista = new ArrayList<Cargo>();
		switch (opcion) {

		// Se eligio id
		case 0:
			cursor = db.query("cargo",camposCargo, "idCargo = ?", id, null,
					null, null);
			break;
		// se eligio nombre
		case 1:

			cursor = db.query("cargo",camposCargo, "nombre= ?", id, null, null,
					null);
			break;
		// Se eligio email
		
		default:
			cursor = db.query("cargo", camposCargo, "idCargo= ?", id, null,
					null, null);
		}
		
		if (cursor.moveToFirst()) {
			do {
				Cargo cargo=new Cargo();

				cargo.setIdCargo(cursor.getInt(0));
				cargo.setNombre(cursor.getString(1));
				cargo.setDescripcion(cursor.getString(2));
				
				lista.add(cargo);
			} while (cursor.moveToNext());
			return lista;

		} else {
			return null;
		}

	}//fin consultar cargo


	public Escuela consultarEscuela(String idEscuela) {
		return null;
	}

	public Institucion consultarInstitucion(String nitInstitucion) {
		String [] nit = {nitInstitucion};
		
		Cursor cursor = db.query("institucion",
				camposInstitucion, "nit = ?", nit,
				null, null, null);
		
		if ( cursor.moveToFirst() ) 				
			return new Institucion(cursor.getString(0), //IdInstitucion
								   cursor.getString(1), //Nombre
								   cursor.getString(2));//NIT
		 else 
			return null;
		
	}
	
	public Institucion consultarInstitucionById(String idInstitucion) {
		String [] id = {idInstitucion};
		
		Cursor cursor = db.query("institucion",
				camposInstitucion, "idinstitucion = ?", id,
				null, null, null);
		
		if ( cursor.moveToFirst() ) 				
			return new Institucion(cursor.getString(0), //IdInstitucion
								   cursor.getString(1), //Nombre
								   cursor.getString(2));//NIT
		 else 
			return null;
		
	}
	
	
	/*
	public Cargo consultarCargo(String idCargo){
		Cargo cargo = new Cargo();
		cargo.setDescripcion("descripcion");
		cargo.setIdCargo("0");
		cargo.setNombre("Supervisor");
		return cargo;
	}
	*/
	public Proyecto consultarProyecto(String codigoProyecto) {

		String[] id = { codigoProyecto };

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
		String[] id = { idSolicitante };
		// String id="nombreProyecto";
		Cursor cursor = db.query("solicitante", camposSolicitante,
				"idsolicitante = ?", id, null, null, null);
	
		if (cursor.moveToFirst()) {			
			Solicitante solicitante= new Solicitante(cursor.getString(1),//IdInstitucion
					cursor.getString(2),//IdCargo
					cursor.getString(3),//NOmbre
					cursor.getString(4),//telefono
					cursor.getString(5));	//email
			
			
			solicitante.setIdSolicitante(idSolicitante);
			Log.i("OK", "Retornado solicitante: "+cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "
					+cursor.getString(3)+" "+cursor.getString(4)+" "+cursor.getString(5)+" ");
			return solicitante;
	
		} else {
			Log.i("NOT OK", "Retornado null");
			return null;
		}
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
		
		//retorna null si no existe el TipoProeycto con ese id

	}

	public TipoTrabajo consultarTipoTrabajo(String idTipoTrabajo) {
		String[] id = { idTipoTrabajo };
		// String id="nombreProyecto";
		Cursor cursor = db.query("tipotrabajo", camposTipoTrabajo,
				"idtipotrabajo = ?", id, null, null, null);
		// Cursor cursor =
		// db.query("proyecto",camposProyecto,id+" LIKE '%"+nombreProyecto+"%'",null,null,null,null,null);
		if (cursor.moveToFirst()) {
			TipoTrabajo objTipoTrabajo = new TipoTrabajo();
			objTipoTrabajo.setIdTipoTrabajo(toString().valueOf(cursor.getInt(0)));
			objTipoTrabajo.setNombre(cursor.getString(1));
			objTipoTrabajo.setValor(toString().valueOf(cursor.getDouble(2)));
			return objTipoTrabajo;
		} else {
			return null;
		}
	}
	
	public String verificarUsuario(String usuario, String password)
	{
		String[] id = { usuario, password };
		String[] campo = {"tipo"};
		Cursor cursor = db.query("usuarios", campo, "usuario = ? AND password = ?", id, null, null, null);
		if (cursor.moveToFirst()) {
			return cursor.getString(0);
		} else {
			return null;
		}
	}

	// Llenado de base de datos para datos iniciales en caso los necesite
	public String llenarBD() {
		return null;
	}

	private boolean verificarIntegridad(Object dato, int relacion)
			throws SQLException {
		switch (relacion) {

		case 1: { // verificar que al insertar proyecto exista IdSolicitante e
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
			Cursor cursor3 = db.query("encargado", null, "idencargado = ?",
					id3, null, null, null);
			if (cursor1.moveToFirst() && cursor2.moveToFirst()
					&& cursor3.moveToFirst()) {
				// Se encontraron datos
				return true;
			}
			return false;
		}

		case 2: {// verifica si existe el proyecto antes de actualizar , eliminar
			//tambien para actualizar tendria que verificar que exista ese tipoproyecto
			//idsolicitante y encargado
			Proyecto proyecto = (Proyecto) dato;
			String[] ids = { String.valueOf(proyecto.getIdProyecto()) };
			abrir();
			Cursor c = db.query("proyecto", null, "idproyecto = ?", ids, null,
					null, null);
			if (c.moveToFirst()) { // Se encontraron datos
				return true;
			}
			return false;
		}

		case 3: { // verificar que existe el tipoProyecto al actualizar
			TipoProyecto tipoProyecto = (TipoProyecto) dato;
			String[] id = { String.valueOf(tipoProyecto.getIdTipoProyecto()) };
			abrir();
			Cursor cursor = db.query("tipoproyecto", camposTipoProyecto,
					"idtipoproyecto=?", id, null, null, null);
			if (cursor.moveToFirst()) {
				return true;
			} else
				return false;
		}

		case 4: { // verifica que existe el tipoProyecto antes de eliminar lo q
			// hace es q verifica si en proyecto esta como fk tipoproyecto de
			// ser //asi
			// regresara true y en el metodo de eliminar se eliminara ese
			// registro //de
			// la tabla proyecto
			TipoProyecto tipoProyecto = (TipoProyecto) dato;
			String id[] = { String.valueOf(tipoProyecto.getIdTipoProyecto()) };
			abrir();
			Cursor cursor = db.query(true, "proyecto",new String[] { "idtipoproyecto" }, "idtipoproyecto='"+ tipoProyecto.getIdTipoProyecto() + "'", null,
					null, null, null, null);
			if (cursor.moveToFirst())
				return true;
			else
				return false;
		}

		case 5: {//ve si existe un tipoProyecto con el mismo nombre
			TipoProyecto tipoProyecto = (TipoProyecto) dato;
			Cursor cursor = db.query("tipoproyecto", camposTipoProyecto,
					"nombre='" + tipoProyecto.getNombre() + "'", null, null,
					null, null);
			if (cursor.moveToFirst()) {
				return true;
			} else
				return false;
		}

		default:
			return false;
		}
	}// fin verificacion integridad

}// fin ControlDB