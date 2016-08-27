package com.halohoop.bounceprogressbarexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.halohoop.bounceprogressbar.views.BounceProgressBar;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {

    private GridView mGv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mGv = (GridView) findViewById(R.id.gv);
        mGv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View inflate = View.inflate(Main2Activity.this, R.layout.grid_item, null);
                BounceProgressBar bpb = (BounceProgressBar) inflate.findViewById(R.id.bpb);
                if (i % 2 == 0) {
                    bpb.setBouncingBallColor(android.graphics.Color.parseColor("#FF4081"));
                    bpb.setLineColor(android.graphics.Color.parseColor("#FFFFEA00"));
                    bpb.setFixBallColor(android.graphics.Color.parseColor("#B6E810A4"));
                }else{
                    bpb.setBouncingBallColor(android.graphics.Color.parseColor("#303F9F"));
                    bpb.setLineColor(android.graphics.Color.parseColor("#FF00FF11"));
                    bpb.setFixBallColor(android.graphics.Color.parseColor("#FFFF9500"));
                }
                Random ran = new Random();
                bpb.setBouncingBallRadius(ran.nextInt(30)+5);
                bpb.setFixBallRadius(ran.nextInt(30)+5);
                bpb.startTotalAnimation();
                return inflate;
            }
        });
    }
}
