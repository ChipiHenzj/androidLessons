package com.example.tetianapriadko.people.dialog_fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

public class DlgFragAddStudentDone extends DlgFragAbstractOkCancel {

    @Override
    protected String getOKText() {
        return "Yes, add it";
    }

    @Override
    protected String getCancelText() {
        return "Cancel";
    }

    @Override
    protected void performOK(DialogInterface dialog, int id) {
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(),
                    Activity.RESULT_OK,
                    null);
            dismiss();
        } else {
            dismiss();
        }
    }

    @Override
    protected void performCancel(DialogInterface dialog, int id) {
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(),
                    Activity.RESULT_CANCELED,
                    null);
            dismiss();
        } else {
            dismiss();
        }
    }

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected String getTitle() {
        return "Add Student?";
    }

    @Override
    protected View getRootView() {
        return null;
    }
}
