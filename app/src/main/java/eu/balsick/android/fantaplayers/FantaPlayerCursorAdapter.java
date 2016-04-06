package eu.balsick.android.fantaplayers;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by balsi on 24/03/2016.
 */
public class FantaPlayerCursorAdapter extends CursorAdapter {

    public FantaPlayerCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.single_player_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = (TextView)view.findViewById(R.id.fantaplayerName);
        ImageView status = (ImageView)view.findViewById(R.id.fantaplayerStatusIcon);
        ImageView team = (ImageView)view.findViewById(R.id.fantaplayerTeamIcon);
        tv.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        team.setImageResource(Team.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("team"))).mipmapId);
    }
}
