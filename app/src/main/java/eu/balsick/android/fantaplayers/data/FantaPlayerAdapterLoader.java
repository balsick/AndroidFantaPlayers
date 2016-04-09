package eu.balsick.android.fantaplayers.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.AddButton;
import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.Separator;

/**
 * Created by balsi on 09/04/2016.
 */
public class FantaPlayerAdapterLoader {

    List<FantaPlayer> players;

    FantaPlayerAdapterLoader() {
    }

    public FantaPlayerAdapterLoader(List<FantaPlayer> players) {
        this.players = players;
    }

    public Object getItem(int position) {
        return players.get(position);
    }


    public List<FantaPlayer> getPlayers() {
        return players;
    }

    public int getCount() {
        return players.size();
    }
}
