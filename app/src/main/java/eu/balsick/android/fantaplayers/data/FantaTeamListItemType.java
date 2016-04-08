package eu.balsick.android.fantaplayers.data;

/**
 * Created by balsi on 07/04/2016.
 */
public enum FantaTeamListItemType {
    AddButton(1), Separator(2), FantaPlayer(3);
    int id;

    FantaTeamListItemType(int i) {
        this.id = i;
    }

    public int getId() {
        return id;
    }
}
