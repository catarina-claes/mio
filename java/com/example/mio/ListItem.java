package com.example.mio;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListItem extends BaseAdapter {

    public ArrayList<String[]> item;
    public Context context;
    public TextView point;
    private final LayoutInflater inflater;

    //getting the inflater and the List
    public ListItem(LayoutInflater a, ArrayList<String[]> b, Context c, TextView d) {
        context = c;
        inflater = a;
        item = b;
        point = d;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //set the View and edit it
        String[] items = item.get(i);
        view = inflater.inflate(R.layout.task_list,null);
        view.findViewById(R.id.delete).setOnClickListener(view1 -> new AlertDialog.Builder(context)
                .setTitle("Deleting '" + items[0] + "'")
                .setPositiveButton("delete", (dialogInterface, wtf) -> {
                    MainActivity.db.deleteTask(items[0], items[1], false);
                    point.setText(String.valueOf(MainActivity.sharedPref.getInt("point",0)));
                    item.remove(i);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Deleting the " + items[0] + "!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("cancel", null)
                .show());

        ((TextView) view.findViewById(R.id.title_task)).setText(items[0]);
        ((TextView) view.findViewById(R.id.clas)).setText(items[1].concat(" | "));
        ((TextView) view.findViewById(R.id.time_stamp)).setText(getDate(Long.parseLong(items[2]), "EEE, hh:mm dd/MM"));
        ((TextView) view.findViewById(R.id.task)).setText(items[3]);
        return view;
    }

    //convert ms to date
    public static String getDate( long milliSeconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
