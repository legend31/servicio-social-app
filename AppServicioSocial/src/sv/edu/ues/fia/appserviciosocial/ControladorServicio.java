package sv.edu.ues.fia.appserviciosocial;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class ControladorServicio {

	// Usando en PHP
	public static String obtenerRespuestaPeticion(String url, Context ctx) {

		String respuesta = " ";

		// Estableciendo tiempo de espera del servicio
		HttpParams parametros = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parametros, 3000);
		HttpConnectionParams.setSoTimeout(parametros, 5000);

		// Creando objetos de conexion
		HttpClient cliente = new DefaultHttpClient(parametros);
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse httpRespuesta = cliente.execute(httpGet);
			StatusLine estado = httpRespuesta.getStatusLine();
			int codigoEstado = estado.getStatusCode();
			if (codigoEstado == 200) {
				HttpEntity entidad = httpRespuesta.getEntity();
				respuesta = EntityUtils.toString(entidad);
			}
		} catch (Exception e) {
			Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG)
					.show();
			// Desplegando el error en el LogCat
			Log.v("Error de Conexión", e.toString());
		}
		return respuesta;
	}

	// Usado en Java
	public static String obtenerRespuestaPost(String url, JSONObject obj,
			Context ctx) {
		String respuesta = " ";
		try {
			HttpParams parametros = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(parametros, 3000);
			HttpConnectionParams.setSoTimeout(parametros, 5000);

			HttpClient cliente = new DefaultHttpClient(parametros);
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("content-type", "application/json");

			StringEntity nuevaEntidad = new StringEntity(obj.toString());
			httpPost.setEntity(nuevaEntidad);
			Log.v("Peticion", url);
			Log.v("POST", httpPost.toString());
			HttpResponse httpRespuesta = cliente.execute(httpPost);
			StatusLine estado = httpRespuesta.getStatusLine();

			int codigoEstado = estado.getStatusCode();
			if (codigoEstado == 200) {
				respuesta = Integer.toString(codigoEstado);
				Log.v("respuesta", respuesta);
			} else {
				Log.v("respuesta", Integer.toString(codigoEstado));
			}
		} catch (Exception e) {
			Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG)
					.show();
			// Desplegando el error en el LogCat
			Log.v("Error de Conexion", e.toString());
		}

		return respuesta;
	}

	public static List<Alumno> obtenerAlumno(String json, Context ctx) {

		List<Alumno> listaAlumnos = new ArrayList<Alumno>();

		try {
			JSONArray alumnosJSON = new JSONArray(json);
			for (int i = 0; i < alumnosJSON.length(); i++) {

				JSONObject obj = alumnosJSON.getJSONObject(i);

				Alumno alumno = new Alumno();
				alumno.setCarnet(obj.getString("CARNET"));
				alumno.setNombre(obj.getString("NOMBRE"));
				alumno.setTelefono(obj.getString("TELEFONO"));
				alumno.setDui(obj.getString("DUI"));
				alumno.setNit(obj.getString("NIT"));
				alumno.setEmail(obj.getString("EMAIL"));
				alumno.setPath(obj.getString("PATH"));
				listaAlumnos.add(alumno);
			}
			return listaAlumnos;
		} catch (Exception e) {
			Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG)
					.show();
			return null;
		}

	}
	
	
	public static List<EncargadoServicioSocial> obtenerEncargado(String json, Context ctx) {

		List<EncargadoServicioSocial> listaEncargados = new ArrayList<EncargadoServicioSocial>();

		try {
			JSONArray encargadosJSON = new JSONArray(json);
			for (int i = 0; i < encargadosJSON.length(); i++) {

				JSONObject obj = encargadosJSON.getJSONObject(i);

				EncargadoServicioSocial encargado = new EncargadoServicioSocial();
				encargado.setIdEncargado(obj.getInt("IDENCARGADO"));
				encargado.setNombre(obj.getString("NOMBRE"));
				encargado.setEmail(obj.getString("EMAIL"));
				encargado.setTelefono(obj.getString("TELEFONO"));
				encargado.setFacultad(obj.getString("FACULTAD"));
				encargado.setEscuela(obj.getString("ESCUELA"));
				encargado.setPath(obj.getString("PATH"));
				listaEncargados.add(encargado);
			}
			return listaEncargados;
		} catch (Exception e) {
			Toast.makeText(ctx, "Error en parseo de JSON", Toast.LENGTH_LONG)
					.show();
			return null;
		}

	}

	// Java
	public static void insertarObjeto(String url, JSONObject obj, Context ctx) {
		String respuesta = obtenerRespuestaPost(url, obj, ctx);
		try {
			if (respuesta.equals("200"))
				Toast.makeText(ctx, "Inserción Correcta en el servidor",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(ctx, "Error registro duplicado en el servidor",
						Toast.LENGTH_LONG).show();
			Log.v("", respuesta);
		} catch (Exception e) {
			Toast.makeText(ctx, "Error en la respuesta JSON", Toast.LENGTH_LONG)
					.show();
		}
	}

	// PHP
	public static void insertarObjeto(String peticion, Context ctx) {

		String json = obtenerRespuestaPeticion(peticion, ctx);
		try {
			JSONObject resultado = new JSONObject(json);
			int respuesta = resultado.getInt("resultado");
			if (respuesta == 1)
				Toast.makeText(ctx, "Registro ingresado en el servidor",
						Toast.LENGTH_LONG).show();
			else
				Toast.makeText(ctx, "Error registro duplicado en el servidor",
						Toast.LENGTH_LONG).show();
			Log.v("JSON", json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static void subirImagen(String archivo, Context ctx) {
		String result = null;
		try {

			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

			HttpPost httppost = new HttpPost(
					"http://hv11002pdm115.hostei.com/serviciosweb/upload.php");
			File file = new File(archivo);

			MultipartEntity multipartEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			multipartEntity.addPart("archivo", new FileBody(file));

			httppost.setEntity(multipartEntity);
			result = httpclient.execute(httppost,new FileUploadResponseHandler());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
	}

	public static void descargarImagen(String nombreImagen, Context ctx) {
		Bitmap loadedImage = null;
		try {
			URL imageUrl = new URL("http://hv11002pdm115.hostei.com/serviciosweb/imagenes/"+ nombreImagen);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.connect();

			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(conn.getInputStream(), null, o);
			final int height = o.outHeight;
		    final int width = o.outWidth;
		    Log.v("Medidas", "Height "+height+", Width "+width);
		    int inSampleSize = 1;
		    int reqHeight=600;
		    int reqWidth =600;
			//Codigo de pagina de Android
			 // Raw height and width of image
			 if (height > reqHeight || width > reqWidth) {

			        final int halfHeight = height / 2;
			        final int halfWidth = width / 2;

			        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
			        // height and width larger than the requested height and width.
			        while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) 
			        {
			            inSampleSize *= 2;
			        }
			    }
			 Log.v("In sample size", ""+inSampleSize);
			 conn.disconnect();
			 conn = (HttpURLConnection) imageUrl.openConnection();
			 conn.connect();
			 // Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = inSampleSize;
			loadedImage = BitmapFactory.decodeStream(conn.getInputStream(),null, o2);
			if(loadedImage == null)
				Log.v("NULOS", "la imagen esta nula");
			File file = new File(Environment.getExternalStorageDirectory(),nombreImagen);
			FileOutputStream fOut = new FileOutputStream(file);
			loadedImage.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
			fOut.flush();
			fOut.close();
			fOut = null;
			loadedImage.recycle();
			loadedImage = null;
		} catch (IOException e) {
			Toast.makeText(ctx, "Error al cargar la imagen: " + e.getMessage(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
}

@SuppressWarnings("rawtypes")
class FileUploadResponseHandler implements ResponseHandler {

	@Override
	public Object handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {

		HttpEntity r_entity = response.getEntity();
		String responseString = EntityUtils.toString(r_entity);
		Log.d("UPLOAD", responseString);

		return responseString;
	}

}
