package com.example.tetianapriadko.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//Adapter
public class Lesson54_3 extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Lesson54_2> objects;//Products

    public Lesson54_3(Context context, ArrayList<Lesson54_2> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.activity_lesson54_2, parent, false);
        }

        Lesson54_2 p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) convertView.findViewById(R.id.tvDescr)).setText(p.name);
        ((TextView) convertView.findViewById(R.id.tvPrice)).setText(p.price + "");
        ((ImageView) convertView.findViewById(R.id.ivImage)).setImageResource(p.image);

        CheckBox cbBuy = (CheckBox) convertView.findViewById(R.id.cbBox);
        cbBuy.setOnCheckedChangeListener(null);
        cbBuy.setChecked(p.box);

        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                objects.get(position).box = isChecked;
            }
        });
        return convertView;
    }

    // товар по позиции
    public Lesson54_2 getProduct(int position) {
        return ((Lesson54_2) getItem(position));
    }

    // содержимое корзины
    public ArrayList<Lesson54_2> getBox() {
        ArrayList<Lesson54_2> box = new ArrayList<Lesson54_2>();
        for (Lesson54_2 p : objects) {
            // если в корзине
            if (p.box)
                box.add(p);
        }
        return box;
    }
}
