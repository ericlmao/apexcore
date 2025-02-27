package games.negative.apexcore.command.ignore;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.CommandContext;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents the "remove" subcommand for the /ignore command.
 */
public class CmdRemove extends Command {

    private final ApexAPI api;

    public CmdRemove(@NotNull ApexAPI api) {
        super(CommandBuilder.builder()
                .name("remove")
                .description("Remove a player from your ignore list.")
                .parameter("player", context -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList())
                .playerOnly(true)
        );
        this.api = api;
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        Player player = context.player().orElseThrow();

        ApexPlayer user = api.getPlayer(player.getUniqueId());
        if (user == null) {
            Locale.GENERIC_PROFILE_ERROR.create().send(player);
            return;
        }

        String[] args = context.args();
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            Locale.GENERIC_PLAYER_NOT_FOUND.create().replace("%player%", args[0]).send(player);
            return;
        }

        UUID uuid = target.getUniqueId();

        if (!user.isIgnoring(uuid)) {
            Locale.IGNORE_NOT_IGNORING.create().replace("%player%", target.getName()).send(player);
            return;
        }

        user.removeIgnoredUser(uuid);
        Locale.IGNORE_REMOVE_SINGLE.create().replace("%player%", target.getName()).send(player);
    }
}
