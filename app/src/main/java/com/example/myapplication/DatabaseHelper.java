package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mahasiswa.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "mahasiswa";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama TEXT, " +
                "nim TEXT, " +
                "jurusan TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertMahasiswa(String nama, String nim, String jurusan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("nim", nim);
        cv.put("jurusan", jurusan);
        return db.insert(TABLE_NAME, null, cv);
    }

    public Cursor getAllMahasiswa() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void deleteMahasiswa(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
    }

    public void updateMahasiswa(int id, String nama, String nim, String jurusan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nama", nama);
        cv.put("nim", nim);
        cv.put("jurusan", jurusan);
        db.update(TABLE_NAME, cv, "id = ?", new String[]{String.valueOf(id)});
    }
}
