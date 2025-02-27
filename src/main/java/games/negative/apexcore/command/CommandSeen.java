package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.CommandContext;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.alumina.util.TimeUtil;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class CommandSeen extends Command {

    private final ApexAPI api;

    public CommandSeen(@NotNull ApexAPI api) {
        super(CommandBuilder.builder()
                .name("seen")
                .playerOnly(true)
                .description("Check when a player was last online.")
                .parameter("player", context -> Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList()));
        this.api = api;
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        Player player = context.player().orElseThrow();

        String[] args = context.args();

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

        ApexPlayer user = api.getPlayer(target.getUniqueId());
        if (user == null) {
            Locale.GENERIC_PLAYER_NOT_FOUND.create()
                    .replace("%player%", args[0])
                    .send(player);
            return;
        }

        long date = user.getLastSeenDate();
        long seen = Math.abs(System.currentTimeMillis() - date);
        String formatted = TimeUtil.format(seen, false);

        Locale.LAST_SEEN.create().replace("%player%", Objects.requireNonNull(target.getName()))
                .replace("%date%", formatted).send(player);
    }

}
