package core.util;

import org.bukkit.event.Listener;

public class RankManager implements Listener {

    /* List of all server ranks as of 04/09/2020
        To create new ranks, add them to the enum
     */

    private String rank;

    public enum Rank {
        Member(null),
        VIP(null),
        Ultra(null),
        MVP(null),
        King(null),
        Titan(null),
        God(null),
        Godplus(null),
        Godplusplus(null),
        Sponsor(null),
        Youtube(null),
        Helper(null),
        Mod(null),
        Admin(null),
        Manager(null),
        Owner(null),
        Dev(null),
        Builder(null);

        private String prefix;

        Rank(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }

        public boolean hasPrefix() {
            return prefix != null;

        }
    }

}
