package games.negative.apexcore.core.structure;

import games.negative.apexcore.api.model.ApexPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Represents the implementation class for the {@link ApexPlayer} interface.
 */
public class ApexPlayerImpl implements ApexPlayer {

    /*
    The UUID of the player.
     */
    private final UUID uuid;

    /*
    The join date of the player.
     */
    private final long joinDate;

    /*
    The last seen date of the player.
     */
    private long lastSeenDate;

    /*
    If the player has message sounds enabled.
     */
    private boolean messageSound;

    /*
    If the player can be messaged.
     */
    private boolean messageable;

    /*
    The ignored users of the player.
     */
    private final List<UUID> ignoredUsers;

    /**
     * Constructor for the {@link ApexPlayer} implementation class.
     * @param uuid The {@link UUID} of the player.
     * @param joinDate The join date of the player.
     * @param lastSeenDate The last seen date of the player.
     * @param messageSound If the player has message sounds enabled.
     * @param messageable If the player can be messaged.
     * @param ignoredUsers The ignored users of the player.
     */
    public ApexPlayerImpl(@NotNull UUID uuid, long joinDate, long lastSeenDate, boolean messageSound, boolean messageable, @NotNull List<UUID> ignoredUsers) {
        this.uuid = uuid;
        this.joinDate = joinDate;
        this.lastSeenDate = lastSeenDate;
        this.messageSound = messageSound;
        this.messageable = messageable;
        this.ignoredUsers = ignoredUsers;
    }

    /**
     * Constructor for the {@link ApexPlayer} implementation class.
     * @param uuid The {@link UUID} of the player.
     * @param joinDate The join date of the player.
     */
    public ApexPlayerImpl(@NotNull UUID uuid, long joinDate) {
        this(uuid, joinDate, joinDate, false, true, List.of());
    }

    @Override
    public @NotNull UUID getUniqueID() {
        return uuid;
    }

    @Override
    public long getJoinDate() {
        return joinDate;
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
        return messageSound;
    }

    @Override
    public void setMessageSound(boolean toggle) {
        messageSound = toggle;
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

}
