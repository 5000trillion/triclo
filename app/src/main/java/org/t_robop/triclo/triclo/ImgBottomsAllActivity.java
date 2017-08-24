package org.t_robop.triclo.triclo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ImgBottomsAllActivity extends AppCompatActivity {
    Realm realm;
    byte[] bytes;
    long ClId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_bottoms_all);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        RealmQuery<ClothesDb> find = realm.where(ClothesDb.class);
        final RealmResults<ClothesDb> results = find.equalTo("genre", "ボトムス").findAll();
        ArrayList<Bitmap> list = new ArrayList<Bitmap>();
        for (int i = 0; i < results.size(); i++) {
            bytes = results.get(i).getImage();
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            list.add(i, bmp);
        }

        BitmapAdapter adapter = new BitmapAdapter(
                getApplicationContext(), R.layout.list_item,
                list);

        GridView gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idi = (int) id;
                ClId = results.get(idi).getId();
                finish();
            }
        });

        if (list.isEmpty()) {
            String message = "コーデが登録されていません";
            Toast.makeText(ImgBottomsAllActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }
}
