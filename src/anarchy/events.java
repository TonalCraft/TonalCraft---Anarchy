package anarchy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class events implements Listener {

    public void onPlayerJoin (PlayerJoinEvent e) {

        Player p = e.getPlayer();
        boolean hasjoined = p.hasPlayedBefore();

        if(!hasjoined) {

            e.setJoinMessage(ChatColor.LIGHT_PURPLE + "Joined >> " + ChatColor.WHITE + p.getName() + " has joined for the first time!");

        }else {

            e.setJoinMessage(ChatColor.GREEN + p.getName() + " logged on!");

        }

        p.sendMessage(ChatColor.GREEN + "This is an anarchy server, there are no rules so do what you want!");
        p.sendMessage(ChatColor.RED + "Damaging the servers integrity (e.g: lag machines) may be frowned upon and result in the only softban possibility!");

    }

    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        String p = e.getPlayer().getDisplayName();
        String msg = e.getMessage();

        Bukkit.getServer().broadcastMessage(ChatColor.GRAY + p + msg);

    }



}
