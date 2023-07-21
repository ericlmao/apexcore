package games.negative.apexcore.listener;

import games.negative.alumina.util.ColorUtil;
import games.negative.apexcore.ApexCore;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Placeholder;
import games.negative.apexcore.task.DelayedProfileInitTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        String join = Placeholder.JOIN.toString();
        join = join.replace("%player%", player.getName());

        event.setJoinMessage(ColorUtil.translate(join));

        ApexPlayer user = api.getPlayer(uuid);
        if (user == null) return;

        user.setLastSeenDate(System.currentTimeMillis());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        String quit = Placeholder.QUIT.toString();
        quit = quit.replace("%player%", player.getName());

        event.setQuitMessage(ColorUtil.translate(quit));

        ApexPlayer user = api.getPlayer(uuid);
        if (user == null) return;

        user.setLastSeenDate(System.currentTimeMillis());
    }
}
