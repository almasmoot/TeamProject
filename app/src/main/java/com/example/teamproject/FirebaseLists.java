package com.example.teamproject;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class FirebaseLists {

    public static List getFirebaseList()
    {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("goals");

        List<Goal> goals = (List<Goal>) mDatabase.child("goals").getDatabase();
        return goals;
    }

    public static void updateFirebaseList(List goals)
    {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("goals").setValue(goals);
    }
}
