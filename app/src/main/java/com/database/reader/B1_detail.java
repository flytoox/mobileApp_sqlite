package com.database.reader;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class B1_detail extends AppCompatActivity {

    DatabaseHelper myDbHelper;
    String         query;
    Cursor         c = null;
    private TextView idTask;
    private TextView idEmp;
    private TextView TypeTask;
    private TextView Place;
    private TextView Task;
    private TextView St;
    private TextView LevelRest;
    private TextView LevelDone;
    private TextView Level;
    private TextView Situation;
    private TextView Description;
    private TextView end_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b1_detail);
        myDbHelper= new DatabaseHelper(B1_detail.this);
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
        idTask = findViewById(R.id.id_TaskD);
        idEmp = findViewById(R.id.id_empD);
        TypeTask = findViewById(R.id.TypeTaskD);
        Place = findViewById(R.id.PlaceD);
        Task = findViewById(R.id.TaskD);
        St = findViewById(R.id.stD);
        LevelRest = findViewById(R.id.Level_restD);
        LevelDone = findViewById(R.id.Level_doneD);
        Level = findViewById(R.id.levelD);
        Situation = findViewById(R.id.SitutationD);
        Description = findViewById(R.id.DescriptionD);
        end_date = findViewById(R.id.end_dateD);
        getIncomingIntent();

    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("task_id")){
            c = null;
            String task_id = getIntent().getStringExtra("task_id");

            query = "SELECT tasks.id_task, tasks.id_emp, tasks.type_task, tasks.place, tasks.task, tasks.st, lev.level_rest," +
                    " lev.level_done, tasks.level, tasks.situtaion, tasks.description, tasks.end_date FROM tasks JOIN lev ON tasks.id_task " +
                    "= lev.id_task WHERE tasks.id_task='" + task_id + "';";
            c = myDbHelper.query(query);
            if (c != null && c.getCount() > 0) {
                c.moveToFirst();
                idTask.setText("Task Id:  " + c.getString(0));
                idEmp.setText("Emp Id:  " + c.getString(1));
                TypeTask.setText("Task Type:  " + c.getString(2));
                Place.setText("Place:  " + c.getString(3));
                Task.setText("Task:  " + c.getString(4));
                St.setText("St:  " + c.getString(5));
                LevelRest.setText("Lv Rest:  " + c.getString(6));
                LevelDone.setText("Lv Done:  " + c.getString(7));
                Level.setText("Lv:  " + c.getString(8));
                Situation.setText("Situation:  " + c.getString(9));
                Description.setText("Descr:  " + c.getString(10));
                end_date.setText("End Date:  " + c.getString(11));
                c.close();
            }
        }
    }
}