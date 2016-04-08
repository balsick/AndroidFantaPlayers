package eu.balsick.android.fantaplayers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.R;
import eu.balsick.android.fantaplayers.comm.data.FantaPlayersStatusAPI;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequestListener;
import eu.balsick.android.fantaplayers.data.APIResultListener;
import eu.balsick.android.fantaplayers.data.FantaPlayer;
import eu.balsick.android.fantaplayers.data.FantaPlayerRole;
import eu.balsick.android.fantaplayers.data.FantaTeam;
import eu.balsick.android.fantaplayers.data.FantaTeamAdapterLoader;
import eu.balsick.android.fantaplayers.data.FantaTeamListItemType;
import eu.balsick.android.fantaplayers.data.adapters.FantaPlayerAdapter;
import eu.balsick.android.fantaplayers.data.adapters.FantaTeamAdapter;
import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.AddButton;

/**
 * Created by balsick on 06/04/2016.
 */
public class FantaTeamFragment extends Fragment implements APIResultListener, AdapterView.OnItemClickListener {

    private ListView listview;
    private FantaTeam fantateam;
    List<FantaPlayer> players = null;
    Map<String, List<FantaPlayer>> playersByRole = null;
    FantaTeamAdapter adapter;
    FantaTeamAdapterLoader loader;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fantateam, container, false);
        listview = (ListView)view.findViewById(R.id.activity_fantateam_players_listview);
        if (fantateam != null) {
//            players = fantateam.getPlayers();
            playersByRole = fantateam.mapByRole();
        } else {
            players = FantaTeam.getSample(getContext());
            fantateam = new FantaTeam();
            fantateam.setPlayers(players);
            playersByRole = fantateam.mapByRole();
        }
        loader = new FantaTeamAdapterLoader(fantateam);
        adapter = new FantaTeamAdapter(getActivity(), R.layout.single_player_view, loader);
        listview.setAdapter(adapter);

        FantaPlayersStatusAPI.getData(getContext(), this);
        return view;
    }

    @Override
    public void apiServiceComplete(Object object) {
        if (object instanceof List)
            FantaPlayer.setDataToPlayers((List<FantaPlayer>)object, loader.getPlayers());
        else if (object instanceof Map)
            FantaPlayer.setDataToPlayers((Map)object, loader.getPlayers());
        loader.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FantaTeamListItemType itemType = loader.getFantaTeamListItemType(position);
        Object item = loader.getItem(position);
        /**
         * dire all'activity che voglio chiamare un altro frammento,
         * - AllPlayers con il filtro del ruolo se AddButton,
         * - da capire, se FantaPlayer
         */
        FragmentRequestListener.FragmentRequest request = new FragmentRequestListener.FragmentRequest();

        switch (itemType) {
            case Separator:
                break;
            case AddButton:
                FantaPlayerRole role = ((AddButton)item).getRole();

                break;
            case FantaPlayer:
//                ((FantaPlayer)item);
                break;
        }
        FragmentActivity activity = getActivity();
        if (activity instanceof FragmentRequestListener)
            ((FragmentRequestListener)activity).fragmentRequestHappened(request);
    }
}