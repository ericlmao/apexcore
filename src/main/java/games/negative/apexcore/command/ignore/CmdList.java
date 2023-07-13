package games.negative.apexcore.command.ignore;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import games.negative.apexcore.ui.IgnoreListMenu;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the "list" subcommand for the /ignore command.
 */
public class CmdList implements Command {

    private final ApexAPI api;


    public CmdList(@NotNull ApexAPI api) {
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

        new IgnoreListMenu(api, user, 1).open(player);
    }
}
