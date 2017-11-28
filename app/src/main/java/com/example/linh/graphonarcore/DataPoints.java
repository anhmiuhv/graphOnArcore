package com.example.linh.graphonarcore;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataPoints extends AppCompatActivity {

    private Button addButton;
    private Button graphButton;
    private TextView XTitle;
    private TextView YTitle;
    private TextView ZTitle;
    private TextInputLayout xLayout;
    private TextInputLayout yLayout;
    private TextInputLayout zLayout;
    private TextInputEditText xPoint;
    private TextInputEditText yPoint;
    private TextInputEditText zPoint;
    private TableLayout table;

    private ArrayList<Float> xArray, yArray, zArray;
    private String XAxisTitle, YAxisTitle, ZAxisTitle;
    private String XUnit, YUnit, ZUnit;
    JSONObject json;
    private int rowCounter;

    View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            table.removeView((TableRow)v.getParent());
            rowCounter--;
        }
    };

    private void init() {

    addButton = findViewById(R.id.addButton);
    graphButton = findViewById(R.id.graphButton);
    XTitle = findViewById(R.id.XAxisTitle);
    YTitle = findViewById(R.id.YAxisTitle);
    ZTitle = findViewById(R.id.ZAxisTitle);
    xLayout = findViewById(R.id.xPointLayout);
    yLayout = findViewById(R.id.yPointLayout);
    zLayout = findViewById(R.id.zPointLayout);
    xPoint = findViewById(R.id.xPoint);
    yPoint = findViewById(R.id.yPoint);
    zPoint = findViewById(R.id.zPoint);
    table = findViewById(R.id.table);
    xArray = new ArrayList<>();
    yArray = new ArrayList<>();
    zArray = new ArrayList<>();
    json = new JSONObject();
    rowCounter = 1;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_points);

        init();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String str1 = extras.getString("X_TITLE").equals("") ? "X Axis" : extras.getString("X_TITLE");
        String str2 = extras.getString("Y_TITLE").equals("") ? "Y Axis" : extras.getString("Y_TITLE");
        String str3 = extras.getString("Z_TITLE").equals("") ? "Z Axis" : extras.getString("Z_TITLE");

        XAxisTitle = str1;
        YAxisTitle = str2;
        ZAxisTitle = str3;

        XUnit = extras.getString("X_UNIT");
        YUnit = extras.getString("Y_UNIT");
        ZUnit = extras.getString("Z_UNIT");

        XTitle.setText(str1);
        YTitle.setText(str2);
        ZTitle.setText(str3);

        xPoint.requestFocus();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isNumericEditText(xLayout, "Enter a valid number")) {

                } else if (isNumericEditText(yLayout, "Enter a valid number")) {

                } else if (isNumericEditText(zLayout, "Enter a valid number")) {

                } else {

                    TableRow row = new TableRow(DataPoints.this);

                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(20, 5, 50, 5);

                    TextView x = new TextView(DataPoints.this);
                    x.setText(xPoint.getText().toString());
                    x.setTextColor(ContextCompat.getColor(DataPoints.this, R.color.colorWhite));
                    x.setTextSize(18);
                    x.setLayoutParams(layoutParams);
                    TextView y = new TextView(DataPoints.this);
                    y.setText(yPoint.getText().toString());
                    y.setTextColor(ContextCompat.getColor(DataPoints.this, R.color.colorWhite));
                    y.setTextSize(18);
                    y.setLayoutParams(layoutParams);
                    TextView z = new TextView(DataPoints.this);
                    z.setText(zPoint.getText().toString());
                    z.setTextColor(ContextCompat.getColor(DataPoints.this, R.color.colorWhite));
                    z.setTextSize(18);
                    z.setLayoutParams(layoutParams);

                    Button deleteButton = new Button(DataPoints.this);
                    deleteButton.setText("Delete");
                    deleteButton.setOnClickListener(deleteButtonListener);

                    row.addView(x);
                    row.addView(y);
                    row.addView(z);
                    row.addView(deleteButton);

                    table.addView(row, rowCounter, layoutParams);
                    rowCounter++;

                    xPoint.getText().clear();
                    yPoint.getText().clear();
                    zPoint.getText().clear();

                    xPoint.requestFocus();

                }
            }
        });

        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //turn your table points into a CSV
                //or send data elsewhere, who knows?

                for(int i = 1; i < table.getChildCount(); i++) {
                    View v = table.getChildAt(i);
                    if (v instanceof TableRow) {

                        TableRow row = (TableRow) v;

                        for(int j = 0; j < 3; j++){

                            TextView point = (TextView) row.getChildAt(j); // get child index on particular row
                            String text = point.getText().toString();

                            if (j == 0) {
                                xArray.add(Float.parseFloat(text));
                            } else if (j == 1) {
                                yArray.add(Float.parseFloat(text));
                            } else {
                                zArray.add(Float.parseFloat(text));
                            }
                        }
                    }
                }

                JSONObject title = new JSONObject();
                JSONObject unit = new JSONObject();

                try {

                    title.put("x", XAxisTitle);
                    title.put("y", YAxisTitle);
                    title.put("z", ZAxisTitle);

                    unit.put("x", XUnit);
                    unit.put("y", YUnit);
                    unit.put("z", ZUnit);

                    JSONArray points = new JSONArray();

                    for(int i = 0; i < xArray.size(); i++) {

                    JSONObject point = new JSONObject();

                    point.put("x", xArray.get(i));
                    point.put("y", yArray.get(i));
                    point.put("z", zArray.get(i));

                    points.put(point);

                    }

                    json.put("title", title);
                    json.put("unit", unit);
                    json.put("points", points);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(DataPoints.this);
                String url ="http://argraph.herokuapp.com/api";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //TODO: get the url from server, start new activity with webView with given url
                        Log.i("VOLLEY RESPONSE: ", response);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("VOLLEY ERROR: ", error.getMessage());
                    }

                }) {

                    @Override
                    public byte[] getBody() throws AuthFailureError {

                        String your_string_json = json.toString(); // put your json
                        return your_string_json.getBytes();
                    }
                };

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
                queue.start();

            }
        });
    }

    public boolean isNumericEditText(TextInputLayout editLayout, String error) {
        String str = editLayout.getEditText().getText().toString();
        if (str.matches("-?\\d+(\\.\\d+)?")) {  //match a number with optional '-' and decimal.
            editLayout.setError(null);
            return false;
        } else {
            editLayout.setError(error);
            return true;
        }
    }
}
