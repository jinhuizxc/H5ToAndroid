package com.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView tvUrl;
    private WebView webview;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUrl = (TextView) findViewById(R.id.tv_url);
        webview = (WebView) findViewById(R.id.wv_test);
        setWebViewSetting();

//        webview.loadUrl("file:///android_asset/test.html");
        webview.loadUrl("file:///android_asset/map_face.html");
        tvUrl.setText(webview.getUrl());

    }

    private void setWebViewSetting() {
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new JsAction(), "action");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webview.setWebChromeClient(new WebChromeClient());
    }

    private class JsAction {

        @JavascriptInterface // H5调用Android的该方法
        public void openMap(final String jsonString) {
            //注意@JavascriptInterface 下的该方法和当前activity并不是同一个线程，所以增加runOnUiThread来
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    JSONObject object = null;
//                    try {
//                        object = new JSONObject(jsonString);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("H5调用Android显示对话框->地图");
//                    if (object != null) {
//                        builder.setMessage(object.optString("message", ""));
//                    }
//                    builder.setNegativeButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            // 在dialog消息的时候，将修改H5的内容
//                            reloadJs("alert", "地图");
//                        }
//                    });
//                    dialog.show();
                    reloadJs("alert", "地图");
                }
            });
        }

        @JavascriptInterface // H5调用Android的该方法
        public void openFace(final String jsonString) {
            //注意@JavascriptInterface 下的该方法和当前activity并不是同一个线程，所以增加runOnUiThread来
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    JSONObject object = null;
//                    try {
//                        object = new JSONObject(jsonString);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                    builder.setTitle("H5调用Android显示对话框->face");
//                    if (object != null) {
//                        builder.setMessage(object.optString("message", ""));
//                    }
//                    builder.setNegativeButton(android.R.string.ok, null);
//                    AlertDialog dialog = builder.create();
//                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            // 在dialog消息的时候，将修改H5的内容
//                            reloadJs("alert", "face");
//                        }
//                    });
//                    dialog.show();

                    reloadJs("alert", "face");
                }
            });
        }
    }

    /**
     * 2个参数
     * @param method
     * @param param1
     * @param params2
     */
    private void reloadJs(String method, String param1, String params2) {
        String url = String.format("javascript:%s(\"%s\", \"%s\")", method, param1, params2);
        Log.e(TAG, "reloadJs: " + url);
        // reloadJs: javascript:alert(face, dd)
        // reloadJs: javascript:alert("face", "dd")
    }

    /**
     * APP通过该方法来调用H5的方法
     *
     * @param method 方法名
     * @param param  方法参数
     */
    private void reloadJs(String method, String param) {
        String url = String.format("javascript:%s(\"%s\")", method, param);
        Log.e(TAG, "reloadJs: " + url);
        // E/MainActivity: reloadJs: javascript:alert("face")
        webview.loadUrl(url);
    }
}
