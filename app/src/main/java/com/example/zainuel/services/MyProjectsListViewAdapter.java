package com.example.zainuel.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by reddy on 27/4/17.
 */

public class MyProjectsListViewAdapter extends ArrayAdapter<ProjectObj> {

    ArrayList<ProjectObj> dataset;
    Context mContext;

    private static class ViewHolder {
        TextView pName;
        TextView pDate;
        TextView pTime;
        TextView status;
        TextView emp;
        TextView empRating;
    }

    public MyProjectsListViewAdapter(ArrayList<ProjectObj> data, Context context) {
        super(context, R.layout.my_projects_lv_temp, data);
        this.dataset = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProjectObj entry = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.my_projects_lv_temp, parent, false);
            viewHolder.pName = (TextView) convertView.findViewById(R.id.name_my_proj_lv_temp);
            viewHolder.pTime = (TextView) convertView.findViewById(R.id.time_my_proj_lv_temp);
            viewHolder.pDate = (TextView) convertView.findViewById(R.id.date_my_proj_lv_temp);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status_my_proj_lv_temp);
            viewHolder.emp = (TextView) convertView.findViewById(R.id.assigned_employee_my_proj_lv_temp);
            viewHolder.empRating = (TextView) convertView.findViewById(R.id.employee_rating_my_proj_lv_temp);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.pDate.setText(entry.getDate());
        viewHolder.pName.setText(entry.getName());
        viewHolder.pTime.setText(entry.getTime());
        viewHolder.status.setText(entry.getStatus());
        viewHolder.emp.setText(entry.getAssignedEmployee());
        viewHolder.empRating.setText(entry.getEmployeeRating());

        return convertView;
    }
}
