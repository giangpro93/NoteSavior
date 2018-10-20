package com.example.git.notesavior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class ClassHub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classhub);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR);
        String currentHr = ""+currentHour;

        Class theclass;
        theclass = FireBaseHandler.getInstance().guessClass(Integer.parseInt(currentHr));

        TextView code = (TextView) findViewById(R.id.textView12);
        code.setText(theclass.code);
        TextView CN = (TextView) findViewById(R.id.textView11);
        CN.setText(theclass.courseName);
        final Button y_button = findViewById(R.id.button2);
        y_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        final Button n_button = findViewById(R.id.button3);
        n_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ClassHub.this, NewCourse.class);
                // myIntent.putExtra("key", value); //Optional parameters
                ClassHub.this.startActivity(myIntent);
            }
        });
    }
}