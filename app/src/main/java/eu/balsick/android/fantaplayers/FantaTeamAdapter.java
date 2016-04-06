package eu.balsick.android.fantaplayers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import eu.balsick.android.fantaplayers.data.FantaPlayerRole;

/**
 * Created by balsi on 06/04/2016.
 */
public class FantaTeamAdapter extends FantaPlayerAdapter {

    Map<String, List<FantaPlayer>> playersByRole = null;

    public FantaTeamAdapter(Context context, int layoutResourceId, List<FantaPlayer> players) {
        super(context, layoutResourceId, players);
    }

    @Override
    public int getCount() {
        int count = 4;
        if (playersByRole != null) {
            for (String role : playersByRole.keySet())
                count += playersByRole.get(role).size();
        }
        return count;
    }

    private class Separator {

        FantaPlayerRole role;

        public Separator(FantaPlayerRole role) {
            this.role = role;
        }
    }

    private class AddButton{

        FantaPlayerRole role;

        public AddButton(FantaPlayerRole role) {
            this.role = role;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        int count = position;
        if (playersByRole != null) {
            for (FantaPlayerRole role : FantaPlayerRole.values()) {
                if (count == 0)
                    return new Separator(role);
                count--;        // this is because of the header
                List<FantaPlayer> players = playersByRole.get(role.role());
                if (players != null && count < players.size())
                    return players.get(count);
                if (players != null)
                    count -= players.size();
                if (count == 0)
                    return new AddButton(role);
                count--;        //this is because of the "add" button
            }
        }
        for (FantaPlayerRole role : FantaPlayerRole.values()) {
            if (count == 0)
                return new Separator(role);
            count--;        // this is because of the header
            if (count == 0)
                return new AddButton(role);
            count--;        //this is because of the "add" button
        }
        return null;

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FantaPlayerHolder holder;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FantaPlayerHolder();
            holder.statusIcon = (ImageView)row.findViewById(R.id.fantaplayerStatusIcon);
            holder.vsTeamIcon = (ImageView)row.findViewById(R.id.fantaplayerTeamVsIcon);
            holder.teamIcon = (ImageView)row.findViewById(R.id.fantaplayerTeamIcon);
            holder.name = (TextView)row.findViewById(R.id.fantaplayerName);
            row.setTag(holder);
        } else {
            holder = (FantaPlayerHolder)row.getTag();
        }

//        row.setOnClickListener((item)->{});
        Object object = getItem(position);
        FantaPlayer player = null;
        if (object instanceof FantaPlayer)
            player = (FantaPlayer)object;
        else if (object instanceof AddButton){
            //TODO
        } else if (object instanceof Separator) {
            //TODO
        }
        if (holder.object != null && holder.object.getClass().equals(object.getClass())) {
            holder.name.setText(player.getName());
            if (player.getTeam() != null)
                holder.teamIcon.setImageResource(Team.getTeamByName(player.getTeam()).mipmapId);
            if (player.getVsTeam() != null)
                holder.vsTeamIcon.setImageResource(Team.getTeamByName(player.getVsTeam()).mipmapId);
            if (player.getStatus() != null)
                holder.statusIcon.setImageResource(player.getStatus().mipmap);
        } else {
            //TODO
        }
        holder.object = object;
        return row;
    }

    static class FantaPlayerHolder {
        Object object;
        ImageView statusIcon;
        TextView name;
        ImageView vsTeamIcon;
        ImageView teamIcon;
    }
}
