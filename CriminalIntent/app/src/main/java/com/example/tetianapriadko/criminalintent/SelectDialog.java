package com.example.tetianapriadko.criminalintent;


import android.app.Dialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;

import java.util.Date;

public class SelectDialog extends DialogFragment {

    private Date mDate;
    private RadioButton date;
    private RadioButton time;

    @Override
    public Dialog onCreateDialog(Bundle SaveInstanceState) {

        mDate = ((Date) getArguments().getSerializable(CrimeFragment.EXTRA_DATE));

        View rootView = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_choose, null);
        date = ((RadioButton) rootView.findViewById(R.id.change_date));
        time = ((RadioButton) rootView.findViewById(R.id.change_time));

        return new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setTitle(R.string.choose_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Bundle bundle = new Bundle();
                                if (date.isChecked()){
                                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                                    bundle.putSerializable(CrimeFragment.EXTRA_DATE, mDate);
                                    datePickerFragment.setArguments(bundle);
                                    datePickerFragment.setTargetFragment(getTargetFragment(), getTargetRequestCode());
                                    datePickerFragment.show(getFragmentManager(), "");
                                } else if (time.isChecked()){
                                    TimePickerFragment timePickerFragment = new TimePickerFragment();
                                    bundle.putSerializable(CrimeFragment.EXTRA_DATE, mDate);
                                    timePickerFragment.setArguments(bundle);
                                    timePickerFragment.setTargetFragment(getTargetFragment(), getTargetRequestCode());
                                    timePickerFragment.show(getFragmentManager(), "");
                                }
                            }
                        })
                .setNeutralButton(
                        android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            dismiss();
                            }
                        }
                )
                .create();
    }

}
