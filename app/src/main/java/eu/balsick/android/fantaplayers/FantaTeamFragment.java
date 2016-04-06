package eu.balsick.android.fantaplayers;

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

import eu.balsick.android.fantaplayers.comm.data.FantaPlayersDB;
import eu.balsick.android.fantaplayers.comm.data.FantaPlayersStatusAPI;
import eu.balsick.android.fantaplayers.data.FantaTeam;

/**
 * Created by balsi on 06/04/2016.
 */
public class FantaTeamFragment extends Fragment implements APIResultListener{

    private ListView listview;
    private FantaTeam fantateam;
    List<FantaPlayer> players = null;
    Map<String, List<FantaPlayer>> playersByRole = null;
    FantaTeamAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fantateam, container);
        listview = (ListView)view.findViewById(R.id.activity_fantateam_players_listview);
        FantaPlayersStatusAPI api = FantaPlayersStatusAPI.getCurrent(getActivity());
        api.addAPIResultListener(this);
        api.query();
        if (fantateam != null) {
            players = fantateam.getPlayers();
            playersByRole = fantateam.mapByRole();
        }
        adapter = new FantaTeamAdapter(getActivity(), R.layout.single_player_view, players);
        listview.setAdapter(adapter);
        return view;
    }

    @Override
    public void apiServiceComplete(Object object) {
        FantaPlayerAdapter adapter = (FantaPlayerAdapter)listview.getAdapter();
        if (object instanceof List)
            adapter.setDataToPlayers((List<FantaPlayer>)object);
        else if (object instanceof Map)
            adapter.setDataToPlayers((Map)object);
        adapter.notifyDataSetChanged();
    }
}