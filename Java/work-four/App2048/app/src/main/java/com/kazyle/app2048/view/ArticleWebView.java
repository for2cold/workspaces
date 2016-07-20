package com.kazyle.app2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by root on 2016/7/20.
 */
public class ArticleWebView extends WebView {

    private ArticleWebViewListener listener = null;
    private boolean overedPage = false;

    public interface ArticleWebViewListener {
        void onScrollOverOneScreen();
        void onScrollLessThanOneScreen();
    }

    public ArticleWebView(Context context) {
        super(context);
    }

    public ArticleWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ArticleWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setListener(ArticleWebViewListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.getHeight() > 0 && t > this.getHeight()/3 && listener != null) {
            if (!overedPage) {
                overedPage = true;
                listener.onScrollOverOneScreen();
            }
        }
        if (this.getHeight() > 0 && t < this.getHeight()/3 && listener != null) {
            if (overedPage) {
                overedPage = false;
                listener.onScrollLessThanOneScreen();
            }
        }
    }
}
