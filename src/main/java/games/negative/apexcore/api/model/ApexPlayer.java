package games.negative.apexcore.api.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Represents a custom player object.
 */
public interface ApexPlayer {

    /**
     * Gets the player's unique ID.
     * @return The player's unique ID.
     */
    @NotNull
    UUID getUniqueID();

    /**
     * Get the player's first join date.
     * @return The player's first join date.
     */
    long getJoinDate();

    /**
     * Get the player's last seen date.
     * @return The player's last seen date.
     */
    long getLastSeenDate();

    /**
     * Set the player's last seen date.
     * @param date The player's last seen date.
     */
    void setLastSeenDate(long date);

    /**
     * Get if the player has message sounds enabled.
     * @return If the player has message sounds enabled.
     */
    boolean isMessageSound();

    /**
     * Set if the player has message sounds enabled.
     * @param toggle If the player has message sounds enabled.
     */
    void setMessageSound(boolean toggle);

    /**
     * Get if the player can be messaged.
     * @return If the player can be messaged.
     */
    boolean isMessageable();

    /**
     * Set if the player can be messaged.
     * @param toggle If the player can be messaged.
     */
    void setMessageable(boolean toggle);

    /**
     * Get the player's ignored users.
     * @return The player's ignored users.
     */
    @NotNull
    List<UUID> getIgnoredUsers();

    /**
     * Add an ignored user.
     * @param uuid The UUID of the user to ignore.
     */
    void addIgnoredUser(@NotNull UUID uuid);

    /**
     * Remove an ignored user.
     * @param uuid The UUID of the user to unignore.
     */
    void removeIgnoredUser(@NotNull UUID uuid);

    /**
     * Get if the player is ignoring a user.
     * @param uuid The UUID of the user to check.
     * @return If the player is ignoring a user.
     */
    default boolean isIgnoring(@NotNull UUID uuid) {
        return getIgnoredUsers().contains(uuid);
    }

}
