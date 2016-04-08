package eu.balsick.android.fantaplayers.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.AddButton;
import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.Separator;

/**
 * Created by balsick on 07/04/2016.
 */
public class FantaTeamAdapterLoader {

    List<Object> objectsList;
    List<FantaPlayer> players;
    FantaTeam fantaTeam;
    Map<String, List<FantaPlayer>> playersByRole = null;

    public FantaTeamAdapterLoader(FantaTeam fantaTeam) {
        this.fantaTeam = fantaTeam;
        if (fantaTeam != null)
            playersByRole = fantaTeam.mapByRole();
        if (playersByRole != null) {
            players = new ArrayList<>();
            for (List<FantaPlayer> list : playersByRole.values())
                for (FantaPlayer player : list)
                    players.add(player);
        }
    }

    public Object getItem(int position) {
        return listObjects().get(position);
    }

    public List<Object> listObjects() {
        if (objectsList != null)
            return objectsList;
        objectsList = new ArrayList<>();
        for (FantaPlayerRole role : FantaPlayerRole.values()) {
            objectsList.add(new Separator(role));
            if (playersByRole != null) {
                List<FantaPlayer> players = playersByRole.get(role.role());
                if (players != null)
                    for (FantaPlayer player : players)
                        objectsList.add(player);
            }
            objectsList.add(new AddButton(role));
        }
        return objectsList;
    }

    public FantaTeamListItemType getFantaTeamListItemType(int position) {
        return FantaTeamListItemType.valueOf(listObjects().get(position).getClass().getSimpleName());
    }

    public void resetObjects() {
        objectsList = null;
    }

    public List<FantaPlayer> getPlayers() {
        return players;
    }

    public void notifyDataSetChanged() {
        objectsList = null;
        playersByRole = fantaTeam.mapByRole();
    }

    public int getCount() {
        return listObjects().size();
    }
}
