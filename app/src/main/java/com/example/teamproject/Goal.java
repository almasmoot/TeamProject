package com.example.teamproject;

import java.util.Date;

public class Goal {
    private String goalName;
    private String description;
    private int quantity;
    private int accomplished;

    public Goal(Goal goal)
    {
        goalName = goal.getGoalName();
        description = goal.getDescription();
        quantity = goal.getQuantity();
        accomplished = 0;
    }

    public Goal(String name, String description, int quantity)
    {
        goalName = name;
        this.description = description;
        this.quantity = quantity;
        accomplished = 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getGoalName() {
        return goalName;
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

    public Boolean goalAchieved(){return (accomplished >= quantity);}
    @Override
    public String toString() {
        return description;
    }
}
