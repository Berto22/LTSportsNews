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
import android.support.v4.app.Fragment;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;
import com.example.android.ltsportsnews.data.SportsTeams;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.R.attr.button;
import static android.R.attr.resource;
import static com.example.android.ltsportsnews.R.id.container;


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

        preferences = getContext().getSharedPreferences("myTeam", Context.MODE_PRIVATE);

        preferences.registerOnSharedPreferenceChangeListener(this);

        favTeam = preferences.getStringSet("team", new HashSet<String>());

        favTeamArray = new ArrayList<String>(favTeam);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorite_teams, container, false);

        return mView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = (Button)view.findViewById(R.id.fav_button);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.myTeams_recyclerView);

        adapter = new MyTeamAdapter(getContext(), favTeamArray);
        int numColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numColumns));
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        button.setOnClickListener(new View.OnClickListener() {
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
        favTeam = sharedPreferences.getStringSet(s, new HashSet<String>());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSharedPreferences("team", 0).unregisterOnSharedPreferenceChangeListener(this);
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
                    holder.teamName.setText(myTeam.get(position));
                }
            }

        }

        public class MyTeamViewHolder extends RecyclerView.ViewHolder {
            private TextView teamName;

            public MyTeamViewHolder(View view) {
                super(view);
                teamName = (TextView)view.findViewById(R.id.myTeam_textView);
            }
        }
    }


}
