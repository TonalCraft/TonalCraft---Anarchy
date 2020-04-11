package core;

import anarchy.events;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {

    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new events(), this);

        Bukkit.getServer().getLogger().info("Loaded Plugin: Anarchy Core!");

    }

    public void onDisable() {

        Bukkit.getServer().getLogger().info("Disabled plugin: Anarchy Core!");

    }

}
