package net.bonn2.slashslime;

import net.bonn2.slashslime.commands.Slime;
import net.bonn2.slashslime.config.Config;
import net.bonn2.slashslime.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SlashSlime extends JavaPlugin {

    public static SlashSlime plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Load init data from file
        Config.load();
        Messages.load();

        // Register commands
        Bukkit.getServer().getCommandMap().register("slashslime", new Slime());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
