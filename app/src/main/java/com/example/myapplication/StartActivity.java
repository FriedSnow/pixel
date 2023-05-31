package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Sampler;
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
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {
    MainActivity ma = new MainActivity();
    public int side = ma.getSize();
    //public int side = 32;                       //сторона "холста" в пикселях
    public int pxn = (int)Math.pow(side,2);     //количество пикселей = сторона * сторона
    private GridView gridView;
    SeekBar sA, sR, sG, sB;
    TextView tA, tR, tG, tB, clr, ers, pnt, deb1, deb2, thr;
    int cA = 255, cR = 0, cG = 255, cB = 0;     //дефолтные значения слайдеров и настроек цвета
    public boolean isPaint = true, isErase = false, isThree = false;

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

        clr = findViewById(R.id.curClr);

        ers = findViewById(R.id.erase);
        pnt = findViewById(R.id.paint);
        thr = findViewById(R.id.three);
        deb1 = findViewById(R.id.debug1);
        deb2 = findViewById(R.id.debug2);

        sA.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                cA = sA.getProgress();
                tA.setText(String.valueOf(i));
                clr.setBackgroundColor(Color.argb(cA, cR, cG, cB));
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
                clr.setBackgroundColor(Color.argb(cA, cR, cG, cB));
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
                clr.setBackgroundColor(Color.argb(cA, cR, cG, cB));
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
                clr.setBackgroundColor(Color.argb(cA, cR, cG, cB));
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
            if (isPaint) {
                view.setBackgroundColor(Color.argb(cA, cR, cG, cB));
                deb1.setText(String.valueOf(position % side+1));
                deb2.setText(String.valueOf(position / side+1));
            }
            if (isErase) {
                view.setBackgroundColor(Color.argb(0, 0, 0, 0));
            }
            if (isThree){
                view.setBackgroundColor(Color.argb(cA, cR, cG, cB));
                deb1.setText(String.valueOf(position % side+1));
                deb2.setText(String.valueOf(position / side+1));

                /*
                xp - корда x+ (справа)
                xm - корда x- (слева)
                yp - корда y+ (сверху)
                ym - корда y- (снизу)

                ypxm - корда y+ x- (сверху слева)
                ypxp - корда y+ x+ (сверху справа)
                ymxp - корда y- x+ (снизу справа)
                ymxm - корда y- x- (снизу слева)
                оторвите мне руки за это пожалуйста
                 */

                int xp = position + 1;
                int xm = position - 1;
                int yp = position - side;
                int ym = position + side;

                int ypxm = position - side - 1;
                int ypxp = position - side + 1;
                int ymxp = position + side + 1;
                int ymxm = position + side - 1;

                //TODO п̶р̶и̶д̶е̶л̶а̶т̶ь̶ ̶с̶ю̶д̶а̶ ̶и̶ф̶э̶л̶с̶ ̶к̶о̶т̶о̶р̶ы̶й̶ ̶б̶у̶д̶е̶т̶ ̶н̶о̶р̶м̶а̶л̶ь̶н̶о̶ ̶р̶и̶с̶о̶в̶а̶т̶ь̶ ̶н̶а̶ ̶у̶г̶л̶а̶х̶/̶г̶р̶а̶н̶я̶х̶
                int divSide = position % side;
                if (divSide != 0 && //сторона слева
                        divSide != side - 1 && //сторона справа
                        position != 0 && // первый пиксель (сверху-слева)
                        position != pxn && // последний пиксель (снизу-справа)
                        position > side && // верхний ряд
                        position < (pxn - side)){ // нижний ряд
                    
                    View vxp = gridView.getChildAt(xp);
                    vxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X+
                    View vxm = gridView.getChildAt(xm);
                    vxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X-
                    View vyx = gridView.getChildAt(yp);
                    vyx.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+
                    View vym = gridView.getChildAt(ym);
                    vym.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y-

                    View vypxm = gridView.getChildAt(ypxm);
                    vypxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+ X-
                    View vypxp = gridView.getChildAt(ypxp);
                    vypxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+ X+
                    View vymxp = gridView.getChildAt(ymxp);
                    vymxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y- X+
                    View vymxm = gridView.getChildAt(ymxm);
                    vymxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y- X-
                }
                if (position == 0) {                                                // сверху слева
                    View vxp = gridView.getChildAt(xp);
                    vxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X+
                    View vym = gridView.getChildAt(ym);
                    vym.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y-
                    View vymxp = gridView.getChildAt(ymxp);
                    vymxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y- X+
                }
                if (position == pxn - 1){                                           // снизу справа
                    View vxm = gridView.getChildAt(xm);
                    vxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X-
                    View vyx = gridView.getChildAt(yp);
                    vyx.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+
                    View vypxm = gridView.getChildAt(ypxm);
                    vypxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+ X-
                }
                if (divSide == 0 && position != 0 && position < (pxn - side) && position >= side){             // слева
                    View vxp = gridView.getChildAt(xp);
                    vxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X+
                    View vyx = gridView.getChildAt(yp);
                    vyx.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+
                    View vym = gridView.getChildAt(ym);
                    vym.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y-
                    View vypxp = gridView.getChildAt(ypxp);
                    vypxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+ X+
                    View vymxp = gridView.getChildAt(ymxp);
                    vymxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y- X+
                }
                if (divSide == 0 && position == (pxn - side)){                       // слева снизу
                    View vyx = gridView.getChildAt(yp);
                    vyx.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+
                    View vxp = gridView.getChildAt(xp);
                    vxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X+
                    View vypxp = gridView.getChildAt(ypxp);
                    vypxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+ X+
                }
                if (divSide != 0 && divSide < side -1 && position > (pxn - side)){         // снизу
                    //Toast.makeText(this, "bebr", Toast.LENGTH_SHORT).show();
                    View vyx = gridView.getChildAt(yp);
                    vyx.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+
                    View vxp = gridView.getChildAt(xp);
                    vxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X+
                    View vxm = gridView.getChildAt(xm);
                    vxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X-
                    View vypxp = gridView.getChildAt(ypxp);
                    vypxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+ X+
                    View vypxm = gridView.getChildAt(ypxm);
                    vypxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+ X-
                }
                if (position <= side - 1 && position != 0 && position != side - 1){           // сверху
                    View vym = gridView.getChildAt(ym);
                    vym.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y-
                    View vxp = gridView.getChildAt(xp);
                    vxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X+
                    View vxm = gridView.getChildAt(xm);
                    vxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X-
                    View vymxp = gridView.getChildAt(ymxp);
                    vymxp.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y- X+
                    View vymxm = gridView.getChildAt(ymxm);
                    vymxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y- X-
                }
                if (position == side - 1){                                         // сверху справа
                    View vxm = gridView.getChildAt(xm);
                    vxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X-
                    View vym = gridView.getChildAt(ym);
                    vym.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y-
                    View vymxm = gridView.getChildAt(ymxm);
                    vymxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y- X-
                }
                if (divSide == side - 1 && !(position == pxn - 1) && !(position <= side-1)){ //справа
                    View vxm = gridView.getChildAt(xm);
                    vxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //X-
                    View vyx = gridView.getChildAt(yp);
                    vyx.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+
                    View vym = gridView.getChildAt(ym);
                    vym.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y-
                    View vymxm = gridView.getChildAt(ymxm);
                    vymxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y- X-
                    View vypxm = gridView.getChildAt(ypxm);
                    vypxm.setBackgroundColor(Color.argb(cA, cR, cG, cB)); //Y+ X-
                }
            }
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
            gridView.getChildAt(i).setBackgroundColor(Color.argb(0,0,0,0));
        }
    }
    public void toRed(MenuItem item) {
        for(int i = 0; i < pxn; i++){
            gridView.getChildAt(i).setBackgroundColor(Color.argb(255,255,0,0));
        }
    }
    public void toGreen(MenuItem item) {
        for(int i = 0; i < pxn; i++){
            gridView.getChildAt(i).setBackgroundColor(Color.argb(255,0,255,0));
        }
    }
    public void toBlue(MenuItem item) {
        for(int i = 0; i < pxn; i++){
            gridView.getChildAt(i).setBackgroundColor(Color.argb(255,0,0,255));
        }
    }
    public void toKek(MenuItem item) {
        for(int i = 0; i < pxn; i++){
            gridView.getChildAt(i).setBackgroundColor(Color.argb(cA,cR,cG,cB));
        }
    }

    public void toErase(View view){
        isErase = true;
        isPaint = false;
        isThree = false;
        ers.setBackgroundResource(R.drawable.is_enable);
        pnt.setBackgroundResource(R.drawable.is_disable);
        thr.setBackgroundResource(R.drawable.is_disable);
        Toast.makeText(this,  getString(R.string.Eraser),Toast.LENGTH_SHORT).show();
    }
    public void toPaint(View view){
        isPaint = true;
        isErase = false;
        isThree = false;
        pnt.setBackgroundResource(R.drawable.is_enable);
        ers.setBackgroundResource(R.drawable.is_disable);
        thr.setBackgroundResource(R.drawable.is_disable);
        Toast.makeText(this,  getString(R.string.Paint),Toast.LENGTH_SHORT).show();
    }
    public void toThree(View view){
        if (side >= 3) {
            isThree = true;
            isPaint = false;
            isErase = false;
            thr.setBackgroundResource(R.drawable.is_enable);
            pnt.setBackgroundResource(R.drawable.is_disable);
            ers.setBackgroundResource(R.drawable.is_disable);
            Toast.makeText(this, getString(R.string.Three), Toast.LENGTH_SHORT).show();
        }
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
            double wid = gridView.getWidth()/(side+(side/2.5));            //получение ширины и +(side/2) к стороне чтобы не плющило (?)
            textView.setTextSize(TypedValue.DENSITY_DEFAULT, (int)wid);    //установка высоты строки через размер текста

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