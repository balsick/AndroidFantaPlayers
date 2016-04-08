package eu.balsick.android.fantaplayers.comm.data;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import eu.balsick.android.fantaplayers.data.FantaPlayer;

/**
 * Created by enrico.balsamo on 28/03/2016.
 */
public class FantaPlayersDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "fanta.db";
    private static final int DATABASE_VERSION = 1;
    public static FantaPlayersDB current;

    private FantaPlayersDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public Cursor getPlayersCursor(String filter) {
        String sql = "select player_name as _id, * from players";
        if (filter != null)
            sql += " where "+filter;
        sql += " order by _id";
        return this.getReadableDatabase().rawQuery(sql, null);
    }
    public List<FantaPlayer> getPlayers() {
        return getPlayers(null);
    }
    public List<FantaPlayer> getPlayers(String filter) {
        List<FantaPlayer> players = new ArrayList<>();
        Cursor cursor = getPlayersCursor(filter);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex("player_name"));
            String team = cursor.getString(cursor.getColumnIndex("player_team"));
            String role = cursor.getString(cursor.getColumnIndex("player_role"));
            FantaPlayer player = new FantaPlayer(name);
            player.setRole(role);
            player.setTeam(team);
            players.add(player);
            cursor.moveToNext();
        }
        close();
        return players;
    }

    public static FantaPlayersDB getCurrent(Context context) {
        if (current == null)
            current = new FantaPlayersDB(context);
        return current;
    }
}
