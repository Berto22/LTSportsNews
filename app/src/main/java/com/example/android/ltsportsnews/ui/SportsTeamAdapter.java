package com.example.android.ltsportsnews.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.SportsTeams;

import java.util.ArrayList;

/**
 * Created by berto on 8/4/2017.
 */

public class SportsTeamAdapter extends ArrayAdapter<SportsTeams> {
    private Context mContext;
    private ArrayList<SportsTeams> teams;

    public SportsTeamAdapter(Context context, ArrayList<SportsTeams> teams) {
        super(context, 0, teams);
        this.mContext = context;
        this.teams = teams;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        SportsTeams currentTeam = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customize_list_item, parent, false);
            viewHolder.teamLogo = (ImageView)convertView.findViewById(R.id.logo_image);
            viewHolder.teamName = (TextView)convertView.findViewById(R.id.team_name);
            viewHolder.pickTeam = (ImageView) convertView.findViewById(R.id.picker_star);

            //viewHolder.pickTeam.setOnCheckedChangeListener((CustomizeActivity) mContext);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.teamLogo.setImageResource(currentTeam.getmImageResourceId());

        viewHolder.teamName.setText(currentTeam.getmTeam());

        if(currentTeam.ismSelected())
            viewHolder.pickTeam.setBackgroundResource(R.drawable.check_symbol);

        else
            viewHolder.pickTeam.setBackgroundResource(R.drawable.add_symbol);

        //viewHolder.pickTeam.setChecked(currentTeam.ismSelected());

        /*if(currentTeam.ismSelected()) {
            viewHolder.pickTeam.setChecked(true);
        } */

        return convertView;
    }

    public void updateMyTeam(ArrayList<SportsTeams> teams) {
        this.teams = teams;

        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView teamLogo;
        TextView teamName;
        ImageView pickTeam;
    }
}
