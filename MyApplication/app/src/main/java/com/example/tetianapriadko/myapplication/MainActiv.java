package com.example.tetianapriadko.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActiv extends AppCompatActivity {

    String[] names = {"Lesson 9", "Lesson 10", "Lesson 11", "Lesson 12", "Lesson 13",
                      "Lesson 14", "Lesson 15", "Lesson 16", "Lesson 17", "Lesson 18",
                      "Lesson 19", "Lesson 20", "Lesson 21", "Lesson 23", "Lesson 24",
                      "Lesson 26", "Lesson 27", "Lesson 28", "Lesson 29", "Lesson 30",
                      "Lesson 31", "Lesson 32", "Lesson 33", "Lesson 34-35", "Lesson 36",
                      "Lesson 37", "Lesson 40", "Lesson 41", "Lesson 42", "Lesson 43",
                      "Lesson 44", "Lesson 45", "Lesson 46", "Lesson 48", "Lesson 49",
                      "Lesson 50", "Lesson 51", "Lesson 54", "Lesson 55", "Lesson 56",
                      "Lesson 57", "Lesson 58", "Lesson 59", "Lesson 60", "Lesson 61",
                      "Lesson 62", "Lesson 66", "Lesson 67", "Lesson 68", "Lesson 69",
                      "Lesson 70", "Lesson 71", "Lesson 72", "Lesson 73", "Lesson 74",
                      "Lesson 75", "Lesson 76", "Lesson 77", "Lesson 78", "Lesson 80",
                      "Lesson 81", "Lesson 82", "Lesson 86", "Lesson 87", "Lesson 88",
                      "Lesson 89", "Lesson 90", "Lesson 91", "Lesson 92", "Lesson 93",
                      "Lesson 99", "Lesson 100", "Lesson 102", "Lesson 102_1", "Lesson 104",
                      "Lesson 105", "Lesson 106", "Lesson 110", "Lesson 115", "Lesson 125",
                      "Lesson 139", "Lesson 140"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvLessons = (ListView) findViewById(R.id.lvLessons);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        lvLessons.setAdapter(adapter);

        lvLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                switch (position) {
                    case 0:
                        Intent lesson9 = new Intent(MainActiv.this, Lesson9.class);
                        startActivity(lesson9);
                        break;
                    case 1:
                        Intent lesson10 = new Intent(MainActiv.this, Lesson10.class);
                        startActivity(lesson10);
                        break;
                    case 2:
                        Intent lesson11 = new Intent(MainActiv.this, Lesson11.class);
                        startActivity(lesson11);
                        break;
                    case 3:
                        Intent lesson12 = new Intent(MainActiv.this, Lesson12.class);
                        startActivity(lesson12);
                        break;
                    case 4:
                        Intent lesson13 = new Intent(MainActiv.this, Lesson13.class);
                        startActivity(lesson13);
                        break;
                    case 5:
                        Intent lesson14 = new Intent(MainActiv.this, Lesson14.class);
                        startActivity(lesson14);
                        break;
                    case 6:
                        Intent lesson15 = new Intent(MainActiv.this, Lesson15.class);
                        startActivity(lesson15);
                        break;
                    case 7:
                        Intent lesson16 = new Intent(MainActiv.this, Lesson16.class);
                        startActivity(lesson16);
                        break;
                    case 8:
                        Intent lesson17 = new Intent(MainActiv.this, Lesson17.class);
                        startActivity(lesson17);
                        break;
                    case 9:
                        Intent lesson18 = new Intent(MainActiv.this, Lesson18.class);
                        startActivity(lesson18);
                        break;
                    case 10:
                        Intent lesson19 = new Intent(MainActiv.this, Lesson19.class);
                        startActivity(lesson19);
                        break;
                    case 11:
                        Intent lesson20 = new Intent(MainActiv.this, Lesson20.class);
                        startActivity(lesson20);
                        break;
                    case 12:
                        Intent lesson21 = new Intent(MainActiv.this, Lesson21_1.class);
                        startActivity(lesson21);
                        break;
                    case 13:
                        Intent lesson23 = new Intent(MainActiv.this, Lesson23.class);
                        startActivity(lesson23);
                        break;
                    case 14:
                        Intent lesson24 = new Intent(MainActiv.this, Lesson24_1.class);
                        startActivity(lesson24);
                        break;
                    case 15:
                        Intent lesson26 = new Intent(MainActiv.this, Lesson26_1.class);
                        startActivity(lesson26);
                        break;
                    case 16:
                        Intent lesson27 = new Intent(MainActiv.this, Lesson27_1.class);
                        startActivity(lesson27);
                        break;
                    case 17:
                        Intent lesson28 = new Intent(MainActiv.this, Lesson28_1.class);
                        startActivity(lesson28);
                        break;
                    case 18:
                        Intent lesson29 = new Intent(MainActiv.this, Lesson29_1.class);
                        startActivity(lesson29);
                        break;
                    case 19:
                        Intent lesson30 = new Intent(MainActiv.this, Lesson30_1.class);
                        startActivity(lesson30);
                        break;
                    case 20:
                        Intent lesson31 = new Intent(MainActiv.this, Lesson31.class);
                        startActivity(lesson31);
                        break;
                    case 21:
                        Intent lesson32 = new Intent(MainActiv.this, Lesson32_1.class);
                        startActivity(lesson32);
                        break;
                    case 22:
                        Intent lesson33 = new Intent(MainActiv.this, Lesson33.class);
                        startActivity(lesson33);
                        break;
                    case 23:
                        Intent lesson34_35 = new Intent(MainActiv.this, Lesson34_35.class);
                        startActivity(lesson34_35);
                        break;
                    case 24:
                        Intent lesson36 = new Intent(MainActiv.this, Lesson36.class);
                        startActivity(lesson36);
                        break;
                    case 25:
                        Intent lesson37 = new Intent(MainActiv.this, Lesson37.class);
                        startActivity(lesson37);
                        break;
                    case 26:
                        Intent lesson40 = new Intent(MainActiv.this, Lesson40.class);
                        startActivity(lesson40);
                        break;
                    case 27:
                        Intent lesson41 = new Intent(MainActiv.this, Lesson41.class);
                        startActivity(lesson41);
                        break;
                    case 28:
                        Intent lesson42 = new Intent(MainActiv.this, Lesson42.class);
                        startActivity(lesson42);
                        break;
                    case 29:
                        Intent lesson43 = new Intent(MainActiv.this, Lesson43.class);
                        startActivity(lesson43);
                        break;
                    case 30:
                        Intent lesson44 = new Intent(MainActiv.this, Lesson44.class);
                        startActivity(lesson44);
                        break;
                    case 31:
                        Intent lesson45 = new Intent(MainActiv.this, Lesson45.class);
                        startActivity(lesson45);
                        break;
                    case 32:
                        Intent lesson46 = new Intent(MainActiv.this, Lesson46.class);
                        startActivity(lesson46);
                        break;
                    case 33:
                        Intent lesson48 = new Intent(MainActiv.this, Lesson48.class);
                        startActivity(lesson48);
                        break;
                    case 34:
                        Intent lesson49 = new Intent(MainActiv.this, Lesson49.class);
                        startActivity(lesson49);
                        break;
                    case 35:
                        Intent lesson50 = new Intent(MainActiv.this, Lesson50.class);
                        startActivity(lesson50);
                        break;
                    case 36:
                        Intent lesson51 = new Intent(MainActiv.this, Lesson51.class);
                        startActivity(lesson51);
                        break;
                    case 37:
                        Intent lesson54 = new Intent(MainActiv.this, Lesson54_1.class);
                        startActivity(lesson54);
                        break;
                    case 38:
                        Intent lesson55 = new Intent(MainActiv.this, Lesson55.class);
                        startActivity(lesson55);
                        break;
                    case 39:
                        Intent lesson56 = new Intent(MainActiv.this, Lesson56.class);
                        startActivity(lesson56);
                        break;
                    case 40:
                        Intent lesson57 = new Intent(MainActiv.this, Lesson57.class);
                        startActivity(lesson57);
                        break;
                    case 41:
                        Intent lesson58 = new Intent(MainActiv.this, Lesson58.class);
                        startActivity(lesson58);
                        break;
                    case 42:
                        Intent lesson59 = new Intent(MainActiv.this, Lesson59.class);
                        startActivity(lesson59);
                        break;
                    case 43:
                        Intent lesson60 = new Intent(MainActiv.this, Lesson60.class);
                        startActivity(lesson60);
                        break;
                    case 44:
                        Intent lesson61 = new Intent(MainActiv.this, Lesson61.class);
                        startActivity(lesson61);
                        break;
                    case 45:
                        Intent lesson62 = new Intent(MainActiv.this, Lesson62_1.class);
                        startActivity(lesson62);
                        break;
                    case 46:
                        Intent lesson66 = new Intent(MainActiv.this, Lesson66.class);
                        startActivity(lesson66);
                        break;
                    case 47:
                        Intent lesson67 = new Intent(MainActiv.this, Lesson67.class);
                        startActivity(lesson67);
                        break;
                    case 48:
                        Intent lesson68 = new Intent(MainActiv.this, Lesson68.class);
                        startActivity(lesson68);
                        break;
                    case 49:
                        Intent lesson69 = new Intent(MainActiv.this, Lesson69_1.class);
                        startActivity(lesson69);
                        break;
                    case 50:
                        Intent lesson70 = new Intent(MainActiv.this, Lesson70.class);
                        startActivity(lesson70);
                        break;
                    case 51:
                        Intent lesson71 = new Intent(MainActiv.this, Lesson71_1.class);
                        startActivity(lesson71);
                        break;
                    case 52:
                        Intent lesson72 = new Intent(MainActiv.this, Lesson72_1.class);
                        startActivity(lesson72);
                        break;
                    case 53:
                        Intent lesson73 = new Intent(MainActiv.this, Lesson73_1.class);
                        startActivity(lesson73);
                        break;
                    case 54:
                        Intent lesson74 = new Intent(MainActiv.this, Lesson74_1.class);
                        startActivity(lesson74);
                        break;
                    case 55:
                        Intent lesson75 = new Intent(MainActiv.this, Lesson75.class);
                        startActivity(lesson75);
                        break;
                    case 56:
                        Intent lesson76 = new Intent(MainActiv.this, Lesson76.class);
                        startActivity(lesson76);
                        break;
                    case 57:
                        Intent lesson77 = new Intent(MainActiv.this, Lesson77_1.class);
                        startActivity(lesson77);
                        break;
                    case 58:
                        Intent lesson78 = new Intent(MainActiv.this, Lesson78.class);
                        startActivity(lesson78);
                        break;
                    case 59:
                        Intent lesson80 = new Intent(MainActiv.this, Lesson80.class);
                        startActivity(lesson80);
                        break;
                    case 60:
                        Intent lesson81 = new Intent(MainActiv.this, Lesson81.class);
                        startActivity(lesson81);
                        break;
                    case 61:
                        Intent lesson82 = new Intent(MainActiv.this, Lesson82_test.class);
                        startActivity(lesson82);
                        break;
                    case 62:
                        Intent lesson86 = new Intent(MainActiv.this, Lesson86.class);
                        startActivity(lesson86);
                        break;
                    case 63:
                        Intent lesson87 = new Intent(MainActiv.this, Lesson87.class);
                        startActivity(lesson87);
                        break;
                    case 64:
                        Intent lesson88 = new Intent(MainActiv.this, Lesson88.class);
                        startActivity(lesson88);
                        break;
                    case 65:
                        Intent lesson89 = new Intent(MainActiv.this, Lesson89.class);
                        startActivity(lesson89);
                        break;
                    case 66:
                        Intent lesson90 = new Intent(MainActiv.this, Lesson90.class);
                        startActivity(lesson90);
                        break;
                    case 67:
                        Intent lesson91 = new Intent(MainActiv.this, Lesson91.class);
                        startActivity(lesson91);
                        break;
                    case 68:
                        Intent lesson92 = new Intent(MainActiv.this, Lesson92_1.class);
                        startActivity(lesson92);
                        break;
                    case 69:
                        Intent lesson93 = new Intent(MainActiv.this, Lesson93_1.class);
                        startActivity(lesson93);
                        break;
                    case 70:
                        Intent lesson99 = new Intent(MainActiv.this, Lesson99_1.class);
                        startActivity(lesson99);
                        break;
                    case 71:
                        Intent lesson100 = new Intent(MainActiv.this, Lesson100_1.class);
                        startActivity(lesson100);
                        break;
                    case 72:
                        Intent lesson102 = new Intent(MainActiv.this, Lesson102.class);
                        startActivity(lesson102);
                        break;
                    case 73:
                        Intent lesson102_1 = new Intent(MainActiv.this, Lesson102_1.class);
                        startActivity(lesson102_1);
                        break;
                    case 74:
                        Intent lesson104 = new Intent(MainActiv.this, Lesson104.class);
                        startActivity(lesson104);
                        break;
                    case 75:
                        Intent lesson105 = new Intent(MainActiv.this, Lesson105.class);
                        startActivity(lesson105);
                        break;
                    case 76:
                        Intent lesson106 = new Intent(MainActiv.this, Lesson106.class);
                        startActivity(lesson106);
                        break;
                    case 77:
                        Intent lesson110 = new Intent(MainActiv.this, Lesson110.class);
                        startActivity(lesson110);
                        break;
                    case 78:
                        Intent lesson125 = new Intent(MainActiv.this, Lesson125.class);
                        startActivity(lesson125);
                        break;
                    case 79:
                        Intent lesson139 = new Intent(MainActiv.this, Lesson139.class);
                        startActivity(lesson139);
                        break;
                    case 80:
                        Intent lesson140 = new Intent(MainActiv.this, Lesson140.class);
                        startActivity(lesson140);
                        break;
                }

            }
        });



    }
}

