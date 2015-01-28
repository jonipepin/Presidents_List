package edu.usna.mobileos.presidentslist;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


public class WebviewActivity extends ActionBarActivity {

    WebView webView;

    private int progress;
    private static Handler progressBarHandler;
    public static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = (WebView) findViewById(R.id.wiki_webview);

        // get the Intent used to launch this Activity
        Intent intent = getIntent();

        // get name of president that was clicked in the ListView
        String president = intent.getStringExtra("president");

        // replace all spaces with the underscore character
        president.replaceAll(" ", "_");

        /*  This also works, but is a bit wordier: */
        //String president = intent.getExtras().getString("president");

//        String fullURL =
//                "http://en.wikipedia.org/wiki/List_of_Presidents_of_the_United_States";

        String baseURL = "http://en.m.wikipedia.org/wiki/";
        String fullURL = baseURL + president;

        // Prevent the default browser from trying to open the page
//        webView.setWebViewClient(new WebViewClient());

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        initializeWebView();

        // Enable Javascript
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(fullURL);
    }

    private void initializeWebView() {

        // enable tracking of webview load progress
        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different
                // scales.
                activity.setProgress(progress * 1000);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url,
                                      android.graphics.Bitmap favicon) {
                // run progress bar
                runProgressbar();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setProgress(progressBar.getMax());
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Toast.makeText(activity, "Network Error: " + description,
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void runProgressbar() {
        progress = 0;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        progressBarHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (progress >= 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    // layout progress bar
                    progress = webView.getProgress();
                    progressBar.setProgress(progress);

                    // Log.i("PEPIN", "Progress: " + progress);
                    progressBarHandler.sendEmptyMessageDelayed(0, 100);
                }
            }
        };
        progressBarHandler.sendEmptyMessage(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
