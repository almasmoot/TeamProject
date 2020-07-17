package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CurrentGoalsScreen extends AppCompatActivity {

    ListView lv;
    ArrayList<Goal> goalList;
    GoalAdapter glAdapter;
    //initialize list here
    //GoalList.GoalAdapter goalAdapter;
    public static final String EXTRA_MESSAGE = "";
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_goals_screen);
        lv = (ListView)findViewById(R.id.listview);
        displayGoalList();


    }

    private void displayGoalList(){
        //goalList = (ArrayList)FirebaseLists.getFirebaseList(); //assume that getFireBaseList will be fixed
        goalList = new ArrayList<Goal>();
        databaseReference = FirebaseDatabase.getInstance().getReference("goals");
        glAdapter = new GoalAdapter(goalList, this);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Goal value=snapshot.getValue(Goal.class);
                goalList.add(value);

                lv.setAdapter(glAdapter);

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




    }



}