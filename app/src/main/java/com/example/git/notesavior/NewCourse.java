package com.example.git.notesavior;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcourse);
        final EditText CourseName = (EditText) findViewById(R.id.editText3);
        final EditText CourseHour = (EditText) findViewById(R.id.editText8);
        final EditText startTime = (EditText) findViewById(R.id.editText5);
        final EditText endTime = (EditText) findViewById(R.id.editText6);
        final Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //fill database with CourseName,CourseHour, starTime and endTime
            }
        });

    }
}