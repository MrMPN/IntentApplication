package com.example.formacio.intentapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        textView = findViewById(R.id.textView);
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            textView.setText(extras.getString("text"));
        }
    }
}
