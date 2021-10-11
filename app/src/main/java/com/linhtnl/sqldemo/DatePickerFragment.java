package com.linhtnl.sqldemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    String className;
    DatePickerFragment(String className){
        this.className = className;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) - 18;
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if(className.equals("MainActivity")){
            return new DatePickerDialog(getActivity(), (MainActivity)getActivity(), year,month,day);
        }else{
            return new DatePickerDialog(getActivity(), (FindNEditActivity)getActivity(), year,month,day);
        }

    }
}