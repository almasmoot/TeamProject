package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProgressGraph extends AppCompatActivity {

    LineChart goalChart;
    String goalName;
    ArrayList<Entry> dataVals = new ArrayList<Entry>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);
        goalChart=(LineChart) findViewById(R.id.linechart);
        //put data in graph
        Intent intent = getIntent();
        goalName = intent.getStringExtra(CurrentGoalsScreen.EXTRA_MESSAGE);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        goalChart.setData(data);
        goalChart.invalidate();



    }

    private ArrayList<Entry> dataValues1()
    {
        DatabaseReference databaseReference;
        databaseReference=FirebaseDatabase.getInstance().getReference("goals");

        //for loop
        databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    //check for specific goalname that was passed from the call
                    //get intent and stuff
                    //Calendar goalDate = Calendar.getInstance();
                    if(goalName == snapshot.getValue(Goal.class).getGoalName()) {
                        //goalDate.setTimeInMillis(snapshot.getValue(Goal.class).getDate().getTime());
                        dataVals.add(new Entry ((int)snapshot.getValue(Goal.class).getDate().getTime()/86400000, snapshot.getValue(Goal.class).getAccomplished()));
                    }
                    //String value=snapshot.getValue(Goal.class).toString();
                    //arrayList.add(value);
                    //arrayAdapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });

        return dataVals;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.newGoal:
                Intent intent1 = new Intent(this, NewGoals.class);
                this.startActivity(intent1);
                return true;
            case R.id.existingGoals:
                Intent intent2 = new Intent(this, CurrentGoalsScreen.class);
                this.startActivity(intent2);
                return true;
            case R.id.progress:
                Intent intent3 = new Intent(this, ProgressGraph.class);
                this.startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
