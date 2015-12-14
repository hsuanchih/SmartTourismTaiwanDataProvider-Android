package com.chiegaia.smarttourismtwdataprovider.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hsuan-Chih Chuang on 12/3/15.
 */
public class SmartTourismTWDBHelper extends SQLiteOpenHelper {

    // Database version control
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "smarttourismtw.db";


    public SmartTourismTWDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
