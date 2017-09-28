package org.t_robop.triclo.triclo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ClDetailActivity extends AppCompatActivity {
    Realm realm;
    long ClId;
    ImageView clImg;
    byte[] bytes;
    Button button;
    TextView nameText;
    TextView genreText;
    TextView seasonText;
    TextView colorText;
    TextView dayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cl_detail);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = (Button) findViewById(R.id.button);

        nameText = (TextView) findViewById(R.id.nameText);
        genreText = (TextView) findViewById(R.id.genreText);
        seasonText = (TextView) findViewById(R.id.seasonText);
        colorText = (TextView) findViewById(R.id.colorText);
        dayText = (TextView) findViewById(R.id.dayText);
        clImg = (ImageView) findViewById(R.id.imageView);

        //IDを受け取る
        Intent intent = getIntent();
        ClId = intent.getLongExtra("ClId", ClId);

        //IDから服を検索して各データをセット
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        RealmQuery<ClothesDb> find = realm.where(ClothesDb.class);
        RealmResults<ClothesDb> results = find.equalTo("id", ClId).findAll();
        for (ClothesDb img : results) {
            bytes = img.getImage();
            nameText.setText(img.getName());
            genreText.setText(img.getGenre());
            seasonText.setText(img.getSeason());
            colorText.setText(img.getColor());
            String month = String.valueOf(img.getMonth() + 1);
            dayText.setText(img.getYear() + "/" + month + "/" + img.getDay());
        }
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        clImg.setImageBitmap(bmp);

    }

    //ツールバーの戻るボタン
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void showDialog(View v) {
        new AlertDialog.Builder(this)
                .setTitle("ちょっと待って！")
                .setMessage("この服を削除しますか？")
                .setNegativeButton("いいえ", null)
                .setPositiveButton("はい",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                realm.beginTransaction();
                                RealmQuery<ClothesDb> find = realm.where(ClothesDb.class);
                                RealmResults<ClothesDb> results = find.equalTo("id", ClId).findAll();
                                results.deleteFromRealm(0);
                                realm.commitTransaction();
                                finish();
                            }
                        }

                )
                .show();
    }
}
