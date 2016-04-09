package eu.balsick.android.fantaplayers.comm.data;

import android.content.Context;

import java.util.List;

import eu.balsick.android.fantaplayers.comm.data.FantaPlayersDB;
import eu.balsick.android.fantaplayers.data.FantaPlayer;

/**
 * Created by balsi on 09/04/2016.
 */
public class CachedGeneralData {

    static private List<FantaPlayer> allPlayers;

    public static List<FantaPlayer> getPlayers(Context context){
        if (allPlayers ==null)
            allPlayers = FantaPlayersDB.getCurrent(context).getPlayers();
        return allPlayers;
    }

    public static List<FantaPlayer> getSample(Context context) {
        return FantaPlayersDB.getCurrent(context).getPlayers("player_team = 'juventus'");
    }
}
