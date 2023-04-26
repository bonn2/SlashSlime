package net.bonn2.slashslime.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.bonn2.slashslime.SlashSlime;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModrinthUpdateChecker {

    private static final String SLUG = "slashslime";

    public static void check(@NotNull JavaPlugin plugin) {
        if (SlashSlime.IS_FOLIA) {
            Bukkit.getServer().getAsyncScheduler().runNow(plugin, scheduledTask -> updateCheck(plugin));
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    updateCheck(plugin);
                }
            }.runTaskAsynchronously(plugin);
        }
    }

    protected static void updateCheck(@NotNull JavaPlugin plugin) {
        try {
            // Get the latest version from modrinth
            URL url = new URL("https://api.modrinth.com/v2/project/%s/version".formatted(SLUG));
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("get", "application/json");
            InputStream responseStream = connection.getInputStream();
            JsonObject latestVersionJson = new GsonBuilder().create().fromJson(new String(responseStream.readAllBytes()), JsonArray.class).get(0).getAsJsonObject();

            // Split versions into comparable parts
            List<Integer> currentVersion = new ArrayList<>(3);
            for (String versionNumber : plugin.getDescription().getVersion().split("\\.")) {
                currentVersion.add(Integer.valueOf(versionNumber));
            }

            List<Integer> latestVersion = new ArrayList<>(3);
            for (String versionNumber : latestVersionJson.get("version_number").getAsString().split("\\.")) {
                latestVersion.add(Integer.valueOf(versionNumber));
            }

            // Compare versions and print message if newer version is available
            if (latestVersion.get(0) > currentVersion.get(0)
                    || (latestVersion.get(1) > currentVersion.get(1) && Objects.equals(latestVersion.get(0), currentVersion.get(0)))
                    || (latestVersion.get(2) > currentVersion.get(2) && Objects.equals(latestVersion.get(0), currentVersion.get(0)) && Objects.equals(latestVersion.get(1), currentVersion.get(1)))) {
                plugin.getLogger().warning("An update is available %s -> %s".formatted(plugin.getDescription().getVersion(), latestVersionJson.get("version_number").getAsString()));
                plugin.getLogger().warning("Latest Changelog:\n%s\n%s\n\nGet it here: https://modrinth.com/plugin/%s/version/%s".formatted(
                                latestVersionJson.get("name").getAsString(),
                                latestVersionJson.get("changelog").getAsString(),
                                SLUG,
                                latestVersionJson.get("version_number").getAsString()
                        )
                );
            }
        } catch (IOException exception) {
            plugin.getLogger().warning("Failed to check for updates!");
        }
    }
}
