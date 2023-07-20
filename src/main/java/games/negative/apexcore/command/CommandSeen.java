package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.alumina.util.TimeUtil;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSeen implements Command {

    private final ApexAPI api;

    public CommandSeen(@NotNull ApexAPI api) {
        this.api = api;
    }

    @Override
    public void execute(@NotNull Context context) {
        Player player = context.getPlayer();
        assert player != null;

        String[] args = context.args();

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        ApexPlayer user = api.getPlayer(target.getUniqueId());
        if (user == null) {
            Locale.GENERIC_PLAYER_NOT_FOUND.replace("%player%", args[0]).send(player);
            return;
        }

        long date = user.getLastSeenDate();
        long seen = Math.abs(System.currentTimeMillis() - date);
        String formatted = TimeUtil.format(seen, false);

        Locale.LAST_SEEN.replace("%player%", player.getName())
                .replace("%date%", formatted).send(player);
    }

}
