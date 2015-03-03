package com.xp.note;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by XP on 2015/2/15.
 */
public class EditNote extends ActionBarActivity implements View.OnClickListener {
    private EditText titleEt;
    private EditText contentEt;
    private FloatingActionButton saveBtn;
    private int noteID = -1;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_text);

        dbManager=new DBManager(this);
        titleEt= (EditText) findViewById(R.id.note_title);
        contentEt= (EditText) findViewById(R.id.note_content);
        saveBtn= (FloatingActionButton) findViewById(R.id.save);
        saveBtn.setOnClickListener(this);

        noteID = getIntent().getIntExtra("id", -1);
        if (noteID!=-1) {
            showNoteData(noteID);
        }
   }
    //显示更新的数据
    private void showNoteData(int id){
        Note note = dbManager.readData(id);
        titleEt.setText(note.getTitle());
        contentEt.setText(note.getContent());
    }

    @Override
    public void onClick(View view) {
        String title=contentEt.getText().toString();
        String content=titleEt.getText().toString();
        String time=getTime();
        if (noteID == -1) {
            dbManager.addToDB(title,content,time);
        } else {
            dbManager.updateNote(noteID, title,content,time);
        }
        Intent i=new Intent(EditNote.this,MainActivity.class);
        startActivity(i);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.finish();
    }

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm E");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
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
                new AlertDialog.Builder(this).setTitle("关于")
                        .setMessage("Created By Me")
                        .setPositiveButton("关闭", null).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //按返回键时
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditNote.this, MainActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.finish();
    }


}
