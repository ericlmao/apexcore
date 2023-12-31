package games.negative.apexcore.core.structure;

import com.google.gson.annotations.SerializedName;
import games.negative.apexcore.api.model.ApexPlayer;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Represents the implementation class for the {@link ApexPlayer} interface.
 */
@Data
public class ApexPlayerImpl implements ApexPlayer {

    /*
    The UUID of the player.
     */
    @SerializedName("uuid")
    private final UUID uuid;

    /*
    The last seen date of the player.
     */
    @SerializedName("last-seen")
    private long lastSeenDate;

    /*
    The first seen date of the player.
     */
    @SerializedName("first-seen")
    private long firstSeenDate;

    /*
    The ID of the player.
     */
    @SerializedName("id")
    private int id;

    /*
    If the player has message sounds enabled.
     */
    @SerializedName("message-sound")
    private boolean messageSoundEnabled;

    /*
    If the player can be messaged.
     */
    @SerializedName("messageable")
    private boolean messageable;

    /*
    The ignored users of the player.
     */
    @SerializedName("ignored-users")
    private final List<UUID> ignoredUsers;

    @SerializedName("message-sound-type")
    private Sound messageSound = DEFAULT_MESSAGE_SOUND;

    /**
     * Constructor for the {@link ApexPlayer} implementation class.
     *
     * @param uuid                The {@link UUID} of the player.
     * @param id                 The ID of the player.
     * @param lastSeenDate        The last seen date of the player.
     * @param messageSoundEnabled If the player has message sounds enabled.
     * @param messageable         If the player can be messaged.
     * @param ignoredUsers        The ignored users of the player.
     */
    public ApexPlayerImpl(@NotNull UUID uuid, int id, long lastSeenDate, boolean messageSoundEnabled, boolean messageable, @NotNull List<UUID> ignoredUsers) {
        this.uuid = uuid;
        this.id = id;
        this.lastSeenDate = lastSeenDate;
        this.firstSeenDate = System.currentTimeMillis();
        this.messageSoundEnabled = messageSoundEnabled;
        this.messageable = messageable;
        this.ignoredUsers = ignoredUsers;
    }

    /**
     * Constructor for the {@link ApexPlayer} implementation class.
     *
     * @param uuid The {@link UUID} of the player.
     */
    public ApexPlayerImpl(@NotNull UUID uuid, int id) {
        this(uuid, id, System.currentTimeMillis(), false, true, List.of());
    }

    @Override
    public @NotNull UUID getUniqueID() {
        return uuid;
    }

    @Override
    public long getLastSeenDate() {
        return lastSeenDate;
    }

    @Override
    public void setLastSeenDate(long date) {
        lastSeenDate = date;
    }

    @Override
    public boolean isMessageSound() {
        return messageSoundEnabled;
    }

    @Override
    public void setMessageSound(boolean toggle) {
        messageSoundEnabled = toggle;
    }

    @Override
    public boolean isMessageable() {
        return messageable;
    }

    @Override
    public void setMessageable(boolean toggle) {
        messageable = toggle;
    }

    @Override
    public @NotNull List<UUID> getIgnoredUsers() {
        return ignoredUsers;
    }

    @Override
    public void addIgnoredUser(@NotNull UUID uuid) {
        ignoredUsers.remove(uuid);
        ignoredUsers.add(uuid);
    }

    @Override
    public void removeIgnoredUser(@NotNull UUID uuid) {
        ignoredUsers.remove(uuid);
    }

    @Override
    public @NotNull Sound getMessageSound() {
        return messageSound;
    }

    @Override
    public void setMessageSound(@Nullable Sound sound) {
        this.messageSound = (sound == null ? DEFAULT_MESSAGE_SOUND : sound);
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public long getFirstSeenDate() {
        return firstSeenDate;
    }

}
