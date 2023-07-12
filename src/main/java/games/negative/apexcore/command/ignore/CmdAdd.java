package games.negative.apexcore.command.ignore;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents the "add" subcommand for the /ignore command.
 */
public class CmdAdd implements Command {

    private final ApexAPI api;

    public CmdAdd(@NotNull ApexAPI api) {
        this.api = api;
    }

    @Override
    public void execute(@NotNull Context context) {
        Player player = context.getPlayer();
        assert player != null;

        ApexPlayer user = api.getPlayer(player.getUniqueId());
        if (user == null) {
            Locale.GENERIC_PROFILE_ERROR.send(player);
            return;
        }

        String[] args = context.args();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        UUID uuid = target.getUniqueId();

        if (user.isIgnoring(uuid)) {
            Locale.IGNORE_ALREADY_IGNORING.replace("%player%", target.getName()).send(player);
            return;
        }

        user.addIgnoredUser(uuid);
        Locale.IGNORE_ADD.replace("%player%", target.getName()).send(player);
    }
}
