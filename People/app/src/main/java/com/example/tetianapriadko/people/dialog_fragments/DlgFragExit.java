package com.example.tetianapriadko.people.dialog_fragments;


import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;

import com.example.tetianapriadko.people.MainActivity;

public class DlgFragExit extends DlgFragAbstractOkCancel {

    private MainActivity targetActivity;
    private int activityRequestCode;

    @Override
    protected String getOKText() {
        return "Yes, exit";
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
        } else if (targetActivity != null){
            targetActivity.customActivityResult(activityRequestCode, Activity.RESULT_OK);
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
        } else if (targetActivity != null){
            targetActivity.customActivityResult(activityRequestCode, Activity.RESULT_CANCELED);
        } else {
            dismiss();
        }
    }

    public void setTargetActivity(MainActivity targetActivity, int requestCode){
        this.targetActivity = targetActivity;
        activityRequestCode = requestCode;
    }

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    protected String getTitle() {
        return "Exit from app?";
    }

    @Override
    protected View getRootView() {
        return null;
    }
}
