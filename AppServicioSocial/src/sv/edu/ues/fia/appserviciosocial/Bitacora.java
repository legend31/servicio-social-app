package sv.edu.ues.fia.appserviciosocial;

public class Bitacora {
	
	  int id;                   
	  int idTipoTrabajo;
	  String carnet;
	  int idProyecto;
	  String fecha;
	  String descripcion;
	  
	  public int getId (){
		  return id;
	  }
	  public void setId(int id){
		  this.id=id;
	  }
	  public int getIdTipoTrabajo(){
		  return idTipoTrabajo;
	  }
	  public void setIdTipoTrabajo(int idTipoTrabajo){
		  this.idTipoTrabajo=idTipoTrabajo;
	  }
	  public String getCarnet(){
		  return carnet;
	  }
	  public void setCarnet(String carnet){
		  this.carnet=carnet;
	  }
	  public int getIdProyecto(){
		  return idProyecto;
	  }
	  public void setIdProyecto(int idProyecto){
		  this.idProyecto = idProyecto;
	  }
	  public String getFecha(){
		  return fecha;
	  }
	  public void setFecha(String fecha){
		  this.fecha=fecha;
	  }
	  public String getdescripcion(){
		  return descripcion;
	  }
	  public void setdescripcion(String descripcion){
		  this.descripcion=descripcion;
	  }

}
