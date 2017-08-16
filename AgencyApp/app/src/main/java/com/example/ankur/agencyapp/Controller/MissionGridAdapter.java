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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.Model.PhotoBook;
import com.example.ankur.agencyapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ALONE on 8/10/2017.
 */

public class MissionGridAdapter extends ArrayAdapter<PhotoBook> {

    private ArrayList<String> listImages;
    Context context;
    public List<Integer> selectedPositions;

    public MissionGridAdapter(@NonNull Context context, @NonNull List<PhotoBook> objects) {
        super(context, 0, objects);
        selectedPositions = new ArrayList<Integer>();
        //row_grid_image_layout
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View row;
        ViewHolderImage holder;

        PhotoBook objPhotoBook = getItem(position);

        if (convertView == null) {
            holder = new ViewHolderImage();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_grid_image_layout, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.grid_image);
            holder.txtImageNumber = (TextView) convertView.findViewById(R.id.txtImageNumber);
            holder.lnrMain = (LinearLayout) convertView.findViewById(R.id.lnrMain);
            row=convertView;
            row.setTag(holder);
        } else {
            holder = (ViewHolderImage) convertView.getTag();
            row=convertView;
        }

        if(objPhotoBook!=null && objPhotoBook.getPhotoPath()!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(objPhotoBook.getPhotoPath());
            // Bitmap lowResBitmap = Bitmap.createScaledBitmap(bitmap,300,300,true);
            holder.image.setImageBitmap(bitmap);
        }
        if(selectedPositions.contains(position)){
            holder.lnrMain.setBackgroundResource(R.drawable.linear_background_color);
        }else{
            holder.lnrMain.setBackgroundResource(R.drawable.linear_background_normal);
        }


        holder.txtImageNumber.setText("Photo " + Integer.toString(position+1));

        return row;
    }
}

class ViewHolderImage{
    ImageView image;
    TextView txtImageNumber;
    LinearLayout lnrMain;
}
