package com.example.git.notesavior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;

public class Login extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username = (EditText) findViewById(R.id.editText2);
        final EditText password = (EditText) findViewById(R.id.editText1);
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (username.getText().toString().equals("databasestuff") && password.getText().toString().equals("databasestuff")) {
                    // if time within some class range go to activity_classhub for that class
                    // else go to create new class
                    //Intent myIntent = new Intent(Login.this, ClassHub.class);
                    //myIntent.putExtra("key", value); //Optional parameters
                    //CurrentActivity.this.startActivity(myIntent);
                }
                else {
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
