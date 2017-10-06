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
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
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
    private String team1;
    private static final String SET_KEY = "team";
    private static final String PREF_KEY = "myTeam";


    public FavoriteTeams() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favTeamArray = new ArrayList<String>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite_teams, container, false);

        //preferences = getContext().getSharedPreferences("myTeam", Context.MODE_PRIVATE);
        preferences = getContext().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);

        preferences.registerOnSharedPreferenceChangeListener(this);

        //favTeam = preferences.getStringSet("team", new HashSet<String>());
        favTeam = preferences.getStringSet(SET_KEY, new HashSet<String>());

        favTeamArray = new ArrayList<String>(favTeam);

        return mView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.myTeams_recyclerView);

        adapter = new MyTeamAdapter(getContext(), favTeamArray);
        int numColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numColumns));

        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(SET_KEY)) {
            sharedPreferences = getContext().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
            favTeam = sharedPreferences.getStringSet(s, new HashSet<String>());

            ArrayList <String> favTeamArray1 = new ArrayList<String>(favTeam);
            adapter.updateMyTeamAdapter(favTeamArray1);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        favTeam = getActivity().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE).getStringSet(SET_KEY, new HashSet<String>());
        favTeamArray = new ArrayList<String>(favTeam);
        Collections.sort(favTeamArray);
        adapter.updateMyTeamAdapter(favTeamArray);

    }

    @Override
    public void onPause() {
        super.onPause();
        preferences.unregisterOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                    String name = myTeam.get(position);
                    if(name.equals(getResources().getString(R.string.arizona_cardinals))) {
                        holder.teamLogo.setImageResource(R.drawable.arizona_cardinals);
                        holder.teamName.setText("ARI");
                        holder.teamName.setContentDescription(getString(R.string.arizona_cardinals));
                        holder.backdrop.setBackgroundResource(R.color.arizona);
                    } else if (name.equals(getResources().getString(R.string.atlanta_falcons))){
                        holder.teamLogo.setImageResource(R.drawable.atlanta_falcons);
                        holder.teamName.setText("ATL");
                        holder.teamName.setContentDescription(getString(R.string.atlanta_falcons));
                        holder.backdrop.setBackgroundResource(R.color.atlanta);
                    } else if(name.equals(getResources().getString(R.string.baltimore_ravens))) {
                        holder.teamLogo.setImageResource(R.drawable.baltimore_ravens);
                        holder.teamName.setText("BAL");
                        holder.teamName.setContentDescription(getString(R.string.baltimore_ravens));
                        holder.backdrop.setBackgroundResource(R.color.baltimore);
                    }else if(name.equals(getResources().getString(R.string.buffalo_bills))) {
                        holder.teamLogo.setImageResource(R.drawable.buffalo_bills);
                        holder.teamName.setText("BUF");
                        holder.teamName.setContentDescription(getString(R.string.buffalo_bills));
                        holder.backdrop.setBackgroundResource(R.color.buffalo);
                    }else if(name.equals(getResources().getString(R.string.carolina_panthers))) {
                        holder.teamLogo.setImageResource(R.drawable.carolina_panthers);
                        holder.teamName.setText("CAR");
                        holder.teamName.setContentDescription(getString(R.string.carolina_panthers));
                        holder.backdrop.setBackgroundResource(R.color.panthers);
                    }else if(name.equals(getResources().getString(R.string.chicago_bears))) {
                        holder.teamLogo.setImageResource(R.drawable.chicago_bears);
                        holder.teamName.setText("CHI");
                        holder.teamName.setContentDescription(getString(R.string.chicago_bears));
                        holder.backdrop.setBackgroundResource(R.color.chicago);
                    }else if(name.equals(getResources().getString(R.string.cincinnati_bengals))) {
                        holder.teamLogo.setImageResource(R.drawable.cincinnati_bengals);
                        holder.teamName.setText("CIN");
                        holder.teamName.setContentDescription(getString(R.string.cincinnati_bengals));
                        holder.backdrop.setBackgroundResource(R.color.bengals);
                    }else if(name.equals(getResources().getString(R.string.clevaland_browns))) {
                        holder.teamLogo.setImageResource(R.drawable.cleveland_browns);
                        holder.teamName.setText("CLE");
                        holder.teamName.setContentDescription(getString(R.string.clevaland_browns));
                        holder.backdrop.setBackgroundResource(R.color.browns);
                    }else if(name.equals(getResources().getString(R.string.dallas_cowboys))) {
                        holder.teamLogo.setImageResource(R.drawable.dallas_cowboys);
                        holder.teamName.setText("DAL");
                        holder.teamName.setContentDescription(getString(R.string.dallas_cowboys));
                        holder.backdrop.setBackgroundResource(R.color.dallas);
                    }else if(name.equals(getResources().getString(R.string.denver_broncos))) {
                        holder.teamLogo.setImageResource(R.drawable.denver_broncos);
                        holder.teamName.setText("DEN");
                        holder.teamName.setContentDescription(getString(R.string.denver_broncos));
                        holder.backdrop.setBackgroundResource(R.color.denver);
                    }else if(name.equals(getResources().getString(R.string.detroit_lions))) {
                        holder.teamLogo.setImageResource(R.drawable.detroit_lions);
                        holder.teamName.setText("DET");
                        holder.teamName.setContentDescription(getString(R.string.detroit_lions));
                        holder.backdrop.setBackgroundResource(R.color.lions);
                    }else if(name.equals(getResources().getString(R.string.green_bay_packers))) {
                        holder.teamLogo.setImageResource(R.drawable.greenbay_packers);
                        holder.teamName.setText("GB");
                        holder.teamName.setContentDescription(getString(R.string.green_bay_packers));
                        holder.backdrop.setBackgroundResource(R.color.packers);
                    }else if(name.equals(getResources().getString(R.string.houston_texans))) {
                        holder.teamLogo.setImageResource(R.drawable.houston_texans);
                        holder.teamName.setText("HOU");
                        holder.teamName.setContentDescription(getString(R.string.houston_texans));
                        holder.backdrop.setBackgroundResource(R.color.texans);
                    }else if(name.equals(getResources().getString(R.string.indianapolis_colts))) {
                        holder.teamLogo.setImageResource(R.drawable.indianapolis_colts);
                        holder.teamName.setText("IND");
                        holder.teamName.setContentDescription(getString(R.string.indianapolis_colts));
                        holder.backdrop.setBackgroundResource(R.color.colts);
                    }else if(name.equals(getResources().getString(R.string.jacksonville_jaguars))) {
                        holder.teamLogo.setImageResource(R.drawable.jacksonville_jaguars);
                        holder.teamName.setText("JAX");
                        holder.teamName.setContentDescription(getString(R.string.jacksonville_jaguars));
                        holder.backdrop.setBackgroundResource(R.color.jaguars);
                    }else if(name.equals(getResources().getString(R.string.kansas_city_chiefs))) {
                        holder.teamLogo.setImageResource(R.drawable.kansascity_chiefs);
                        holder.teamName.setText("KS");
                        holder.teamName.setContentDescription(getString(R.string.kansas_city_chiefs));
                        holder.backdrop.setBackgroundResource(R.color.chiefs);
                    }else if(name.equals(getResources().getString(R.string.los_angeles_chargers))) {
                        holder.teamLogo.setImageResource(R.drawable.losangeles_chargers);
                        holder.teamName.setText("LAC");
                        holder.teamName.setContentDescription(getString(R.string.los_angeles_chargers));
                        holder.backdrop.setBackgroundResource(R.color.chargers);
                    }else if(name.equals(getResources().getString(R.string.los_angeles_rams))) {
                        holder.teamLogo.setImageResource(R.drawable.losangeles_rams);
                        holder.teamName.setText("LAR");
                        holder.teamName.setContentDescription(getString(R.string.los_angeles_rams));
                        holder.backdrop.setBackgroundResource(R.color.rams);
                    }else if(name.equals(getResources().getString(R.string.miami_dolphins))) {
                        holder.teamLogo.setImageResource(R.drawable.miami_dolphins);
                        holder.teamName.setText("MIA");
                        holder.teamName.setContentDescription(getString(R.string.miami_dolphins));
                        holder.backdrop.setBackgroundResource(R.color.dolphins);
                    }else if(name.equals(getResources().getString(R.string.minnesota_vikings))) {
                        holder.teamLogo.setImageResource(R.drawable.minnesota_vikings);
                        holder.teamName.setText("MIN");
                        holder.teamName.setContentDescription(getString(R.string.minnesota_vikings));
                        holder.backdrop.setBackgroundResource(R.color.vikings);
                    }else if(name.equals(getResources().getString(R.string.new_england_patriots))) {
                        holder.teamLogo.setImageResource(R.drawable.newenglang_patriots);
                        holder.teamName.setText("NE");
                        holder.teamName.setContentDescription(getString(R.string.new_england_patriots));
                        holder.backdrop.setBackgroundResource(R.color.patriots);
                    }else if(name.equals(getResources().getString(R.string.new_orleans_saints))) {
                        holder.teamLogo.setImageResource(R.drawable.neworleans_saints);
                        holder.teamName.setText("NO");
                        holder.teamName.setContentDescription(getString(R.string.new_orleans_saints));
                        holder.backdrop.setBackgroundResource(R.color.saints);
                    }else if(name.equals(getResources().getString(R.string.new_york_giants))) {
                        holder.teamLogo.setImageResource(R.drawable.newyork_giants);
                        holder.teamName.setText("NYG");
                        holder.teamName.setContentDescription(getString(R.string.new_york_giants));
                        holder.backdrop.setBackgroundResource(R.color.giants);
                    }else if(name.equals(getResources().getString(R.string.new_york_jets))) {
                        holder.teamLogo.setImageResource(R.drawable.newyork_jets);
                        holder.teamName.setText("NYJ");
                        holder.teamName.setContentDescription(getString(R.string.new_york_jets));
                        holder.backdrop.setBackgroundResource(R.color.jets);
                    }else if(name.equals(getResources().getString(R.string.oakland_raiders))) {
                        holder.teamLogo.setImageResource(R.drawable.oakland_raiders);
                        holder.teamName.setText("OAK");
                        holder.teamName.setContentDescription(getString(R.string.oakland_raiders));
                        holder.backdrop.setBackgroundResource(R.color.raider);
                    }else if(name.equals(getResources().getString(R.string.philadelphia_eagles))) {
                        holder.teamLogo.setImageResource(R.drawable.philadelphia_eagles);
                        holder.teamName.setText("PHI");
                        holder.teamName.setContentDescription(getString(R.string.philadelphia_eagles));
                        holder.backdrop.setBackgroundResource(R.color.eagles);
                    }else if(name.equals(getResources().getString(R.string.pittsburgh_steelers))) {
                        holder.teamLogo.setImageResource(R.drawable.pittsburgh_steelers);
                        holder.teamName.setText("PIT");
                        holder.teamName.setContentDescription(getString(R.string.pittsburgh_steelers));
                        holder.backdrop.setBackgroundResource(R.color.steelers);
                    }else if(name.equals(getResources().getString(R.string.san_francisco_49ers))) {
                        holder.teamLogo.setImageResource(R.drawable.sanfrancisco_49ers);
                        holder.teamName.setText("SF");
                        holder.teamName.setContentDescription(getString(R.string.san_francisco_49ers));
                        holder.backdrop.setBackgroundResource(R.color.sanfran49ers);
                    }else if(name.equals(getResources().getString(R.string.seattle_seahawks))) {
                        holder.teamLogo.setImageResource(R.drawable.seattle_seahawks);
                        holder.teamName.setText("SEA");
                        holder.teamName.setContentDescription(getString(R.string.seattle_seahawks));
                        holder.backdrop.setBackgroundResource(R.color.seahawks);
                    }else if(name.equals(getResources().getString(R.string.tampa_bay_buccanneers))) {
                        holder.teamLogo.setImageResource(R.drawable.tampabay_bucs);
                        holder.teamName.setText("TB");
                        holder.teamName.setContentDescription(getString(R.string.tampa_bay_buccanneers));
                        holder.backdrop.setBackgroundResource(R.color.bucs);
                    }else if(name.equals(getResources().getString(R.string.tennessee_titans))) {
                        holder.teamLogo.setImageResource(R.drawable.tennessee_titans);
                        holder.teamName.setText("TEN");
                        holder.teamName.setContentDescription(getString(R.string.tennessee_titans));
                        holder.backdrop.setBackgroundResource(R.color.titans);
                    }else if(name.equals(getResources().getString(R.string.washington_redskins))) {
                        holder.teamLogo.setImageResource(R.drawable.washington_redskins);
                        holder.teamName.setText("WSH");
                        holder.teamName.setContentDescription(getString(R.string.washington_redskins));
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
