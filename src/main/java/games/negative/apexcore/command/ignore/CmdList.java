package games.negative.apexcore.command.ignore;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.CommandContext;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import games.negative.apexcore.ui.IgnoreListMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the "list" subcommand for the /ignore command.
 */
public class CmdList extends Command {

    private final ApexAPI api;


    public CmdList(@NotNull ApexAPI api) {
        super(CommandBuilder.builder()
                .name("list")
                .description("List all players on your ignore list.")
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

        new IgnoreListMenu(user).open(player);
    }
}
