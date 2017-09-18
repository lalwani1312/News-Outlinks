package com.codefather.inshortsoutlinks.mvp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public abstract class MvpPresenter<VIEW extends MvpView> {

    private static final String TAG = MvpPresenter.class.getSimpleName();

    protected VIEW mView;
    private boolean isPersistedOne;

    private MvpViewInvocationHandler mInvocationHandler;

    public MvpPresenter(Class<VIEW> clazz) {
        mInvocationHandler = new MvpViewInvocationHandler();
        mView = (VIEW) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, mInvocationHandler);
    }

    public void attachView(VIEW view) {
        mInvocationHandler.setView(view);
    }

    public void onCreate() {

    }

    public void onDestroy() {

    }

    public void onStart() {

    }

    public void onStop() {

    }

    public void detachView() {
        mInvocationHandler.setView(null);
    }

    protected VIEW getView() {
        return mView;
    }

    private class MvpViewInvocationHandler implements InvocationHandler {
        private VIEW view;

        public void setView(VIEW view) {
            this.view = view;
        }

        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            if (view != null) {
                method.invoke(view, objects);
            }
            return null;
        }
    }

    public boolean isPersistedOne() {
        return isPersistedOne;
    }

    public void setPersistedOne(boolean persistedOne) {
        isPersistedOne = persistedOne;
    }
}
