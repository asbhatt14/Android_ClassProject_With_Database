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

import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALONE on 8/10/2017.
 */

public class MissionGridAdapter extends ArrayAdapter {

    private ArrayList<String> listImages;
    Context context;

    public MissionGridAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View row;
        ViewHolderImage holder;

        if (convertView == null) {
            holder = new ViewHolderImage();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_grid_image_layout, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.grid_image);
            row=convertView;
            row.setTag(holder);
        } else {
            holder = (ViewHolderImage) convertView.getTag();
            row=convertView;
        }

        return row;
    }
}

class ViewHolderImage{
    ImageView image;
}
