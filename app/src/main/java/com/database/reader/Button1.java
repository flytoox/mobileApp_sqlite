package com.database.reader;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Button1 extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private B1adapter mAdapter;
    private B1adapter filterAdapter;
    private ArrayList<B1Model> B1ModelArrayList;
    private TextView TypeTask;
    private TextView Name;
    private TextView idTask;
    private TextView Phone;
    private TextView Task;
    private TextView Place;
    private TextView end_date;
    private String query;
    Cursor c = null;
    Cursor c1 = null;
    RecyclerView recyclerView ;
    DatabaseHelper myDbHelper;
    FloatingActionButton date_picker;
    String               date_picked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_button1);
        myDbHelper= new DatabaseHelper(Button1.this);
                try {
                    myDbHelper.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    myDbHelper.openDataBase();
                } catch (SQLException sqle) {
                    throw sqle;
                }
                query = "SELECT tasks.type_task, emp.name, tasks.id_task, emp.phone, tasks.task, " +
                        "tasks.place, tasks.end_date FROM tasks JOIN emp ON tasks.id_emp " +
                        "= emp.id_emp;";
                c = myDbHelper.query(query);
        recyclerView = findViewById(R.id.recycleB1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new B1adapter(this, c);
        recyclerView.setAdapter(mAdapter);
        date_picker = findViewById(R.id.add_fab);
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        Button1.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                date_picked = year + "-" + + (monthOfYear + 1) +  "-" + dayOfMonth;
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
                filter_date(date_picked);
            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });
        return true;
    }


    private void filter_date(String text) {
        text = 2022 + "-" + 12 + "-" + 31;
        Toast.makeText(this, ""+ text, Toast.LENGTH_SHORT).show();
        query = "SELECT tasks.type_task, emp.name, tasks.id_task, emp.phone, tasks.task, " +
                "tasks.place, tasks.end_date FROM tasks JOIN emp ON tasks.id_emp " +
                "= emp.id_emp WHERE tasks.start_date='" + text + "' ;";
        c1 = myDbHelper.query(query);
        filterAdapter = new B1adapter(this, c1);
        recyclerView.setAdapter(filterAdapter);
    }

    private void filter(String text) {
        query = "SELECT tasks.type_task, emp.name, tasks.id_task, emp.phone, tasks.task, " +
                "tasks.place, tasks.end_date FROM tasks JOIN emp ON tasks.id_emp " +
                "= emp.id_emp WHERE name LIKE '" + text + "%' ;";
        c1 = myDbHelper.query(query);
        filterAdapter = new B1adapter(this, c1);
        recyclerView.setAdapter(filterAdapter);
    }
}