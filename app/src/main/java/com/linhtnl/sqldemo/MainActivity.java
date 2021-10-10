package com.linhtnl.sqldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView edtBirthday;
    private TextView edtCode;
    private TextView edtFullname;
    //Call DB
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtBirthday = findViewById(R.id.edtBirthday);
        edtCode = findViewById(R.id.edtCode);
        edtFullname = findViewById(R.id.edtFullname);
    }

    public void clickToViewAll(View view) {
        Intent intent = new Intent(this, ListStudentActivity.class);
        startActivity(intent);
    }

    public void clickToCreateNew(View view) {
        StudentModel student = new StudentModel(edtCode.getText().toString(), edtFullname.getText().toString(), edtBirthday.getText().toString());

        boolean success = dataBaseHelper.createNewStudent(student);
        if (success) {
            Toast.makeText(MainActivity.this, "Create Success", Toast.LENGTH_SHORT).show();
            edtCode.setText("");
            edtFullname.setText("");
        } else {
            Toast.makeText(MainActivity.this, "Create Fail!", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickToChangeDate(View view) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        edtBirthday.setText(date);
    }

    public void clickToFindAndEdit(View view) {
        Intent intent = new Intent(this, FindNEditActivity.class);
        startActivity(intent);
    }
}