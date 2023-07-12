package games.negative.apexcore.command.ignore;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.apexcore.api.ApexAPI;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the main command class for the /ignore command.
 */
public class CommandIgnore implements Command {

    private final ApexAPI api;

    public CommandIgnore(@NotNull ApexAPI api) {
        this.api = api;
    }

    @Override
    public void execute(@NotNull Context context) {

    }
}
