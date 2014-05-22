package sv.edu.ues.fia.appserviciosocial;

public class Lista_entrada {
	
	private int idImagen;
	private String textoDebajo;
	private  String textoEncima;
	
	
	public Lista_entrada(int idImagen, String textoEncima)
	{
		this.idImagen = idImagen;
		this.textoEncima = textoEncima;
	}
	
	public int getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(int idImagen) {
		this.idImagen = idImagen;
	}

	public String getTextoDebajo() {
		return textoDebajo;
	}

	public void setTextoDebajo(String textoDebajo) {
		this.textoDebajo = textoDebajo;
	}

	public String getTextoEncima() {
		return textoEncima;
	}

	public void setTextoEncima(String textoEncima) {
		this.textoEncima = textoEncima;
	}

	

}
