package net.bonn2.slashslime.config;

import de.exlll.configlib.*;
import net.bonn2.slashslime.SlashSlime;

import java.io.File;

@Configuration
public class Config {

    @Ignore
    public static Config instance;
    @Ignore
    private static final File configFile = new File(SlashSlime.plugin.getDataFolder() + File.separator + "config.yml");

    @Comment({"Where the message should be displayed", "Valid options: CHAT, ACTION_BAR"})
    public String messageLocation = "CHAT";

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
