package com.vahabilisim.localdb.android.example;

import android.content.Context;
import com.vahabilisim.localdb.LocalDBTrans;
import com.vahabilisim.localdb.android.AndroidDBCore;

public class ExampleDBCore extends AndroidDBCore {

    private static final int VERSION = 3;

    public ExampleDBCore(Context context) {
        super(context, "exampledb.sqlite", VERSION);
    }

    @Override
    public void onCreate(LocalDBTrans trans) {
        // table "car" is from version 1
        trans.execSQL("CREATE TABLE car (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");

        // table "truck" is from version 2
        trans.execSQL("CREATE TABLE truck (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");

        // table "bike" is from this version = version 3
        trans.execSQL("CREATE TABLE bike (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");
    }

    @Override
    public void onUpgrade(LocalDBTrans trans, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            trans.execSQL("CREATE TABLE truck (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");
        }

        if (oldVersion < 3) {
            trans.execSQL("CREATE TABLE bike (id TEXT PRIMARY KEY, vendor TEXT, model TEXT, year TEXT)");
        }
    }

}
