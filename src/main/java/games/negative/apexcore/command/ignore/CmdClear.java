package games.negative.apexcore.command.ignore;

import com.google.common.collect.Lists;
import games.negative.alumina.command.Command;
import games.negative.alumina.command.CommandContext;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Represents the "clear" subcommand for the /ignore command.
 */
public class CmdClear extends Command {

    private final ApexAPI api;

    public CmdClear(@NotNull ApexAPI api) {
        super(CommandBuilder.builder()
                .name("clear")
                .description("Clear your ignore list.")
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

        List<UUID> toRemove = Lists.newArrayList(user.getIgnoredUsers());
        for (UUID uuid : toRemove) {
            user.removeIgnoredUser(uuid);
        }

        int removed = toRemove.size();

        Locale.IGNORE_REMOVE_MULTIPLE.create().replace("%amount%", String.valueOf(removed)).send(player); //todo: replace with fancy number format
    }
}
