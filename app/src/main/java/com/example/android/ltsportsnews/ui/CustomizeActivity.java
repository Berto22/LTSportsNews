package com.example.android.ltsportsnews.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
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

import static android.system.Os.remove;

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
    private SharedPreferences pref;
    private static final String KEY = "team";

    Set<String> favTeams;
    private Set<String> favSet;
    //private static String[] myTeam;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_activity);

        pref = getSharedPreferences("myTeam", MODE_PRIVATE);
        favSet = pref.getStringSet(KEY, new HashSet<String>());

        //favTeams = new HashSet<String>();

        teamList = new ArrayList<SportsTeams>();

        teamList.add(new SportsTeams(getString(R.string.arizona_cardinals), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.atlanta_falcons), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.baltimore_ravens), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.buffalo_bills), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.carolina_panthers), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.chicago_bears), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.cincinnati_bengals), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.clevaland_browns), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.dallas_cowboys), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.denver_broncos), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.detroit_lions), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.green_bay_packers), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.houston_texans), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.indianapolis_colts), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.jacksonville_jaguars), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.kansas_city_chiefs), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.los_angeles_chargers), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.los_angeles_rams), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.miami_dolphins), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.minnesota_vikings), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.new_england_patriots), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.new_orleans_saints), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.new_york_giants), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.new_york_jets), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.oakland_raiders), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.philadelphia_eagles), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.pittsburgh_steelers), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.san_francisco_49ers), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.seattle_seahawks), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.tampa_bay_buccanneers), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.tennessee_titans), R.drawable.falconslogo3));
        teamList.add(new SportsTeams(getString(R.string.washington_redskins), R.drawable.falconslogo3));

        listView = (ListView)findViewById(R.id.customize_listView);
        mSportsTeamAdapter = new SportsTeamAdapter(getApplicationContext(), teamList);
        listView.setAdapter(mSportsTeamAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final ContentValues values = new ContentValues();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String key = "team";
                //SportsTeams selectedTeam = teamList.get(i);
                SportsTeams selectedTeam = (SportsTeams)adapterView.getItemAtPosition(i);
                String teamName = selectedTeam.getmTeam();

                //pref = getSharedPreferences("myTeam", MODE_PRIVATE);
                //selectedTeam.setmSelected(true);
                //favSet = pref.getStringSet(key, new HashSet<String>());



                if(!favSet.contains(teamName)) {
                    favSet.add(teamName);
                    //selectedTeam.setmSelected(true);

                }else {
                    favSet.remove(teamName);
                    //selectedTeam.setmSelected(false);
                }

                //favSet.addAll(favTeams);

                //mSportsTeamAdapter.updateMyTeam(teamList);


                //SharedPreferences pref = getSharedPreferences("myTeam", MODE_PRIVATE);

                SharedPreferences.Editor editor = pref.edit().clear();
                editor.putStringSet(KEY, favSet);

                editor.commit();




                mSportsTeamAdapter.updateMyTeam(teamList);

                //String[] teamArray = favTeams.toArray(new String[favTeams.size()]);
                Iterator<String> iterator = favSet.iterator();
                while (iterator.hasNext()) {
                    String tm = iterator.next();
                    Log.d(TAG, "iterator " + tm);
                }

                Set<String> prefSet = pref.getStringSet(KEY, new HashSet<String>());

                int tmSize = prefSet.size();

                Log.d(TAG, "Set size " + tmSize);


            }
        });


    }

    /*@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("team", favTeams);
    } */
}

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
