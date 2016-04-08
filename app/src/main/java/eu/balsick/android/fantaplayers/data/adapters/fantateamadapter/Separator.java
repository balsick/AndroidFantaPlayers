package eu.balsick.android.fantaplayers.data.adapters.fantateamadapter;

import eu.balsick.android.fantaplayers.data.FantaPlayerRole;

/**
 * Created by balsi on 07/04/2016.
 */
public class Separator {

    FantaPlayerRole role;

    public Separator(FantaPlayerRole role) {
        this.role = role;
    }

    public String getText() {
        return role.toString() + "S";
    }
}
