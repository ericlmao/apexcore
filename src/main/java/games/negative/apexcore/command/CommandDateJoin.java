package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.alumina.util.TimeUtil;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;

public class CommandDateJoin implements Command {

    private final ApexAPI api;

    // Example: Fri, January 1, 2021, at 12:00 AM
    private static final String DATE_FORMAT = "EEE, MMMM d, yyyy, 'at' hh:mm a";

    public CommandDateJoin(@NotNull ApexAPI api) {
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

        long date = user.getFirstSeenDate();
        long ago = Math.abs(System.currentTimeMillis() - date);
        String agoFormat = TimeUtil.format(ago, true);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String formattedDate = dateFormat.format(date);

        Locale.FIRST_SEEN.replace("%date%", formattedDate)
                .replace("%ago%", agoFormat).send(player);
    }

}
