package games.negative.apexcore.core.provider;

import games.negative.apexcore.ApexCore;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.ApexDataManager;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.structure.ApexPlayerImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class ApexAPIProvider implements ApexAPI {

    private final Map<UUID, ApexPlayer> players;
    private final ApexDataManager data;
    public ApexAPIProvider(@NotNull ApexCore plugin) {
        this.data = new ApexDataManagerProvider(plugin);
        this.players = data.init();

        plugin.getLogger().info("Loaded " + players.size() + " players from data folder!");
    }

    @Override
    public @Nullable ApexPlayer getPlayer(@NotNull UUID uuid) {
        return players.getOrDefault(uuid, null);
    }

    @Override
    public boolean createPlayer(@NotNull UUID uuid) {
        if (players.containsKey(uuid)) return false;

        ApexPlayer player = new ApexPlayerImpl(uuid);
        players.put(uuid, player);

        return true;
    }

    @Override
    public boolean deletePlayer(@NotNull UUID uuid) {
        if (!players.containsKey(uuid)) return false;

        ApexPlayer player = players.get(uuid);
        boolean success = data.deletePlayer(player);

        if (success) players.remove(uuid);

        return success;
    }

    @Override
    public @NotNull Map<UUID, ApexPlayer> getPlayers() {
        return players;
    }

    @Override
    public void disable() {
        data.disable(players);
    }
}
