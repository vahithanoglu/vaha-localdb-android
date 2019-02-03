package com.vahabilisim.localdb.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.vahabilisim.localdb.LocalDBCore;
import com.vahabilisim.localdb.LocalDBException;
import com.vahabilisim.localdb.LocalDBTrans;

public abstract class AndroidDBCore extends SQLiteOpenHelper implements LocalDBCore {

    public AndroidDBCore(Context context, String dbName, int version) {
        super(context, dbName, null, version);
    }

    @Override
    public LocalDBTrans startReadableTrans() throws LocalDBException {
        final SQLiteDatabase conn = getReadableDatabase();
        conn.beginTransaction();
        return new AndroidDBTrans(conn);
    }

    @Override
    public LocalDBTrans startWritableTrans() throws LocalDBException {
        final SQLiteDatabase conn = getWritableDatabase();
        conn.beginTransaction();
        return new AndroidDBTrans(conn);
    }

    @Override
    public void onCreate(SQLiteDatabase conn) {
        conn.beginTransaction();

        final LocalDBTrans trans = new AndroidDBTrans(conn);
        try {
            onCreate(trans);
        } finally {
            trans.commit();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase conn, int oldVersion, int newVersion) {
        conn.beginTransaction();

        final LocalDBTrans trans = new AndroidDBTrans(conn);
        try {
            onUpgrade(trans, oldVersion, newVersion);
        } finally {
            trans.commit();
        }
    }

}
