package com.example.teamproject;

import java.util.Date;

public class Goal {
    private String goalName;
    private String description;
    private int quantity;
    private Date day;

    public Goal(String name, String description, int quantity, Date day)
    {
        goalName = name;
        this.description = description;
        this.quantity = quantity;
        this.day=day;
    }

    public Date getDay() {
        return day;
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

    public void setDay(Date day) {
        this.day = day;
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

    @Override
    public String toString() {
        return description;
    }
}
