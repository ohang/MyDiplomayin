package com.example.goro.mydiplomayin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Goro on 05.03.2018.
 */

public class Main extends Activity {
Button loadtest;
Button linkcheck;
EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        loadtest = (Button) findViewById(R.id.loadtest);
        linkcheck = (Button) findViewById(R.id.linkcheck);
        editText=(EditText)findViewById(R.id.editTextmain);


        loadtest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Main.this, MainActivity.class);
               myIntent.putExtra("key", editText.getText().toString()); //Optional parameters
                Main.this.startActivity(myIntent);

            }
        });

        linkcheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Main.this, Async.class);
                //   myIntent.putExtra("key", value); //Optional parameters
                Main.this.startActivity(myIntent);

            }
        });


    }}