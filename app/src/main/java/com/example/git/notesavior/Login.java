package com.example.git.notesavior;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    private FireBaseHandler firebaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseHandler = FireBaseHandler.getInstance();
        firebaseHandler.getTeachers();

        final EditText username = (EditText) findViewById(R.id.editText2);
        final EditText password = (EditText) findViewById(R.id.editText1);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (firebaseHandler.checkTeacherLogin(username.getText().toString(),password.getText().toString())) {
                    Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, ClassHub.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((username.getText().toString().length()>0) && (password.getText().toString().length()>0)) {
                    //put this stuff in database
                    if (firebaseHandler.addTeacher(username.getText().toString(),password.getText().toString())) {
                        Toast.makeText(getApplicationContext(),"Create new account successfully",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Username is already exist",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Example of a call to a native method

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    //public native String stringFromJNI();
}
