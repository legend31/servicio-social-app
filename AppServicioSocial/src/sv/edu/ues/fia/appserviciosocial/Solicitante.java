package sv.edu.ues.fia.appserviciosocial;

public class Solicitante {
	
	private String idSolicitante,
					idInstitucion,
					idCargo,
					nombre,
					telefono,
					correo;

	public Solicitante( String idInstitucion,
			String idCargo, String nombre, String telefono, String correo) {
		super();
		//this.idSolicitante = idSolicitante;
		this.idInstitucion = idInstitucion;
		this.idCargo = idCargo;
		this.nombre = nombre;
		this.telefono = telefono;
		this.correo = correo;
	}

	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(String idCargo) {
		this.idCargo = idCargo;
	}

	public String getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(String idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
}
