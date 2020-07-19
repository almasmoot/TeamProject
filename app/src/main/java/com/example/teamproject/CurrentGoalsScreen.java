package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/*
/ CURRENT GOALS SCREEN
/ Java class for the activity displaying the listView for the current goals
/ in Firebase. When items are selected from the list of goals, the user
/ is taken to a graph of the progress made since the goal was created
*/

public class CurrentGoalsScreen extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> selectedItems;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;
    ListView listView;
    public static final String EXTRA_MESSAGE = "com.example.teamproject.goalInfo";
    Context context;

    @Override

    // Retrieves information from Firebase to display the current goals in the listView
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_goals_screen);
        selectedItems=new ArrayList<String>();
        context = this;
        databaseReference=FirebaseDatabase.getInstance().getReference("goals");
        listView=(ListView) findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>(CurrentGoalsScreen.this,R.layout.checkable_list_layout,R.id.txt_title,arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value=snapshot.getValue(Goal.class).toString();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // takes the user to the Progress Graph screen when the goal is clicked
    /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(context, ProgressGraph.class);
            TextView textview = findViewById(R.id.txt_title);
            String goalInfo = textview.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, goalInfo);
            startActivity(intent);
        }
    });*/
    }

    /*
    /OPTIONS MENU
    /Contains the code required for the options menu in the top right for app navigation
    */

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
                Intent intent3 = new Intent(this, MainActivity.class);
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



