package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.example.teamproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private CheckBox goal1_check, goal2_check, goal3_check;
    ArrayList<String> numberList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        get_json();
    }

    // assign the checkboxes values
    public void addListenerOnButton() {
        goal1_check = (CheckBox) findViewById(R.id.goal1);
        goal2_check = (CheckBox) findViewById(R.id.goal2);
        goal3_check = (CheckBox) findViewById(R.id.goal3);
    }

    // Creates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void get_json() {
        String json;
        boolean daily = false;
        boolean weekly = false;
        boolean oneTime = false;
        try {
            InputStream is = getAssets().open("file_that_has_goals?");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i <jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("frequency").equals("daily")) {
                    daily = true;
                }
                if (obj.getString("frequency").equals("weekly")) {
                    weekly = true;
                }
                if (obj.getString("frequency").equals("oneTime")) {
                    oneTime = true;
                }
                String quantity = obj.getString("quantity");
                String description = obj.getString("description");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch(JSONException e) {
            e.printStackTrace();
        }
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
                Intent intent2 = new Intent(this, FirstFragment.class);
                this.startActivity(intent2);
                return true;
            case R.id.progress:
                Intent intent3 = new Intent(this, ProgressGraph.class);
                this.startActivity(intent3);
                return true;
            case R.id.friendProgress:
                Intent intent4 = new Intent(this, SecondFragment.class);
                this.startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}