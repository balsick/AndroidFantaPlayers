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
    public String role(){
        return role;
    }
}
