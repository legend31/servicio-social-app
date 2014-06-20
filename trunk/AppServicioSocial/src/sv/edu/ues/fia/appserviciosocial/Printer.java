package sv.edu.ues.fia.appserviciosocial;



import java.io.FileOutputStream;
import java.io.IOException;

import android.view.View;
import android.widget.Toast;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;

public class Printer  {
	private Context context;

	public Printer(Context context){
		this.context = context;
	}
	
	@SuppressLint("NewApi")
	public class MyPrintDocumentAdapter extends PrintDocumentAdapter	{
		Context context;
		private int pageHeight;
		private int pageWidth;
		public PdfDocument myPdfDocument; 
		public int totalpages;
		private Informacion informacion;
		
		public MyPrintDocumentAdapter(Informacion inf, Context context)		{
			this.context = context;	
			informacion = inf;
			totalpages = inf.getSize();
		}
		
		@Override
		public void onLayout(PrintAttributes oldAttributes,
				      PrintAttributes newAttributes,
				      CancellationSignal cancellationSignal,
				      LayoutResultCallback callback,
				      Bundle metadata) {

			myPdfDocument = new PrintedPdfDocument(context, newAttributes);
				    
			pageHeight = newAttributes.getMediaSize().getHeightMils()/1000 * 72;
			pageWidth = newAttributes.getMediaSize().getWidthMils()/1000 * 72;
				    
			if (cancellationSignal.isCanceled() ) {
				callback.onLayoutCancelled();
				return;
			}
				    
			if (totalpages > 0) {
			   PrintDocumentInfo.Builder builder = new PrintDocumentInfo
				  .Builder(informacion.getDocName()+".pdf") 
				  .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
				  .setPageCount(totalpages);
				                
			   PrintDocumentInfo info = builder.build();
			   callback.onLayoutFinished(info, true);		   
	
			} else {
			   callback.onLayoutFailed("Page count is zero.");
			}
		}		

	
		//Método responsable del renderizad
		@Override
		public void onWrite(final PageRange[] pageRanges,
		              final ParcelFileDescriptor destination,
		              final CancellationSignal cancellationSignal,
		              final WriteResultCallback callback) {
			
			for (int i = 0; i < totalpages; i++) {
				if (pageInRange(pageRanges, i))   	{
				     PageInfo newPage = new PageInfo.Builder(pageWidth, 
		                         pageHeight, i).create();
				    	
				     PdfDocument.Page page = 
		                          myPdfDocument.startPage(newPage);

				     if (cancellationSignal.isCanceled()) {
						  callback.onWriteCancelled();
						  myPdfDocument.close();
						  myPdfDocument = null;
						  return;
				     }
				     drawPage(page, i);
				     myPdfDocument.finishPage(page);  
				}
			}
			    
			try {
				myPdfDocument.writeTo(new FileOutputStream(
				            destination.getFileDescriptor()));
				   //Toast.makeText(context,"Se genero el archivo",Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				callback.onWriteFailed(e.toString());
				return;
			} finally {
				myPdfDocument.close();
				myPdfDocument = null;
			}

			callback.onWriteFinished(pageRanges);
			
		}
		
		private boolean pageInRange(PageRange[] pageRanges, int page)
		{
			for (int i = 0; i<pageRanges.length; i++)
			{
				if ((page >= pageRanges[i].getStart()) && 
	                    	                 (page <= pageRanges[i].getEnd()))
					return true;
			}
			return false;			
		}
		

		private void drawPage(PdfDocument.Page page, 
                              int pagenumber) {
		    Canvas canvas = page.getCanvas();
		    pagenumber++; // Make sure page numbers start at 1		    
		    int titleBaseLine = 72;
		    int leftMargin = 54;

		    Paint paint = new Paint();
		    paint.setColor(Color.BLACK);
		    paint.setTextSize(25);		    
		    String tituloPagina = 
		    		"Listado alumnos - Servicio Social. pág."+pagenumber;
		    canvas.drawText(
                     tituloPagina,
                                                   leftMargin,
                                                   titleBaseLine, 
                                                   paint);
		    paint.setTextSize(14);
		    int index = pagenumber-1;
		    int heighSpace = 35;
		    if ( informacion.get(index) != null ){
			    canvas.drawText("Carnet: "+informacion.get(index).getCarnet(), leftMargin, titleBaseLine + heighSpace, paint);
			    canvas.drawText("Nombre: "+informacion.get(index).getNombre(), leftMargin, titleBaseLine + heighSpace*2, paint);
			    canvas.drawText("Teléfono: "+informacion.get(index).getTelefono(), leftMargin, titleBaseLine + heighSpace*3, paint);
			    canvas.drawText("DUI: "+informacion.get(index).getDui(), leftMargin, titleBaseLine + heighSpace*4, paint);
			    canvas.drawText("NIT: "+informacion.get(index).getNit(), leftMargin, titleBaseLine + heighSpace*5, paint);
			    canvas.drawText("E-mail: "+informacion.get(index).getEmail(), leftMargin, titleBaseLine + heighSpace*6, paint);
		    }
		    PageInfo pageInfo = page.getInfo();
		    
		 
		}
		
	}

	
	@SuppressLint("NewApi")
	public void printDocument()	{
		ControlBD controlDB = new ControlBD(context);
		controlDB.abrir();
		/*Alumno a1,a2;
		String c1 = "FG12098",c2="MJ10458";
		a1 = controlDB.consultarAlumno(c1);
		a2 = controlDB.consultarAlumno(c2);
		*/
		Alumno [] arrayAlumnos = controlDB.getAlumnos();
		controlDB.cerrar();
		
     	PrintManager printManager = 
     			(PrintManager) context.getSystemService(Context.PRINT_SERVICE);

	    String jobName = R.string.app_name + " Document";	    
	    /*
	    arrayAlumnos[0]=a1;
	    arrayAlumnos[1]=a2;*/
	    if (arrayAlumnos != null){
	    		Informacion informacion = new Informacion("listado", arrayAlumnos);
	    		printManager.print(jobName, new MyPrintDocumentAdapter(informacion,context), null);
	    }else
	    	Toast.makeText(context, "No existen registros de alumnos",
	    			Toast.LENGTH_LONG).show();
	    
	}	
}

class Informacion{
	private String docName;
	private Alumno [] alumnos;
	
	public Informacion(String name,Alumno ... alumnos ){
		docName = name;
		this.alumnos = alumnos;
	}
	public String getDocName(){ return docName; }
	
	public int getSize(){
		return alumnos.length;
	}
	
	public Alumno get(int i){
		if (i<getSize())
			return alumnos[i];
		else
			return null;
	}
}
