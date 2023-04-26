package net.bonn2.slashslime;

import de.exlll.configlib.ConfigLib;
import de.exlll.configlib.YamlConfigurationProperties;
import org.bukkit.plugin.java.JavaPlugin;

public final class SlashSlime extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        YamlConfigurationProperties bukkitDefaultProperties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
