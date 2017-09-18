package com.codefather.inshortsoutlinks.factory;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

public interface SingletonFactory {

    NetworkManager networkManager();

    DbRepository dbRepository();
}
