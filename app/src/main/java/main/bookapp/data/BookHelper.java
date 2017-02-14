package main.bookapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
