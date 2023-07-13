package games.negative.apexcore.api.event;

import games.negative.apexcore.api.model.ApexPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

//todo replace with PluginEvent from Alumina API
public class UniquePlayerJoinEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final UUID uuid;
    private final ApexPlayer user;

    public UniquePlayerJoinEvent(@NotNull UUID uuid, @NotNull ApexPlayer user) {
        this.uuid = uuid;
        this.user = user;
    }

    @NotNull
    public ApexPlayer getUser() {
        return user;
    }

    @NotNull
    public UUID getUniqueID() {
        return uuid;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
