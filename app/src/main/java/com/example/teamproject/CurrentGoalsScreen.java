package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CurrentGoalsScreen extends AppCompatActivity {

    ListView lv;
    ArrayList<Goal> goalList;
    GoalAdapter glAdapter;
    //initialize list here
    //GoalList.GoalAdapter goalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_goals_screen);
        lv = (ListView)findViewById(R.id.listview);
        displayGoalList();

    }

    private void displayGoalList(){
        goalList = (ArrayList)FirebaseLists.getFirebaseList(); //assume that getFireBaseList will be fixed
        glAdapter = new GoalAdapter(goalList, this);
        lv.setAdapter(glAdapter);
    }



}