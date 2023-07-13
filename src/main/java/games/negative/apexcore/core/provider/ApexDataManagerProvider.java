package games.negative.apexcore.core.provider;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import games.negative.apexcore.ApexCore;
import games.negative.apexcore.api.ApexDataManager;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.structure.ApexPlayerImpl;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Map;
import java.util.UUID;

public class ApexDataManagerProvider implements ApexDataManager {

    private final ApexCore plugin;

    public ApexDataManagerProvider(@NotNull ApexCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull Map<UUID, ApexPlayer> init() {
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            boolean success = main.mkdir();
            if (success) plugin.getLogger().info("Created main plugin folder!");
        }

        File data = new File(main, "data");
        if (!data.exists()) {
            boolean success = data.mkdir();
            if (success) plugin.getLogger().info("Created data folder!");
        }

        File[] files = data.listFiles();
        if (files == null) {
            plugin.getLogger().warning("Failed to fetch data from data folder! Returning empty map.");
            return Maps.newHashMap();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        Map<UUID, ApexPlayer> players = Maps.newHashMap();
        for (File file : files) {
            if (!file.getName().endsWith(".json")) continue;

            try (Reader reader = new FileReader(file)) {
                ApexPlayer player = gson.fromJson(reader, ApexPlayerImpl.class);
                players.put(player.getUniqueID(), player);
                plugin.getLogger().info("Loaded player data from file " + file.getName() + "!");
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to load player data from file " + file.getName() + "!");
                e.printStackTrace();
            }
        }

        return players;
    }

    @Override
    public void disable(@NotNull Map<UUID, ApexPlayer> players) {
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            boolean success = main.mkdir();
            if (success) plugin.getLogger().info("Created main plugin folder!");
        }

        File data = new File(main, "data");
        if (!data.exists()) {
            boolean success = data.mkdir();
            if (success) plugin.getLogger().info("Created data folder!");
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        for (ApexPlayer player : players.values()) {
            UUID uuid = player.getUniqueID();

            File file = new File(data, uuid + ".json");
            if (!file.exists()) {
                try {
                    boolean success = file.createNewFile();
                    if (success) plugin.getLogger().info("Created file " + file.getName() + "!");

                    try (Writer writer = new FileWriter(file)) {
                        gson.toJson(player, writer);
                        plugin.getLogger().info("Saved player data to file " + file.getName() + "!");
                    } catch (IOException e) {
                        plugin.getLogger().warning("Failed to save player data to file " + file.getName() + "!");
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    plugin.getLogger().warning("Failed to create file " + file.getName() + "!");
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean deletePlayer(@NotNull ApexPlayer player) {
        File main = plugin.getDataFolder();
        if (!main.exists()) {
            boolean success = main.mkdir();
            if (success) plugin.getLogger().info("Created main plugin folder!");
        }

        File data = new File(main, "data");
        if (!data.exists()) {
            boolean success = data.mkdir();
            if (success) plugin.getLogger().info("Created data folder!");
        }

        UUID uuid = player.getUniqueID();
        File file = new File(data, uuid + ".json");
        if (!file.exists()) {
            plugin.getLogger().warning("Failed to delete player data from file " + file.getName() + "!");
            return false;
        }

        boolean success = file.delete();
        if (success) plugin.getLogger().info("Deleted player data from file " + file.getName() + "!");
        return success;
    }
}
