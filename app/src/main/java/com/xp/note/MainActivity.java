package com.xp.note;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private FloatingActionButton addBtn;
    private NoteData noteData;
    private DBManager dbManager;
    private MyAdapter adapter;
    private ListView listView;

    long waitTime = 2000;
    long touchTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteData=new NoteData(this);


        listView= (ListView) findViewById(R.id.list);
        addBtn = (FloatingActionButton) findViewById(R.id.text);
        addBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,EditNote.class);
        switch (view.getId()){
            case R.id.text:
                startActivity(i);
                Log.d("Main","----------------------to EditNote");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        readFromDB();
    }

    public void readFromDB() {
        noteData.refreshNoteData();
//        cursor = dbReader.query(noteDBOpenHelper.TABLE_NAME, null, null, null, null,
//                null, null);
        adapter = new MyAdapter(this,noteData.getNoteDataList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditNote.class);
//                intent.putExtra(NoteDBOpenHelper.ID,
//                        noteData.getNoteDataList().get(i).getId());
//                intent.putExtra(NoteDBOpenHelper.TITLE, noteData.getNoteDataList().get(i).getTitle());
//                intent.putExtra(NoteDBOpenHelper.CONTENT, noteData.getNoteDataList().get(i).getContent());
//                intent.putExtra(NoteDBOpenHelper.TIME, cursor.getString(cursor.getColumnIndex(NoteDBOpenHelper.TIME)));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new MaterialDialog.Builder(MainActivity.this)
                        .content("Are you sure to delete？")
                        .positiveText("delete")
                        .negativeText("cancel")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                dbManager.deleteNote(????);
                            }
                        }).show();
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                new MaterialDialog.Builder(this)
                        .title("About")
                        .content("from bigggge")
                        .positiveText("close")
                        .show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            finish();
        }
    }


}
