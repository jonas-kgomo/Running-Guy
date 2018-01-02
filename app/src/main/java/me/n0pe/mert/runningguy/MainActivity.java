package me.n0pe.mert.runningguy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView.enableSlowWholeDocumentDraw();
		WebView.setWebContentsDebuggingEnabled(true);
		final WebView webView = new WebView(this);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setBackgroundColor(Color.TRANSPARENT);
		webView.setLayoutParams(new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT,
			ViewGroup.LayoutParams.MATCH_PARENT
		));
		//webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setSupportZoom(false);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDisplayZoomControls(false);
		webView.setDrawingCacheEnabled(false);
		webView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				webView.evaluateJavascript("setTimeout(function(e) { return Android.doThing(); }, 24 * 1000);",
					new ValueCallback<String>() {
						@Override
						public void onReceiveValue(String s) {
							return;
						}
				});
				return;
			}
		});
		webView.addJavascriptInterface(new JSInterface(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, "Bye!", Toast.LENGTH_SHORT).show();
				MainActivity.this.finish();
				return;
			}
		}), "Android");
		webView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				webView.reload();
				return true;
			}
		});
		webView.loadUrl("http://www.p01.org/jsconf_asia_2015/final.htm");
		setContentView(webView);
		return;
	}
}


class JSInterface {
	Runnable runnable = null;
	public JSInterface(Runnable runnable) {
		this.runnable = runnable;
		return;
	}
	@Keep
	@JavascriptInterface
	public void doThing() {
		runnable.run();
		return;
	}
}
