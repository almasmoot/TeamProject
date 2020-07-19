package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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


//This graph does not work, it was fundamentally flawed as the database model was not set up for it.
//We decided to cut scope
public class ProgressGraph extends AppCompatActivity {

    //global variables
    LineChart goalChart;
    String goalString;
    ArrayList<Entry> dataVals = new ArrayList<Entry>();

    //onCreate function, creates the graph after populating the global list with data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);
        goalChart=(LineChart) findViewById(R.id.linechart);
        //get the intent
        Intent intent = getIntent();
        goalString = intent.getStringExtra(CurrentGoalsScreen.EXTRA_MESSAGE);
        //calls function to fill list with data entries
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        //put data entries into the graph
        LineData data = new LineData(dataSets);
        goalChart.setData(data);
        goalChart.invalidate();
    }

    private ArrayList<Entry> dataValues1()
    {
        //function variables, accesses firebase
        DatabaseReference databaseReference;
        databaseReference=FirebaseDatabase.getInstance().getReference("goals");

        //adds data point from specific goal
        databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    //check for specific goal that was passed from the call
                    //Calendar goalDate = Calendar.getInstance();
                    Goal temp = snapshot.getValue(Goal.class);
                    if(goalString == temp.toString()) {
                        //goalDate.setTimeInMillis(snapshot.getValue(Goal.class).getDate().getTime());

                        dataVals.add(new Entry ((int)temp.getDate().getTime()/86400000, temp.getAccomplished()));
                    }
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

    //Menu selector
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
//items in the menu
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
                Intent intent3 = new Intent(this, MainActivity.class);
                this.startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
