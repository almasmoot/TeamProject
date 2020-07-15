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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

//    private CheckBox goal1_check, goal2_check, goal3_check;
    ArrayList<String> numberList = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference=FirebaseDatabase.getInstance().getReference("goals");
        listView=(ListView) findViewById(R.id.goalsView);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arrayList);
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
//        addListenerOnButton();
    }

    // assign the checkboxes values
//    public void addListenerOnButton() {
//        goal1_check = (CheckBox) findViewById(R.id.goal1);
//        goal2_check = (CheckBox) findViewById(R.id.goal2);
//        goal3_check = (CheckBox) findViewById(R.id.goal3);
//    }

    // Creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

//    public void get_json() {
//        String json;
//        boolean daily = false;
//        boolean weekly = false;
//        boolean oneTime = false;
//        String description = "";
//        String quantity;
//        try {
//            InputStream is = getAssets().open("file_that_has_goals?");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//
//            json = new String(buffer, "UTF-8");
//            JSONArray jsonArray = new JSONArray(json);
//
//            for(int i = 0; i <jsonArray.length(); i++) {
//                JSONObject obj = jsonArray.getJSONObject(i);
//                if (obj.getString("frequency").equals("daily")) {
//                    daily = true;
//                }
//                if (obj.getString("frequency").equals("weekly")) {
//                    weekly = true;
//                }
//                if (obj.getString("frequency").equals("oneTime")) {
//                    oneTime = true;
//                }
//                quantity = obj.getString("quantity");
//                description = obj.getString("description");
//            }
//        }catch (IOException e) {
//            e.printStackTrace();
//        }catch(JSONException e) {
//            e.printStackTrace();
//        }
//
//        setContentView(R.layout.activity_main);
//        TextView goal1View = (TextView)findViewById(R.id.goal1);
//        goal1View.setText(description);
//    }
//    public void showGoals() {
//        boolean daily = false;
//        boolean weekly = false;
//        boolean oneTime = false;
//        String description = "";
//        String quantity;
//        DatabaseReference mDatabase;
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        List<Goal> goals = (List<Goal>) mDatabase.child("goals").getDatabase();
//        for (Goal goal : goals) {
//
//        }
//        description = goals.get("description");
//        setContentView(R.layout.activity_main);
//        ListView goal1View = (ListView)findViewById(R.id.goal1);
//        goal1View.setText(description);
//    }

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