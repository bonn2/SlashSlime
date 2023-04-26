package net.bonn2.slashslime.commands;

import net.bonn2.slashslime.SlashSlime;
import net.bonn2.slashslime.config.Config;
import net.bonn2.slashslime.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Slime extends Command implements PluginIdentifiableCommand {

    public Slime() {
        super(
                "slime",
                "Check if the chunk you are in is a slime chunk.",
                "/slime",
                new ArrayList<>(0)
        );
        setPermission("slashslime.use");
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return new ArrayList<>(0);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (sender instanceof Player player) {
            switch (Config.instance.messageLocation.toUpperCase(Locale.ROOT)) {
                case "CHAT" ->
                        player.sendMessage(
                                player.getLocation().getChunk().isSlimeChunk() ?
                                        Messages.get("is-slime-chunk") :
                                        Messages.get("not-slime-chunk"));
                case "ACTION_BAR" ->
                        player.sendActionBar(
                                player.getLocation().getChunk().isSlimeChunk() ?
                                        Messages.get("is-slime-chunk") :
                                        Messages.get("not-slime-chunk"));
            }
        } else {
            sender.sendMessage(Messages.get("only-players"));
        }
        return true;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return SlashSlime.plugin;
    }
}
