package com.codefather.inshortsoutlinks.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.codefather.inshortsoutlinks.BaseActivity;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public abstract class MvpBaseActivity<VIEW extends MvpView, PRESENTER extends MvpPresenter<VIEW>>
        extends BaseActivity
        implements LoaderManager.LoaderCallbacks<PRESENTER> {

    private static final String TAG = MvpBaseActivity.class.getSimpleName();

    private final int ID_PRESENTER_LOADER = Integer.MAX_VALUE;
    private PRESENTER mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(ID_PRESENTER_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getPresenter() != null) {
            getPresenter().onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onStop();
    }

    @Override
    protected void onDestroy() {
        getPresenter().detachView();
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public Loader<PRESENTER> onCreateLoader(int id, Bundle args) {
        return new MvpPresenterLoader<>(getApplicationContext(), createPresenter());
    }

    @Override
    public void onLoadFinished(Loader<PRESENTER> loader, PRESENTER presenter) {
        mPresenter = presenter;
        boolean persistedOne = presenter.isPersistedOne();
        if (!persistedOne) {
            presenter.setPersistedOne(true);
            presenter.onCreate();
        }
        onPresenterLoaded(presenter, persistedOne);
        mPresenter.attachView((VIEW) this);
    }

    @Override
    public void onLoaderReset(Loader<PRESENTER> loader) {

    }

    protected abstract PRESENTER createPresenter();

    protected void onPresenterLoaded(PRESENTER presenter, boolean isPersistedOne) {
        if (!isPersistedOne) {
            presenter.onStart();
        }
    }

    protected PRESENTER getPresenter() {
        return mPresenter;
    }


}
