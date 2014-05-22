package sv.edu.ues.fia.appserviciosocial;

public class TipoTrabajo {
	String idTipoTrabajo;
	String nombre;
	Double valor;
	
	public String getIdTipoTrabajo(){
		return idTipoTrabajo;
	}

	public String getNombre(){
		return nombre;
	}
	public Double getValor() {
		return valor;
	}
	public void setIdTipoTrabajo(String idTipoTrabajo){
		this.idTipoTrabajo=idTipoTrabajo;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setValor(String valor){
		this.valor=Double.parseDouble(valor);
	}
}
