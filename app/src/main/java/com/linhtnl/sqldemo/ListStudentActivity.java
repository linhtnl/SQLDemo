package com.linhtnl.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListStudentActivity extends AppCompatActivity {
    private List<Integer> listChoose;
    private ListView studentList;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(ListStudentActivity.this);
    List<StudentModel> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student);


        studentList = findViewById(R.id.listStudent);
        studentList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //fill data to list view
        loadData();
        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                listChoose = new ArrayList<>();
                for (int i = 0; i < adapterView.getCount(); i++) {
                    boolean check = studentList.getCheckedItemPositions().get(i);
                    if (check) {
                        listChoose.add(i);
                    }
                }
            }
        });
    }

    private void loadData() {
        //Get all Students from db
        students = dataBaseHelper.getAllStudents();
        ArrayAdapter adapter = new ArrayAdapter<StudentModel>(this, android.R.layout.simple_list_item_multiple_choice, students);
        studentList.setAdapter(adapter);
    }

    public void deleteStudent(View view) {
        int count = 0;
        for (int position : listChoose) {
            boolean success = dataBaseHelper.DeleteAStudent(students.get(position).getStudentCode());
            if (success) {
                count++;
            }
        }
        if (count == listChoose.size()) {
            Toast.makeText(ListStudentActivity.this, "Deleted " + count + " student", Toast.LENGTH_SHORT).show();
            loadData();
        } else {
            Toast.makeText(ListStudentActivity.this, "Deleted failed", Toast.LENGTH_SHORT).show();
        }
    }


}