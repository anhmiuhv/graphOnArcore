package com.example.linh.graphonarcore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private Button nextButton;
    private EditText XTitle;
    private EditText XUnit;
    private EditText YTitle;
    private EditText YUnit;
    private EditText ZTitle;
    private EditText ZUnit;

    private void init() {

    nextButton = findViewById(R.id.nextButton);
    XTitle = findViewById(R.id.XTitle);
    XUnit = findViewById(R.id.XUnit);
    YTitle = findViewById(R.id.YTitle);
    YUnit = findViewById(R.id.YUnit);
    ZTitle = findViewById(R.id.ZTitle);
    ZUnit = findViewById(R.id.ZUnit);

    nextButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, DataPoints.class);
            Bundle extras = new Bundle();

            extras.putString("X_TITLE", XTitle.getText().toString());
            extras.putString("X_UNIT", XUnit.getText().toString());
            extras.putString("Y_TITLE", YTitle.getText().toString());
            extras.putString("Y_UNIT", YUnit.getText().toString());
            extras.putString("Z_TITLE", ZTitle.getText().toString());
            extras.putString("Z_UNIT", ZUnit.getText().toString());

            intent.putExtras(extras);

            startActivity(intent);

        }
    });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }
}
