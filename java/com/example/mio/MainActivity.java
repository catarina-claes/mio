package com.example.mio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String[]> item;
    private ListItem customAdapter;
    private ListView scroll;
    private LayoutInflater inflater;
    private TextView point;
    public static DataBase db;
    public static SharedPreferences.Editor editor;
    public static SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the inflater service
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting the list View and the Point View
        scroll =  findViewById(R.id.scroll_task);
        point = findViewById(R.id.point_main);

        //setting both database and the editor
        sharedPref = getSharedPreferences("com.example.mio.overall", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        db = new DataBase(this);

    }
    
    //add Task List View to scrollView, image, point, name.
    private void refreshView() {

        //set the point View
        point.setText(String.valueOf(sharedPref.getInt("point",0)));

        //set the Mission Views
        ((TextView) findViewById(R.id.mission_d)).setText("D • ".concat(String.valueOf(sharedPref.getInt("D",0))));
        ((TextView) findViewById(R.id.mission_c)).setText("C • ".concat(String.valueOf(sharedPref.getInt("C",0))));
        ((TextView) findViewById(R.id.mission_b)).setText("B • ".concat(String.valueOf(sharedPref.getInt("B",0))));
        ((TextView) findViewById(R.id.mission_a)).setText("A • ".concat(String.valueOf(sharedPref.getInt("A",0))));
        ((TextView) findViewById(R.id.mission_s)).setText("S • ".concat(String.valueOf(sharedPref.getInt("S",0))));

        //getting the Task Lists
        item = db.readTask();

        //creating the custom adapter and adding Task Lists to it
        customAdapter = new ListItem(inflater, item, this, point);
        scroll.setAdapter(customAdapter);

    }

    //claim rewards
    public void claim(View view) {

        int sum = 0;
        String nameTask = "";

        //checking the task one by one and deleting it if the time has passed
        int siz = item.size();
        for (int i = 0; i < siz; i++) {
            String[] task = item.get(i);
            if (System.currentTimeMillis() > Long.parseLong(task[2])) {
                MainActivity.db.deleteTask(task[0], task[1], true);
                item.remove(i);
                customAdapter.notifyDataSetChanged();
                i -= 1;
                siz -= 1;
                sum += 1;
                nameTask = task[0];
            }
        }
        if (sum == 0) Toast.makeText(this, "You haven't completed any tasks!", Toast.LENGTH_SHORT).show();
        if (sum == 1) Toast.makeText(this, "Claiming the " + nameTask + "!", Toast.LENGTH_SHORT).show();
        if (sum > 1) Toast.makeText(this, "Claiming " + sum + " tasks!", Toast.LENGTH_SHORT).show();

        refreshView();
    }
    
    //change activity to activity_task
    public void goTask(View view) {
        startActivity(new Intent(this, Create.class));
    }
    
    //change activity to activity_shop
    public void goShop(View view) {
        startActivity(new Intent(this, Shop.class));
    }

    //refreshing the View when the screen onResume
    @Override
    public void onResume() {
        super.onResume();
        refreshView();
    }
}