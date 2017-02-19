package main.bookapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by danny on 2017/2/17.
 */

public class DatabaseContract {

    public static final String TABLE_BOOKS = "books";

    public static final class BooksColumns implements BaseColumns{
        public static final  String CDATE = "cdate";
        public static final  String INFO = "info";
        public static final  String AMOUNT = "amount";
    }

    public static final String CONTENT_AUTHORITY = "main.bookapp";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_BOOKS)
            .build();
}
