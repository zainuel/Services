package com.example.zainuel.services.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zainuel.services.ProjectObj;
import com.example.zainuel.services.R;

import java.util.ArrayList;

/**
 * Created by reddy on 29/4/17.
 */

public class AdminProjectsListViewAdapter  extends ArrayAdapter<ProjectObj>{

    ArrayList<ProjectObj> dataset;
    Context mContext;


    customButtonListener customListner;

    public interface customButtonListener {
         void onButtonClickListner(int position);
    }


    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private static class ViewHolder {
        TextView pName;
        TextView pDate;
        TextView pTime;
        TextView status;
        Button tkup;

    }

    public AdminProjectsListViewAdapter(ArrayList<ProjectObj> data, Context context) {
        super(context, R.layout.projects_lv_temp, data);
        this.dataset = data;
        this.mContext=context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ProjectObj entry = getItem(position);
       ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.projects_lv_temp, parent, false);
            viewHolder.pName = (TextView) convertView.findViewById(R.id.name_my_proj_lv_temp);
            viewHolder.pTime = (TextView) convertView.findViewById(R.id.time_my_proj_lv_temp);
            viewHolder.pDate = (TextView) convertView.findViewById(R.id.date_my_proj_lv_temp);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status_my_proj_lv_temp);
            viewHolder.tkup = (Button) convertView.findViewById(R.id.take_up_button_projects_lv_temp);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.pDate.setText(entry.getDate());
        viewHolder.pName.setText(entry.getName());
        viewHolder.pTime.setText(entry.getTime());
        viewHolder.status.setText(entry.getStatus());

        viewHolder.tkup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (customListner != null) {
                    customListner.onButtonClickListner(position);
                }

            }
        });

        return convertView;
    }
}
