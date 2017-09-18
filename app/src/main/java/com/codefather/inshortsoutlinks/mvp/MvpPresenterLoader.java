package com.codefather.inshortsoutlinks.mvp;

import android.content.Context;
import android.support.v4.content.Loader;


/**
 * Created by hitesh-lalwani on 18/9/17.
 */
public class MvpPresenterLoader<PRESENTER extends MvpPresenter> extends Loader<PRESENTER> {


    private PRESENTER mPresenter;
    public static final String TAG = MvpPresenterLoader.class.getSimpleName();

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public MvpPresenterLoader(Context context, PRESENTER presenter) {
        super(context);
        checkPresenterIfNull(presenter);
        this.mPresenter = presenter;
    }

    @Override
    protected void onStartLoading() {
        deliverResult(mPresenter);
    }

    private void checkPresenterIfNull(PRESENTER presenter) {
        if (presenter == null) {
            throw new RuntimeException("Presenter passed to loader can not be null");
        }
    }
}
