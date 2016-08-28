package com.halohoop.bounceprogressbarexample2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.halohoop.bounceprogressbar.views.BounceProgressBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BounceProgressBar bpb = (BounceProgressBar) findViewById(R.id.bpb);
        bpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bpb.startTotalAnimation();
            }
        });
    }
}
