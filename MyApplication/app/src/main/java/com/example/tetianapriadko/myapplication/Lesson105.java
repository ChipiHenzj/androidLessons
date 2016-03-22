package com.example.tetianapriadko.myapplication;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class Lesson105 extends AppCompatActivity {

    Lesson105_1 frag1;
    Lesson105_2  frag2;
    FragmentTransaction fTrans;
    CheckBox chbStack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson105);

        frag1 = new Lesson105_1();
        frag2 = new Lesson105_2();

        chbStack = (CheckBox)findViewById(R.id.chbStack);
    }

    public void onClick(View v) {
        fTrans = getSupportFragmentManager().beginTransaction();
        
        switch (v.getId()) {

            case R.id.btnAdd:
                fTrans.add(R.id.frgmCont, frag1);
                break;

            case R.id.btnRemove:
                fTrans.remove(frag1);
                break;

            case R.id.btnReplace:
                fTrans.replace(R.id.frgmCont, frag2);
               break;

            default:
                break;
        }

        if (chbStack.isChecked()) fTrans.addToBackStack(null);
        fTrans.commit();

    }
}
