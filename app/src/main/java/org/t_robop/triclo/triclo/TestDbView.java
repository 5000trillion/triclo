package org.t_robop.triclo.triclo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class TestDbView extends AppCompatActivity {

    Realm realm;
    EditText editText;
    TextView textViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db_view);

        editText = (EditText) findViewById(R.id.editText);
        textViewLog = (TextView) findViewById(R.id.textViewLog);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
    }

    //addボタンを押す
    public void add(View v) {
        //realmTransactionの開始
        realm.beginTransaction();
        //Instanceの作成
        DbAssist model = realm.createObject(DbAssist.class);

        //Instanceにデータを突っ込む
        model.setId(1);
        model.setName(editText.getText().toString());

        //transactionを閉じる＆データを書き込み
        realm.commitTransaction();
    }

    //Logボタンを押す
    public void showLog(View v) {
        //検索用クエリ
        RealmQuery<DbAssist> query = realm.where(DbAssist.class);

        //Instanceを作成
        RealmResults<DbAssist> results = query.findAll();

        //Logに突っ込む
        for (DbAssist test : results) {
            //System.out.println(test.getName());
            textViewLog.setText(test.getName().toString() + "\n");
        }
    }
}