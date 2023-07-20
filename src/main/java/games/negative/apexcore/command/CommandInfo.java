package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandInfo implements Command {

    @Override
    public void execute(@NotNull Context context) {
        Player player = context.getPlayer();
    }

}
