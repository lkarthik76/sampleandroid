package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private TextView textview;
    private Button btnStart;
    private Button btnStop;
    private double previous = 0;
    private Integer stepCount =1;
    private Integer lapcount = 1;
    List list = new ArrayList<>();
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview =findViewById(R.id.textview);
        btnStart = findViewById(R.id.btnstart);
        btnStop = findViewById(R.id.btnStop);


        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepCount = 0;
                sensorManager.registerListener(MainActivity.this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.unregisterListener(MainActivity.this);
                updatelaplist();
            }
        });
        if(list.size()<=0){
            ListData data = new ListData(lapcount,stepCount);
            list.add(list.size(),data);
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        adapter = new ListAdapter(this,list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void updatelaplist() {
        if(list.size()>0){
            lapcount++;
            ListData data = new ListData(lapcount,stepCount);
            list.add(list.size(),data);
        }
        adapter.notifyDataSetChanged();
    }

    protected void onPause(){
        super.onPause();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepcount",stepCount);
        editor.apply();
    }
    protected void onStop(){
        super.onStop();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("stepcount",stepCount);
        editor.apply();
    }
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepcount",0);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event!=null){
            float x_acceleration = event.values[0];
            float y_acceleration = event.values[1];
            float z_acceleration = event.values[2];

            double accelScalar = Math.sqrt(x_acceleration*x_acceleration + y_acceleration*y_acceleration
                    + z_acceleration*z_acceleration);

            double accelScalarNoGravity = accelScalar - 9.8;
            if(accelScalarNoGravity > 0){
                double delta = accelScalar - previous;
                previous = accelScalar;
                if(delta >= 1)
                    stepCount++;
                System.out.println(" $$$$$ delta : " +delta);
                System.out.println(" $$$$$ stepCount : " +stepCount);
            }
            textview.setText(stepCount.toString());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}