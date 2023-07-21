package games.negative.apexcore.api.model;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a PlaceholderAPI placeholder for ApexCore.
 */
public interface ApexPlaceholder {

    /**
     * Method to handle the placeholder logic
     *
     * @param player The player to handle the placeholder for
     * @param params The parameters of the placeholder
     * @return The return value of the placeholder
     * @apiNote The return value can be null!
     */
    @Nullable
    String handle(@NotNull OfflinePlayer player, @NotNull String[] params);

}
