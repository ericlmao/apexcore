package games.negative.apexcore.command.ignore;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.CommandContext;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.core.Locale;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the main command class for the /ignore command.
 */
public class CommandIgnore extends Command {

    public CommandIgnore(ApexAPI api) {
        super(CommandBuilder.builder()
                .name("ignore")
                .description("Add, remove, or list ignored players.")
                .playerOnly(true)
                .smartTabComplete(true)
        );

        addSubCommand(new CmdAdd(api));
        addSubCommand(new CmdRemove(api));
        addSubCommand(new CmdClear(api));
        addSubCommand(new CmdList(api));
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        Locale.IGNORE_HELP.create().send(context.sender());
    }
}
