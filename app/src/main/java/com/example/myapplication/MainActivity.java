package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    public int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

//        EditText et = (EditText) findViewById(R.id.editSize);
//        String text = et.getText().toString();
//        size = Character.getNumericValue(text.length());

        EditText editText = findViewById(R.id.editSize);
        SeekBar seekBar = findViewById(R.id.seekSize);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                size = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        String strValue = editText.getText().toString();
//            if (!strValue.isEmpty()) {
//        size = Integer.parseInt(strValue);
//        }
//            else {
//        System.out.println("ЪЕЬ");
//        }

    }
    public int getSize(){
        return size;
    }
    public void toStart(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
