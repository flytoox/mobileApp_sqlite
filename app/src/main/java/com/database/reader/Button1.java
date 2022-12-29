package com.database.reader;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

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
    private void filter(String text) {
        query = "SELECT tasks.type_task, emp.name, tasks.id_task, emp.phone, tasks.task, " +
                "tasks.place, tasks.end_date FROM tasks JOIN emp ON tasks.id_emp " +
                "= emp.id_emp WHERE name LIKE '" + text + "%' ;";
        c1 = myDbHelper.query(query);
        filterAdapter = new B1adapter(this, c1);
        recyclerView.setAdapter(filterAdapter);
    }
}