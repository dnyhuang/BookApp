package main.bookapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.bookapp.R;

/**
 * Created by danny on 2017/2/14.
 */

public class BookHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "books.db";
    private static final int DB_VERSION = 1;

    private final Context context;

    public BookHelper(Context context){
        super(context, DB_NAME ,null,DB_VERSION);
        this.context = context;
    }

//    public BookHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//        this.context = context;
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("danny","aaaaa");
        sqLiteDatabase.execSQL("CREATE TABLE books " +
        "(_id INTEGER PRIMARY KEY, " +
        "cdate DATETIME NOT NULL, " +
        "info VARCHAR ," +
        "amount INTEGER)");

        InsertDataToDB(sqLiteDatabase);
    }

    private void InsertDataToDB(SQLiteDatabase sqLiteDatabase) {
        InputStream is = context.getResources().openRawResource(R.raw.book);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        try {
            String line = bufferedReader.readLine();
            while (line != null){
                sb.append(line);
                sb.append("\n");
                line = bufferedReader.readLine();
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray books = jsonObject.getJSONArray("books");

            for (int i = 0; i < books.length(); i++) {
                JSONObject innerObj = books.getJSONObject(i);
                String cdate = innerObj.getString(DatabaseContract.BooksColumns.CDATE);
                String info = innerObj.getString(DatabaseContract.BooksColumns.INFO);
                int amount = innerObj.getInt(DatabaseContract.BooksColumns.AMOUNT);

                ContentValues val = new ContentValues(3);
                val.put(DatabaseContract.BooksColumns.CDATE,cdate);
                val.put(DatabaseContract.BooksColumns.INFO,info);
                val.put(DatabaseContract.BooksColumns.AMOUNT,amount);

                long id = sqLiteDatabase.insert("books",null,val);

                if(id > 0){
                    Log.i("danny","Success");
                }else{
                    Log.i("danny","Fail");

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
