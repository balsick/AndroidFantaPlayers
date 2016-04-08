package eu.balsick.android.fantaplayers.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.comm.data.FantaPlayersDB;

/**
 * Created by balsi on 06/04/2016.
 */
public class FantaTeam {

    private List<FantaPlayer> players;

    private Map<String, List<FantaPlayer>> mapByRole;

    public List<FantaPlayer> getPlayers() {
        return players;
    }

    public void addPlayer(FantaPlayer player) {
        if (this.players == null)
            players = new ArrayList<>();
        players.add(player);
    }

    public Map<String, List<FantaPlayer>> mapByRole() {
        if (mapByRole != null)
            return mapByRole;
        mapByRole = new HashMap<>();
        for (FantaPlayer player : players){
            if (!mapByRole.containsKey(player.getRole()))
                mapByRole.put(player.getRole(), new ArrayList<FantaPlayer>());
            mapByRole.get(player.getRole()).add(player);
        }
        return mapByRole;
    }

    public static List<FantaPlayer> getSample(Context context) {
        return FantaPlayersDB.getCurrent(context).getPlayers("player_team = 'juventus'");
    }

    public void setPlayers(List<FantaPlayer> players) {
        this.players = players;
    }
}
