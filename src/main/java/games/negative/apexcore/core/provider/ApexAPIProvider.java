package games.negative.apexcore.core.provider;

import com.google.common.collect.Maps;
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
    private final Map<UUID, UUID> conversations;

    public ApexAPIProvider(@NotNull ApexCore plugin) {
        this.data = new ApexDataManagerProvider(plugin);
        this.players = data.init();

        plugin.getLogger().info("Loaded " + players.size() + " players from data folder!");

        this.conversations = Maps.newHashMap();
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

    @Override
    public Map<UUID, UUID> conversations() {
        return conversations;
    }

    @Override
    public void addConversation(@NotNull UUID player, @NotNull UUID recipient) {
        this.conversations.remove(player);
        this.conversations.put(player, recipient);
    }

    @Override
    public void removeConversation(@NotNull UUID player) {
        this.conversations.remove(player);
    }

    @Override
    public boolean hasConversation(@NotNull UUID player) {
        return this.conversations.containsKey(player);
    }

    @Override
    public @Nullable UUID getConversation(@NotNull UUID player) {
        return this.conversations.getOrDefault(player, null);
    }

}
