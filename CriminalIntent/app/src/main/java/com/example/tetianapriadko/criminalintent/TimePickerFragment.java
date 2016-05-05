package com.example.tetianapriadko.criminalintent;


import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TimePicker;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {


    private Date mDate;

    @Override
    public Dialog onCreateDialog(Bundle SaveInstanceState) {

        mDate = (Date) getArguments().getSerializable(CrimeFragment.EXTRA_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        View rootView = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker) rootView.findViewById(R.id.dialog_time_timePicker);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mDate.setHours(hourOfDay);
                mDate.setMinutes(minute);
            }
        });

        return new android.app.AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            sendResult();
                            }
                        })
                .create();
    }

    private void sendResult() {
        if (getTargetFragment() == null){
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra(CrimeFragment.EXTRA_DATE, mDate);
            getTargetFragment()
                    .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        }
    }
}
