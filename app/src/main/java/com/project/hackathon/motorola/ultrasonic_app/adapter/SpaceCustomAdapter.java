package com.project.hackathon.motorola.ultrasonic_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.hackathon.motorola.ultrasonic_app.InformationSpaceActivity;
import com.project.hackathon.motorola.ultrasonic_app.R;
import com.project.hackathon.motorola.ultrasonic_app.model.Space;

import java.util.ArrayList;

/**
 * Created by matheuscatossi on 5/20/17.
 */

public class SpaceCustomAdapter extends ArrayAdapter<Space> implements View.OnClickListener {

    private ArrayList<Space> dataSet;
    private Context mContext;

    @Override
    public void onClick(View v) {

    }

    private static class ViewHolder {
        TextView tv_name;
        TextView tv_id;
        LinearLayout ll_line;
    }

    public SpaceCustomAdapter(ArrayList<Space> data, Context context) {
        super(context, R.layout.row_item_space, data);

        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Space space = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_space, parent, false);

            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.ll_line = (LinearLayout) convertView.findViewById(R.id.ll_line);

            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tv_id.setText("ID:" + space.getId());
        viewHolder.tv_name.setText("   " + space.getName());

        viewHolder.ll_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, InformationSpaceActivity.class);

                i.putExtra("id", String.valueOf(space.getId()));

                mContext.startActivity(i);
            }
        });

        return convertView;
    }
}
