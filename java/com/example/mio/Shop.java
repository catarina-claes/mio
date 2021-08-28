package com.example.mio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Shop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //setting the Point View
        ((TextView) findViewById(R.id.point_shop)).setText(String.valueOf(MainActivity.sharedPref.getInt("point",0)));
    }

    public void buy(View view) {

        //buying the items
        int anime = Integer.parseInt(((EditText) findViewById(R.id.input_anime)).getText().toString().equals("") ? "0" : ((EditText) findViewById(R.id.input_anime)).getText().toString()) * 60;
        int animer = Integer.parseInt(((EditText) findViewById(R.id.input_animer)).getText().toString().equals("") ? "0" : ((EditText) findViewById(R.id.input_animer)).getText().toString()) * 90;
        int game = Integer.parseInt(((EditText) findViewById(R.id.input_game)).getText().toString().equals("") ? "0" : ((EditText) findViewById(R.id.input_game)).getText().toString()) * 120;
        int jajan = Integer.parseInt(((EditText) findViewById(R.id.input_jajan)).getText().toString().equals("") ? "0" : ((EditText) findViewById(R.id.input_jajan)).getText().toString()) * 100;
        int mln = Integer.parseInt(((EditText) findViewById(R.id.input_mangaln)).getText().toString().equals("") ? "0" : ((EditText) findViewById(R.id.input_mangaln)).getText().toString()) * 60;
        int mlnr = Integer.parseInt(((EditText) findViewById(R.id.input_mangalnr)).getText().toString().equals("") ? "0" : ((EditText) findViewById(R.id.input_mangalnr)).getText().toString()) * 140;
        int medsos = Integer.parseInt(((EditText) findViewById(R.id.input_medsos)).getText().toString().equals("") ? "0" : ((EditText) findViewById(R.id.input_medsos)).getText().toString()) * 150;
        int result = anime + animer + game + jajan + mln + mlnr + medsos;

        //dialog to alert the user when want to buy somethings
        new AlertDialog.Builder(this)
                .setTitle("The Prices are " + result)
                .setPositiveButton("buy", (dialogInterface, i) -> {
                    if (MainActivity.sharedPref.getInt("point", 0) >= result) {
                        MainActivity.editor.putInt("point", MainActivity.sharedPref.getInt("point", 0) - result);
                        MainActivity.editor.apply();
                        ((TextView) findViewById(R.id.point_shop)).setText(String.valueOf(MainActivity.sharedPref.getInt("point", 0)));
                    }
                })
                .setNegativeButton("cancel", null)
                .show();
    }
}