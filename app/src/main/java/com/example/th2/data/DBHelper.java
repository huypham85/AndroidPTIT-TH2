package com.example.th2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.th2.model.MyModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "db_thuc_hanh";

    // Country table name
    private static final String TABLE_THUC_HANH = "tbl_thuc_hanh";

    // Country Table Columns names
    private static final String KEY_ID = "id";
    private static final String BOOK_NAME = "book_name";
    private static final String AUTHOR = "author";
    private static final String RELEASE_DATE = "release_date";
    private static final String PUBLISHER = "publisher";
    private static final String PRICE = "price";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_THUC_HANH + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + BOOK_NAME + " TEXT,"
                + AUTHOR + " TEXT,"
                + RELEASE_DATE + " TEXT,"
                + PUBLISHER + " TEXT,"
                + PRICE + " REAL)";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUC_HANH);
        // Create tables again
        onCreate(db);
    }

    // Adding new country
    public void addData(MyModel myModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, myModel.getBookName());
        values.put(AUTHOR, myModel.getAuthor());
        values.put(RELEASE_DATE, myModel.getReleaseDate());
        values.put(PUBLISHER, myModel.getPublisher());
        values.put(PRICE, myModel.getPrice());

        // Inserting Row
        db.insert(TABLE_THUC_HANH, null, values);
        db.close(); // Closing database connection
    }

    // Getting single row
    MyModel getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_THUC_HANH, new String[]{KEY_ID,
                        BOOK_NAME,
                        AUTHOR,
                        RELEASE_DATE,
                        PUBLISHER,
                        PRICE
                }, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        // return country
        return new MyModel(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                Integer.parseInt(cursor.getString(5))
        );
    }

    // Get all data
    public List<MyModel> getAllData() {
        List<MyModel> data = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_THUC_HANH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MyModel myModel = new MyModel();
                myModel.setId(Integer.parseInt(cursor.getString(0)));
                myModel.setBookName(cursor.getString(1));
                myModel.setAuthor(cursor.getString(2));
                myModel.setReleaseDate(cursor.getString(3));
                myModel.setPublisher(cursor.getString(4));
                myModel.setPrice(Integer.parseInt(cursor.getString(5)));
                // Adding data to list
                data.add(myModel);
            } while (cursor.moveToNext());
        }

        // return data list
        return data;
    }

    // Updating single row
    public int updateData(MyModel myModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, myModel.getId());
        values.put(BOOK_NAME, myModel.getBookName());
        values.put(AUTHOR, myModel.getAuthor());
        values.put(RELEASE_DATE, myModel.getReleaseDate());
        values.put(PUBLISHER, myModel.getPublisher());
        values.put(PRICE, myModel.getPrice());

        // updating row
        return db.update(TABLE_THUC_HANH, values, KEY_ID + " = ?",
                new String[]{String.valueOf(myModel.getId())});
    }

    // Deleting single row
    public void deleteData(MyModel myModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_THUC_HANH, KEY_ID + " = ?",
                new String[]{String.valueOf(myModel.getId())});
        db.close();
    }

    // Getting row count
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_THUC_HANH;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}