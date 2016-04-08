package eu.balsick.android.fantaplayers.data.adapters.fantateamadapter;

import eu.balsick.android.fantaplayers.data.FantaPlayerRole;

/**
 * Created by balsi on 07/04/2016.
 */
public class AddButton {

    FantaPlayerRole role;

    public AddButton(FantaPlayerRole role) {
        this.role = role;
    }

    public String getText() {
        return role.toString();
    }

    public FantaPlayerRole getRole() {
        return role;
    }
}
