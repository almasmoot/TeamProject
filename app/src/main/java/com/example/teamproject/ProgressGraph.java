package com.example.teamproject;

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
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.List;

public class ProgressGraph extends AppCompatActivity {

    LineChart goalChart;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);
        goalChart=(LineChart) findViewById(R.id.linechart);
        //put data in graph
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        goalChart.setData(data);
        goalChart.invalidate();
    }

    private ArrayList<Entry> dataValues1()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
       /* dataVals.add(new Entry(0,20));
        dataVals.add(new Entry(1,24));
        dataVals.add(new Entry(2,2));
        dataVals.add(new Entry(3,10));
        dataVals.add(new Entry(4,28));
*/
        List<Goal> goal = FirebaseLists.getFirebaseList();


        int count = 0;
        for(Goal goalList : goal){
            //checks to see if the goal names line up, only displays data for that name
            //if(name == goalList.getGoalName()){
            dataVals.add(new Entry( (float) count, (float) goalList.getAccomplished()));
            count++;
            //}
        }
        return dataVals;
    }


    //dropdown menu of goals, pass in firebase database, or list of goals
   /* public void getDropdownChoices(){
        //set FirebaseList?
        List<Goal> goal = FirebaseLists.getBrennansList();
        //sets Spinner to the id
        Spinner spinner = (Spinner) findViewById(R.id.goal_dropdown);
        //Spinner needs to have access to the data so it can be placed in the menu
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                //goal.getGoalName(), android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        //spinner.setAdapter(adapter);
    }*/


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
