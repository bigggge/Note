package com.xp.note;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by XP on 2015/2/15.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notes;


public MyAdapter(Context context,List<Note> notes){
    this.context=context;
    this.notes=notes;
}

    @Override
    public int getCount() {
        return notes.size();


    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView
                    = LayoutInflater.from(context).inflate(R.layout.notes_row, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle
                    = (TextView) convertView.findViewById(R.id.note_title);
            viewHolder.tvContent
                    = (TextView) convertView.findViewById(R.id.note_content);
            viewHolder.tvTime
                    = (TextView) convertView.findViewById(R.id.note_time);
            convertView.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
//        viewHolder.tvTitle.setText(notesList.get(position).getTitle());
//        viewHolder.tvContent.setText(notesList.get(position).getContent());
//        viewHolder.tvTime.setText(notesList.get(position).getTime());
//        String title= cursor.getString(cursor.getColumnIndex("title"));
//        String content = cursor.getString(cursor.getColumnIndex("content"));
//        String time = cursor.getString(cursor.getColumnIndex("time"));
        viewHolder.tvTitle.setText(notes.get(position).getTitle());
        viewHolder.tvContent.setText(notes.get(position).getContent());
        viewHolder.tvTime.setText(notes.get(position).getTime());
        return convertView;
    }

        public static class ViewHolder{
            public TextView tvTitle;
            public TextView tvContent;
            public TextView tvTime;
        }

    }