package com.xp.note;

/**
 * Created by XP on 2015/2/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;




    //保存日记的数据
public class NoteData {
    //保存日记数据的集合
    private List<Note> noteDataList = new ArrayList<Note>();
    private Context context = null;
    private DBManager dm = null;
    public NoteData(Context context) {
        this.context = context;
        dm =  new DBManager(context);
        dm.readFromDB(noteDataList);
    }
    // 得到日记记录
    public List<Note> getNoteDataList() {
        return noteDataList;
    }


    // 重新从数据库中读数据

    public void refreshNoteData(){
        noteDataList.clear();
        dm.readFromDB(noteDataList);
    }
}

