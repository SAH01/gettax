package com.example.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        SQLiteStudioService.instance().stop();
        super.onDestroy();
    }

    private EditText etName,etSex;
    private Button btnAdd;
    private Db db;
    String name;
    String sex;
    private SQLiteDatabase dbRead,dbWrite;
    private View.OnClickListener btnAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues cv =new ContentValues();
            cv.put("name",etName.getText().toString());
            cv.put("sex",etSex.getText().toString());
            Log.i("name",etName.getText().toString());
            Log.i("sex",etSex.getText().toString());
            Log.i("!!!数据库!!!",db.toString());
            dbWrite.insert("user",null,cv);
            if (db == null) {
                Log.i("!!!数据库!!!","数据库是空的");
            }
            else{
                Log.i("!!!数据库!!!","数据库非空");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SQLiteStudioService.instance().start(this);

        etName = findViewById(R.id.etName);
        etSex = findViewById(R.id.etSex);

        db = new Db(this);
        dbRead = db.getReadableDatabase();
        dbWrite = db.getWritableDatabase();

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(btnAddListener);
        //Cursor对象是查询数据库的结果对象
        Cursor cursor = dbRead.rawQuery("select * from user;",null);
        while (cursor.moveToNext())
        {
            name=cursor.getString(cursor.getColumnIndex("name"));
            sex=cursor.getString(cursor.getColumnIndex("sex"));
        }

        ListView lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }
            @Override
            public Object getItem(int position) {
                return null;
            }
            @Override
            public long getItemId(int position) {
                return 0;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if(convertView==null){
                    view=View.inflate(getBaseContext(),R.layout.user_list_cell,null);
                }
                else{
                    view=convertView;
                }
                TextView etName=view.findViewById(R.id.etName);
                TextView etSex =view.findViewById(R.id.etSex);
                etName.setText(name);
                etSex.setText(sex);
                return view;
            }
        });
    }
}
