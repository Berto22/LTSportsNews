package com.example.android.ltsportsnews.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
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

import static android.provider.Contacts.SettingsColumns.KEY;
import static android.system.Os.getsockname;
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
    private static final String SET_KEY = "team";
    //private static final String SET_KEY = context.getResources().getString(R.string.shared_pref_key)
    private static final String PREF_KEY = "myTeam";
    //private static final String PREF_KEY = context.getString(R.string.stringSet_key);

    Set<String> favTeams;
    private Set<String> favSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.customize_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextView done = (TextView) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pref = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        favSet = pref.getStringSet(SET_KEY, new HashSet<String>());


        teamList = new ArrayList<SportsTeams>();

        teamList.add(new SportsTeams(getString(R.string.arizona_cardinals), R.drawable.arizona_cardinals));
        teamList.add(new SportsTeams(getString(R.string.atlanta_falcons), R.drawable.atlanta_falcons));
        teamList.add(new SportsTeams(getString(R.string.baltimore_ravens), R.drawable.baltimore_ravens));
        teamList.add(new SportsTeams(getString(R.string.buffalo_bills), R.drawable.buffalo_bills));
        teamList.add(new SportsTeams(getString(R.string.carolina_panthers), R.drawable.carolina_panthers));
        teamList.add(new SportsTeams(getString(R.string.chicago_bears), R.drawable.chicago_bears));
        teamList.add(new SportsTeams(getString(R.string.cincinnati_bengals), R.drawable.cincinnati_bengals));
        teamList.add(new SportsTeams(getString(R.string.clevaland_browns), R.drawable.cleveland_browns));
        teamList.add(new SportsTeams(getString(R.string.dallas_cowboys), R.drawable.dallas_cowboys));
        teamList.add(new SportsTeams(getString(R.string.denver_broncos), R.drawable.denver_broncos));
        teamList.add(new SportsTeams(getString(R.string.detroit_lions), R.drawable.detroit_lions));
        teamList.add(new SportsTeams(getString(R.string.green_bay_packers), R.drawable.greenbay_packers));
        teamList.add(new SportsTeams(getString(R.string.houston_texans), R.drawable.houston_texans));
        teamList.add(new SportsTeams(getString(R.string.indianapolis_colts), R.drawable.indianapolis_colts));
        teamList.add(new SportsTeams(getString(R.string.jacksonville_jaguars), R.drawable.jacksonville_jaguars));
        teamList.add(new SportsTeams(getString(R.string.kansas_city_chiefs), R.drawable.kansascity_chiefs));
        teamList.add(new SportsTeams(getString(R.string.los_angeles_chargers), R.drawable.losangeles_chargers));
        teamList.add(new SportsTeams(getString(R.string.los_angeles_rams), R.drawable.losangeles_rams));
        teamList.add(new SportsTeams(getString(R.string.miami_dolphins), R.drawable.miami_dolphins));
        teamList.add(new SportsTeams(getString(R.string.minnesota_vikings), R.drawable.minnesota_vikings));
        teamList.add(new SportsTeams(getString(R.string.new_england_patriots), R.drawable.newenglang_patriots));
        teamList.add(new SportsTeams(getString(R.string.new_orleans_saints), R.drawable.neworleans_saints));
        teamList.add(new SportsTeams(getString(R.string.new_york_giants), R.drawable.newyork_giants));
        teamList.add(new SportsTeams(getString(R.string.new_york_jets), R.drawable.newyork_jets));
        teamList.add(new SportsTeams(getString(R.string.oakland_raiders), R.drawable.oakland_raiders));
        teamList.add(new SportsTeams(getString(R.string.philadelphia_eagles), R.drawable.philadelphia_eagles));
        teamList.add(new SportsTeams(getString(R.string.pittsburgh_steelers), R.drawable.pittsburgh_steelers));
        teamList.add(new SportsTeams(getString(R.string.san_francisco_49ers), R.drawable.sanfrancisco_49ers));
        teamList.add(new SportsTeams(getString(R.string.seattle_seahawks), R.drawable.seattle_seahawks));
        teamList.add(new SportsTeams(getString(R.string.tampa_bay_buccanneers), R.drawable.tampabay_bucs));
        teamList.add(new SportsTeams(getString(R.string.tennessee_titans), R.drawable.tennessee_titans));
        teamList.add(new SportsTeams(getString(R.string.washington_redskins), R.drawable.washington_redskins));

        listView = (ListView)findViewById(R.id.customize_listView);
        mSportsTeamAdapter = new SportsTeamAdapter(getApplicationContext(), teamList);
        listView.setAdapter(mSportsTeamAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final ContentValues values = new ContentValues();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SportsTeams selectedTeam = (SportsTeams)adapterView.getItemAtPosition(i);
                String teamName = selectedTeam.getmTeam();

                if(!favSet.contains(teamName)) {
                    favSet.add(teamName);

                }else {
                    favSet.remove(teamName);

                }

                SharedPreferences.Editor editor = pref.edit().clear();
                editor.putStringSet(SET_KEY, favSet);

                editor.commit();

                mSportsTeamAdapter.updateMyTeam(teamList);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home || item.getItemId() == R.id.done){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

