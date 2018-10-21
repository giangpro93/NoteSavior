package com.example.git.notesavior;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class tempClass extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    private FireBaseHandler firebaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stulogin);
        Bundle bundle = getIntent().getExtras();
        String code = bundle.getString("key");
        }

    }