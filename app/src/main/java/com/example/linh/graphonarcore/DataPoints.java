package com.example.linh.graphonarcore;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataPoints extends AppCompatActivity {

    private Button addButton;
    private Button graphButton;
    private Button QRButton;
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
    private int screenWidth;
    private int screenHeight;
    private boolean graphARCORE;
    private int rowCounter;

    private final int deleteButtonWidth = 350;

    //removes the table row that the delete button is in from the table layout
    View.OnClickListener deleteButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            table.removeView((TableRow)v.getParent());
            rowCounter--;
        }
    };

    //initialize the variables
    private void init() {

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        addButton = findViewById(R.id.addButton);
        graphButton = findViewById(R.id.graphButton);
        QRButton = findViewById(R.id.QRButton);
        XTitle = findViewById(R.id.XAxisTitle);
        XTitle.setMaxWidth((screenWidth - deleteButtonWidth) / 3);
        XTitle.setMinWidth((screenWidth - deleteButtonWidth) / 3);
        XTitle.setMaxLines(1);
        XTitle.setEllipsize(TextUtils.TruncateAt.END);
        YTitle = findViewById(R.id.YAxisTitle);
        YTitle.setMaxWidth((screenWidth - deleteButtonWidth) / 3);
        YTitle.setMinWidth((screenWidth - deleteButtonWidth) / 3);
        YTitle.setMaxLines(1);
        YTitle.setEllipsize(TextUtils.TruncateAt.END);
        ZTitle = findViewById(R.id.ZAxisTitle);
        ZTitle.setMaxWidth((screenWidth - 400) / 3);
        ZTitle.setMinWidth((screenWidth - 400) / 3);
        ZTitle.setMaxLines(1);
        ZTitle.setEllipsize(TextUtils.TruncateAt.END);
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
        graphARCORE = true;
        rowCounter = 1;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_points);

        init();

        //get main activity intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //get the axis titles
        String str1 = extras.getString("X_TITLE").equals("") ? "X Axis" : extras.getString("X_TITLE");
        String str2 = extras.getString("Y_TITLE").equals("") ? "Y Axis" : extras.getString("Y_TITLE");
        String str3 = extras.getString("Z_TITLE").equals("") ? "Z Axis" : extras.getString("Z_TITLE");

        XAxisTitle = str1;
        YAxisTitle = str2;
        ZAxisTitle = str3;

        //get the unit titles
        XUnit = extras.getString("X_UNIT");
        YUnit = extras.getString("Y_UNIT");
        ZUnit = extras.getString("Z_UNIT");

        XTitle.setText(str1);
        YTitle.setText(str2);
        ZTitle.setText(str3);

        //get focus on x point
        xPoint.requestFocus();

        //add point
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if user entered a number
                if (isNumericEditText(xLayout, "Enter a valid number")) {

                } else if (isNumericEditText(yLayout, "Enter a valid number")) {

                } else if (isNumericEditText(zLayout, "Enter a valid number")) {

                } else {

                    //create a new table row with the given data points
                    TableRow row = new TableRow(DataPoints.this);

                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

                    layoutParams.setMargins(40, 5, 50, 5);

                    //create x point
                    TextView x = new TextView(DataPoints.this);
                    x.setText(xPoint.getText().toString());
                    x.setTextColor(ContextCompat.getColor(DataPoints.this, R.color.colorWhite));
                    x.setTextSize(18);
                    x.setLayoutParams(layoutParams);
                    x.setMaxWidth((screenWidth - 900) / 3);
                    x.setMinWidth((screenWidth - 900) / 3);
                    x.setMaxLines(1);
                    x.setEllipsize(TextUtils.TruncateAt.END);

                    //create y point
                    TextView y = new TextView(DataPoints.this);
                    y.setText(yPoint.getText().toString());
                    y.setTextColor(ContextCompat.getColor(DataPoints.this, R.color.colorWhite));
                    y.setTextSize(18);
                    y.setLayoutParams(layoutParams);
                    y.setMaxWidth((screenWidth - 900) / 3);
                    y.setMinWidth((screenWidth - 900) / 3);
                    y.setMaxLines(1);
                    y.setEllipsize(TextUtils.TruncateAt.END);

                    //create z point
                    TextView z = new TextView(DataPoints.this);
                    z.setText(zPoint.getText().toString());
                    z.setTextColor(ContextCompat.getColor(DataPoints.this, R.color.colorWhite));
                    z.setTextSize(18);
                    z.setLayoutParams(layoutParams);
                    z.setMaxWidth((screenWidth - 900) / 3);
                    z.setMinWidth((screenWidth - 900) / 3);
                    z.setMaxLines(1);
                    z.setEllipsize(TextUtils.TruncateAt.END);

                    //create delete button
                    Button deleteButton = new Button(DataPoints.this);
                    deleteButton.setText("Delete");
                    deleteButton.setOnClickListener(deleteButtonListener);

                    //add elements to the TableRow
                    row.addView(x);
                    row.addView(y);
                    row.addView(z);
                    row.addView(deleteButton);

                    //add TableRow to the layout
                    table.addView(row, rowCounter, layoutParams);
                    rowCounter++;

                    //clear the points
                    xPoint.getText().clear();
                    yPoint.getText().clear();
                    zPoint.getText().clear();

                    //reset focus to x point
                    xPoint.requestFocus();

                }
            }
        });

        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get the points from the table and add them to the arrays
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

                //create json for the three arrays
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
                    Log.i("JSON ERROR: ", e.getMessage());
                }

                //pop up to choose graph mode
                final AlertDialog.Builder alt_bld = new AlertDialog.Builder(DataPoints.this);

                final String[] graphs = {"ARCore", "Three.js"};

                alt_bld.setTitle("Select a Graphing Mode");
                alt_bld.setSingleChoiceItems(graphs, -1, new DialogInterface
                        .OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        //if item is 1, three.js was selected
                        graphARCORE = item != 1;

                        dialog.dismiss();

                        // Instantiate the RequestQueue.
                        final RequestQueue queue = Volley.newRequestQueue(DataPoints.this);
                        String url = graphARCORE ? "http://argraph.herokuapp.com/api" : "http://argraph.herokuapp.com/api/nonar";

                        // Request a string response from the provided URL.
                        JsonObjectRequest stringRequest = new JsonObjectRequest(url,json, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("VOLLEY RESPONSE: ", response.toString());

                                try {
                                    Intent intent = ArcoreIntentFactory.getIntent(DataPoints.this, response.getString("url"));
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                if (error.getMessage() == null) return;
                                Log.i("VOLLEY ERROR: ", error.getMessage());

                                Context context = getApplicationContext();
                                CharSequence text = "Interrupted Thread. Retrying...";
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();

                                queue.start();
                            }

                        }) {

                            @Override
                            public byte[] getBody() {

                                String your_string_json = json.toString(); // put your json
                                Log.i("JSON your_string_json: ", your_string_json);
                                return your_string_json.getBytes();
                            }
                        };

                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);
                        queue.start();

                    }
                });

                AlertDialog alert = alt_bld.create();
                alert.show();

            }
        });

        QRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DataPoints.this, QRScanActivity.class);
                startActivity(intent);

            }
        });

        zPoint.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    addButton.performClick();
                }
                return false;
            }
        });
    }

    //checks to see if input is a valid number, accepts negatives and decimals
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

    //converts pixels to dp
    public int pixelToDp(int px) {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;

        return (int) Math.ceil(px / logicalDensity);
    }
}
