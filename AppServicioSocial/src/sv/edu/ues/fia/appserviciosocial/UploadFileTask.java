package sv.edu.ues.fia.appserviciosocial;

import java.io.File;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

class UploadFileTask extends AsyncTask<String,Void,String> {

	private Activity activity;
	
	public UploadFileTask(Activity activity){
		this.activity = activity;
	}
	
    protected String doInBackground(String... archivo) {
    	String result = null;
    	try {

        	HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

            HttpPost httppost = new HttpPost("http://localhost/serviciosweb/upload.php");
            File file = new File(archivo[0]);
            
            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
            multipartEntity.addPart("archivo", new FileBody(file));

            httppost.setEntity(multipartEntity);
            result = httpclient.execute(httppost, new FileUploadResponseHandler());
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return result;
    }

    protected void onPostExecute(String feed) {
    	Toast.makeText(activity, feed,
		        Toast.LENGTH_SHORT).show();
    }
 }

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

