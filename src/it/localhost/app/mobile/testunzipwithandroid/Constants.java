package it.localhost.app.mobile.testunzipwithandroid;

import java.io.File;

import android.os.Environment;

public class Constants {
	
	public final static String FILE_ZIP_INPUT = 
//			"http://sdrv.ms/1bsacbd";
//			"http://sdrv.ms/1bsapeo";
			"https://dl.dropboxusercontent.com/u/22350279/ZIPFast.zip";
//			"https://dl.dropboxusercontent.com/u/22350279/ZIPStore.zip"
			;
	
	public final static String FOLDER_ZIP_OUTPUT = 
			Environment.getExternalStorageDirectory() + 
			File.separator +
			"ZipLocalFolder";
	
	public final static String INDEX_HTML = 
			FOLDER_ZIP_OUTPUT + 
			File.separator + 
			"index.html";
}
