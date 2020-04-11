package core;

import anarchy.events;
import commands.home;
import commands.tp;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class main extends JavaPlugin implements Listener {

    home h = new home();



    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new events(), this);
        getCommand("sethome").setExecutor(new home());
        getCommand("home").setExecutor(new home());
        getCommand("tpa").setExecutor(new tp());
        getCommand("tpaccept").setExecutor(new tp());
        getCommand("tpdeny").setExecutor(new tp());

        h.hOnEnable();



        Bukkit.getServer().getLogger().info("Loaded Plugin: Anarchy Core!");

    }

    public void onDisable() {

        Bukkit.getServer().getLogger().info("Disabled plugin: Anarchy Core!");

    }

}

