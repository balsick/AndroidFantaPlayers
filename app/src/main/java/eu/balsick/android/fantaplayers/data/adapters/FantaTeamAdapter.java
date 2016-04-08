package eu.balsick.android.fantaplayers.data.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import eu.balsick.android.fantaplayers.data.FantaPlayer;
import eu.balsick.android.fantaplayers.R;
import eu.balsick.android.fantaplayers.data.FantaTeamAdapterLoader;
import eu.balsick.android.fantaplayers.data.FantaTeamListItemType;
import eu.balsick.android.fantaplayers.data.Team;
import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.AddButton;
import eu.balsick.android.fantaplayers.data.adapters.fantateamadapter.Separator;

/**
 * Created by balsick on 06/04/2016.
 */
public class FantaTeamAdapter extends FantaPlayerAdapter {

    FantaTeamAdapterLoader loader;

    public FantaTeamAdapter(Context context, int playerLayoutResourceId, FantaTeamAdapterLoader loader) {
        super(context, playerLayoutResourceId, null);
        assert loader != null;
        this.loader = loader;
    }

    @Override
    public int getCount() {
        return loader.getCount();
    }

    @Override
    public Object getItem(int position) {
        return loader.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Object object = getItem(position);
        AbstractHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(getLayoutForItem(object), parent, false);
            holder = getHolder(object).init(row);
            row.setTag(holder);
        } else {
            holder = (AbstractHolder)row.getTag();
            if (holder.itemType.getId() != getItemViewType(position)) {
                if (convertView.getParent() != null && convertView.getParent().equals(parent))
                    parent.removeView(convertView);
                return getView(position, null, parent);
            }
        }
        holder.set(object);
        return row;
    }

    @Override
    public int getItemViewType(int position) {
        return FantaTeamListItemType.valueOf(getItem(position).getClass().getSimpleName()).getId();
    }

    private int getLayoutForItem(Object object) {
        FantaTeamListItemType type = FantaTeamListItemType.valueOf(object.getClass().getSimpleName());
        if (type == FantaTeamListItemType.AddButton)
            return R.layout.fragment_fantateam_addbutton;
        if (type == FantaTeamListItemType.Separator)
            return R.layout.fragment_fantateam_separator;
        return standardItemLayoutId;
    }


    /** HOLDERS SECTION **/

    private static AbstractHolder getHolder(Object object) {
        return Holder.valueOf(object.getClass().getSimpleName()).newHolder();
    }

    private enum Holder {
        AddButton(AddButtonHolder.class), Separator(SeparatorHolder.class), FantaPlayer(FantaPlayerHolder.class);

        Class<? extends AbstractHolder> aClass;
        FantaTeamListItemType itemType;
        Holder(Class<? extends AbstractHolder> c) {
            this.aClass = c;
            itemType = FantaTeamListItemType.valueOf(Holder.this.name());
        }

        AbstractHolder newHolder() {
            AbstractHolder holder = null;
            try {
                holder = aClass.newInstance();
                holder.itemType = itemType;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return holder;
        }
    }

    private static abstract class AbstractHolder{

        public FantaTeamListItemType itemType;

        public abstract AbstractHolder init(View row);

        public abstract void set(Object object);
    }

    class SeparatorHolder extends AbstractHolder {

        TextView tv;
        @Override
        public AbstractHolder init(View row) {
            tv = (TextView) row.findViewById(R.id.fantateamtvseparator);
            return this;
        }

        @Override
        public void set(Object object) {
            assert object != null;
            Separator separator = (Separator) object;
            this.tv.setText(separator.getText());
        }
    }

    static class AddButtonHolder extends AbstractHolder {

        Button button;
        @Override
        public AbstractHolder init(View row) {
            button = (Button)row.findViewById(R.id.fantateambuttonadd);
            return this;
        }

        @Override
        public void set(Object object) {
            assert object != null;
            AddButton button = (AddButton) object;
            String text = "ADD " +button.getText();
            this.button.setText(text);
        }
    }

    static class FantaPlayerHolder extends AbstractHolder {

        ImageView statusIcon;
        TextView name;
        ImageView vsTeamIcon;
        ImageView teamIcon;

        @Override
        public AbstractHolder init(View row) {
            statusIcon = (ImageView)row.findViewById(R.id.fantaplayerStatusIcon);
            vsTeamIcon = (ImageView)row.findViewById(R.id.fantaplayerTeamVsIcon);
            teamIcon = (ImageView)row.findViewById(R.id.fantaplayerTeamIcon);
            name = (TextView)row.findViewById(R.id.fantaplayerName);
            return this;
        }

        @Override
        public void set(Object object) {
            assert object != null;
            FantaPlayer player = (FantaPlayer)object;
            name.setText(player.getName());
            if (player.getTeam() != null)
                teamIcon.setImageResource(Team.getTeamByName(player.getTeam()).getMipmapId());
            if (player.getVsTeam() != null)
                vsTeamIcon.setImageResource(Team.getTeamByName(player.getVsTeam()).getMipmapId());
            if (player.getStatus() != null)
                statusIcon.setImageResource(player.getStatus().getMipmapId());
        }
    }
}
