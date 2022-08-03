package fr.blixow.conodia;

import fr.blixow.conodia.commands.*;
import fr.blixow.conodia.events.BanEvent;
import fr.blixow.conodia.events.MuteEvent;
import fr.blixow.conodia.runnable.UnmuteCheckRunnable;
import fr.blixow.conodia.utils.SanctionManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConodiaSanction extends JavaPlugin {

    private static ConodiaSanction conodiaSanction;
    private FileConfiguration configuration;
    private SanctionManager sanctionManager;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.configuration = getConfig();

        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnmuteCommand());
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("kick").setExecutor(new KickCommand());

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new MuteEvent(), this);
        manager.registerEvents(new BanEvent(), this);

        new UnmuteCheckRunnable().runTaskTimerAsynchronously(this, 20L, 10*20L);
    }

    @Override
    public void onDisable() {}

    @Override
    public void onLoad() {
        ConodiaSanction.conodiaSanction = this;
        this.sanctionManager = new SanctionManager();
    }

    public static ConodiaSanction getConodiaSanction() {
        return conodiaSanction;
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public SanctionManager getSanctionManager() {
        return sanctionManager;
    }

}
