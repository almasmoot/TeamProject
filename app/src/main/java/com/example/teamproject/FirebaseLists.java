package com.example.teamproject;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseLists {

    public static final String TAG = "FirebaseLists";
    private static DatabaseReference mReference;
    public static Map<String,Goal> goals;
    public static List getFirebaseList() {

        mReference = FirebaseDatabase.getInstance().getReference().child("goals");
        ValueEventListener goalListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                goals = dataSnapshot.getValue(Map.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mReference.addValueEventListener(goalListener);
        List<Goal> goalvalues = new ArrayList<Goal>(goals.values());

        return goalvalues;
    }

    public static void updateFirebaseList(List goals) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("goals").setValue(goals);
    }

    /*public static List getBrennansList() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("goals");
        final List<Goal>[] goals = new List[]{new ArrayList<Goal>()};

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 This method is called once with the initial value and again whenever data at this location is updated.
                long value = dataSnapshot.getChildrenCount();
                //Log.d(TAG, "no of children: " + value);

                //GenericTypeIndicator<List<Goal>> genericTypeIndicator = new GenericTypeIndicator<List<Goal>>() {
                //};

                //List<Goal> taskDesList = dataSnapshot.getValue(genericTypeIndicator);
                //goals[0] = taskDesList;
                /*for (int i = 0; i < taskDesList.size(); i++) {
                    //Toast.makeText(MainActivity.this, "TaskTitle = " + taskDesList.get(i).getTaskTitle(), Toast.LENGTH_LONG).show();
                    goals[i] = taskDesList[i];
                }
                //return taskDesList;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    return goals[0];
    }*/
}
