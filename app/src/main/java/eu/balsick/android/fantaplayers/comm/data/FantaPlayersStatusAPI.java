package eu.balsick.android.fantaplayers.comm.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.balsick.android.fantaplayers.data.APIResultListener;
import eu.balsick.android.fantaplayers.data.FantaPlayer;
import eu.balsick.android.fantaplayers.R;
import eu.balsick.android.fantaplayers.fragments.FantaTeamFragment;

/**
 * Created by balsick on 02/04/2016.
 */
public class FantaPlayersStatusAPI implements Response.Listener<String>, Response.ErrorListener {

    static final String charset = java.nio.charset.StandardCharsets.UTF_8.name();

    private static FantaPlayersStatusAPI current;

    String url;
    String json;
    Context context;
    List<APIResultListener> apiResultListeners;

    List<FantaPlayer> players;

    public static FantaPlayersStatusAPI getCurrent(Context context){
        if (current == null)
            current = new FantaPlayersStatusAPI(context);
        return current;
    }

    private FantaPlayersStatusAPI(Context context) {
        url = context.getString(R.string.api_base_url);
        this.context = context;
    }

    public void addAPIResultListener(APIResultListener listener) {
        if (apiResultListeners == null)
            apiResultListeners = new ArrayList<>();
        apiResultListeners.add(listener);
    }

    public void setRequest(List<FantaPlayer> players) {
        this.players = players;
    }

    public void query(){
        String additionalQuery = "";
        String comma = "";
        if (players != null){
            additionalQuery = "?players=";
            for (FantaPlayer player : players){
                additionalQuery += player.getName() + comma;
                comma = ",";
            }
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url + additionalQuery, this, this);
        queue.add(stringRequest);
    }

    public void applyStatusesToPlayers(List<FantaPlayer> players) {
        query();
    }

    public void onResponse(String response) {
        if (response == null)
            return;
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> map = new Gson().fromJson(response, type);
//        Map<String, Map<String, String>> result = (Map<String, Map<String, String>>)map.get("result");
//        Map<String, FantaPlayer> playersMap = new HashMap<>();
//        for (FantaPlayer player : players){
//            playersMap.put(player.getName().toUpperCase(), player);
//        }
//        for (String key : result.keySet()){
//            Map<String, String> info = result.get(key);
//            String status = info.get("status");
//            String vsteam = info.get("vsteam");
//            FantaPlayer player = playersMap.get(key.toUpperCase());
//            player.setStatus(status);
//            player.setVsTeam(vsteam);
//        }
        if (context != null && context instanceof APIResultListener)
            ((APIResultListener)context).apiServiceComplete(map.get("result"));//players == null ? map.get("result") : players);
        if (apiResultListeners != null)
            for (APIResultListener listener : apiResultListeners)
                listener.apiServiceComplete(map.get("result"));//players == null ? map.get("result") : players);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String abc = null;
    }

    public void getData(APIResultListener listener) {
        addAPIResultListener(listener);
        query();
    }

    public static void getData(Context context, APIResultListener listener) {
        getCurrent(context).getData(listener);

    }
}
