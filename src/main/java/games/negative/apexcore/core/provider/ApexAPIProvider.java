package games.negative.apexcore.core.provider;

import com.google.common.collect.Maps;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class ApexAPIProvider implements ApexAPI {

    private final Map<UUID, ApexPlayer> players;

    public ApexAPIProvider() {
        this.players = Maps.newHashMap();
    }

    @Override
    public @Nullable ApexPlayer getPlayer(@NotNull UUID uuid) {
        return players.getOrDefault(uuid, null);
    }

    @Override
    public boolean createPlayer(@NotNull UUID uuid) {
        return false;
    }

    @Override
    public boolean deletePlayer(@NotNull UUID uuid) {
        return false;
    }

    @Override
    public @NotNull Map<UUID, ApexPlayer> getPlayers() {
        return null;
    }
}
