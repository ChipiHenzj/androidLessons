package com.example.tetianapriadko.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Lesson15 extends AppCompatActivity {

    final int MENU_SIZE_22 = 1;
    final int MENU_SIZE_26 = 2;
    final int MENU_SIZE_30 = 3;

    final int MENU_COLOR_RED = 4;
    final int MENU_COLOR_BLUE = 5;
    final int MENU_COLOR_GREEN = 6;

    TextView tvColor;
    TextView tvSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson15);

        tvColor = (TextView)findViewById(R.id.tvColor);
        tvSize = (TextView)findViewById(R.id.tvSize);

        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);
    }


    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        switch(v.getId()){
             case R.id.tvColor:
                 menu.add(0, MENU_COLOR_RED, 0, "Red");
                 menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
                 menu.add(0, MENU_COLOR_GREEN, 0, "Green");
                 break;
            case R.id.tvSize:
                menu.add(0, MENU_SIZE_22, 0, "22");
                menu.add(0, MENU_SIZE_26, 0, "24");
                menu.add(0, MENU_SIZE_30, 0, "30");
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case MENU_COLOR_RED:
                tvColor.setTextColor(Color.RED);
                tvColor.setText("Text color = red ");
                break;
            case MENU_COLOR_BLUE:
                tvColor.setTextColor(Color.BLUE);
                tvColor.setText("Text color = blue");
                break;
            case MENU_COLOR_GREEN:
                tvColor.setTextColor(Color.GREEN);
                tvColor.setText("Text color = green");
                break;
            case MENU_SIZE_22:
                tvSize.setTextSize(22);
                tvSize.setText("Text size = 22");
                break;
            case MENU_SIZE_26:
                tvSize.setTextSize(26);
                tvSize.setText("Text size = 26");
                break;
            case MENU_SIZE_30:
                tvSize.setTextSize(30);
                tvSize.setText("Text size = 30");
                break;
        }
        return super.onContextItemSelected(item);
    }





}
