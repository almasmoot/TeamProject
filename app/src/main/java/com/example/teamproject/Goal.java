package com.example.teamproject;

import java.util.Calendar;

public class Goal {
    private String goalName;
    private String description;
    private int quantity;
    private int accomplished;
    private Calendar date;

    public Goal()
    {
        goalName = "";
        description = "";
        quantity = 0;
        accomplished = 0;
        date = Calendar.getInstance();
    }

    public Goal(Goal goal)
    {
        goalName = goal.getGoalName();
        description = goal.getDescription();
        quantity = goal.getQuantity();
        accomplished = 0;
        date = goal.getDate();
    }

    public Goal(String name, String description, int quantity, Calendar date)
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

    public Calendar getDate(){ return date;}

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

    public void setDate(long millis)
    {
        date.setTimeInMillis(millis);
    }

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
        return goalName + '\n' +description;
    }
}
