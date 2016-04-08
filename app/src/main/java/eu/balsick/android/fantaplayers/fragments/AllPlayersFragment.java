package eu.balsick.android.fantaplayers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.R;
import eu.balsick.android.fantaplayers.comm.data.FantaPlayersDB;
import eu.balsick.android.fantaplayers.comm.data.FantaPlayersStatusAPI;
import eu.balsick.android.fantaplayers.data.APIResultListener;
import eu.balsick.android.fantaplayers.data.FantaPlayer;
import eu.balsick.android.fantaplayers.data.adapters.FantaPlayerAdapter;

/**
 * Created by balsick on 03/04/2016.
 */
public class AllPlayersFragment extends android.support.v4.app.Fragment implements APIResultListener {

    private ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players_list, container, false);
        listview = (ListView)view.findViewById(R.id.activity_players_list_listview);
        listview.setTextFilterEnabled(true);
        List<FantaPlayer> players = FantaPlayersDB.getCurrent(getContext()).getPlayers();

        FantaPlayersStatusAPI api = FantaPlayersStatusAPI.getCurrent(getContext());
        api.addAPIResultListener(this);
        api.query();

        final FantaPlayerAdapter adapter = new FantaPlayerAdapter(getContext(), R.layout.single_player_view, players);
        listview.setAdapter(adapter);
        EditText et = ((EditText)view.findViewById(R.id.search_key));
        if (et != null)
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    adapter.filter(s.toString());
                }
            });
        return view;
    }

    @Override
    public void apiServiceComplete(Object object) {
        FantaPlayerAdapter adapter = (FantaPlayerAdapter)listview.getAdapter();
        if (object instanceof List)
            FantaPlayer.setDataToPlayers((List<FantaPlayer>)object, adapter.getPlayers());
        else if (object instanceof Map)
            FantaPlayer.setDataToPlayers((Map)object, adapter.getPlayers());
        adapter.notifyDataSetChanged();
    }
}
