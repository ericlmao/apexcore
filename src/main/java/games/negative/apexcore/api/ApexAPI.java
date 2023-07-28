package games.negative.apexcore.api;

import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.api.model.Conversation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

/**
 * Represents the API bridge between the plugin and the systems of the plugin.
 */
public interface ApexAPI {

    /**
     * Get an {@link ApexPlayer} by a {@link UUID}.
     * @param uuid The {@link UUID} of the player.
     * @return The {@link ApexPlayer} object.
     */
    @Nullable
    ApexPlayer getPlayer(@NotNull UUID uuid);

    /**
     * Create a new {@link ApexPlayer} object.
     * @param uuid The {@link UUID} of the player.
     * @return If the player was created successfully.
     */
    boolean createPlayer(@NotNull UUID uuid);

    /**
     * Delete a {@link ApexPlayer} object.
     * @param uuid The {@link UUID} of the player.
     * @return If the player was deleted successfully.
     */
    boolean deletePlayer(@NotNull UUID uuid);

    /**
     * Get all cached {@link ApexPlayer}s.
     * @return All cached {@link ApexPlayer}s.
     */
    @NotNull
    Map<UUID, ApexPlayer> getPlayers();

    /**
     * Runs disable methods throughout the API.
     */
    void disable();

    /**
     * Get the map of message conversations.
     * @return The map of message conversations.
     */
    Map<UUID, Conversation> conversations();

    /**
     * Add a new conversation entry.
     * @param player The player
     * @param recipient The recipient
     */
    void addConversation(@NotNull UUID player, @NotNull UUID recipient);

    /**
     * Remove a conversation entry.
     * @param player The player
     */
    void removeConversation(@NotNull UUID player);

    /**
     * Check if a player has a conversation.
     * @param player The player
     * @return If the player has a conversation.
     */
    boolean hasConversation(@NotNull UUID player);

    /**
     * Update a conversation.
     * @param uuid The player
     * @param recipient The recipient
     */
    void updateConversation(@NotNull UUID uuid, @NotNull UUID recipient);

    /**
     * Get the conversation of a player.
     * @param player The player
     * @return The conversation of the player.
     */
    @Nullable
    Conversation getConversation(@NotNull UUID player);

    /**
     * Get the placeholder manager.
     * @return The placeholder manager.
     */
    @NotNull
    ApexPlaceholderManager getPlaceholderManager();
}
