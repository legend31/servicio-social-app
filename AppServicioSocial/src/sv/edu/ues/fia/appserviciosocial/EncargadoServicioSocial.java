package sv.edu.ues.fia.appserviciosocial;



public class EncargadoServicioSocial {

	private int idEncargado;
     private String nombre;
     private String email;
     private String telefono;
     private String facultad;
     private String escuela;
     
	

	public int getIdEncargado() {
		return idEncargado;
	}

	public void setIdEncargado(int idEncargado) {
		this.idEncargado = idEncargado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFacultad() {
		return facultad;
	}

	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}

	public String getEscuela() {
		return escuela;
	}

	public void setEscuela(String escuela) {
		this.escuela = escuela;
	}

	@Override
	public String toString() {
		return "EncargadoServicioSocial [idEncargado=" + idEncargado
				+ ", nombre=" + nombre + "]";
	}
	
	
     
}
