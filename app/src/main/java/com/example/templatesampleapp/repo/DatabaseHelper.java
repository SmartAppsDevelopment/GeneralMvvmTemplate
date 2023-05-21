package com.example.templatesampleapp.repo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.accuspot.quotes.model.AuthorModel;
import com.accuspot.quotes.model.CategoryModel;
import com.example.templatesampleapp.helper.Constant;
import com.example.templatesampleapp.model.QuotesModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private final Context myContext;
    static int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, Constant.DB_NAME, null, DB_VERSION);
        this.myContext = context;
        DB_PATH = myContext.getDatabasePath(Constant.DB_NAME)
                .toString();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    //copy database from assets folder (.sqlite) file to an empty database
    public void copyDataBase() throws IOException {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(Constant.DB_NAME);

        String outFileName = DB_PATH;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TBL_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TBL_AUTHOR);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TBL_LATEST_QUOTES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TBL_BOOKMARK);
        onCreate(sqLiteDatabase);
    }

    public long insertNote(QuotesModel quoteModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.TBL_ID, quoteModel.getId());
        values.put(Constant.TBL_QUOTES_COLUMN_ID, quoteModel.getQuotes_id());
        values.put(Constant.TBL_QUOTES_COLUMN_NAME, quoteModel.getQuotes_name());
        values.put(Constant.TBL_QUOTES_BOOKMARK, quoteModel.getBookmark());
        values.put(Constant.TBL_CATEGORY_COLUMN_ID, quoteModel.getCat_id());

        // insert row
        long id = db.insert(Constant.TBL_BOOKMARK, null, values);
        db.close();
        return id;
    }

    public void deleteNote(QuotesModel quoteModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constant.TBL_BOOKMARK, Constant.TBL_QUOTES_COLUMN_NAME + " = ?",
                new String[]{String.valueOf(quoteModel.getQuotes_name())});
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<QuotesModel> getBookmarkData() {

        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {Constant.TBL_ID, Constant.TBL_QUOTES_COLUMN_ID, Constant.TBL_QUOTES_COLUMN_NAME, Constant.TBL_QUOTES_BOOKMARK,
                Constant.TBL_CATEGORY_COLUMN_ID};
        Cursor cursor = db.query(Constant.TBL_BOOKMARK, columns, null, null, null,
                null, null);
        ArrayList<QuotesModel> bookmarkArrayList = new ArrayList<>();

        while (cursor.moveToNext()) {
            QuotesModel bookmark = new QuotesModel("", 0, "", "", false, 0);

            bookmark.setId(cursor.getInt(cursor.getColumnIndex(Constant.TBL_ID)));
            bookmark.setQuotes_id(cursor.getString(cursor.getColumnIndex(Constant.TBL_QUOTES_COLUMN_ID)));
            bookmark.setQuotes_name(cursor.getString(cursor.getColumnIndex(Constant.TBL_QUOTES_COLUMN_NAME)));
            bookmark.setBookmark(cursor.getString(cursor.getColumnIndex(Constant.TBL_QUOTES_BOOKMARK)));
            bookmark.setCat_id(cursor.getInt(cursor.getColumnIndex(Constant.TBL_CATEGORY_COLUMN_ID)));

            bookmarkArrayList.add(bookmark);
        }
        return bookmarkArrayList;
    }

    @SuppressLint("Range")
    public List<QuotesModel> getAllNotes() {
        List<QuotesModel> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Constant.TBL_BOOKMARK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                QuotesModel note = new QuotesModel("", 0, "", "", false, 0);

                note.setId(cursor.getInt(cursor.getColumnIndex(Constant.TBL_ID)));
                note.setQuotes_id(cursor.getString(cursor.getColumnIndex(Constant.TBL_QUOTES_COLUMN_ID)));
                note.setQuotes_name(cursor.getString(cursor.getColumnIndex(Constant.TBL_QUOTES_COLUMN_NAME)));
                note.setBookmark(cursor.getString(cursor.getColumnIndex(Constant.TBL_QUOTES_BOOKMARK)));
                note.setCat_id(cursor.getInt(cursor.getColumnIndex(Constant.TBL_CATEGORY_COLUMN_ID)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        db.close();

        return notes;
    }


    public String getCategoryName(Integer cat_id) {
        String selectQuery = "SELECT " + Constant.TBL_CATEGORY_COLUMN_NAME + " FROM " + Constant.TBL_CATEGORY
                + " WHERE " + Constant.TBL_CATEGORY_COLUMN_ID + "=" + cat_id;

        String cat_name = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            cat_name = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return cat_name;
    }

    public int getQuotesCountCategory(int catId) {
        int count = 1;
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String Query = "Select * from " + Constant.TBL_QUOTES + " where " + Constant.TBL_CATEGORY_COLUMN_ID + " = " + catId;
        Cursor cursor = sqldb.rawQuery(Query, null);

        if (cursor.moveToFirst()) {
            count = 1;
            while (cursor.moveToNext()) {
                count++;
            }
        }
        cursor.close();
        sqldb.close();
        return count;
    }

    public int getQuotesCountAuthor(int author_id) {
        int count = 1;
        SQLiteDatabase sqldb = this.getWritableDatabase();
        String Query = "Select * from " + Constant.TBL_QUOTES + " where " + " author_id " + " = " + author_id;
        Cursor cursor = sqldb.rawQuery(Query, null);

        if (cursor.moveToFirst()) {
            count = 1;
            while (cursor.moveToNext()) {
                count++;
            }
        }
        cursor.close();
        sqldb.close();
        return count;
    }


    public ArrayList<CategoryModel> getAllCategories() {
        ArrayList<CategoryModel> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constant.TBL_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Integer cat_id = cursor.getInt(0);
                String cat_name = cursor.getString(1);
                byte[] cat_image = cursor.getBlob(2);
                CategoryModel categoryModel = new CategoryModel(cat_id, cat_name, cat_image);
                list.add(categoryModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<AuthorModel> getAllAuthor() {
        ArrayList<AuthorModel> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constant.TBL_AUTHOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Integer author_id = cursor.getInt(0);
                String author_name = cursor.getString(1);
                byte[] author_image = cursor.getBlob(2);
                AuthorModel authorModel = new AuthorModel(author_name, author_id, author_image);
                list.add(authorModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }


    public ArrayList<QuotesModel> getLatestQuotes() {
        ArrayList<QuotesModel> list_latest = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + Constant.TBL_LATEST_QUOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String quotes_id = cursor.getString(0);
                String quotes_name = cursor.getString(1);
                String quotes_date = cursor.getString(2);
                Boolean is_quotes_fav = cursor.getInt(3) > 0;

                QuotesModel quotesModel = new QuotesModel(quotes_id, quotes_name,
                        quotes_date, is_quotes_fav);
                list_latest.add(quotesModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list_latest;
    }


    public ArrayList<QuotesModel> getAllquotessByCatAuth(Integer id, Boolean isCategory) {
        ArrayList<QuotesModel> list = new ArrayList<>();
        String selectQuery = "";
        if (isCategory) {
            selectQuery = "SELECT * FROM " + Constant.TBL_QUOTES + " WHERE " + Constant.TBL_CATEGORY_COLUMN_ID + " = " + id;
        } else {
            selectQuery = "SELECT * FROM " + Constant.TBL_QUOTES + " WHERE " + Constant.TBL_AUTHOR_COLUMN_ID + " = " + id;
        }
        Log.d("selectQuery :", " - " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String quotes_id = cursor.getString(0);
                Integer quotes_cat_id = cursor.getInt(1);
                String quotes_name = cursor.getString(2);
                String quotes_date = cursor.getString(3);
                Boolean is_quotes_fav = cursor.getInt(4) > 0;
                Integer quotes_author_id = cursor.getInt(5);
                QuotesModel quotesModel = new QuotesModel(quotes_id, quotes_cat_id, quotes_name,
                        quotes_date, is_quotes_fav, quotes_author_id);
                list.add(quotesModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<QuotesModel> getBestQuotes() {

        ArrayList<QuotesModel> list = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Constant.TBL_BEST_QUOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String quotes_id = cursor.getString(0);
                String quotes_name = cursor.getString(1);
                byte[] quotes_image = cursor.getBlob(2);

                QuotesModel quotesModel = new QuotesModel(quotes_id, quotes_name, quotes_image);
                list.add(quotesModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
