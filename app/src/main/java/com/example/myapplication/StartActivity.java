package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    MainActivity ma = new MainActivity();
    public int side = ma.getSize();
    //public int side = 32;                       //сторона "холста" в пикселях
    public int pxn = (int)Math.pow(side,2);     //количество пикселей = сторона * сторона
    private GridView gridView;
    SeekBar sA, sR, sG, sB;
    TextView tA, tR, tG, tB;
    int cA = 255, cR = 0, cG = 255, cB = 0;     //дефолтные значения слайдеров и настроек цвета

    //TODO стартовая активити, с выбором размера холста
    //TODO выпадающий список с кнопкой о̶ч̶и̶с̶т̶к̶и̶ ̶и̶ сейва
    //                                           -> сейвить скрином?
    //TODO zoom layout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid_view);

        sA = findViewById(R.id.seekA);
        sR = findViewById(R.id.seekR);
        sG = findViewById(R.id.seekG);
        sB = findViewById(R.id.seekB);

        tA = findViewById(R.id.textA1);
        tR = findViewById(R.id.textR1);
        tG = findViewById(R.id.textG1);
        tB = findViewById(R.id.textB1);

        sA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cA = sA.getProgress();
                tA.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cR = sR.getProgress();
                tR.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cG = sG.getProgress();
                tG.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cB = sB.getProgress();
                tB.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        List<String> items = new ArrayList<>();
        for (int i = 0; i < pxn; i++){
            items.add("");              //FIXME з̶а̶п̶о̶л̶н̶и̶т̶ь̶ ̶в̶с̶е̶ ̶к̶л̶е̶т̶к̶и̶ ̶п̶у̶с̶т̶ы̶м̶и̶ ̶с̶и̶м̶в̶о̶л̶а̶м̶и̶
        }

        MyAdapter adapter = new MyAdapter(items);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            view.setBackgroundColor(Color.argb(cA,cR,cG,cB));
        });


        adjustGridView();               //применить настройки грида
    }



    private void adjustGridView() {                 //настройки грида
        //TODO а̶д̶а̶п̶т̶и̶в̶н̶а̶я̶ ̶в̶ы̶с̶о̶т̶a̶ ̶к̶л̶е̶т̶к̶и̶ ̶(̶=̶ш̶и̶р̶и̶н̶а̶)̶
        gridView.setNumColumns(side);               //установка количества столбцов, количество рядов подтянется само,
        // тк квадратный корень из числа стороны во второй степени
        gridView.setVerticalSpacing(1);             //сетка, можно и без неё
        gridView.setHorizontalSpacing(1);
    }

    public void toClear(MenuItem item) {
        for(int i = 0; i < pxn; i++){
            gridView.getChildAt(i).setBackgroundColor(Color.argb(255,255,255,255));
        }
    }

    public void toSave(MenuItem item) {

    }


    private class MyAdapter extends BaseAdapter {   //А? даптер
        private List<String> items;
        public MyAdapter(List<String> items) {
            this.items = items;
        }
        @Override
        public int getCount() {
            return items.size();
        }
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.list_item, null);
            }

            TextView textView = view.findViewById(R.id.text_view);
            //textView.setText("");                                   //заполнение массива пустыми символами чтобы работало
            int wid = gridView.getWidth()/(side+10);                //получение ширины и +10 к стороне чтобы не плющило (?)
            textView.setTextSize(TypedValue.DENSITY_DEFAULT, wid);  //установка высоты строки через размер текста

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }




}