package net.bonn2.slashslime.util;

import net.bonn2.slashslime.SlashSlime;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Messages {

    private static final File messagesFile = new File(SlashSlime.plugin.getDataFolder() + File.separator + "messages.txt");
    private static final Map<String, String> messages = new HashMap<>();
    private static final Map<String, String> defaultMessages = new HashMap<>();

    /**
     * Load messages from messages.txt into a map
     */
    public static void load() {
        // Load default translation keys
        try {
            String[] lines = new String(SlashSlime.plugin.getClass().getResourceAsStream("/messages.txt").readAllBytes()).split("\n");
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    defaultMessages.put(key, value);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Write default file if it doesn't exist
        messagesFile.getParentFile().mkdirs();
        if (!messagesFile.exists()) {
            try (FileWriter writer = new FileWriter(messagesFile, false)) {
                writer.write(new String(SlashSlime.plugin.getClass().getResourceAsStream("/messages.txt").readAllBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Load messages.txt
        StringBuilder messagestxt;
        try {
            messagestxt = new StringBuilder(Files.readString(messagesFile.toPath()));
            String[] lines = messagestxt.toString().split("\n");
            for (String line : lines) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    messages.put(key, value);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Check if messages.txt is missing any keys
        boolean missingKeys = false;
        for (String key : defaultMessages.keySet()) {
            if (!messages.containsKey(key)) {
                missingKeys = true;
                SlashSlime.plugin.getLogger().warning("Missing translation key %s default value will be written to messages.txt".formatted(key));
                messagestxt.append("\n%s=%s".formatted(key, defaultMessages.get(key)));
            }
        }
        // Prune extra newlines and write updated file
        if (missingKeys) {
            try (FileWriter fileWriter = new FileWriter(messagesFile)) {
                fileWriter.write(messagestxt.toString().replaceAll("\n\n", "\n"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Returns a message associated with the given key. If the key is not present in the messages map,
     * the method will attempt to find the key in the defaultMessages map. If the key is not present in
     * the defaultMessages map, the key itself will be returned.
     * @param key The key associated with the desired message
     * @return The message associated with the given key, or the key itself if the key is not found in any map
     */
    public static @NotNull Component get(String key) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(messages.getOrDefault(key, defaultMessages.getOrDefault(key, key)));
    }

    /**
     * Returns a message associated with the given key and applies placeholders. If the key is not present in the messages map,
     * the method will attempt to find the key in the defaultMessages map. If the key is not present in
     * the defaultMessages map, the key itself will be returned.
     * @param key The key associated with the desired message
     * @param placeholders A map where the key is the placeholder and the value is the value that placeholder should be replaced with
     * @return The message associated with the given key, or the key itself if the key is not found in any map
     */
    public static @NotNull Component get(String key, @NotNull Map<String, String> placeholders) {
        String message = messages.getOrDefault(key, defaultMessages.getOrDefault(key, key));
        for (String placeholder : placeholders.keySet()) {
            message = message.replaceAll(placeholder, placeholders.get(placeholder));
        }
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }
}
