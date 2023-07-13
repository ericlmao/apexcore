package games.negative.apexcore.command.ignore;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.apexcore.core.Locale;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the main command class for the /ignore command.
 */
public class CommandIgnore implements Command {

    @Override
    public void execute(@NotNull Context context) {
        Locale.IGNORE_HELP.send(context.sender());
    }
}
