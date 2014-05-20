package sv.edu.ues.fia.appserviciosocial;

public class TipoTrabajo {
	String idTipoTrabajo;
	String nombre;
	String valor;
	
	public String getIdTipoTrabajo(){
		return idTipoTrabajo;
	}

	public String getNombre(){
		return nombre;
	}
	public String getValor() {
		return valor;
	}
	public void setIdTipoTrabajo(String idTipoTrabajo){
		this.idTipoTrabajo=idTipoTrabajo;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setValor(String valor){
		this.valor=valor;
	}
}
