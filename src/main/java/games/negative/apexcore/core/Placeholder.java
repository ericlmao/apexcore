package games.negative.apexcore.core;

import games.negative.alumina.message.Message;
import games.negative.apexcore.ApexCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Represents simple placeholders for messages
 */
public enum Placeholder {

    SERVER_START("Fri. July 1st, 2021"),
    DEFAULT_WORLD("world"),

    JOIN("&8[&2+&8] &7%player%"),
    QUIT("&8[&4-&8] &7%player%"),
    ;

    private String value;

    Placeholder(@NotNull String value) {
        this.value = value;
    }

    public static void init(@NotNull ApexCore plugin) {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        validateFile(plugin, file);

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        boolean changed = false;
        for (Placeholder entry : values()) {
            if (config.isSet(entry.name())) continue;

            config.set(entry.name(), entry.value);
            changed = true;
        }

        if (changed) saveFile(plugin, file, config);

        for (Placeholder entry : values()) {
            entry.value = config.getString(entry.name());
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

    @Override
    public String toString() {
        return value;
    }
}
