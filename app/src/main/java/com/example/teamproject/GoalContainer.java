package com.example.teamproject;

import java.util.List;
import com.example.teamproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.teamproject.FirebaseLists;

public class GoalContainer {

    // Local variable to hold our list
    List list;

    public void fillList() {
        list = FirebaseLists.getFirebaseList();
    }
}
