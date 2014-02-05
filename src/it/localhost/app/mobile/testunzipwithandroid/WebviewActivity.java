package it.localhost.app.mobile.testunzipwithandroid;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;

public class WebviewActivity extends Activity {

	private WebView webView;
	private TextView textViewUrl;
	private String indexHtml = 
			Constants.FOLDER_ZIP_OUTPUT + 
			File.separator + 
			"index.html";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(this.getClass().getSimpleName(), "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		
		try {
			textViewUrl = (TextView)findViewById(R.id.textViewUrl);
			webView = (WebView)findViewById(R.id.webView1);
			
			File fileIndexHtml = new File(indexHtml);
			Log.d(this.getClass().getSimpleName(), "Index.html Path: " + fileIndexHtml.getAbsolutePath());
			
			textViewUrl.setText(fileIndexHtml.getName());
			webView.loadUrl("file:///" + fileIndexHtml.getAbsolutePath());
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "Exception", e);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.webview, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN) {
			switch(keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if(webView.canGoBack() == true) {
					webView.goBack();
				} else {
					finish();
				}
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
