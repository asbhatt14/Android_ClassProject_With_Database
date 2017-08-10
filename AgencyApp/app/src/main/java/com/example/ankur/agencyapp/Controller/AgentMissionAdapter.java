package com.example.ankur.agencyapp.Controller;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.Mission;
import com.example.ankur.agencyapp.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALONE on 8/4/2017.
 */

public class AgentMissionAdapter extends ArrayAdapter<Mission> {

    private ArrayList<Mission> listOfMission;
    Context context;

    public AgentMissionAdapter(@NonNull Context context, @NonNull List<Mission> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Mission objMission = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolderMission viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolderMission();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_agent_mission_layout, parent, false);
            viewHolder.mission_txtMissionName = (TextView) convertView.findViewById(R.id.mission_txtMissionName);
            viewHolder.mission_txtMissionDate = (TextView) convertView.findViewById(R.id.mission_txtMissionDate);
            viewHolder.mission_txtMissionStatus = (TextView) convertView.findViewById(R.id.mission_txtMissionStatus);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderMission) convertView.getTag();
            result=convertView;
        }

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

        viewHolder.mission_txtMissionName.setText("Mission Name:"+objMission.getMissionName());
        viewHolder.mission_txtMissionDate.setText("Date:"+df.format(objMission.getMissionDate()));
        viewHolder.mission_txtMissionStatus.setText(objMission.getMissionStatus());

        return result;
    }
}

class ViewHolderMission{
    TextView mission_txtMissionName;
    TextView mission_txtMissionDate;
    TextView mission_txtMissionStatus;
}
