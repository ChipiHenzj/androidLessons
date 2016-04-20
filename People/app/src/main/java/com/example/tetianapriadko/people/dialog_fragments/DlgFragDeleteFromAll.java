package com.example.tetianapriadko.people.dialog_fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

public class DlgFragDeleteFromAll extends DlgFragAbstractOkCancel {
    @Override
    protected String getOKText() {
        return "Yes, delete it";
    }

    @Override
    protected String getCancelText() {
        return "Cancel";
    }

    @Override
    protected void performOK(DialogInterface dialog, int id) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra("position", getArguments().getInt("position"));
            getTargetFragment().onActivityResult(getTargetRequestCode(),
                    Activity.RESULT_OK,
                    intent);
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
        return "Delete?";
    }

    @Override
    protected View getRootView() {
        return null;
    }
}
