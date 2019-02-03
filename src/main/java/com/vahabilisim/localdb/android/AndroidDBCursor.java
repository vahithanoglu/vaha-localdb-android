package com.vahabilisim.localdb.android;

import android.database.Cursor;
import com.vahabilisim.localdb.LocalDBCursor;

public class AndroidDBCursor implements LocalDBCursor {

    private final Cursor cursor;

    public AndroidDBCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public boolean moveToFirst() {
        return cursor == null ? false : cursor.moveToFirst();
    }

    @Override
    public boolean moveToNext() {
        return cursor == null ? false : cursor.moveToNext();
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        return cursor.getBlob(columnIndex);
    }

    @Override
    public String getString(int columnIndex) {
        return cursor.getString(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) {
        return cursor.getShort(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) {
        return cursor.getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) {
        return cursor.getLong(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) {
        return cursor.getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) {
        return cursor.getDouble(columnIndex);
    }
}
