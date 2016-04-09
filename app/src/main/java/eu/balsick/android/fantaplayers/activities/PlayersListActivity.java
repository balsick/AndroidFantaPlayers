package eu.balsick.android.fantaplayers.activities;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import eu.balsick.android.fantaplayers.comm.internal.FragmentRequest;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequestListener;
import eu.balsick.android.fantaplayers.fragments.AllPlayersFragment;
import eu.balsick.android.fantaplayers.R;
import eu.balsick.android.fantaplayers.fragments.FantaTeamFragment;
import eu.balsick.android.fantaplayers.comm.internal.FragmentRequestBus;

public class PlayersListActivity extends FragmentActivity {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle;
    private FragmentRequestListener<FantaTeamFragment.AddPlayerToRoleRequest> asAddPlayerToRoleRequestListener;
    private FragmentRequestListener<AllPlayersFragment.PlayerSelectedRequest> asPlayerSelectedRequestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        mPlanetTitles = getResources().getStringArray(R.array.drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        /**
         * registration to eventbus
         */
        FragmentRequestBus.getCurrent().registerListenerForRequest(FantaTeamFragment.AddPlayerToRoleRequest.class, asAddPlayerToRoleRequestListener());

    }

    public FragmentRequestListener<FantaTeamFragment.AddPlayerToRoleRequest> asAddPlayerToRoleRequestListener() {
        if (asAddPlayerToRoleRequestListener == null)
            asAddPlayerToRoleRequestListener = new FragmentRequestListener<FantaTeamFragment.AddPlayerToRoleRequest>() {
                @Override
                public void fragmentRequestHappened(FragmentRequest<FantaTeamFragment.AddPlayerToRoleRequest> request) {
                    Fragment fragment = AllPlayersFragment.newInstance(request, request.type.getRole());
                    swapToFragment(fragment);
                }
            };
        return asAddPlayerToRoleRequestListener;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /** Swaps fragments in the main content view */
    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment;
        if (position == 0)
            fragment = new AllPlayersFragment();
        else if (position == 1)
            fragment = new FantaTeamFragment();
        else
            return;
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
        swapToFragment(fragment);


        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void swapToFragment(Fragment fragment) {
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(fragment.toString())
                .commitAllowingStateLoss();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if (getActionBar() != null)
            getActionBar().setTitle(mTitle);
    }
}
