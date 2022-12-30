package com.database.reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Button2 extends AppCompatActivity {
    private B1adapter mAdapter;
    private String query;
    Cursor c = null;
    RecyclerView recyclerView ;
    DatabaseHelper myDbHelper;
    FloatingActionButton date_picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_button1);
        getSupportActionBar().hide();
        myDbHelper= new DatabaseHelper(Button2.this, getApplicationContext().getFilesDir().getPath());
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
                "= emp.id_emp WHERE DATE('now', 'localtime') BETWEEN tasks.start_date AND tasks.end_date AND (tasks.situtaion IS NULL OR tasks.situtaion='');";
        c = myDbHelper.query(query);
        recyclerView = findViewById(R.id.recycleB1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new B1adapter(this, c);
        recyclerView.setAdapter(mAdapter);
        date_picker = findViewById(R.id.add_fab);
        date_picker.setVisibility(View.GONE);
    }

}