package main.bookapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import main.bookapp.data.BookHelper;

public class MainActivity extends AppCompatActivity {

    private BookHelper helper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Create DB
        helper = new BookHelper(this);

        //
//        sqlLiteInsert();

        //
        ListView listview = (ListView) findViewById(R.id.listview);
        cursor = helper.getReadableDatabase().query("books", null, null, null, null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_expandable_list_item_2,
                cursor,
                new String[]{"cdate","info"},
                new int[]{android.R.id.text1,android.R.id.text2}
        ,0);
        listview.setAdapter(adapter);



    }

    private void sqlLiteInsert() {
        ContentValues contentValue = new ContentValues(3);
        contentValue.put("cdate","2016-11-08");
        contentValue.put("info","Android Books");
        contentValue.put("amount","2000");

        long id ;
        id = helper.getWritableDatabase().insert("books", null, contentValue);
        if (id > 0){
            Log.i("danny","yessssss!" + id);
        }else{
            Log.i("danny","nooooooo!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
