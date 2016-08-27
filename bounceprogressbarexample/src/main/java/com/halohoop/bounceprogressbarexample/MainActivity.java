package com.halohoop.bounceprogressbarexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halohoop.bounceprogressbar.views.BounceProgressBar;

public class MainActivity extends AppCompatActivity {
    Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BounceProgressBar bpb = (BounceProgressBar) findViewById(R.id.bpb);
        bpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpb.startTotalAnimation();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                        startActivity(intent);
                    }
                }, 5000);
            }
        });
    }

}
