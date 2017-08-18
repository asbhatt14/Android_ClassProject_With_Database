package com.example.ankur.agencyapp.Controller;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.Mission;
import com.example.ankur.agencyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALONE on 8/10/2017.
 */

public class CustomDialogAdapter extends ArrayAdapter<Mission> {

    private List<Mission> allList;
    public List<Mission> selectedItems;
    Context context;

    public CustomDialogAdapter(@NonNull Context context, @NonNull List<Mission> objects,List<Mission> lstMissionHistoryTemp) {
        super(context, 0, objects);
        //selectedItems = new ArrayList<Mission>();
        selectedItems = lstMissionHistoryTemp;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Mission objMission = getItem(position);
        final int pos = position;
        // Check if an existing view is being reused, otherwise inflate the view
        final DialogViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new DialogViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_custom_doalog_layout, parent, false);
            viewHolder.dialog_chkMission = (CheckBox) convertView.findViewById(R.id.dialog_chkMission);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DialogViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.dialog_chkMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewHolder.dialog_chkMission.isChecked()) {
                    selectedItems.add(objMission);
                   // Toast.makeText(getContext(),Integer.toString(selectedItems.size()),Toast.LENGTH_SHORT).show();
                } else {
                    selectedItems.remove(objMission);
                    //Toast.makeText(getContext(),"Removed",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getContext(),Boolean.toString(viewHolder.dialog_chkMission.isChecked()),Toast.LENGTH_SHORT).show();
            }
        });

      for(int i=0;i<selectedItems.size();i++){
           if(objMission.getMissionId() == selectedItems.get(i).getMissionId()){
               viewHolder.dialog_chkMission.setChecked(true);
               break;
           }else {
               viewHolder.dialog_chkMission.setChecked(false);
           }
        }

        viewHolder.dialog_chkMission.setText(objMission.getMissionName());


        return result;
    }
}
class DialogViewHolder{
    CheckBox dialog_chkMission;
}
