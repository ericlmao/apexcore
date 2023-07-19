package games.negative.apexcore.api.model;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Represents a custom player object.
 */
public interface ApexPlayer {

    /**
     * The default name color.
     */
    ChatColor DEFAULT_NAME_COLOR = ChatColor.WHITE;

    /**
     * The default message sound.
     */
    Sound DEFAULT_MESSAGE_SOUND = Sound.ENTITY_ARROW_HIT_PLAYER;

    /**
     * Gets the player's unique ID.
     * @return The player's unique ID.
     */
    @NotNull
    UUID getUniqueID();

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

    /**
     * Get the player's name color.
     * @return The player's name color.
     */
    @NotNull
    ChatColor getNameColor();

    /**
     * Set the player's name color.
     * @param color The player's name color.
     */
    void setNameColor(@Nullable ChatColor color);

    /**
     * Get the player's message sound.
     * @return The player's message sound.
     */
    @NotNull
    Sound getMessageSound();

    /**
     * Set the player's message sound.
     * @param sound The player's message sound.
     */
    void setMessageSound(@Nullable Sound sound);
}
