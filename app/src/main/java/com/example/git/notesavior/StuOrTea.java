package com.example.git.notesavior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StuOrTea extends AppCompatActivity {


        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.intro);

        final Button sBtn = findViewById(R.id.student);
        sBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(StuOrTea.this, StuLogin.class));
            }
        });




        final Button tBtn = findViewById(R.id.teacher);
        tBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(StuOrTea.this, Login.class));
            }
        });
    }

}
