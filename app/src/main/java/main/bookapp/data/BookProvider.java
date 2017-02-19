package main.bookapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by danny on 2017/2/17.
 */

public class BookProvider extends ContentProvider {

    public static final String TAG = BookProvider.class.getName();

    private BookHelper bookHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    //All
    // content://main.bookapp/books , 123456

    //Single
    // content://main.bookapp/books/1 , 654321

    private static final int BOOKS = 123;

    private static final int BOOKS_WITH_ID = 456;

    static {
        //ALL
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_BOOKS,
                BOOKS);
        //Single
        uriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY,
                DatabaseContract.TABLE_BOOKS + "/#",
                BOOKS_WITH_ID);  //# 可以是任何數字
    }

    @Override
    public boolean onCreate() {
        bookHelper = new BookHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)){
            case BOOKS:
                //All
                Log.d(TAG,"Query All book");
                break;

            case BOOKS_WITH_ID:
                //Single
                selection = ((selection == null) ? "" : selection);
                /* 三元運算子
                if(selection == null)
                    selection = "";
                 else
                    selection = selection;
                 */
                selection = new StringBuffer(selection)
                        .append(DatabaseContract.BooksColumns._ID)
                        .append("=")
                        .append(uri.getLastPathSegment())
                        .toString();
                /*
                //All
                // content://main.bookapp/books , 這串的getLastPathSegment --> books
                //Single
                // content://main.bookapp/books/1 , 這串的getLastPathSegment --> 1
                 */
                break;
        }

        Cursor cursor = bookHelper.getWritableDatabase().query(DatabaseContract.TABLE_BOOKS,projection,selection,selectionArgs,null,null,sortOrder);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null; // no use
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        long id = bookHelper.getWritableDatabase().insert(DatabaseContract.TABLE_BOOKS , null , contentValues);

        if(id < 0){
            throw new SQLException("Insert Failed");
        }

        Uri newUri = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI,id);

        return newUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
