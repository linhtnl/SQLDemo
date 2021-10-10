package com.linhtnl.sqldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindNEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText edtCode;
    private EditText edtFullName;
    private TextView edtDOB;
    private StudentModel studentModel;
    private TextView txtErrorCode;
    private TextView txtErrorName;
    private TextView txtError;
    private Button btnUpdate;
    //Call DB
    private DataBaseHelper dataBaseHelper = new DataBaseHelper(FindNEditActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_nedit);
        edtCode = findViewById(R.id.edtCode2);
        edtDOB = findViewById(R.id.edtBirthday2);
        edtFullName = findViewById(R.id.edtFullname2);
        txtErrorCode = findViewById(R.id.txtErrorCode2);
        txtError = findViewById(R.id.txtError);
        btnUpdate=findViewById(R.id.btnUpdate);
        txtErrorName=findViewById(R.id.txtErrorName2);
    }

    public void clickToChangeDate(View view) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void clickToFindStudent(View view) {
        txtErrorName.setText("");
        if (edtCode.getText().toString().isEmpty()) {
            txtErrorCode.setText("Code is required!!!");
        } else {
            txtErrorCode.setText("");
            boolean isStudentCodeExist = dataBaseHelper.checkExistStudentCode(edtCode.getText().toString());
            if (isStudentCodeExist) {
                txtError.setText("");
                studentModel = dataBaseHelper.findStudentByCode(edtCode.getText().toString());
                btnUpdate.setEnabled(true);
                loadData(studentModel);
            } else {
                btnUpdate.setEnabled(false);
                txtError.setText("Student not found!");
            }
        }

    }

    public void clickToUpdate(View view) {
        StudentModel student = new StudentModel(edtCode.getText().toString(), edtFullName.getText().toString(), edtDOB.getText().toString());
        if (student.getStudentCode().equals(studentModel.getStudentCode())) {
            if (edtFullName.getText().toString().isEmpty()) {
                txtErrorName.setText("Name is required!!!");
            } else {
                txtErrorName.setText("");
                dataBaseHelper.updateStudent(student);
                Toast.makeText(FindNEditActivity.this, "Updated Success", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(FindNEditActivity.this, "Not allow to modify the code", Toast.LENGTH_SHORT).show();
            edtCode.setText(studentModel.getStudentCode());
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        edtDOB.setText(date);
    }

    private void loadData(StudentModel student) {
        edtCode.setText(student.getStudentCode());
        edtDOB.setText(student.getDateOfBirth());
        edtFullName.setText(student.getFullName());
    }
}