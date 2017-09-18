package com.codefather.inshortsoutlinks.home;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public interface Executor {

    void execute(Runnable runnable);

    void onDestroy();
}
