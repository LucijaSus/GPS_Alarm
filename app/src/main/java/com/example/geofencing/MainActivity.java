package com.example.geofencing;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String naslov;
    String radij;
    int s;
    TextView alarm;
    TextView m;
    Switch stikalo;
    ConstraintLayout nov_alarm;
    SharedPreferences sp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getApplicationContext().getSharedPreferences("Alarm", Context.MODE_PRIVATE);
        naslov = sp.getString("naslov", "");
        radij = sp.getString("radij", "");
        s = sp.getInt("true", 0);

        alarm = findViewById(R.id.alarm);
        m = findViewById(R.id.m);
        stikalo = findViewById(R.id.stikalo);
        nov_alarm = findViewById(R.id.nov_alarm);

        nov_alarm.setVisibility(View.GONE);
        alarm.setVisibility(View.GONE);
        m.setVisibility(View.GONE);
        stikalo.setVisibility(View.GONE);

        SharedPreferences.Editor editor = sp.edit();
        stikalo.setChecked(sp.getBoolean("isChecked", true));
        stikalo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();
                if(isChecked) {
                    editor.putBoolean("isChecked", true);
                }
                else {
                    editor.putBoolean("isChecked", false);
                }
                editor.commit();
            }
        });

        if(s != 0) {
            nov_alarm.setVisibility(View.VISIBLE);
            alarm.setVisibility(View.VISIBLE);
            alarm.setText(naslov);

            m.setVisibility(View.VISIBLE);
            m.setText(radij + "m");

            stikalo.setVisibility(View.VISIBLE);
        }

        nov_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                SharedPreferences.Editor editor = sp.edit();
                s=1;
                editor.putInt("true", s);
                editor.commit();
                startActivity(intent);
            }
        });
    }

    public void dodaj(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
        s=0;
        editor.putInt("true", s);
        editor.commit();
        startActivity(intent);
    }

    public void izbrisi(View view) {
        nov_alarm.setVisibility(View.GONE);
        alarm.setVisibility(View.GONE);
        Toast.makeText(MainActivity.this, "Alarm izbrisan", Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = sp.edit();

        editor.clear();
        editor.commit();
        s=0;
        editor.putInt("true", s);
        editor.commit();
    }
    @Override
    public void onBackPressed() {
        // make sure you have this outcommented
        // super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}