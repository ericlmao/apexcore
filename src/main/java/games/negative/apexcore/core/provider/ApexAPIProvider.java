package games.negative.apexcore.core.provider;

import com.google.common.collect.Maps;
import games.negative.apexcore.ApexCore;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.ApexDataManager;
import games.negative.apexcore.api.ApexPlaceholderManager;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.api.model.Conversation;
import games.negative.apexcore.core.structure.ApexPlayerImpl;
import games.negative.apexcore.task.ConversationExpireTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class ApexAPIProvider implements ApexAPI {

    private final Map<UUID, ApexPlayer> players;
    private final ApexDataManager data;
    private final ApexPlaceholderManager placeholderManager;
    private final Map<UUID, Conversation> conversations;

    public ApexAPIProvider(@NotNull ApexCore plugin) {
        this.data = new ApexDataManagerProvider(plugin);
        this.placeholderManager = new ApexPlaceholderManagerProvider(plugin);
        this.players = data.init();

        plugin.getLogger().info("Loaded " + players.size() + " players from data folder!");

        this.conversations = Maps.newHashMap();

        new ConversationExpireTask(this).runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public @Nullable ApexPlayer getPlayer(@NotNull UUID uuid) {
        return players.getOrDefault(uuid, null);
    }

    @Override
    public boolean createPlayer(@NotNull UUID uuid) {
        if (players.containsKey(uuid)) return false;

        int id = players.size() + 1;

        ApexPlayer player = new ApexPlayerImpl(uuid, id);
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
    public Map<UUID, Conversation> conversations() {
        return conversations;
    }

    @Override
    public void addConversation(@NotNull UUID player, @NotNull UUID recipient) {
        this.conversations.remove(player);
        this.conversations.put(player, new Conversation(recipient, System.currentTimeMillis()));
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
    public void updateConversation(@NotNull UUID uuid, @NotNull UUID recipient) {
        if (!this.conversations.containsKey(uuid)) {
            addConversation(uuid, recipient);
            return;
        }

        Conversation current = getConversation(uuid);
        if (current == null || !current.recipient().equals(recipient)) return;

        this.conversations.replace(uuid, new Conversation(recipient, System.currentTimeMillis()));
    }

    @Override
    public @Nullable Conversation getConversation(@NotNull UUID player) {
        return this.conversations.getOrDefault(player, null);
    }

    @Override
    public @NotNull ApexPlaceholderManager getPlaceholderManager() {
        return placeholderManager;
    }

}
