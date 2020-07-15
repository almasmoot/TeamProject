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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseLists {

    public static List getFirebaseList() {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("goals");

        List<Goal> goals = (List<Goal>) mDatabase.child("goals").getDatabase();
        return goals;
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
