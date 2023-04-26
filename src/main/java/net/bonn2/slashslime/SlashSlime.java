package net.bonn2.slashslime;

import net.bonn2.slashslime.commands.Slime;
import net.bonn2.slashslime.config.Config;
import net.bonn2.slashslime.util.Messages;
import net.bonn2.slashslime.util.ModrinthUpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public final class SlashSlime extends JavaPlugin {

    public static SlashSlime plugin;
    public static boolean IS_FOLIA;

    @Override
    public void onEnable() {
        plugin = this;

        // Check if running in Folia
        try {
            Server.class.getDeclaredMethod("getGlobalRegionScheduler");
            IS_FOLIA = true;
        } catch (Throwable ignored) {
            IS_FOLIA = false;
        }

        // Load init data from file
        Config.load();
        Messages.load();

        // Register commands
        Bukkit.getServer().getCommandMap().register("slashslime", new Slime());

        // Check for updates
        if (Config.instance.checkForUpdates) {
            ModrinthUpdateChecker.check(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
