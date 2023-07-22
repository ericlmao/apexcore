package games.negative.apexcore.task;

import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.event.UniquePlayerJoinEvent;
import games.negative.apexcore.api.model.ApexPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DelayedProfileInitTask extends BukkitRunnable {

    private final ApexAPI api;
    private final UUID uuid;
    private final AsyncPlayerPreLoginEvent event;

    public DelayedProfileInitTask(AsyncPlayerPreLoginEvent event, @NotNull ApexAPI api, @NotNull UUID uuid) {
        this.api = api;
        this.uuid = uuid;
        this.event = event;
    }

    @Override
    public void run() {
        AsyncPlayerPreLoginEvent.Result result = event.getLoginResult();
        if (result != AsyncPlayerPreLoginEvent.Result.ALLOWED) return;

        boolean success = api.createPlayer(uuid);
        if (!success) return;

        ApexPlayer user = api.getPlayer(uuid);
        if (user == null) return;

        UniquePlayerJoinEvent event = new UniquePlayerJoinEvent(uuid, user);
        Bukkit.getPluginManager().callEvent(event); //todo replace with Alumina Event caller
    }
}
