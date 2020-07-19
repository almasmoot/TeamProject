package com.example.teamproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import static com.example.teamproject.FirebaseLists.goals;

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
    private int frequency = 2; //0 for once, 1 for weekly, 2 for Daily


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goals);

        /*
        this section sets up the button in the middle of the page to pull up a calendar when it is
        clicked. from there you can select a date and it will save that to be used for the goal
         */
        selectDate = findViewById(R.id.btnDate);
        date = findViewById(R.id.tvSelectedDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        date.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH)+1) + "/" + calendar.get(Calendar.YEAR));
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
                                calendar.set(year,month,day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    /*
    This method handles the radio buttons and changes a global variable to indicate the frequency of
    the goal.
     */

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

    /*
    This method creates a new goal when the create button is pressed based off of the values in the
    text fields. if any of the required fields are empty, it will notify the user via a toast about
    which fields need to be filled out. after creating the goal, it then pushes it up to firebase.
     */
    public void createGoal(View view)
    {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName3);
        String name = editText.getText().toString();
        EditText editText1 = (EditText) findViewById(R.id.editTextTextPersonName4);
        String description = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.editTextNumber);
        int quantity = 0;
        try {
            quantity = Integer.parseInt(editText2.getText().toString());
        }
        catch(Exception e)
        {
        }
        if((quantity == 0)||(name == "")||(description==""))
        {
            String message = "required fields not filled out:";
            if(quantity==0)
            {
                message += "\nplease enter a quantity";
            }
            if(name.isEmpty())
            {
                message += "\nplease enter a goal name";
            }
            if(description.isEmpty())
            {
                message += "\nplease enter goal description";
            }
            message += ".";
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        }
        else {
            Goal createdGoal = new Goal(name, description, quantity, calendar.getTime());
            Calendar today = Calendar.getInstance();
            Calendar temp = Calendar.getInstance();
            temp.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
            String key;
            int count = 0;
            Map<String, Object> childUpdate = new HashMap<>(); //use a map for more than one time for additional goa
            switch (frequency) {
                case 0: // one time
                    key = mDatabase.child("goals").push().getKey();
                    childUpdate.put("/goals/"+key,createdGoal);
                    break;
                case 1: // weekly recurrence
                    while (today.before(temp)) {
                        Goal goalIn = new Goal(createdGoal);

                        goalIn.setDate(temp.getTime());
                        key = mDatabase.child("goals").push().getKey();
                        childUpdate.put("/goals/"+key,goalIn);
                        count++;
                        temp.add(temp.DATE, -(7 * count));
                    }

                    break;
                case 2: // daily recurrence
                    while (today.before(temp)) {
                        Goal goalIn = new Goal(createdGoal);

                        goalIn.setDate(temp.getTime());
                        key = mDatabase.child("goals").push().getKey();
                        childUpdate.put("/goals/"+key,goalIn);
                        count++;
                        temp.add(temp.DATE, -(count));
                    }
                    break;
                default:
            }
            mDatabase.updateChildren(childUpdate, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if(error == null)
                    {
                        Log.i(TAG, "success");
                    }
                    else
                    {
                        Log.w(TAG,"fail",error.toException());
                    }
                }
            });
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
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
