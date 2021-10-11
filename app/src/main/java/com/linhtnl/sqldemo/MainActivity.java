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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView txtBirthday;
    private EditText edtCode;
    private EditText edtFullname;
    private TextView txtErrorCode;
    private TextView txtErrorName;
    //Call DB
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBirthday = findViewById(R.id.txtBirthday);
        edtCode = findViewById(R.id.edtCode);
        edtFullname = findViewById(R.id.edtFullname);
        txtErrorCode = findViewById(R.id.txtErrorCode);
        txtErrorName = findViewById(R.id.txtErrorName);
    }

    public void clickToViewAll(View view) {
        Intent intent = new Intent(this, ListStudentActivity.class);
        startActivity(intent);
    }

    public void clickToCreateNew(View view) {
        boolean check = true;
        if (edtCode.getText().toString().isEmpty()) {
            txtErrorCode.setText("Code is required!!!");
            check = false;
        } else {
            txtErrorCode.setText("");
            boolean isStudentCodeExist = dataBaseHelper.checkExistStudentCode(edtCode.getText().toString());
            if (isStudentCodeExist) {
                txtErrorCode.setText("Code is exist!!!");
                check = false;
            }
        }
        if (edtFullname.getText().toString().isEmpty()) {
            txtErrorName.setText("Name is required!!!");
            check = false;
        } else {
            txtErrorName.setText("");
        }
        if (check) {
            StudentModel student = new StudentModel(edtCode.getText().toString(), edtFullname.getText().toString(), txtBirthday.getText().toString());
            boolean success = dataBaseHelper.createNewStudent(student);
            if (success) {
                Toast.makeText(MainActivity.this, "Create Success", Toast.LENGTH_SHORT).show();
                edtCode.setText("");
                edtFullname.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Create Fail!", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void clickToChangeDate(View view) {
        DialogFragment dateFragment = new DatePickerFragment("MainAcivity");
        dateFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        txtBirthday.setText(date);
    }

    public void clickToFindAndEdit(View view) {
        Intent intent = new Intent(this, FindNEditActivity.class);
        startActivity(intent);
    }
}