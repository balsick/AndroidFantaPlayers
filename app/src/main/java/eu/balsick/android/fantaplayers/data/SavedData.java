package eu.balsick.android.fantaplayers.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by balsi on 06/04/2016.
 */
public class SavedData {

    private List<FantaTeam> teams;

    public void addFantaTeam(FantaTeam team) {
        if (teams == null)
            teams = new ArrayList<>();
        teams.add(team);
    }

    public FantaTeam getTeam(int i){
        if (teams != null && teams.size() > i)
            return teams.get(i);
        return null;
    }

    public List<FantaTeam> getTeams() {
        return teams;
    }


}
