package sv.edu.ues.fia.appserviciosocial;


public class Proyecto {
	
	int idProyecto;
	int idSolicitante;
	int idTipoProyecto;
	int idEncargado;
	String nombre;
	int numeroProyectos;
	private String enviado;
	
	Proyecto(int idProyecto, int idSolicitante, int idTipoProyecto, int idEncargado, String nombre)
	{
		this.idProyecto=idProyecto;
		this.idSolicitante=idSolicitante;
		this.idTipoProyecto=idTipoProyecto;
		this.idEncargado=idEncargado;
		this.nombre=nombre;
	}

	Proyecto(){}
	
	public int getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(int idProyecto) {
		this.idProyecto = idProyecto;
	}

	public int getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(int idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public int getIdTipoProyecto() {
		return idTipoProyecto;
	}

	public void setIdTipoProyecto(int idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

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
	public void setNumeroProyectos(int numero){
		this.numeroProyectos=numero;
	}
	
	public int getNumeroProyectos(){
		return numeroProyectos;
	}

	public String getEnviado() {
		return enviado;
	}

	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}
	
	
}
