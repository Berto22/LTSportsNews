package com.example.android.ltsportsnews.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.ltsportsnews.R;
import com.example.android.ltsportsnews.data.ItemsContract;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteTeams.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteTeams#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteTeams extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public String TAG = FavoriteTeams.class.getSimpleName();

    static final int MYTEAM_LOADER = 1;
    private Cursor mCursor;
    private RecyclerView mRecyclerView;
    private View mView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public FavoriteTeams() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteTeams.
     */
    // TODO: Rename and change types and number of parameters
    /*public static FavoriteTeams newInstance(String param1, String param2) {
        FavoriteTeams fragment = new FavoriteTeams();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    } */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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

        getActivity().getLoaderManager().initLoader(MYTEAM_LOADER, null, this);

        Button button = (Button)view.findViewById(R.id.fav_button);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.myTeams_recyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getFragmentManager().beginTransaction().replace(android.R.id.content, new SelectFavsFragment()).commit();
                Intent intent = new Intent(getActivity(), CustomizeActivity.class );
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),
                ItemsContract.TeamsEntry.CONTENT_URI,
                ItemsContract.TeamsEntry.TEAMS_COLUMNS.toArray(new String[]{}),
                null, null, ItemsContract.TeamsEntry.DEFAULT_SORT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        MyTeamAdapter adapter = new MyTeamAdapter(cursor);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseUtils.dumpCursor(cursor);

        /*if (!isAdded()) {
            if (cursor != null) {
                cursor.close();
            }
        }

        mCursor = cursor;
        if (mCursor != null && !mCursor.moveToFirst()) {
            mCursor.close();
            mCursor = null;
        } */

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;

    }

    private class MyTeamAdapter extends RecyclerView.Adapter<MyTeamViewHolder> {
        private Cursor cursor;

        public MyTeamAdapter(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public long getItemId(int position) {
            cursor.moveToPosition(position);
            return  cursor.getLong(cursor.getColumnCount());
        }

        @Override
        public MyTeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater(getArguments()).inflate(R.layout.myteam_list_item, parent, false);

            final MyTeamViewHolder myTeamViewHolder = new MyTeamViewHolder(view);
            return myTeamViewHolder;
        }

        @Override
        public void onBindViewHolder(MyTeamViewHolder holder, int position) {
            cursor.moveToPosition(position);
            String rId = cursor.getString(cursor.getColumnIndex(ItemsContract.TeamsEntry.LOGO_IMAGE_RESOURCE_ID));
            int logoResourceId = Integer.parseInt(rId);
            holder.myTeamLogo.setImageResource(logoResourceId);
            holder.myTeamName.setText(cursor.getString(cursor.getColumnIndex(ItemsContract.TeamsEntry.COLUMN_TEAM_NAME)));

            Log.d(TAG, " row " + rId);
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    public static class MyTeamViewHolder extends RecyclerView.ViewHolder {
        public TextView myTeamName;
        public ImageView myTeamLogo;

        public MyTeamViewHolder(View view) {
            super(view);
            myTeamName = (TextView) view.findViewById(R.id.myTeam_textView);
            myTeamLogo = (ImageView) view.findViewById(R.id.myTeam_logo);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
