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

public class StuLogin extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    private FireBaseHandler firebaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stulogin);

        firebaseHandler = FireBaseHandler.getInstance();
        firebaseHandler.getTeachers();

        final EditText username = (EditText) findViewById(R.id.editText);
        final EditText code = (EditText) findViewById(R.id.editText4);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (FireBaseHandler.getInstance().addStudent(username.getText().toString(),code.getText().toString())) {
                    Intent myIntent = new Intent(StuLogin.this, tempClass.class);
                    myIntent.putExtra("key", code.getText().toString()); //Optional parameter
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
}
