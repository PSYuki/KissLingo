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
import android.content.res.AssetManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import java.io.File;
import java.lang.String;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.InputStream;

//▼MainActivity class開始▼
public class MainActivity extends AppCompatActivity {

    private final static String DB_NAME="example_sentences.db";
    private final static String DB_TABLE="ExampleSentences";
    private final static int DB_VERSION=1;

    private SQLiteDatabase DatabaseObject;
    //private SQLiteDatabase preDatabaseObject;

    private String wclassStr;
    private String wordStr;
    private String subjectStr;
    private String tenseStr;


    @Override
    // ▼onCreate method設定▼
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ▼pre-made database設定▼
        //Database();

        //▼csvファイルからDatabaseへの落とし込み
        DatabaseHelper DbHelperObject = new DatabaseHelper(MainActivity.this);
        DatabaseObject =
                DbHelperObject.getWritableDatabase();

        String dropTable = "DROP TABLE IF EXISTS " + DB_TABLE;

        String createTable = "CREATE TABLE " + DB_TABLE +
                "(id integer primary key autoincrement, ylang text, tlang text, level text, wclass text, word text, subject text, tense text, type text, ylang_ex text, tlang_ex text, tlang_exf, furigana text, chikugoyaku text)";

        // 古いテーブルを破棄
        DatabaseObject.execSQL(dropTable);
        // テーブルを作成
        DatabaseObject.execSQL(createTable);

       /* try {
            //for(int i=1; i<4; i++) {

                AssetManager assetManager = getApplicationContext().getAssets();
                //InputStream inputStream = assetManager.open("jap_verb" + i + ".csv");
                InputStream inputStream = assetManager.open("jap_verb.csv");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferReader.readLine()) != null) {
                    String[] RowData = line.split(",");
                    String InsertRowData =
                            "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, ylang_ex, tlang_ex, tlang_exf, furigana, chikugoyaku) VALUES ('" + RowData[1] + "','" + RowData[2] + "','" + RowData[3] + "','" + RowData[4] + "','" + RowData[5] + "','" + RowData[6] + "','" + RowData[7] + "','" + RowData[8] + "','" + RowData[9] + "','" + RowData[10] + "','" + RowData[11] + "','" + RowData[12] + "','" + RowData[13] + "')";

                    DatabaseObject.execSQL(InsertRowData);
                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        } */

