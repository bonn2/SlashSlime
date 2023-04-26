package net.bonn2.slashslime;

import net.bonn2.slashslime.commands.Slime;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SlashSlime extends JavaPlugin {

    public static SlashSlime plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // Register commands
        Bukkit.getServer().getCommandMap().register("slashslime", new Slime());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
