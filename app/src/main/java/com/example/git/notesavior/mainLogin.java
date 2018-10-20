package com.example.git.notesavior;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class mainLogin extends AppCompatActivity {

//    private Login Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainlogin);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View teacherLogin) {
                Intent intent = new Intent(mainLogin.this, Login.class);
                startActivity(intent);
            }
        });

        final Button button2 = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View studentLogin) {
                Intent intent = new Intent(mainLogin.this, Login.class);
                startActivity(intent);
            }
        });

//        public void teacherLogin (View view)
//            {
//            Intent intent= new Intent(this,Login.class);
//            login.startActivity(intent);
//        }

//        public void studentLogin (View view)
//        {
//            Intent intent= new Intent(this,Login.class);
//            login.startActivity(intent);
//        }
    }
}

