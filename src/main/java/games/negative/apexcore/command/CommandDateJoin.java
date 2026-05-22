package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.CommandContext;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.alumina.util.NumberUtil;
import games.negative.alumina.util.TimeUtil;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class CommandDateJoin extends Command {

    private final ApexAPI api;

    // Example: Fri, January 1, 2021, at 12:00 AM
    private static final String DATE_FORMAT = "EEE, MMMM d, yyyy, 'at' hh:mm a";

    public CommandDateJoin(@NotNull ApexAPI api) {
        super(CommandBuilder.builder()
                .name("datejoin")
                .aliases("joindate")
                .description("Check when you first joined the server.")
                .playerOnly(true)
        );
        this.api = api;
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        Player player = context.player().orElseThrow();

        ApexPlayer user = api.getPlayer(player.getUniqueId());

        String name = player.getName();

        String[] args = context.args();
        if (args.length > 0) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            ApexPlayer temp = api.getPlayer(target.getUniqueId());
            if (temp == null) {
                Locale.GENERIC_PROFILE_ERROR_OTHER.create().send(player);
                return;
            }

            user = temp;
            name = target.getName();
        }

        if (user == null) {
            Locale.GENERIC_PROFILE_ERROR.create().send(player);
            return;
        }
        int id = user.getID();
        long date = user.getFirstSeenDate();
        long ago = Math.abs(System.currentTimeMillis() - date);
        String agoFormat = TimeUtil.format(ago, true);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String formattedDate = dateFormat.format(date);

        Locale.FIRST_SEEN.create()
                .replace("%player%", Objects.requireNonNull(name))
                .replace("%date%", formattedDate)
                .replace("%ago%", agoFormat)
                .replace("%id%", NumberUtil.decimalFormat(id))
                .send(player);
    }

}
