package anarchy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class events implements Listener {

    public void onPlayerJoin (PlayerJoinEvent e) {

        Player p = e.getPlayer();
        p.sendMessage(ChatColor.GREEN + "This is an anarchy server, there are no rules so do what you want!");
        p.sendMessage(ChatColor.RED + "Damaging the servers integrity (e.g: lag machines) may be frowned upon and result in the only softban possibility!");

    }

}
