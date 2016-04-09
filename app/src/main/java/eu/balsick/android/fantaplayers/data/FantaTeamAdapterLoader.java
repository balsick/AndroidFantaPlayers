package eu.balsick.android.fantaplayers.data;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.AddButton;
import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.Separator;

/**
 * Created by balsick on 07/04/2016.
 */
public class FantaTeamAdapterLoader extends FantaPlayerAdapterLoader {

    List<Object> objectsList;
    FantaTeam fantaTeam;
    Map<String, List<FantaPlayer>> playersByRole = null;
    ListView subscriber;

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

    public void setSubscriber(ListView subscriber) {
        this.subscriber = subscriber;
    }

    public Object getItem(int position) {
        return listObjects().get(position);
    }

    private List<Object> listObjects() {
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

    public void notifyDataSetChanged() {
        objectsList = null;
        fantaTeam.notifyDataSetChanged();
        playersByRole = fantaTeam.mapByRole();
//        if (subscriber != null)
//            subscriber.getAdapter()
    }

    public int getCount() {
        return listObjects().size();
    }

    public void addPlayer(FantaPlayer player) {
        fantaTeam.addPlayer(player);
    }
}
