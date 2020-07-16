package com.example.teamproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

    // display the list

    public class GoalAdapter extends ArrayAdapter {

        private static final String EXTRA_MESSAGE = "com.example.teamproject.GoalAdapter";
        private List<Goal> firebaseList;
        private Context context;

        public GoalAdapter(List<Goal> firebaseList, Context context) {
            super(context, R.layout.single_listview_item, firebaseList);
            this.firebaseList = firebaseList;
            this.context = context;
        }


        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        private class GoalHolder {
            public Button goal;
            public CheckBox chkBox;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            GoalHolder holder = new GoalHolder();

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.single_listview_item, null);

                holder.goal   = (Button) v.findViewById(R.id.button);
                //holder.chkBox = (CheckBox) v.findViewById(R.id.chk_box);

                holder.chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //what do you do when it's checked?

                    }
                });

            }
            else {
                holder = (GoalHolder) v.getTag();
            }

            final Goal g = firebaseList.get(position);
            holder.goal.setText(g.toString());
            holder.goal.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProgressGraph.class);
                    intent.putExtra(EXTRA_MESSAGE, (g.toString()));
                    context.startActivity(intent);
                }
            });

            holder.chkBox.setChecked(g.isAccomplished());
            holder.chkBox.setTag(g);

            return v;
        }

        public void toGraph(View view) {

        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
