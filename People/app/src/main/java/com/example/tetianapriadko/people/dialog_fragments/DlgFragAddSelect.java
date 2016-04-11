package com.example.tetianapriadko.people.dialog_fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.example.tetianapriadko.people.R;

public class DlgFragAddSelect extends DlgFragAbstractOkCancel {

    private RadioGroup radioGroup;

    @Override
    protected String getOKText() {
        return "Add";
    }

    @Override
    protected String getCancelText() {
        return "Cancel";
    }

    @Override
    protected void performOK(DialogInterface dialog, int id) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.radioBtn_student:
                    intent.putExtra("Human", "Student");
                    break;
                case R.id.radioBtn_teacher:
                    intent.putExtra("Human", "Teacher");
                    break;
            }
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    }

    @Override
    protected void performCancel(DialogInterface dialog, int id) {

    }

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected String getTitle() {
        return "Add:";
    }

    @Override
    protected View getRootView() {
        LayoutInflater inflater
                = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View rootView = inflater.inflate(R.layout.frag_dlg_add_selection, null, false);
        radioGroup = ((RadioGroup) rootView.findViewById(R.id.radioGroup_select_all));
        radioGroup.check(R.id.radioBtn_teacher);

        return rootView;
    }
}
