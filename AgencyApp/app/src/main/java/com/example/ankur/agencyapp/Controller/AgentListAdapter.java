package com.example.ankur.agencyapp.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.ankur.agencyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALONE on 8/3/2017.
 */

public class AgentListAdapter extends ArrayAdapter<Agents> {
    private ArrayList<Agents> listOfAgents;
    Context context;

    public AgentListAdapter(@NonNull Context context, @NonNull List<Agents> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Agents objAgent = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_agent_list_layout, parent, false);
            viewHolder.agentList_txtLevel = (TextView) convertView.findViewById(R.id.agentList_txtLevel);
            viewHolder.agentList_txtName = (TextView) convertView.findViewById(R.id.agentList_txtName);
            viewHolder.agentList_imgAgentProfile = (ImageView) convertView.findViewById(R.id.agentList_imgAgentProfile);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.agentList_txtName.setText("Name:"+objAgent.getAgentName());
        viewHolder.agentList_txtLevel.setText("Level:"+objAgent.getAgentLevel());
        if(objAgent!=null && objAgent.getAgentPhotoPath()!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(objAgent.getAgentPhotoPath());
            Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);
            viewHolder.agentList_imgAgentProfile.setImageBitmap(lowResBitmap);
            if(bitmap!=null)
            {
                bitmap.recycle();
                bitmap=null;
            }
        }

        return result;
    }
}

class ViewHolder{
    TextView agentList_txtName;
    TextView agentList_txtLevel;
    ImageView agentList_imgAgentProfile;
}
