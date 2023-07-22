package games.negative.apexcore.core;

import games.negative.alumina.message.Message;
import games.negative.apexcore.ApexCore;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Enum for handling the messages.yml file
 */
public enum Locale {

    /*
     * Generic Messages
     */
    GENERIC_PROFILE_ERROR("&cAn error occurred while attempting to retrieve your profile."),

    GENERIC_PROFILE_ERROR_OTHER("&cAn error occurred while attempting to retrieve the profile of the target user."),

    GENERIC_PLAYER_NOT_FOUND("&cThe player &e%player% &cwas not found."),

    IGNORE_HELP(
            "&8&m----------------------------------------",
            "&6&lIgnore &7- Block messages from players you don't want to see.",
            "&7/ignore add <player> &8- &7Ignore a player.",
            "&7/ignore remove <player> &8- &7Unignore a player.",
            "&7/ignore list &8- &7List all ignored players.",
            "&7/ignore clear &8- &7Clear all ignored players.",
            "&8&m----------------------------------------"
    ),

    IGNORE_REMOVE_MULTIPLE("&aYou have removed &e%amount% &ausers from your ignored list."),

    IGNORE_REMOVE_SINGLE("&aYou have removed &e%player% &afrom your ignored list."),

    IGNORE_ALREADY_IGNORING("&cYou are already ignoring &e%player%&c."),

    IGNORE_ADD("&aYou are now ignoring &e%player%&a."),

    IGNORE_NOT_IGNORING("&cYou are not ignoring &e%player%&c."),

    MESSAGE_NO_MESSAGE("&cYou must provide a message to send."),

    MESSAGE_CANNOT_SEND_IGNORING("&cYou cannot send a message to &e%player%&c because you are ignoring them."),

    MESSAGE_CANNOT_SEND_IGNORED("&cYou cannot send a message to &e%player%&c because they are ignoring you."),

    MESSAGE_CANNOT_SEND_DISABLED("&cYou cannot send a message to &e%player%&c because they have messages disabled."),

    MESSAGE_SENDER("&7To &r%player%&7: &f%message%"),
    MESSAGE_RECEIVER("&7From &r%player%&7: &f%message%"),

    REPLY_CANNOT_SEND("&cYou have not sent a message to anyone yet."),

    MESSAGE_TOGGLE_SOUND_ENABLED("&aYou will now receive a sound when you receive a message."),
    MESSAGE_TOGGLE_SOUND_DISABLED("&cYou will no longer receive a sound when you receive a message."),

    MESSAGE_TOGGLE_ENABLED("&aYou will now receive messages."),
    MESSAGE_TOGGLE_DISABLED("&cYou will no longer receive messages."),

    LAST_SEEN("&7%player% &8- &7Last seen: &f%date%"),

    FIRST_SEEN("&aYou have first joined the server: &f%date% &7(%ago%)"),

    SERVER_INFO(
            "&8&m----------------------------------------",
            "&6&lServer Info &7- Information about the server.",
            "&7Server Start: &f%server_start%",
            "&7Server Uptime: &f%uptime%",
            " ",
            "&7Unique Players: &f%unique%",
            " ",
            "&7TPS: &f%tps%",
            " ",
            "&7World Size: &f%size%",
            "&8&m----------------------------------------"
    ),

    FIRST_JOIN("&aWelcome %player% to the server! &7(#%id%)"),
    FIRST_JOIN_PERSONALIZED("&7&o(You are the %id-fancy% player to join the server!)"),
    ;

    private final String[] defMessage;
    private Message message;

    Locale(String... defMessage) {
        this.defMessage = defMessage;
    }

    public static void init(@NotNull ApexCore plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        validateFile(plugin, file);

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        boolean changed = false;
        for (Locale entry : values()) {
            if (config.isSet(entry.name())) continue;

            List<String> message = List.of(entry.defMessage);
            config.set(entry.name(), message);
            changed = true;
        }

        if (changed) saveFile(plugin, file, config);

        for (Locale entry : values()) {
            entry.message = Message.of(config.getStringList(entry.name()));
        }
    }

    private static void saveFile(@NotNull ApexCore plugin, @NotNull File file, @NotNull FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save messages.yml file!");
        }
    }

    private static void validateFile(@NotNull ApexCore plugin, @NotNull File file) {
        if (!file.exists()) {
            boolean dirSuccess = file.getParentFile().mkdirs();
            if (dirSuccess) plugin.getLogger().info("Created new plugin directory file!");

            try {
                boolean success = file.createNewFile();
                if (!success) return;

                plugin.getLogger().info("Created messages.yml file!");
            } catch (IOException e) {
                plugin.getLogger().severe("Could not create messages.yml file!");
            }
        }
    }


    public void send(CommandSender sender) {
        message.send(sender);
    }

    public void broadcast() {
        message.broadcast();
    }

    public Message replace(String placeholder, String replacement) {
        return message.replace(placeholder, replacement);
    }
}
