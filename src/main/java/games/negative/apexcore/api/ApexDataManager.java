package games.negative.apexcore.api;

import games.negative.apexcore.ApexCore;
import games.negative.apexcore.api.model.ApexPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

/**
 * Represents the data manager / data handler class for ApexCore.
 */
public interface ApexDataManager {

    /**
     * Invoked when the plugin is enabled.
     */
    @NotNull
    Map<UUID, ApexPlayer> init();

    /**
     * Invoked when the plugin is disabled.
     */
    void disable(@NotNull Map<UUID, ApexPlayer> players);

    /**
     * Delete a {@link ApexPlayer} instance (and data source).
     * @param player The {@link ApexPlayer} instance to delete.
     * @return {@code true} if the player was deleted, otherwise {@code false}.
     */
    boolean deletePlayer(@NotNull ApexPlayer player);

}
