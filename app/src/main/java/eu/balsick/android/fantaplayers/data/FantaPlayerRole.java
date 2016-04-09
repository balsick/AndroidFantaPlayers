package eu.balsick.android.fantaplayers.data;

/**
 * Created by balsi on 06/04/2016.
 */
public enum FantaPlayerRole {
    GOALKEEPER("GK"), DEFENDER("DEF"), MIDFIELDER("MF"), ATTACKER("ATT");

    String role;

    private FantaPlayerRole(String role) {
        this.role = role;
    }

    public static FantaPlayerRole get(String role){
        for (FantaPlayerRole item : values())
            if (item.role.equals(role))
                return item;
        return null;
    }

    public String role(){
        return role;
    }
}
