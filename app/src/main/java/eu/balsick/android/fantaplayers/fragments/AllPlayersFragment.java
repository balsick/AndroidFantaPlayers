package eu.balsick.android.fantaplayers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.R;
import eu.balsick.android.fantaplayers.comm.data.CachedGeneralData;
import eu.balsick.android.fantaplayers.comm.data.FantaPlayersDB;
import eu.balsick.android.fantaplayers.comm.data.FantaPlayersStatusAPI;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequest;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequestBus;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequestListener;
import eu.balsick.android.fantaplayers.comm.internal.FragmentResult;
import eu.balsick.android.fantaplayers.data.APIResultListener;
import eu.balsick.android.fantaplayers.data.FantaPlayer;
import eu.balsick.android.fantaplayers.data.FantaPlayerAdapterLoader;
import eu.balsick.android.fantaplayers.data.FantaPlayerFilter;
import eu.balsick.android.fantaplayers.data.FantaPlayerRole;
import eu.balsick.android.fantaplayers.data.adapters.FantaPlayerAdapter;

/**
 * Created by balsick on 03/04/2016.
 */
public class AllPlayersFragment extends android.support.v4.app.Fragment implements APIResultListener, AdapterView.OnItemClickListener {

    private ListView listview;
    private FantaPlayerFilter immutableFilter;
    private FragmentRequest<?> request;
    FantaPlayerAdapterLoader loader;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players_list, container, false);
        listview = (ListView)view.findViewById(R.id.activity_players_list_listview);
        listview.setTextFilterEnabled(true);
        List<FantaPlayer> players = CachedGeneralData.getPlayers(getContext());

        if (immutableFilter != null)
            players = immutableFilter.applyTo(players);
        FantaPlayersStatusAPI api = FantaPlayersStatusAPI.getCurrent(getContext());
        api.addAPIResultListener(this);
        api.query();
        loader = new FantaPlayerAdapterLoader(players);
        final FantaPlayerAdapter adapter = new FantaPlayerAdapter(getContext(), R.layout.single_player_view, loader);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FantaPlayer player = (FantaPlayer) loader.getItem(position);
        if (request != null) {
            FragmentResult result = new FragmentResult(request, player);
            FragmentRequestBus.getCurrent().callback(result);
            getFragmentManager().beginTransaction()
                    .remove(this)
                    .commitAllowingStateLoss();
        }
    }

    public void setRequest(FragmentRequest<?> request) {
        this.request = request;
    }

    public FragmentRequest<?> getRequest() {
        return request;
    }

    public class PlayerSelectedRequest implements FragmentRequestListener.RequestType {

        FantaPlayer player;

        public PlayerSelectedRequest(FantaPlayer player) {
            this.player = player;
        }

        public FantaPlayer getPlayer() {
            return player;
        }
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

    public static AllPlayersFragment newInstance(FragmentRequest<FantaTeamFragment.AddPlayerToRoleRequest> request, FantaPlayerRole role) {
        AllPlayersFragment fragment = new AllPlayersFragment();
        FantaPlayerFilter filter = new FantaPlayerFilter(role);
        fragment.setImmutableFilter(filter);
        fragment.setRequest(request);
        return fragment;
    }

    public void setImmutableFilter(FantaPlayerFilter immutableFilter) {
        this.immutableFilter = immutableFilter;
    }

    public FantaPlayerFilter getImmutableFilter() {
        return immutableFilter;
    }
}
