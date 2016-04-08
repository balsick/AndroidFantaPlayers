package eu.balsick.android.fantaplayers.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import eu.balsick.android.fantaplayers.R;

/**
 * Created by balsi on 24/03/2016.
 */
public enum Team {
    ATALANTA("atalanta", R.mipmap.atalanta), BOLOGNA("bologna", R.mipmap.bologna),
    CARPI("carpi", R.mipmap.carpi), CHIEVO("chievo", R.mipmap.chievo),
    VERONA("verona", R.mipmap.verona), EMPOLI("empoli", R.mipmap.empoli),
    FIORENTINA("fiorentina", R.mipmap.fiorentina), FROSINONE("frosinone", R.mipmap.frosinone),
    GENOA("genoa", R.mipmap.genoa), SAMPDORIA("sampdoria", R.mipmap.sampdoria),
    INTER("inter", R.mipmap.inter), MILAN("milan", R.mipmap.milan),
    JUVENTUS("juventus", R.mipmap.juventus), TORINO("torino", R.mipmap.torino),
    ROMA("roma", R.mipmap.roma), LAZIO("lazio", R.mipmap.lazio), NAPOLI("napoli", R.mipmap.napoli),
    PALERMO("palermo", R.mipmap.palermo), SASSUOLO("sassuolo", R.mipmap.sassuolo),
    UDINESE("udinese", R.mipmap.udinese);

    final static Map<String, Team> teamsMap;
    static {
        Map<String, Team> map = new HashMap<>();
        for (Team team : values())
            map.put(team.name, team);
        teamsMap = Collections.unmodifiableMap(map);
    }

    String name;
    Integer mipmapId;

    Team(String name, int mipmap){
        this.name = name;
        this.mipmapId = mipmap;
    }

    public Integer getMipmapId() {
        return mipmapId;
    }

    public static Team getTeamByName(String name) {
        return teamsMap.get(name);
    }
}
