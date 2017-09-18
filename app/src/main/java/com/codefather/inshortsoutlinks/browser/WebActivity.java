package com.codefather.inshortsoutlinks.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codefather.inshortsoutlinks.BaseActivity;
import com.codefather.inshortsoutlinks.R;
import com.codefather.inshortsoutlinks.model.News;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {

    public static final String EXTRA_NEWS = "news";
    public static final String EXTRA_POSITION = "position";

    private News mNews;
    private int mPosition;

    @BindView(R.id.web_view)
    WebView mWebView;

    @BindView(R.id.error_layout)
    LinearLayout mErrorLayout;

    @BindView(R.id.error)
    TextView mError;

    @BindView(R.id.fab_bookmark)
    FloatingActionButton mFabBookmark;

    public static void launchForResult(Activity activity, int requestCode, int position, News news) {
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_NEWS, news);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mNews = getIntent().getParcelableExtra(EXTRA_NEWS);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, -1);
        if (mNews == null || TextUtils.isEmpty(mNews.getUrl())) {
            finish();
        }
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.loadUrl(mNews.getUrl());
    }

    @OnClick(R.id.fab_bookmark)
    void onFabBookmarkClicked() {
        if (mNews.getIsBookmarked() == 1) {
            mNews.setIsBookmarked(0);
            mNews.save();
            mFabBookmark.setImageResource(R.drawable.ic_bookmark_unselected);
        } else {
            mNews.setIsBookmarked(1);
            mNews.save();
            mFabBookmark.setImageResource(R.drawable.ic_bookmark_selected);
        }
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra(EXTRA_POSITION, mPosition);
        data.putExtra(EXTRA_NEWS, mNews);
        setResult(RESULT_OK, data);
        super.onBackPressed();
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            mFabBookmark.setVisibility(View.VISIBLE);
            if (mNews.getIsBookmarked() == 1) {
                mFabBookmark.setImageResource(R.drawable.ic_bookmark_selected);
            } else {
                mFabBookmark.setImageResource(R.drawable.ic_bookmark_unselected);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            mWebView.setVisibility(View.GONE);
            mFabBookmark.setVisibility(View.GONE);
            mErrorLayout.setVisibility(View.VISIBLE);
            mError.setText(errorCode + ": " + description);
        }
    }
}
