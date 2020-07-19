package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> selectedItems;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;
    ListView listView;
    CalendarView calendarView;
    static int year;
    static int month;
    static int dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.calendarView);
        selectedItems=new ArrayList<String>();
        databaseReference=FirebaseDatabase.getInstance().getReference("goals");
        listView=(ListView) findViewById(R.id.goalsView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        String date = getIntent().getStringExtra("date");

        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.checkable_list_layout,R.id.txt_title,arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Calendar calendar1;
                Calendar calendar2;
                calendar1 = Calendar.getInstance();
                calendar1.setTimeInMillis(calendarView.getDate());
                Goal value=snapshot.getValue(Goal.class);
                calendar2 = Calendar.getInstance();
                calendar2.setTime(value.getDate());
                if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                        calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)) {
                    arrayList.add(value.toString());
                    arrayAdapter.notifyDataSetChanged();
                }


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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goalAchieved();
            }

        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                MainActivity.month = month;
                MainActivity.year = year;
                MainActivity.dayOfMonth = dayOfMonth;
                arrayList.clear();
                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Calendar calendar2;
                        Goal value=snapshot.getValue(Goal.class);
                        calendar2 = Calendar.getInstance();
                        calendar2.setTime(value.getDate());
                        if (MainActivity.year == calendar2.get(Calendar.YEAR) && MainActivity.month == calendar2.get(Calendar.MONTH) &&
                                MainActivity.dayOfMonth == calendar2.get(Calendar.DAY_OF_MONTH)) {
                            arrayList.add(value.toString());
                            arrayAdapter.notifyDataSetChanged();
                        }


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
            }
        });
    }




    // Creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    private void goalAchieved() {
        Toast.makeText(this, "Goal accomplished!!",Toast.LENGTH_LONG).show();

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}