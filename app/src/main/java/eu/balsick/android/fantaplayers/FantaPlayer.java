package eu.balsick.android.fantaplayers;

import java.util.Map;

public class FantaPlayer {

    String name;
    String team;
    String vsteam;
    FantaPlayerStatus status;
    private String role;

    public FantaPlayer(String name) {
        this.name = name;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public FantaPlayerStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getVsTeam() {
        return vsteam;
    }

    public String getTeam() {
        return team;
    }

    public void setVsTeam(String vsTeam) {
        this.vsteam = vsTeam;
    }

    public void setStatus(String status) {
        this.status = FantaPlayerStatus.get(status);
    }

    public void setStatus(FantaPlayerStatus status) {
        this.status = status;
    }

    public void setData(FantaPlayer fantaPlayer) {
        if (fantaPlayer == null)
            return;
        setVsTeam(fantaPlayer.getVsTeam());
        setStatus(fantaPlayer.getStatus());
    }

    public void setData(Map<String, String> data) {
        if (data == null)
            return;
        setVsTeam(data.get("vsteam"));
        setStatus(data.get("status"));
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public enum FantaPlayerStatus {
        BENCH("bench", R.mipmap.bench), PLAYING("playing", R.mipmap.playing), UNAVAILABLE("unavailable", R.mipmap.unavailable);
        String value;
        Integer mipmap;

        FantaPlayerStatus(String value, int mipmapId){
            this.value = value;
            this.mipmap = mipmapId;
        }

        public static FantaPlayerStatus get(String val) {
            for (FantaPlayerStatus status : values())
                if (status.value.equalsIgnoreCase(val))
                    return status;
            return null;
        }
    }

//    public static int getStatusImageResource(FantaPlayerStatus status){
//        switch (status) {
//            case BENCH:
//                return R.drawable.status_bench;
//            case PLAYING:
//                return R.drawable.status_playing;
//            case UNAVAILABLE:
//                return R.drawable.status_unavailable;
//        }
//    }
}
