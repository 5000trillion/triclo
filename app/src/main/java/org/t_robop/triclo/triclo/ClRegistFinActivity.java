package org.t_robop.triclo.triclo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ClRegistFinActivity extends AppCompatActivity {
    Realm realm;
    long clId;
    ImageView clImg;
    byte[] bytes;
    TextView nameText;
    TextView genreText;
    TextView seasonText;
    TextView colorText;
    TextView dayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clregist_fin);

        nameText = (TextView) findViewById(R.id.nameText);
        genreText = (TextView) findViewById(R.id.genreText);
        seasonText = (TextView) findViewById(R.id.seasonText);
        colorText = (TextView) findViewById(R.id.colorText);
        dayText = (TextView) findViewById(R.id.dayText);
        Button button1 = (Button) findViewById(R.id.buttonNextRegist);
        Button button2 = (Button) findViewById(R.id.buttonClose);
        clImg = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        clId = intent.getLongExtra("clId", clId);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        RealmQuery<ClothesDb> find = realm.where(ClothesDb.class);
        RealmResults<ClothesDb> results = find.equalTo("id", clId).findAll();
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

    public void close(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void nextRegist(View v) {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    public void returnToDebug(View v) {
        Intent intent = new Intent(this, DebugActivity.class);
        startActivity(intent);
    }
}
