package com.example.git.notesavior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CourseGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursegallery);
        Bundle bundle = getIntent().getExtras();
        String CN = bundle.getString("key");
        TextView Course = (TextView) findViewById(R.id.textView15);
        Course.setText(CN);
        final Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(CourseGallery.this, CameraActivity.class);
                //myIntent.putExtra("key", value); //Optional parameters
                CourseGallery.this.startActivity(myIntent);
            }
        });

    }
}