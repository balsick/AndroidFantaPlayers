package eu.balsick.android.fantaplayers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.R;
import eu.balsick.android.fantaplayers.comm.data.FantaPlayersStatusAPI;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequest;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequestBus;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequestCallbackHandler;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequestListener;
import eu.balsick.android.fantaplayers.comm.internal.FragmentResult;
import eu.balsick.android.fantaplayers.data.APIResultListener;
import eu.balsick.android.fantaplayers.data.FantaPlayer;
import eu.balsick.android.fantaplayers.data.FantaPlayerRole;
import eu.balsick.android.fantaplayers.data.FantaTeam;
import eu.balsick.android.fantaplayers.data.FantaTeamAdapterLoader;
import eu.balsick.android.fantaplayers.data.FantaTeamListItemType;
import eu.balsick.android.fantaplayers.data.adapters.FantaTeamAdapter;
import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.AddButton;

/**
 * Created by balsick on 06/04/2016.
 */
public class FantaTeamFragment extends Fragment implements APIResultListener, AdapterView.OnItemClickListener, FragmentRequestCallbackHandler {

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
        loader.setSubscriber(listview);
        adapter = new FantaTeamAdapter(getActivity(), R.layout.single_player_view, loader);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

        FantaPlayersStatusAPI.getData(getContext(), this);
        return view;
    }

    public void notifyDataSetChanged(){
        loader.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void apiServiceComplete(Object object) {
        if (object instanceof List)
            FantaPlayer.setDataToPlayers((List<FantaPlayer>)object, loader.getPlayers());
        else if (object instanceof Map)
            FantaPlayer.setDataToPlayers((Map)object, loader.getPlayers());
        notifyDataSetChanged();
    }

    @Override
    public void handle(FragmentResult result_) {
        if (result_ == null)
            return;
        FragmentRequest request_ = result_.getRequest();
        if (request_.type instanceof FantaTeamFragment.AddPlayerToRoleRequest){
//            FantaTeamFragment.AddPlayerToRoleRequest req = (FantaTeamFragment.AddPlayerToRoleRequest) request_.type;
//            FantaPlayerRole role = req.getRole();
            FantaPlayer player = (FantaPlayer) result_.getResult();
            loader.addPlayer(player);
            notifyDataSetChanged();
        }
    }

    public class AddPlayerToRoleRequest implements FragmentRequestListener.RequestType {

        FantaPlayerRole role;

        public AddPlayerToRoleRequest(FantaPlayerRole role) {
            this.role = role;
        }

        public FantaPlayerRole getRole() {
            return role;
        }
    }

    public class RemovePlayerRequest implements FragmentRequestListener.RequestType {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FantaTeamListItemType itemType = loader.getFantaTeamListItemType(position);
        Object item = loader.getItem(position);
        FragmentRequest request = null;

        switch (itemType) {
            case Separator:
                break;
            case AddButton:
                FantaPlayerRole role = ((AddButton)item).getRole();
                request = new FragmentRequest<>(new AddPlayerToRoleRequest(role));
                break;
            case FantaPlayer:
//                ((FantaPlayer)item);
                break;
        }
        FragmentRequestBus.getCurrent().request(request, this);
    }
}