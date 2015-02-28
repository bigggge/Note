package com.xp.note;

import android.content.Context;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private FloatingActionButton addBtn;
    private NoteData noteData;
    private DBManager dbManager;
    private MyAdapter adapter;
    private ListView listView;
    private Context context;


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
                finish();
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
        adapter = new MyAdapter(this,noteData.getNoteDataList());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MyAdapter.ViewHolder viewHolder= (MyAdapter.ViewHolder)view.getTag();
                Log.d("Main","-----------------------view.gettag");
                String noteId = viewHolder.tvId.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, EditNote.class);
                intent.putExtra("id" , Integer.parseInt(noteId));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view,final int i, long l) {
                final Note note = ((MyAdapter)adapterView.getAdapter()).getItem(i);
                                if(note == null){
                                        return true;
                                    }
                                final int id = note.getId();
                new MaterialDialog.Builder(MainActivity.this)
                        .content(R.string.areyousure)
                        .positiveText(R.string.delete)
                        .negativeText(R.string.cancel)
                        .callback(new MaterialDialog.ButtonCallback() {
                                      @Override
                                      public void onPositive(MaterialDialog dialog) {
                                         DBManager.getInstance(MainActivity.this).deleteNote(id);
                                         adapter.removeItem(i);
                                      }
                                  }

                        ).show();

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
                        .title(R.string.about)
                        .content(R.string.content)
                        .positiveText(R.string.close)
                        .show();
                break;
            case R.id.action_clean:
                new MaterialDialog.Builder(MainActivity.this)
                        .content(R.string.areyousure)
                        .positiveText(R.string.clean)
                        .negativeText(R.string.cancel)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                dbManager.getDbReadable().execSQL("TRUNCATE TABLE");

                                       }
                        }).show();

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
    //清除数据

    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

}
