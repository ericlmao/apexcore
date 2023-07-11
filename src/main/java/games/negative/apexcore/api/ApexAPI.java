package games.negative.apexcore.api;

import games.negative.apexcore.api.model.ApexPlayer;
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
}
