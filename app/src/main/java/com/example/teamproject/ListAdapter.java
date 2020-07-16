package com.example.teamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class GoalList {

    // local variables
    String name;
    String description;
    boolean selected = false;

    // constructor
    public GoalList (String name, String description) {
        this.name = name;
        this.description = description;
    }

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // display the list

    public class GoalAdapter extends ListAdapter<FirebaseLists> {

        private List<GoalList> firebaseList;
        private Context context;

        public GoalAdapter(List<GoalList> firebaseList, Context context) {
            //super(context, R.layout.single_listview_item, firebaseList);
            this.firebaseList = firebaseList;
            this.context = context;
        }

        private class GoalHolder {
            public TextView goalName;
            public TextView description;
            public CheckBox chkBox;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            GoalHolder holder = new GoalHolder();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //v = inflater.inflate(R.layout.single_listview_item, null);

                holder.goalName = (TextView) v.findViewById(R.id.textView4);
                holder.description = (TextView) v.findViewById(R.id.description);
                holder.chkBox = (CheckBox) v.findViewById(R.id.chk_box);

                holder.chkBox.setOnCheckedChangeListener((CurrentGoalsScreen) context);
            } else {
                holder = (GoalHolder) v.getTag();
            }

            Goal g = GoalList.get(position);
            holder.goalName.setText(g.getGoalName());
            holder.description.setText(g.getDescription());
            holder.chkBox.setChecked(g.goalAchieved());
            holder.chkBox.setTag(g);

            return v;
        }
    }

}
