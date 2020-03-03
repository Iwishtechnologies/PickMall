package tech.iwish.pickmall.sqlconnection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyhelperSql extends SQLiteOpenHelper {

    public static final String DB_NAME = "";
    public static final int version = 1;

    public MyhelperSql(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE RESULT(_id INTEGER PRIMARY KEY AUTOINCREMENT , QUESTION_SNO  , USER_SELECT_OPTION , USER_SNO , CORRECT_ANSWER)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
