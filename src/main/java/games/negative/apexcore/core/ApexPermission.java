package games.negative.apexcore.core;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApexPermission extends Permission {

    /*
      Bypass ignore restrictions.
     */
    public static final ApexPermission IGNORE_BYPASS = new ApexPermission("ignore.bypass", "Allows a player to bypass ignore restrictions.");

    /*
      Bypass message restrictions.
     */
    public static final ApexPermission MESSAGE_BYPASS = new ApexPermission("message.bypass", "Allows the player to bypass message restrictions.");

    protected ApexPermission(@NotNull String name, @Nullable String description) {
        this(name, description, PermissionDefault.OP);
    }

    protected ApexPermission(@NotNull String name, @Nullable String description, @Nullable PermissionDefault defaultValue) {
        super("apexcore." + name, description, defaultValue);
    }

}
