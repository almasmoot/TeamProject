package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.teamproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> selectedItems;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;
    ListView listView;
    Module module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedItems=new ArrayList<String>();
        databaseReference=FirebaseDatabase.getInstance().getReference("goals");
        listView=(ListView) findViewById(R.id.goalsView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,R.layout.checkable_list_layout,R.id.txt_title,arrayList);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteGoal();
                // selected item
//                String selectedItem = ((TextView) view).getText().toString();
//                if(selectedItems.contains(selectedItem)) {

//                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
//                }
//                else
//                    selectedItems.add(selectedItem); //add selected item to the list of selected items

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


    private void deleteGoal() {
        String goalId = databaseReference.push().getKey();
        DatabaseReference drGaol = FirebaseDatabase.getInstance().getReference("goals").child(goalId);

        drGaol.removeValue();
        Toast.makeText(this, "Goal accomplished!", Toast.LENGTH_LONG).show();
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