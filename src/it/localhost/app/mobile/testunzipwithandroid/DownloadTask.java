package it.localhost.app.mobile.testunzipwithandroid;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private File baseFolder;
	
	public DownloadTask(Context ctx) {
		this.context = ctx;
	}
	
	@Override
	protected void onPreExecute() {
		Log.d(this.getClass().getSimpleName(), "onPreExecute");
		super.onPreExecute();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Log.d(this.getClass().getSimpleName(), "doInBackground");
		
		InputStream is;
		ZipInputStream zis;
		try 
		{
			// InputStream from File.
//			is = new FileInputStream(FILEPATHIN);
			
			// InputStream from URL
			is = new URL(Constants.FILE_ZIP_INPUT).openStream();
			zis = new ZipInputStream(new BufferedInputStream(is));
			ZipEntry zipEntry;
		 
			// INTERNAL STORAGE
			//baseFolder = new File(c.getFilesDir(), Constants.FOLDER_ZIP_OUTPUT);
		 
			// EXTERNAL STORAGE
			baseFolder = new File(Constants.FOLDER_ZIP_OUTPUT);
			if(!baseFolder.exists() && !baseFolder.isDirectory()) {
				baseFolder.mkdirs();
				Log.d(this.getClass().getSimpleName(), "Base DIR created!");
			}
			
			Log.d(this.getClass().getSimpleName(), "LOCAL STORAGE FOLDER: " + baseFolder.getAbsolutePath());
		 
			while((zipEntry = zis.getNextEntry()) != null) 
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int count;
		
				String zipEntryName = zipEntry.getName();
				Log.d(this.getClass().getSimpleName(), "Entry from Zipped file: " + zipEntryName);
		 
				if(zipEntry.isDirectory()) {
					// FOLDER
					Log.d(this.getClass().getSimpleName(), "Entry is a Folder");
					File zipEntryFolder = new File(Constants.FOLDER_ZIP_OUTPUT + File.separator + zipEntryName);
					if(!zipEntryFolder.isDirectory()) {
						zipEntryFolder.mkdirs();
					}
				} else {
					// FILE
					Log.d(this.getClass().getSimpleName(), "Entry is a File");
					FileOutputStream fout = new FileOutputStream(Constants.FOLDER_ZIP_OUTPUT + File.separator + zipEntryName);
					// reading and writing
					while((count = zis.read(buffer)) != -1) 
					{
						baos.write(buffer, 0, count);
						byte[] bytes = baos.toByteArray();
						fout.write(bytes);             
						baos.reset();
						Log.d(this.getClass().getSimpleName(), "Data written: " + bytes.length);
					}
					
					fout.close();               
					zis.closeEntry();
				}
			}
		
			zis.close();
		 } 
		 catch(IOException e)
		 {
			 Log.e(this.getClass().getSimpleName(), "IOException", e);
			 cancel(true);
		 }
		 catch (Exception e) {
			 Log.e(this.getClass().getSimpleName(), "Exception", e);
			 cancel(true);
		}
		
		return null;
	}

	@Override
	protected void onCancelled() {
		Log.d(this.getClass().getSimpleName(), "onCancelled");
		super.onCancelled();
	}
	
	@Override
	protected void onPostExecute(Void result) {
		Log.d(this.getClass().getSimpleName(), "onPostExecute");
		super.onPostExecute(result);
		
		File fileLocal = new File(Constants.FOLDER_ZIP_OUTPUT);
		if(fileLocal.isDirectory()) {
			File[] fileList = fileLocal.listFiles();
			for(File f : fileList) {
				Log.i(this.getClass().getSimpleName(), "Local Unzipped Folder: " + f.getAbsolutePath());
			}
		}
		
		// Navigate to new Activity
		Intent i = new Intent(this.context, WebviewActivity.class);
		this.context.startActivity(i);
	};
	
}
