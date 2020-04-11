package commands;

import anarchy.util.sethomeutils;
import core.util.C;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

public class home extends JavaPlugin implements Listener {

    //@EventHandler
    //public void
    C c = new C();
    private File file = new File(getDataFolder(), "Homes.yml");

    YamlConfiguration homes = YamlConfiguration.loadConfiguration(file);

    private FileConfiguration config = getConfig();

    private HashMap<Player, Integer> cooldownTimeHome;
    private HashMap<Player, BukkitRunnable> cooldownTaskHome;

    private HashMap<Player, Integer> cooldownTimeSetHome;
    private HashMap<Player, BukkitRunnable> cooldownTaskSetHome;

    private static final String prefixError = C.Red + "[" + C.Green + "Anarchy" + C.Red + "]" + C.Gray;

    private sethomeutils utils = new sethomeutils(this);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equals("sethome")) {

            if (sender instanceof ConsoleCommandSender) {
                getLogger().log(Level.WARNING, "This message can only be executed by a player!");

            }else if (sender instanceof  Player) {

                Player player = (Player) sender;

                if (config.getBoolean("sethome-command-delay")) {

                    int coolDown = config.getInt("sethome-time-delay");
                    if (cooldownTimeSetHome.containsKey(player)) {

                        player.sendMessage(prefixError + "You must wait for" + C.Red + cooldownTimeSetHome.get(player) + C.Green + " seconds!");


                    }else {

                        setPlayerHome(player);

                    }

                }



            }else {

                sender.sendMessage(prefixError + "Invalid command protocol!");
            }

        } else if (command.getName().equals("home")) {

            if (sender instanceof ConsoleCommandSender) {

                getLogger().log(Level.WARNING, "Only players can execute this command!");

            } else if (sender instanceof Player) {

                Player player = (Player) sender;

                if (utils.homeIsNull(player)) {

                    player.sendMessage(prefixError + "You do not have a home set! Please do /sethome");

                } else {

                    if (config.getBoolean("home-command-delay")) {

                        int coolDown = config.getInt("home-time-delay");

                        if (cooldownTimeHome.containsKey(player)) {

                            player.sendMessage(prefixError + "You must wait for" + C.Red + cooldownTimeHome + C.Gray + " seconds!");

                        } else
                            sendPlayerHome(player);
                            setCoolDownTimeHome(player, 5);

                    } else {

                        sendPlayerHome(player);

                    }

                }




            }

        } return false;
    }

    public void hOnEnable() {

        config.options().copyDefaults(true);
        saveDefaultConfig();

        try {

            config.save(getDataFolder() + File.separator + "config.yml");

        }catch (IOException e) {

            e.printStackTrace();

        }

        if(!file.exists()) {

            saveHomesFile();

        }

        cooldownTimeHome = new HashMap<>();
        cooldownTaskHome = new HashMap<>();

        cooldownTimeSetHome = new HashMap<>();
        cooldownTaskSetHome = new HashMap<>();

    }

    public void saveHomesFile() {

        try {

            homes.save(file);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    void sendPlayerHome(Player player) {
        utils.sendHome(player);
        if (config.getBoolean("play-warp-sound")) {
            player.playSound(utils.getHomeLocation(player), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        }
        String strFormatted = config.getString("teleport-message").replace("%player%", player.getDisplayName());
        if (config.getBoolean("show-teleport-message")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', strFormatted));
        }
    }

    void setPlayerHome(Player player) {
        // --- Set player's home by saving it to a file (Homes.yml)
        utils.setHome(player);
        // --- If option 'show-sethome-message' is enabled in config, show the player the 'sethome-message' as defined in 'config.yml'
        if (config.getBoolean("show-sethome-message")) {
            // --- Create instance of a String that is formatted from the 'config.yml' file.
            String strFormatted = config.getString("sethome-message").replace("%player%", player.getDisplayName());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', strFormatted));
        }
    }

    void setCoolDownTimeHome(Player player, int coolDown) {
        cooldownTimeHome.put(player, coolDown);
        cooldownTaskHome.put(player, new BukkitRunnable() {
            public void run() {
                cooldownTimeHome.put(player, cooldownTimeHome.get(player) - 1);
                if (cooldownTimeHome.get(player) == 0) {
                    cooldownTimeHome.remove(player);
                    cooldownTaskHome.remove(player);
                    cancel();
                }
            }
        });
        cooldownTaskHome.get(player).runTaskTimer(this, 20, 20);
    }
    void setCoolDownTimeSetHome(Player player, int coolDown) {
        cooldownTimeSetHome.put(player, coolDown);
        cooldownTaskSetHome.put(player, new BukkitRunnable() {
            public void run() {
                cooldownTimeSetHome.put(player, cooldownTimeSetHome.get(player) - 1);
                if (cooldownTimeSetHome.get(player) == 0) {
                    cooldownTimeSetHome.remove(player);
                    cooldownTaskSetHome.remove(player);
                    cancel();
                }
            }
        });
        cooldownTaskSetHome.get(player).runTaskTimer(this, 20, 20);
    }

    private home instance;


    public void setHome(Player player) {
        instance.homes.set("Homes." + player.getUniqueId().toString() + ".X", player.getLocation().getX());
        instance.homes.set("Homes." + player.getUniqueId().toString() + ".Y", player.getLocation().getY());
        instance.homes.set("Homes." + player.getUniqueId().toString() + ".Z", player.getLocation().getZ());
        instance.homes.set("Homes." + player.getUniqueId().toString() + ".Yaw", player.getLocation().getYaw());
        instance.homes.set("Homes." + player.getUniqueId().toString() + ".Pitch", player.getLocation().getPitch());
        instance.homes.set("Homes." + player.getUniqueId().toString() + ".World", player.getLocation().getWorld().getName());
        instance.saveHomesFile();
    }

    public void sendHome(Player player) {
        player.teleport(getHomeLocation(player));
    }

    public Location getHomeLocation(Player player) {

        return new Location(
                Bukkit.getWorld(instance.homes.getString("Homes." + player.getUniqueId().toString() + ".World")),
                instance.homes.getDouble("Homes." + player.getUniqueId().toString() + ".X"),
                instance.homes.getDouble("Homes." + player.getUniqueId().toString() + ".Y"),
                instance.homes.getDouble("Homes." + player.getUniqueId().toString() + ".Z"),
                instance.homes.getLong("Homes." + player.getUniqueId().toString() + ".Yaw"),
                instance.homes.getLong("Homes." + player.getUniqueId().toString() + ".Pitch")
        );
    }


    public boolean homeIsNull(Player player) {
        return instance.homes.getString("Homes." + player.getUniqueId()) == null;
    }



}
