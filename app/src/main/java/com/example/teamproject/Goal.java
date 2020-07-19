package com.example.teamproject;

import java.util.Calendar;
import java.util.Date;

public class Goal {
    private String goalName;
    private String description;
    private int quantity;
    private int accomplished;
    private Date date;

    public Goal()
    {
        goalName = "";
        description = "";
        quantity = 0;
        accomplished = 0;
        date = new Date();
    }

    public Goal(Goal goal)
    {
        goalName = goal.getGoalName();
        description = goal.getDescription();
        quantity = goal.getQuantity();
        accomplished = 0;
        date = goal.getDate();
    }

    public Goal(String name, String description, int quantity, Date date)
    {
        goalName = name;
        this.description = description;
        this.quantity = quantity;
        accomplished = 0;
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDate(){ return date;}

    public String getDescription() {
        return description;
    }

    public String getGoalName() {
        return goalName;
    }

    public int getAccomplished() {
        return accomplished;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAccomplished(int quant){accomplished = quant;}

    public void setDate(Date calendar){date = new Date(calendar.getTime());}

    public void goalAchieved(){accomplished = quantity;}


    public Boolean laterGoal(Goal goal)
    {
        if(this.date.before(goal.getDate())||this.date.equals(goal.getDate()))
        {
            return true;
        }
        else
            return false;
    }
    @Override
    public String toString() {
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(date);
        return goalName.toUpperCase() + '\n' +description + '\n' + "Deadline: " + (deadline.get(Calendar.MONTH) + 1) + '/' + deadline.get(Calendar.DAY_OF_MONTH) + '/' + deadline.get(Calendar.YEAR);
    }

    public boolean isEqual(Goal goal) {
        return ((this.description == goal.getDescription()) && (this.quantity == goal.quantity)
                && (this.goalName == goal.getGoalName()) && ((this.date.compareTo(goal.getDate()) == 0)));
    }
}
