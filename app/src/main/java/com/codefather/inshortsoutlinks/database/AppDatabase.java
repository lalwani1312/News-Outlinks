package com.codefather.inshortsoutlinks.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by hitesh-lalwani on 18/9/17.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "outlinks";
    public static final int VERSION = 1;
}
