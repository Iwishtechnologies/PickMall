package tech.iwish.pickmall.sqlconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyhelperSql extends SQLiteOpenHelper {

    public static final String DB_NAME = "product_card";
    public static final int version = 1;

    public MyhelperSql(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE PRODUCT_CARD(_id INTEGER PRIMARY KEY AUTOINCREMENT , PRODUCT_ID  ,PRODUCT_NAME , PRODUCT_QTY ,PRODUCT_COLOR  , PRODUCT_SIZE ,   PRODUCT_IMAGE , PRODUCT_AMOUNT , PRODUCT_AMOUNT_DICOUNT , PRODUCT_TYPE)";
        sqLiteDatabase.execSQL(sql);
    }

    public void dataAddCard(String product_id ,String product_name ,String product_qty ,String  product_color,String product_size ,String product_image ,String product_amount ,String product_amount_dicount  , String product_type, SQLiteDatabase database ){
        ContentValues values = new ContentValues();
        values.put("PRODUCT_ID" , product_id);
        values.put("PRODUCT_NAME" , product_name);
        values.put("PRODUCT_QTY" , product_qty);
        values.put("PRODUCT_COLOR" , product_color);
        values.put("PRODUCT_SIZE" , product_size);
        values.put("PRODUCT_IMAGE" , product_image);
        values.put("PRODUCT_AMOUNT" , product_amount);
        values.put("PRODUCT_AMOUNT_DICOUNT" , product_amount_dicount);
        values.put("PRODUCT_TYPE" , product_type);
        Integer no ;
        no = Integer.parseInt(String.valueOf(database.insert("PRODUCT_CARD",null,values)));
        Log.d("key",String.valueOf(no));
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
