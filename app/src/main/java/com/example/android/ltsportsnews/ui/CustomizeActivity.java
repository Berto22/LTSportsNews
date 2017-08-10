package com.example.android.ltsportsnews.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.example.android.ltsportsnews.data.SportsTeams;
import com.example.android.ltsportsnews.remote.FetchNewsUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import timber.log.Timber;

/**
 * Created by berto on 8/3/2017.
 */

public class CustomizeActivity extends AppCompatActivity {
    public String TAG = CustomizeActivity.this.toString();

    private static ListView listView;
    private static SportsTeamAdapter mSportsTeamAdapter;
    private static ArrayList<SportsTeams> teamList;
    private static ArrayList<Integer> myTeam;
    private static Context context;

    Set<String> favTeams;
    //private static String[] myTeam;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_activity);

        favTeams = new HashSet<String>();

        teamList = new ArrayList<SportsTeams>();

        teamList.add(new SportsTeams("Arizona Cardinals", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Atlanta Falcons", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Baltimore Ravens", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Buffalo Bills", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Carolina Panthers", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Chicago Bears", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Cincinnati Bengals", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Cleveland Browns", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Dallas Cowboys", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Denver Broncos", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Detroit Lions", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Green Bay Packers", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Houston Texans", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Indianapolis Colts", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Jacksonville Jaguars", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Kansas City Chiefs", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Los Angeles Chargers", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Los Angeles Rams", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Miami Dolphins", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Minnesota Vikings", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("New England Patriots", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("New Orleans Saints", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("New York Giants", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("New York Jets", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Oakland Raiders", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Philadelphia Eagles", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Pittsburgh Steelers", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("San Francisco 49ers", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Seattle Seahawks", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Tampa Bay Buccaneers", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Tennessee Titans", R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams("Washington Redskins", R.drawable.arizona_cardinals));

        listView = (ListView)findViewById(R.id.customize_listView);
        mSportsTeamAdapter = new SportsTeamAdapter(getApplicationContext(), teamList);
        listView.setAdapter(mSportsTeamAdapter);
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final ContentValues values = new ContentValues();

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myTeam = new ArrayList<Integer>();
                SportsTeams selectedTeam = teamList.get(i);

                int pos = listView.getPositionForView(view);

                String teamName = selectedTeam.getmTeam();
                int logoImageResourceId = selectedTeam.getmImageResourceId();

                //Timber.d("team " + teamName + " logo " + logoImageResourceId);
                Log.d(TAG, "team " + teamName + "logo " + logoImageResourceId + " position " + pos );

                if(selectedTeam.ismSelected() && !myTeam.contains(pos)){
                    selectedTeam.setmSelected(false);
                    //FetchNewsUtil.addMyTeam(context,teamName, logoImageResourceId);
                    values.put(ItemsContract.TeamsEntry.COLUMN_TEAM_NAME, teamName);
                    values.put(ItemsContract.TeamsEntry.LOGO_IMAGE_RESOURCE_ID, logoImageResourceId);
                    getContentResolver().insert(ItemsContract.TeamsEntry.CONTENT_URI, values);
                    myTeam.add(i, pos);


                }
                else {
                    selectedTeam.setmSelected(true);
                    //FetchNewsUtil.removeMyTeam(context,teamName, logoImageResourceId);
                    //myTeam.remove(pos);
                    String stringLogo = Integer.toString(logoImageResourceId);
                    String[] selectionArgs = {teamName};
                    getContentResolver().delete(ItemsContract.TeamsEntry.CONTENT_URI, ItemsContract.TeamsEntry._ID + "=?",
                            selectionArgs);
                }

                teamList.set(i, selectedTeam);

                mSportsTeamAdapter.updateMyTeam(teamList);


            }
        }); */

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key = "team";
                SportsTeams selectedTeam = teamList.get(i);
                String teamName = selectedTeam.getmTeam();

                if(!favTeams.contains(teamName)) {
                    favTeams.add(teamName);
                }else {
                    favTeams.remove(teamName);
                }

                SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putStringSet(key, favTeams);

                editor.commit();

                mSportsTeamAdapter.updateMyTeam(teamList);

                //String[] teamArray = favTeams.toArray(new String[favTeams.size()]);
                Iterator<String> iterator = favTeams.iterator();
                while (iterator.hasNext()) {
                    String tm = iterator.next();
                    Log.d(TAG, "iterator " + tm);
                }

                Set<String> prefSet = pref.getStringSet(key, new HashSet<String>());

                int tmSize = prefSet.size();

                Log.d(TAG, "Set size " + tmSize);


            }
        });


    }

}
