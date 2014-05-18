package sv.edu.ues.fia.appserviciosocial;

public class Institucion {
	private String idInstitucion,
				   nombre,
				   nit,
				   nitAnterior;	

	public Institucion(String idInstitucion,String nombre, String nit) {
		this(nombre,nit);
		this.idInstitucion = idInstitucion;		
	}
	
	public Institucion(String nombre, String nit) {
		super();
		this.nombre = nombre;
		this.nit = nit;
	}

	
	public String getNitAnterior() {
		return nitAnterior;
	}


	public void setNitAnterior(String nitAnterior) {
		this.nitAnterior = nitAnterior;
	}


	public String getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(String idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}


	
	

}
