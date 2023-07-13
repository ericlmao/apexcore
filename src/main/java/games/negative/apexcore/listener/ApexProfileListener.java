package games.negative.apexcore.listener;

import games.negative.apexcore.ApexCore;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.task.DelayedProfileInitTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ApexProfileListener implements Listener {

    private final ApexCore plugin;
    private final ApexAPI api;

    public ApexProfileListener(@NotNull ApexCore plugin) {
        this.plugin = plugin;
        this.api = plugin.api();
    }

    @EventHandler
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event) {
        UUID uuid = event.getUniqueId();

        new DelayedProfileInitTask(api, uuid).runTaskLater(plugin, 1L);
    }
}
