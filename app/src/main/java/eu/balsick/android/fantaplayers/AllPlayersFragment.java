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

/**
 * Created by balsi on 03/04/2016.
 */
public class AllPlayersFragment extends Fragment implements APIResultListener{

    private ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players_list, container);
        listview = (ListView)view.findViewById(R.id.activity_players_list_listview);
        listview.setTextFilterEnabled(true);
        List<FantaPlayer> players = FantaPlayersDB.getCurrent(getActivity()).getPlayers();

        FantaPlayersStatusAPI api = FantaPlayersStatusAPI.getCurrent(getActivity());
        api.addAPIResultListener(this);
        api.query();

        final FantaPlayerAdapter adapter = new FantaPlayerAdapter(getActivity(), R.layout.single_player_view, players);
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
            adapter.setDataToPlayers((List<FantaPlayer>)object);
        else if (object instanceof Map)
            adapter.setDataToPlayers((Map)object);
        adapter.notifyDataSetChanged();
    }
}
