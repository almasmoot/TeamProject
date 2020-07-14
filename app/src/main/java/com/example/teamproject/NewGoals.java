package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewGoals extends AppCompatActivity {


    private Button selectDate;
    private TextView date;
    private DatePickerDialog datePickerDialog;
    private int year;
    private int month;
    private int dayOfMonth;
    private Calendar calendar;
    public static final String EXTRA_MESSAGE = "com.example.teamproject.MESSAGE";
    public static final String TAG = "NewGoals";
    private int frequency = 0; //0 for once, 1 for weekly, 2 for Daily


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goals);

        selectDate = findViewById(R.id.btnDate);
        date = findViewById(R.id.tvSelectedDate);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(NewGoals.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date.setText(day + "/" + (month + 1) + "/" + year);
                                calendar.set(year,month+1,day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioDaily:
                if (checked)
                    frequency = 2;
                    break;
            case R.id.radioWeekly:
                if (checked)
                    frequency = 1;
                    break;
            case R.id.radioOneTime:
                if (checked)
                    frequency = 0;
                    break;
        }
    }

    public void createGoal(View view)
    {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        List<Goal> goals = (List<Goal>) mDatabase.child("goals").getDatabase();
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName3);
        String name = editText.getText().toString();
        EditText editText1 = (EditText) findViewById(R.id.editTextTextPersonName4);
        String description = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.editTextNumber);
        int quantity = Integer.parseInt(editText2.getText().toString());
        Goal createdGoal = new Goal(name,description,quantity,calendar);
        if(goals == null)
        {
            goals = new ArrayList<Goal>();
        }
        long today = Calendar.getInstance().getTimeInMillis();
        long deadline = calendar.getTimeInMillis();
        switch(frequency)
        {
            case 0: // one time
                goals.add(createdGoal);
                break;
            case 1: // weekly recurrence
                while(deadline > today)
                {
                    Goal goalIn = new Goal(createdGoal);
                    goalIn.setDate(deadline);
                    goals.add(goalIn);
                    deadline = deadline - 604800000;
                }
                break;
            case 2: // daily recurrence
                while(deadline > today)
                {
                    Goal goalIn = new Goal(createdGoal);
                    goalIn.setDate(deadline);
                    //myRef.setValue(createdGoal);
                    goals.add(goalIn);
                    deadline = deadline - 86400000;
                }
                break;
            default:
        }

        mDatabase.child("goals").setValue(goals);

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
