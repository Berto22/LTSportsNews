package com.example.android.ltsportsnews.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.example.android.ltsportsnews.data.SportsTeams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.R.attr.button;
import static android.R.attr.resource;
import static android.os.Build.VERSION_CODES.M;
import static com.example.android.ltsportsnews.R.id.container;
import static java.security.AccessController.getContext;


public class FavoriteTeams extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public String TAG = FavoriteTeams.class.getSimpleName();

    static final int MYTEAM_LOADER = 1;
    private Cursor mCursor;
    private RecyclerView mRecyclerView;
    private View mView;
    private static ArrayList<String> favTeamArray;
    private MyTeamAdapter adapter;
    private Set<String> favTeam;
    private SharedPreferences preferences;


    public FavoriteTeams() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*preferences = getContext().getSharedPreferences("myTeam", Context.MODE_PRIVATE);

        preferences.registerOnSharedPreferenceChangeListener(this);

        favTeam = preferences.getStringSet("team", new HashSet<String>());

        favTeamArray = new ArrayList<String>(favTeam); */

        //preferences.registerOnSharedPreferenceChangeListener(this);
        favTeamArray = new ArrayList<String>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite_teams, container, false);

        preferences = getContext().getSharedPreferences("myTeam", Context.MODE_PRIVATE);

        preferences.registerOnSharedPreferenceChangeListener(this);

        favTeam = preferences.getStringSet("team", new HashSet<String>());

        favTeamArray = new ArrayList<String>(favTeam);

        return mView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Button button = (Button)view.findViewById(R.id.fav_button);
        FloatingActionButton editButton = (FloatingActionButton)view.findViewById(R.id.edit_fab);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.myTeams_recyclerView);

        adapter = new MyTeamAdapter(getContext(), favTeamArray);
        int numColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numColumns));
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(android.R.id.content, new SelectFavsFragment()).commit();
                Intent intent = new Intent(getActivity(), CustomizeActivity.class );
                getActivity().startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        //Set<String> favTeamUpdate = sharedPreferences.getStringSet(s, new HashSet<String>());
        if(s.equals("team")) {
            sharedPreferences = getContext().getSharedPreferences("myTeam", Context.MODE_PRIVATE);
            favTeam = sharedPreferences.getStringSet(s, new HashSet<String>());

            ArrayList <String> favTeamArray1 = new ArrayList<String>(favTeam);
            //ArrayList<String> updatedData = new ArrayList<String>(favTeamUpdate);

            adapter.updateMyTeamAdapter(favTeamArray1);
        }
        /*favTeam = sharedPreferences.getStringSet(s, new HashSet<String>());

        favTeamArray = new ArrayList<String>(favTeam);
        //ArrayList<String> updatedData = new ArrayList<String>(favTeamUpdate);

        adapter.updateMyTeamAdapter(favTeamArray); */

        //MyTeamAdapter adapter2 = new MyTeamAdapter(getContext(), updatedData);
        //mRecyclerView.setAdapter(adapter2);


    }

    @Override
    public void onResume() {
        super.onResume();
        //preferences.registerOnSharedPreferenceChangeListener(this);
        //getActivity().getSharedPreferences("team", 0).registerOnSharedPreferenceChangeListener(this);
        //getActivity().getSharedPreferences("team", 0).unregisterOnSharedPreferenceChangeListener(this);
        //adapter.updateMyTeamAdapter(favTeamArray);
        favTeam = getActivity().getSharedPreferences("myTeam", Context.MODE_PRIVATE).getStringSet("team", new HashSet<String>());
        favTeamArray = new ArrayList<String>(favTeam);
        Collections.sort(favTeamArray);
        adapter.updateMyTeamAdapter(favTeamArray);


    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
        //getActivity().getSharedPreferences("team", 0).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //getActivity().getSharedPreferences("team", 0).unregisterOnSharedPreferenceChangeListener(this);

    }



    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.MyTeamViewHolder> {
        private ArrayList<String> myTeam;
        private Context mContext;


        public MyTeamAdapter(Context context, ArrayList<String> myTeam) {
            this.mContext = context;
            this.myTeam = myTeam;
        }

        @Override
        public int getItemCount() {
            return myTeam.size();
        }

        @Override
        public MyTeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View viewItem = LayoutInflater.from(mContext).inflate(R.layout.myteam_list_item, parent, false);
            final MyTeamViewHolder viewHolder = new MyTeamViewHolder(viewItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyTeamViewHolder holder, int position) {

            if(!myTeam.isEmpty()) {
                for ( int i = 0; i < myTeam.size(); i++ ) {
                    //holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                    //holder.teamName.setText(myTeam.get(position));
                    String name = myTeam.get(position);
                    SportsTeams sportsTeams = new SportsTeams(name, 2);
                    if(name.equals(getResources().getString(R.string.arizona_cardinals))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo2);
                        holder.teamName.setText("ARI");
                        holder.backdrop.setBackgroundResource(R.color.arizona);
                    } else if (name.equals(getResources().getString(R.string.atlanta_falcons))){
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("ATL");
                        holder.backdrop.setBackgroundResource(R.color.atlanta);
                    } else if(name.equals(getResources().getString(R.string.baltimore_ravens))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("BAL");
                        holder.backdrop.setBackgroundResource(R.color.baltimore);
                    }else if(name.equals(getResources().getString(R.string.buffalo_bills))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("BUF");
                        holder.backdrop.setBackgroundResource(R.color.buffalo);
                    }else if(name.equals(getResources().getString(R.string.carolina_panthers))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("CAR");
                        holder.backdrop.setBackgroundResource(R.color.panthers);
                    }else if(name.equals(getResources().getString(R.string.chicago_bears))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("CHI");
                        holder.backdrop.setBackgroundResource(R.color.chicago);
                    }else if(name.equals(getResources().getString(R.string.cincinnati_bengals))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("CIN");
                        holder.backdrop.setBackgroundResource(R.color.bengals);
                    }else if(name.equals(getResources().getString(R.string.clevaland_browns))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("CLE");
                        holder.backdrop.setBackgroundResource(R.color.browns);
                    }else if(name.equals(getResources().getString(R.string.dallas_cowboys))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("DAL");
                        holder.backdrop.setBackgroundResource(R.color.dallas);
                    }else if(name.equals(getResources().getString(R.string.denver_broncos))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("DEN");
                        holder.backdrop.setBackgroundResource(R.color.denver);
                    }else if(name.equals(getResources().getString(R.string.detroit_lions))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("DET");
                        holder.backdrop.setBackgroundResource(R.color.lions);
                    }else if(name.equals(getResources().getString(R.string.green_bay_packers))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("GB");
                        holder.backdrop.setBackgroundResource(R.color.packers);
                    }else if(name.equals(getResources().getString(R.string.houston_texans))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("HOU");
                        holder.backdrop.setBackgroundResource(R.color.texans);
                    }else if(name.equals(getResources().getString(R.string.indianapolis_colts))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("IND");
                        holder.backdrop.setBackgroundResource(R.color.colts);
                    }else if(name.equals(getResources().getString(R.string.jacksonville_jaguars))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("JAX");
                        holder.backdrop.setBackgroundResource(R.color.jaguars);
                    }else if(name.equals(getResources().getString(R.string.kansas_city_chiefs))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("KS");
                        holder.backdrop.setBackgroundResource(R.color.chiefs);
                    }else if(name.equals(getResources().getString(R.string.los_angeles_chargers))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("LAC");
                        holder.backdrop.setBackgroundResource(R.color.chargers);
                    }else if(name.equals(getResources().getString(R.string.los_angeles_rams))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("LAR");
                        holder.backdrop.setBackgroundResource(R.color.rams);
                    }else if(name.equals(getResources().getString(R.string.miami_dolphins))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("MIA");
                        holder.backdrop.setBackgroundResource(R.color.dolphins);
                    }else if(name.equals(getResources().getString(R.string.minnesota_vikings))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("MIN");
                        holder.backdrop.setBackgroundResource(R.color.vikings);
                    }else if(name.equals(getResources().getString(R.string.new_england_patriots))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("NE");
                        holder.backdrop.setBackgroundResource(R.color.patriots);
                    }else if(name.equals(getResources().getString(R.string.new_orleans_saints))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("NO");
                        holder.backdrop.setBackgroundResource(R.color.saints);
                    }else if(name.equals(getResources().getString(R.string.new_york_giants))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("NYG");
                        holder.backdrop.setBackgroundResource(R.color.giants);
                    }else if(name.equals(getResources().getString(R.string.new_york_jets))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("NYJ");
                        holder.backdrop.setBackgroundResource(R.color.jets);
                    }else if(name.equals(getResources().getString(R.string.oakland_raiders))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("OAK");
                        holder.backdrop.setBackgroundResource(R.color.raider);
                    }else if(name.equals(getResources().getString(R.string.philadelphia_eagles))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("PHI");
                        holder.backdrop.setBackgroundResource(R.color.eagles);
                    }else if(name.equals(getResources().getString(R.string.pittsburgh_steelers))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("PIT");
                        holder.backdrop.setBackgroundResource(R.color.steelers);
                    }else if(name.equals(getResources().getString(R.string.san_francisco_49ers))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("SF");
                        holder.backdrop.setBackgroundResource(R.color.sanfran49ers);
                    }else if(name.equals(getResources().getString(R.string.seattle_seahawks))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("SEA");
                        holder.backdrop.setBackgroundResource(R.color.seahawks);
                    }else if(name.equals(getResources().getString(R.string.tampa_bay_buccanneers))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("TB");
                        holder.backdrop.setBackgroundResource(R.color.bucs);
                    }else if(name.equals(getResources().getString(R.string.tennessee_titans))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("TEN");
                        holder.backdrop.setBackgroundResource(R.color.titans);
                    }else if(name.equals(getResources().getString(R.string.washington_redskins))) {
                        holder.teamLogo.setImageResource(R.drawable.falconslogo3);
                        holder.teamName.setText("WSH");
                        holder.backdrop.setBackgroundResource(R.color.redskins);
                    }

                }
            }

        }

        public class MyTeamViewHolder extends RecyclerView.ViewHolder {
            private TextView teamName;
            private ImageView teamLogo;
            private LinearLayout backdrop;

            public MyTeamViewHolder(View view) {
                super(view);
                teamLogo = (ImageView) view.findViewById(R.id.myTeam_logo);
                teamName = (TextView)view.findViewById(R.id.myTeam_textView);
                backdrop = (LinearLayout) view.findViewById(R.id.my_team_backdrop);
            }
        }

        private void updateMyTeamAdapter(ArrayList<String> myTeam) {
            this.myTeam = myTeam;

            notifyDataSetChanged();
        }


    }


}
