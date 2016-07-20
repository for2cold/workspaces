package com.kazyle.app2048;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeActivity extends Activity implements GestureDetector.OnGestureListener, View.OnClickListener {

    private String youmiAppId = "40a156907be82f5b";
    private String youmiAppSecret = "001a1e1faed62044";
    private boolean youmiTest = false;
    private long period = 11 * 1000;

    @InjectView(R.id.progress)
    ProgressBar progress;

    @InjectView(R.id.webView)
    WebView webView;

    @InjectView(R.id.btn)
    Button btn;

    private String url;

    private GestureDetector detector;

    private int flingWidth;

    private Timer timer;

    private boolean showed = false;

    private long delay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.inject(this);
        btn.setOnClickListener(this);
        initweb();
        url = "file:///android_asset/index.html";
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
        }
        AdManager.getInstance(this).init(youmiAppId, youmiAppSecret, youmiTest);
        // 积分墙
        OffersManager.getInstance(this).onAppLaunch();
        // 插屏广告
        SpotManager.getInstance(this).loadSpotAds();
        SpotManager.getInstance(this).setSpotOrientation(SpotManager.ORIENTATION_LANDSCAPE);
        SpotManager.getInstance(this).setAnimationType(SpotManager.ANIM_ADVANCE);
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!showed) {
                    SpotManager.getInstance(HomeActivity.this).showSpotAds(HomeActivity.this, new SpotDialogListener() {
                        @Override
                        public void onShowSuccess() {
                            showed = true;
                        }
                        @Override
                        public void onShowFailed() {}

                        @Override
                        public void onSpotClosed() {
                            showed = false;
                        }
                        @Override
                        public void onSpotClick(boolean b) {}
                    });
                }
            }
        }, 1000, period);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OffersManager.getInstance(this).onAppExit();
        SpotManager.getInstance(this).onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onClick(View view) {
        OffersManager.getInstance(this).showOffersWall();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - delay) > 2000) {
                Toast.makeText(HomeActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                delay = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initweb() {
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new InnerWebViewClient());
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progress.setVisibility(View.GONE);
                } else {
                    if (View.GONE == progress.getVisibility()) {
                        progress.setVisibility(View.VISIBLE);
                    }
                    progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1 == null || e2 == null) {
            return false;
        }
        if (e2.getX() - e1.getX() > flingWidth && Math.abs(velocityX) > 200) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        return false;
    }

    private class InnerWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
