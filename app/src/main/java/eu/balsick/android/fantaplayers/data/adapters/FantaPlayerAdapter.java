package eu.balsick.android.fantaplayers.data.adapters;

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

import eu.balsick.android.fantaplayers.data.FantaPlayer;
import eu.balsick.android.fantaplayers.R;
import eu.balsick.android.fantaplayers.data.FantaPlayerAdapterLoader;
import eu.balsick.android.fantaplayers.data.FantaTeamAdapterLoader;
import eu.balsick.android.fantaplayers.data.Team;

/**
 * Created by balsi on 24/03/2016.
 */
public class FantaPlayerAdapter extends BaseAdapter implements Filterable {

    Context context;
    int standardItemLayoutId;
    List<FantaPlayer> filteredPlayers;
    private Filter filter;
    FantaPlayerAdapterLoader loader;

    public FantaPlayerAdapter(Context context, int standardItemLayoutId, FantaPlayerAdapterLoader loader) {
        super();
        this.context = context;
        this.standardItemLayoutId = standardItemLayoutId;
        assert loader != null;
        this.loader = loader;
    }
    @Override
    public int getCount() {
        if (filteredPlayers != null)
            return filteredPlayers.size();
        return loader.getCount();
    }

    @Override
    public Object getItem(int position) {
        if (filteredPlayers != null)
            return filteredPlayers.get(position);
        return loader.getItem(position);
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
            row = inflater.inflate(standardItemLayoutId, parent, false);

            holder = new FantaPlayerHolder();
            holder.statusIcon = (ImageView)row.findViewById(R.id.fantaplayerStatusIcon);
            holder.vsTeamIcon = (ImageView)row.findViewById(R.id.fantaplayerTeamVsIcon);
            holder.teamIcon = (ImageView)row.findViewById(R.id.fantaplayerTeamIcon);
            holder.name = (TextView)row.findViewById(R.id.fantaplayerName);

            row.setTag(holder);
        } else {
            holder = (FantaPlayerHolder)row.getTag();
        }

        FantaPlayer player = (FantaPlayer) getItem(position);
        holder.name.setText(player.getName());
        if (player.getTeam() != null)
            holder.teamIcon.setImageResource(Team.getTeamByName(player.getTeam()).getMipmapId());
        if (player.getVsTeam() != null)
            holder.vsTeamIcon.setImageResource(Team.getTeamByName(player.getVsTeam()).getMipmapId());
        if (player.getStatus() != null)
            holder.statusIcon.setImageResource(player.getStatus().getMipmapId());
        return row;
    }

    public void filter(String s) {
        getFilter().filter(s);
    }

    @Override
    public Filter getFilter() {
        if (filter != null)
            return filter;
        return filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.values = null;
                    results.count = 0;
                } else {
                    List<FantaPlayer> filteredPlayers = new ArrayList<>();
                    final Pattern pattern = Pattern.compile("(?i)(?:\\w+ +)*?" + constraint + "(?-i).*");

                    for (FantaPlayer player : loader.getPlayers()) {
                        if (pattern.matcher(player.getName()).matches() || pattern.matcher(player.getTeam()).matches())
                            filteredPlayers.add(player);
                    }
                    Collections.sort(filteredPlayers, new Comparator<FantaPlayer>() {
                        @Override
                        public int compare(FantaPlayer lhs, FantaPlayer rhs) {
                            return lhs.getName().compareTo(rhs.getName());
                        }
                    });
                    results.values = filteredPlayers;
                    results.count = filteredPlayers.size();
                }
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredPlayers = (List<FantaPlayer>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public List<FantaPlayer> getPlayers() {
        return loader.getPlayers();
    }

    static class FantaPlayerHolder {
        ImageView statusIcon;
        TextView name;
        ImageView vsTeamIcon;
        ImageView teamIcon;
    }
}
