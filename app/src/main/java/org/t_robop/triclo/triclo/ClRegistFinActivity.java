package org.t_robop.triclo.triclo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClRegistFinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cl_regist_fin);

        Button button1 = (Button) findViewById(R.id.buttonNextRegist);
        Button button2 = (Button) findViewById(R.id.buttonClose);

    }

    public void close(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void nextRegist(View v){
        Intent intent = new Intent(this,CameraActivity.class);
        startActivity(intent);
    }

    public void returnToDebug(View v){
        Intent intent = new Intent(this,DebugActivity.class);
        startActivity(intent);
    }
}
