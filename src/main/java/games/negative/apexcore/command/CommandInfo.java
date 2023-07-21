package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.alumina.util.NumberUtil;
import games.negative.alumina.util.TimeUtil;
import games.negative.apexcore.ApexCore;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.core.Locale;
import games.negative.apexcore.core.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandInfo implements Command {

    private final ApexCore plugin;
    private final ApexAPI api;

    public CommandInfo(@NotNull ApexCore plugin) {
        this.plugin = plugin;
        this.api = plugin.api();
    }

    @Override
    public void execute(@NotNull Context context) {
        Player player = context.getPlayer();
        assert player != null;

        String uptime = TimeUtil.format(Math.abs(plugin.getStart() - System.currentTimeMillis()), true);
        String start = Placeholder.SERVER_START.toString();
        String tps = String.format("%.2f", plugin.getTpsHandler().getTicks());
        int unique = Bukkit.getOfflinePlayers().length;

        World world = Bukkit.getWorld(Placeholder.DEFAULT_WORLD.toString());
        if (world == null)
            world = Bukkit.getWorlds().get(0);

        if (world == null) throw new NullPointerException("No worlds found");

        long sizeBytes = calculateWorldSizeInBytes(world);
        String parsed = formatSizeInBytes(sizeBytes);

        Locale.SERVER_INFO.replace("%server_start%", start)
                .replace("%uptime%", uptime)
                .replace("%tps%", tps)
                .replace("%unique%", NumberUtil.parse(unique))
                .replace("%size%", parsed)
                .send(player);
    }

    public long calculateWorldSizeInBytes(@NotNull World world) {
        Path worldFolderPath = Paths.get(world.getWorldFolder().getPath());

        try {
            return Files.walk(worldFolderPath)
                    .filter(p -> p.toFile().isFile())
                    .mapToLong(p -> p.toFile().length())
                    .sum();
        } catch (IOException e) {
            throw new RuntimeException("Error calculating world size", e);
        }
    }

    public static String formatSizeInBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return bytes / 1024 + " KB";
        } else if (bytes < 1024 * 1024 * 1024) {
            return bytes / (1024 * 1024) + " MB";
        } else if (bytes < 1024L * 1024 * 1024 * 1024) {
            return bytes / (1024 * 1024 * 1024) + " GB";
        } else {
            return bytes / (1024L * 1024 * 1024 * 1024) + " TB";
        }
    }

}
