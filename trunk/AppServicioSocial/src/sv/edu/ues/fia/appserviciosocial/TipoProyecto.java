package sv.edu.ues.fia.appserviciosocial;

public class TipoProyecto {
	
	int idTipoProyecto;
	String nombre;
	private String enviado;
	
	public TipoProyecto(int idTipoProyecto, String nombre) {
		this.idTipoProyecto = idTipoProyecto;
		this.nombre = nombre;
	}
	
	public TipoProyecto(){}

	public int getIdTipoProyecto() {
		return idTipoProyecto;
	}

	public void setIdTipoProyecto(int idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEnviado() {
		return enviado;
	}

	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}
	
	
}