        try {
            writetoDatabase(String jap_verb.csv,String[] arrrjap_verb);
            writetoDatabase(String jap_adjective.csv,String[] arrrjap_verb);
            writetoDatabase(String eng_verb.csv,String[] arrreng_verb);
            }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }

        private void writetoDatabase(String filename,String[] arrr){
            AssetManager assetManager = getApplicationContext().getAssets();
            //InputStream inputStream = assetManager.open("jap_verb" + i + ".csv");
            InputStream inputStream = assetManager.open("filename.csv");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                String[] arrr = line.split(",");
                String Insertarrr =
                        "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, ylang_ex, tlang_ex, tlang_exf, furigana, chikugoyaku) VALUES ('" + arrr[1] + "','" + arrr[2] + "','" + arrr[3] + "','" + arrr[4] + "','" + arrr[5] + "','" + arrr[6] + "','" + arrr[7] + "','" + arrr[8] + "','" + arrr[9] + "','" + arrr[10] + "','" + arrr[11] + "','" + arrr[12] + "','" + arrr[13] + "')";

                DatabaseObject.execSQL(Insertarrr);

        }


        // ▼spinner　onItemSelected設定▼
        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        final Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        final Spinner spinner6 = (Spinner) findViewById(R.id.spinner6);
        final Spinner spinner7 = (Spinner) findViewById(R.id.spinner7);

        ArrayAdapter<String> adapter4
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.CLJ));
        ArrayAdapter<String> adapter5
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.WVLE));
        ArrayAdapter<String> adapter6
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.SLE));
        ArrayAdapter<String> adapter7
                = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TVLE));

        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner4.setAdapter(adapter4);
        spinner5.setAdapter(adapter5);
        spinner6.setAdapter(adapter6);
        spinner7.setAdapter(adapter7);

        // spinner未選択時
        wclassStr = getResources().getStringArray(R.array.CLJ)[0];
        wordStr = getResources().getStringArray(R.array.WVLE)[0];
        subjectStr = getResources().getStringArray(R.array.SLE)[0];
        tenseStr = getResources().getStringArray(R.array.TVLE)[0];

        // default表示切替
        spinner4.setSelection(0, false);
        spinner5.setSelection(0, false);
        spinner6.setSelection(0, false);
        spinner7.setSelection(0, false);

        // spinner4へリスナーを登録
        spinner4.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {

                Spinner spinner4 = (Spinner)parent;
                wclassStr = (String)spinner4.getSelectedItem();

                //spinner5 and spinner7のclassStrによる分岐定義
                if(wclassStr.equals("動詞")){
                    setSpinner(spinner5, getResources().getStringArray(R.array.WVLE));
                    setSpinner(spinner7, getResources().getStringArray(R.array.TVLE));
                }else if( wclassStr.equals("形容詞")) {
                    setSpinner(spinner5, getResources().getStringArray(R.array.WALE));
                    setSpinner(spinner7, getResources().getStringArray(R.array.TALJ));
                }else{
                    //ここで、spinner5を画面から消去し、spinner6をsubjectNounList（this/this noun / I / mine etc）に変更したい
                    setSpinner(spinner5, getResources().getStringArray(R.array.WNLE));
                    setSpinner(spinner7, getResources().getStringArray(R.array.TNLJ));
                }

            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }

            private void setSpinner(Spinner spinner,String[] arr){
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, arr);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setSelection(0, false);
            }
        });

        // spinner6へリスナーを登録
        spinner6.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {

                Spinner spinner6 = (Spinner)parent;
                subjectStr = (String)spinner6.getSelectedItem();
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });


        // リスナーを登録 spinner5
        spinner5.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {

                Spinner spinner5 = (Spinner)parent;
                wordStr = (String)spinner5.getSelectedItem();
            }

            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        // リスナーを登録 spinner7
        spinner7.setOnItemSelectedListener(new OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {

                Spinner spinner7 = (Spinner)parent;
                tenseStr = (String)spinner7.getSelectedItem();
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
                                    String selectsql ="SELECT id,ylang,tlang,level,wclass,word,subject,tense,type,ylang_ex,tlang_ex,tlang_exf,furigana,chikugoyaku FROM ExampleSentences";
                                    //String selectsql ="SELECT id,ylang,tlang,level,wclass,word,subject,tense,type,ylang_ex,tlang_ex,tlang_exf,furigana,chikugoyaku FROM ExampleSentences WHERE class ='" + wclassStr + "' and word ='" + wordStr + "' and subject ='" + subjectStr + "' and tense ='" + tenseStr + "'";

                                    Cursor cursor = DatabaseObject.rawQuery(selectsql,null);

                                    ad = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1);

                                    if(cursor.moveToFirst()){
                                        do{
                                            int id = cursor.getInt(cursor.getColumnIndex("id"));
                                            String wclass = cursor.getString(cursor.getColumnIndex("wclass"));
                                            String word = cursor.getString(cursor.getColumnIndex("word"));
                                            String subject = cursor.getString(cursor.getColumnIndex("subject"));
                                            String tense = cursor.getString(cursor.getColumnIndex("tense"));
                                            String ylang_ex = cursor.getString(cursor.getColumnIndex("ylang_ex"));
                                            String tlang_ex = cursor.getString(cursor.getColumnIndex("tlang_ex"));

                                            String row = id + ":" + word + ":" + subject + ":" + tense + ":" + ylang_ex + ":" + tlang_ex;
                                            //String row = tense + ":" ;
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
                    "(id integer primary key autoincrement, ylang text, tlang text, level text, wclass text, word text, subject text, tense text, type text, ylang_ex text, tlang_ex text, tlang_exf, furigana text, chikugoyaku text)";

            String[] insertData = {
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '現在・肯定', 'I study English every day.', '私は毎日英語を勉強する')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '現在・肯定', 'Do I study English every day?', '私は毎日英語を勉強する？')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '現在・肯定', 'What do I study every day?', '私は毎日何を勉強する？')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '現在・否定', 'I don’t study it at school.', '私はそれを学校で勉強しない')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '現在・否定', 'Don’t I study it at school?', '私はそれを学校で勉強しない？')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '現在・否定', 'Why don’t I study it at school?', '私はなぜそれを学校で勉強しない？')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '過去・肯定', 'I studied math last night.', '私は昨夜数学を勉強した')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '過去・肯定', 'Did I study math last night?', '私は昨夜数学を勉強した？')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '過去・肯定', 'What did I study last night?', '私は昨夜何を勉強した？')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '過去・否定', 'I didn’t study it this morning.', '私は今朝それを勉強しなかった')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '過去・否定', 'Didn’t I study it this morning?', '私は今朝それを勉強しなかった？')",
                    "INSERT INTO " + DB_TABLE + "(ylang, tlang, level, wclass, word, subject, tense, type, eng_example, jap_example) VALUES ('日本語','英語', '初級','動詞','study', 'I', '過去・否定', 'Why didn’t I study it this morning?', '私はなぜ今朝それを勉強しなかった？')"
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
                        "(id integer primary key autoincrement, ylang text, tlang text, level text, wclass text, word text, subject text, tense text, type text, ylang_ex text, tlang_ex text, tlang_exf, furigana text, chikugoyaku text)"
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
