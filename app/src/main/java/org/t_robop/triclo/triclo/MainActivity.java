package org.t_robop.triclo.triclo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Realm realm;

    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private TextView textView4, textView5;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private byte[] bytes;
    private long ClId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        permissionAcquisition();
        permissionAcquisition1();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView Listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item1:
                        Log.d("TAG", "Item 1");

                        Intent intent = new Intent(MainActivity.this, Tab1_Activity.class);


                        startActivity(intent);

                    case R.id.menu_item2:
                        Log.d("TAG", "Item 2");

                        Intent intent1 = new Intent(MainActivity.this, Tab1_Activity.class);


                        startActivity(intent1);
                }


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }


        });
        

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        //最初にテキストが見えるのを防止する
        textView4.startAnimation(fab_close);
        textView5.startAnimation(fab_close);

    }


    @Override
    protected void onResume(){
        super.onResume();

        ArrayList<Bitmap> list = new ArrayList<Bitmap>();
        RealmQuery<ClothesDb> find = realm.where(ClothesDb.class);
        final RealmResults<ClothesDb> results = find.findAll();
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
                int id2 = (int) id;
                ClId = results.get(id2).getId();
                Intent intent = new Intent(MainActivity.this, ClDetailActivity.class);
                intent.putExtra("ClId", ClId);
                startActivity(intent);
            }
        });

        if (list.isEmpty()) {
            String message = "コーデが登録されていません";
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }


    private void permissionAcquisition() {

        // Android6.0以降でのPermissionの確認
        //拒否されている時の処理
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // 許可されている時の処理
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //拒否された時 Permissionが必要な理由を表示して再度許可を求めたり、機能を無効にしたりします。
            //AlertDialog
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("ストレージへのアクセスが必要です。" + "\n" + "次の画面で許可を押してください。");      //内容
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            });
            alertDialog.create();
            alertDialog.show();

        } else {
            //まだ許可を求める前の時、許可を求めるダイアログを表示します。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

    private void permissionAcquisition1() {

        // Android6.0以降でのPermissionの確認
        //拒否されている時の処理
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 許可されている時の処理
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            //拒否された時 Permissionが必要な理由を表示して再度許可を求めたり、機能を無効にしたりします。
            //AlertDialog
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("ストレージへのアクセスが必要です。" + "\n" + "次の画面で許可を押してください。");      //内容
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                }
            });
            alertDialog.create();
            alertDialog.show();

        } else {
            //まだ許可を求める前の時、許可を求めるダイアログを表示します。
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);

        }
    }


    //fab
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                Log.d("Raj", "Fab 1");
                Intent intent1 = new Intent(MainActivity.this, CodePickActivity.class);
                startActivity(intent1);
                break;
            case R.id.fab2:
                Log.d("Raj", "Fab 2");
                Intent intent2 = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent2);
                break;
        }
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            textView4.startAnimation(fab_close);
            textView5.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            textView4.setClickable(false);
            textView5.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            textView4.startAnimation(fab_open);
            textView5.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            textView4.setClickable(true);
            textView5.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }


}







