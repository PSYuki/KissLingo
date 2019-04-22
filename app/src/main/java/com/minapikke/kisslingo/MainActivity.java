package com.minapikke.kisslingo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import java.io.File;
import java.lang.String;
import android.util.Log;

//▼MainActivity class開始▼
public class MainActivity extends AppCompatActivity {

    private final static String DB_NAME="example_sentences.db";
    private final static String DB_TABLE="ExampleSentences";
    private final static int DB_VERSION=1;

    private SQLiteDatabase DatabaseObject;
    //private SQLiteDatabase preDatabaseObject;

    private String subjectStr;
    private String tenseStr;


    @Override
    // ▼onCreate method設定▼
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ▼database設定▼
        Database();


        // ▼spinner　onItemSelected設定▼
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tenseList));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        // spinner未選択時
        tenseStr = getResources().getStringArray(R.array.tenseList)[0];
        // default表示切替
        spinner.setSelection(0, false);

        // リスナーを登録
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {

                Spinner spinner = (Spinner)parent;
                tenseStr = (String)spinner.getSelectedItem();
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });
        // ▼spinner　onItemSelected終了▼



        // ▼button onClick method設定▼
        findViewById(R.id.button)
                .setOnClickListener(
                        new View.OnClickListener() {
                            // ListViewに表示するためのArrayAdapter
                            ArrayAdapter<String> ad;

                            @Override
                            public void onClick(View v) {
                                try {
                                    String selectsql ="SELECT id,word,subject,tense,eng_example,jap_example FROM ExampleSentences WHERE tense ='" + tenseStr + "'";

                                    Cursor cursor = DatabaseObject.rawQuery(selectsql,null);

                                    ad = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

                                    if(cursor.moveToFirst()){
                                        do{
                                            int id = cursor.getInt(cursor.getColumnIndex("id"));
                                            String word = cursor.getString(cursor.getColumnIndex("word"));
                                            String subject = cursor.getString(cursor.getColumnIndex("subject"));
                                            String tense = cursor.getString(cursor.getColumnIndex("tense"));
                                            String eng_example = cursor.getString(cursor.getColumnIndex("eng_example"));
                                            String jap_example = cursor.getString(cursor.getColumnIndex("jap_example"));

                                            String row = id + ":" + word + ":" + subject + ":" + tense + ":" + eng_example + ":" + jap_example;
                                            ad.add(row);
                                        }while(cursor.moveToNext());
                                    }
                                } catch(Exception e){
                                    // データベースオブジェクトをクローズ
                                    DatabaseObject.close();
                                    }
                                ((ListView) findViewById(R.id.ListView)).setAdapter(ad);
                            }
                        });
    }
                            //▼button onClick method終了▼

                            //　▼onCreate method終了▼




        // ▼database method設定▼
    private void Database(){

            // database
            DatabaseHelper DbHelperObject = new DatabaseHelper(MainActivity.this);
            DatabaseObject =
                    DbHelperObject.getWritableDatabase();


            String dropTable = "DROP TABLE IF EXISTS " + DB_TABLE;

            String createTable = "CREATE TABLE " + DB_TABLE +
                    "(id integer primary key autoincrement, word text, subject text, tense text, eng_example text, jap_example text)";

            String[] insertData = {
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '現在・肯定', 'I study English every day.', '私は毎日英語を勉強する')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '現在・肯定', 'Do I study English every day?', '私は毎日英語を勉強する？')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '現在・肯定', 'What do I study every day?', '私は毎日何を勉強する？')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '現在・否定', 'I don’t study it at school.', '私はそれを学校で勉強しない')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '現在・否定', 'Don’t I study it at school?', '私はそれを学校で勉強しない？')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '現在・否定', 'Why don’t I study it at school?', '私はなぜそれを学校で勉強しない？')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '過去・肯定', 'I studied math last night.', '私は昨夜数学を勉強した')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '過去・肯定', 'Did I study math last night?', '私は昨夜数学を勉強した？')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '過去・肯定', 'What did I study last night?', '私は昨夜何を勉強した？')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '過去・否定', 'I didn’t study it this morning.', '私は今朝それを勉強しなかった')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '過去・否定', 'Didn’t I study it this morning?', '私は今朝それを勉強しなかった？')",
                    "INSERT INTO " + DB_TABLE + "(word, subject, tense, eng_example, jap_example) VALUES ('study', 'I', '過去・否定', 'Why didn’t I study it this morning?', '私はなぜ今朝それを勉強しなかった？')"
            };

            // 古いテーブルを破棄
            DatabaseObject.execSQL(dropTable);
            // テーブルを作成
            DatabaseObject.execSQL(createTable);
            // データ登録
            for(int i = 0; i < insertData.length; i++){
                DatabaseObject.execSQL(insertData[i]);
            }

            // データベースオブジェクトをクローズ
//        DatabaseObject.close();
    }

    //▼database method終了▼





    //▼DatabaseHelper class設定▼
    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {

           super(
                context,DB_NAME,null,DB_VERSION
           );
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " +
                        DB_TABLE +
                        "(id integer primary key autoincrement, word text, subject text, tense text, eng_example text, jap_example text)"
        );
        Log.d("Database","Create Table");
        }
        @Override

        public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
        ) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
        }
    }
    //▼DatabaseHelper class終了▼

}
// ▼MainActivity class終了▼
