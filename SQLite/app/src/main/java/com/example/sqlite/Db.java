package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {
    public Db(Context context)
    {
        super(context,"db",null,1);
    }
    public void onCreate(SQLiteDatabase db)
    {
        //创建数据库内部结构
        db.execSQL("CREATE TABLE user("
                +"name TEXT DEFAULT \"\", "
                +"sex TEXT DEFAULT \"\" )"
        );
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {

    }
}
