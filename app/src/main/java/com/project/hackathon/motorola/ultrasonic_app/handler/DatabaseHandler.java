package com.project.hackathon.motorola.ultrasonic_app.handler;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.hackathon.motorola.ultrasonic_app.model.Space;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by matheuscatossi on 5/14/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "spaceManager";
    private static final String TABLE_SPACE = "space";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POSITION1 = "position1";
    private static final String KEY_POSITION2 = "position2";
    private static final String KEY_POSITION3 = "position3";
    private static final String KEY_POSITION4 = "position4";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SPACE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_POSITION1 + " TEXT,"
                + KEY_POSITION2 + " TEXT,"
                + KEY_POSITION3 + " TEXT,"
                + KEY_POSITION4 + " TEXT )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPACE);
        onCreate(db);
    }

    public void addSpace(Space space) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, space.getName());
        values.put(KEY_POSITION1, space.getPosition1());
        values.put(KEY_POSITION2, space.getPosition2());
        values.put(KEY_POSITION3, space.getPosition3());
        values.put(KEY_POSITION4, space.getPosition4());

        db.insert(TABLE_SPACE, null, values);
        db.close();
    }

    public Space getSpace(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SPACE, new String[]{KEY_ID,
                        KEY_NAME, KEY_POSITION1, KEY_POSITION2, KEY_POSITION3, KEY_POSITION4}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Space space = new Space(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(3)), Double.parseDouble(cursor.getString(4)), Double.parseDouble(cursor.getString(5)));

        return space;
    }

    public List<Space> getAllSpaces() {
        List<Space> spaceList = new ArrayList<Space>();

        String selectQuery = "SELECT  * FROM " + TABLE_SPACE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Space space = new Space();
                space.setId(Integer.parseInt(cursor.getString(0)));
                space.setName(cursor.getString(1));
                space.setPosition1(Double.parseDouble(cursor.getString(2)));
                space.setPosition2(Double.parseDouble(cursor.getString(3)));
                space.setPosition3(Double.parseDouble(cursor.getString(4)));
                space.setPosition4(Double.parseDouble(cursor.getString(5)));
                spaceList.add(space);
            } while (cursor.moveToNext());
        }

        return spaceList;
    }

    public int updateSpace(Space space) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, space.getName());
        values.put(KEY_POSITION1, space.getPosition1());
        values.put(KEY_POSITION2, space.getPosition2());
        values.put(KEY_POSITION3, space.getPosition3());
        values.put(KEY_POSITION4, space.getPosition4());

        return db.update(TABLE_SPACE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(space.getId())});
    }

    public void deleteSpace(Space space) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SPACE, KEY_ID + " = ?",
                new String[]{String.valueOf(space.getId())});
        db.close();
    }

    public int getSpacesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SPACE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
