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
    private B1adapter mAdapter;
    private B1adapter filterAdapter;
    private String query;
    Cursor c = null;
    Cursor c1 = null;
    RecyclerView recyclerView ;
    DatabaseHelper myDbHelper;
    FloatingActionButton date_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_button1);
        myDbHelper= new DatabaseHelper(Button1.this, getApplicationContext().getFilesDir().getPath());
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


                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Button1.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                               filter_date(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                            }
                        },

                        year, month, day);
                datePickerDialog.show();
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


        query = "SELECT tasks.type_task, emp.name, tasks.id_task, emp.phone, tasks.task, " +
                "tasks.place, tasks.end_date FROM tasks JOIN emp ON tasks.id_emp " +
                "= emp.id_emp WHERE tasks.end_date='" + text + "' ;";
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