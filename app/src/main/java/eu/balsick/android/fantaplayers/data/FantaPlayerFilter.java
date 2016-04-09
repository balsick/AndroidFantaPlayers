package eu.balsick.android.fantaplayers.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import eu.balsick.android.fantaplayers.data.adapters.FantaPlayerAdapter;

/**
 * Created by balsi on 09/04/2016.
 */
public class FantaPlayerFilter {

    String nameLike;
    List<FantaPlayerRole> roles;
    String teamLike;

    Pattern namePattern;
    Pattern teamPattern;

    public FantaPlayerFilter(FantaPlayerRole role) {
        this.roles = new ArrayList<>();
        roles.add(role);
    }

    private void init() {
        if (nameLike != null)
            namePattern = Pattern.compile("(?i)(?:\\w+ +)*?" + nameLike + "(?-i).*");

        if (teamLike != null)
            teamPattern = Pattern.compile("(?i)(?:\\w+ +)*?" + teamLike + "(?-i).*");
    }

    public boolean appliesTo(FantaPlayer player){
        Boolean result = null;
        if (namePattern != null && !namePattern.matcher(player.getName()).matches())
            result = false;
        if (result == null && teamPattern != null && !teamPattern.matcher(player.getTeam()).matches())
            result = false;
        if (result == null && roles != null && !roles.contains(FantaPlayerRole.get(player.getRole())))
            result = false;
        if (result == null)
            return true;
        return result;
    }

    public List<FantaPlayer> applyTo(List<FantaPlayer> players){
        List<FantaPlayer> result = new ArrayList<>();
        for (FantaPlayer player : players){
            if (appliesTo(player))
                result.add(player);
        }
        return result;
    }
}
