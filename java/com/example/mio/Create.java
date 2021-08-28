package com.example.mio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Create extends AppCompatActivity {
    private String clas;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        
        //setting the Point View
        ((TextView) findViewById(R.id.point_create)).setText(String.valueOf(MainActivity.sharedPref.getInt("point",0)));
    }

    public void start(View view) {
        
        //getting name, class, task, and the time_state
        name = ((EditText) findViewById(R.id.name_of_mission)).getText().toString();
        clas = ((EditText) findViewById(R.id.dcbas)).getText().toString().toUpperCase();
        String task = ((EditText) findViewById(R.id.task_detail)).getText().toString();
        String day0 = (((EditText) findViewById(R.id.time_day)).getText().toString());
        String hour0 = (((EditText) findViewById(R.id.time_hour)).getText().toString());
        String min0 = (((EditText) findViewById(R.id.time_min)).getText().toString());
        int day = Integer.parseInt( day0.equals("") ? "0" : day0 ) * 86400000;
        int hour = Integer.parseInt( hour0.equals("") ? "0" : hour0 ) * 3600000;
        int min = Integer.parseInt( min0.equals("") ? "0" : min0 ) * 60000;

        //make sure the inputs are valid
        if (name.contains("'")) {
            ((EditText) findViewById(R.id.name_of_mission)).setText(name.replaceAll("'", ""));
            name = ((EditText) findViewById(R.id.name_of_mission)).getText().toString();
        }
        if (!(clas.equals("D") | clas.equals("C") | clas.equals("B") | clas.equals("A") | clas.equals("S"))) {
            Log.d("hmm", "enggak pernah masuk sini");
            ((EditText) findViewById(R.id.dcbas)).setText("D");
            clas = ((EditText) findViewById(R.id.dcbas)).getText().toString().toUpperCase();
        }

        //dialog to alert the user
        new AlertDialog.Builder(this)
                .setTitle(clas + " | " + name)
                .setPositiveButton("start", (dialogInterface, i) -> {
                    MainActivity.db.addTask(name, clas, String.valueOf(System.currentTimeMillis() + day + hour + min), task);
                    ((EditText) findViewById(R.id.name_of_mission)).setText("");
                    ((EditText) findViewById(R.id.dcbas)).setText("");
                    ((EditText) findViewById(R.id.task_detail)).setText("");
                    ((EditText) findViewById(R.id.time_day)).setText("");
                    ((EditText) findViewById(R.id.time_hour)).setText("");
                    ((EditText) findViewById(R.id.time_min)).setText("");
                })
                .setNegativeButton("cancel", null)
                .show();
    }
}