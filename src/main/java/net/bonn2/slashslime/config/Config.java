package net.bonn2.slashslime.config;

import de.exlll.configlib.*;
import net.bonn2.slashslime.SlashSlime;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;

@Configuration
public class Config {

    @Ignore
    public static Config instance;
    @Ignore
    private static final File configFile = new File(SlashSlime.plugin.getDataFolder() + File.separator + "config.yml");

    @Comment({"Where the message should be displayed", "Valid options: CHAT, ACTION_BAR"})
    public String messageLocation = "CHAT";

    @Comment({"If /slime should cost anything"})
    public boolean cost = true;

    @Comment({"What item checking a slime chunk costs."})
    public ItemStack price = new ItemStack(Material.SLIME_BALL, 1);

    public static void load() {
        instance = new Config();
        YamlConfigurationProperties properties = ConfigLib.BUKKIT_DEFAULT_PROPERTIES.toBuilder()
                .header("""
                        SlashSlime config
                        
                        """)
                .build();
        instance = YamlConfigurations.update(configFile.toPath(), Config.class, properties);
    }
}
