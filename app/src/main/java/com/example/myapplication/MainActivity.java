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


public class MainActivity extends AppCompatActivity {
    public int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    public int getSize(){
        return size;
    }
    public void toStart(View view) {
        InfoM data = getInfo();
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("size", data.mSize);
        startActivity(intent);
    }

    public void to16(View view) {
        size=16;
    }
    public void to32(View view) {
        size=32;
    }
    private InfoM getInfo(){
        int mSize = getSharedPreferences(getString(R.string.size_key), MODE_PRIVATE).getInt("size", 0);

        InfoM data = new InfoM();
        if (mSize != 0){
            data.mSize = mSize;
        }
        else {
            data.mSize = 0;
        }
        return data;
    }
    class InfoM{
        int mSize;
    }
}
