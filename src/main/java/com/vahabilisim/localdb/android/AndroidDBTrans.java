package com.vahabilisim.localdb.android;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.Map;
import com.vahabilisim.localdb.LocalDBCursor;
import com.vahabilisim.localdb.LocalDBTrans;

public class AndroidDBTrans implements LocalDBTrans {

    private final SQLiteDatabase conn;

    private boolean error;

    public AndroidDBTrans(SQLiteDatabase conn) {
        this.conn = conn;
        error = false;
    }

    @Override
    public synchronized boolean success() {
        return false == error;
    }

    @Override
    public synchronized void commit() {
        try {
            if (error == false) {
                conn.setTransactionSuccessful();
            }
            conn.endTransaction();
        } catch (Exception ex) {
            error = true;
        }
    }

    @Override
    public void rollback() {
        try {
            conn.endTransaction();
        } catch (Exception ex) {
            error = true;
        }
    }

    @Override
    public void execSQL(String sql) {
        try {
            conn.execSQL(sql);
        } catch (SQLException ex) {
            error = true;
        }
    }

    @Override
    public void execSQL(String sql, Object[] bindArgs) {
        try {
            conn.execSQL(sql, bindArgs);
        } catch (SQLException ex) {
            error = true;
        }
    }

    @Override
    public void insert(String table, String nullColumnHack, Map<String, Object> values) {
        if (error) {
            return;
        }
        try {
            conn.insertOrThrow(table, nullColumnHack, createContentValues(values));
        } catch (SQLException ex) {
            error = true;
        }
    }

    @Override
    public int delete(String table, String whereClause, String[] whereArgs) {
        return conn.delete(table, whereClause, whereArgs);

    }

    @Override
    public int update(String table, Map<String, Object> values, String whereClause, String[] whereArgs) {
        return conn.update(table, createContentValues(values), whereClause, whereArgs);
    }

    @Override
    public LocalDBCursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return new AndroidDBCursor(conn.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit));
    }

    private ContentValues createContentValues(Map<String, Object> values) {
        final ContentValues contentValues = new ContentValues(values.size());
        for (String key : values.keySet()) {
            final Object value = values.get(key);
            if (value instanceof String) {
                contentValues.put(key, (String) value);
            } else if (value instanceof Byte) {
                contentValues.put(key, (Byte) value);
            } else if (value instanceof Short) {
                contentValues.put(key, (Short) value);
            } else if (value instanceof Integer) {
                contentValues.put(key, (Integer) value);
            } else if (value instanceof Long) {
                contentValues.put(key, (Long) value);
            } else if (value instanceof Float) {
                contentValues.put(key, (Float) value);
            } else if (value instanceof Double) {
                contentValues.put(key, (Double) value);
            } else if (value instanceof Boolean) {
                contentValues.put(key, (Boolean) value);
            } else if (value instanceof byte[]) {
                contentValues.put(key, (byte[]) value);
            } else {
                contentValues.putNull(key);
            }
        }
        return contentValues;
    }

}
