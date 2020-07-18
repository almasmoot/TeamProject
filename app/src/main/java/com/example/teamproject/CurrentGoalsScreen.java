package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
    ArrayList<String> selectedItems;

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
        //glAdapter = new GoalAdapter(goalList, this);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.getValue(Goal.class).toString();
                goalList.add(value);
                //lv.setAdapter(glAdapter);
                lv.deferNotifyDataSetChanged();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void showSelectedItems(View view){
        String selItems="";
        for(String item:selectedItems){
            if(selItems=="")
                selItems=item;
            else
                selItems+="/"+item;
        }
        Toast.makeText(this, selItems, Toast.LENGTH_LONG).show();
    }




    // Navigation with the menu
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
/*            case R.id.friendProgress:
                Intent intent4 = new Intent(this, SecondFragment.class);
                this.startActivity(intent4);
                return true;
*/            default:
                return super.onOptionsItemSelected(item);
        }
    }



}