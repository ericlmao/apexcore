package games.negative.apexcore;

import games.negative.alumina.AluminaPlugin;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.command.ignore.*;
import games.negative.apexcore.core.Locale;
import games.negative.apexcore.core.provider.ApexAPIProvider;
import games.negative.apexcore.listener.ApexProfileListener;
import org.jetbrains.annotations.Nullable;

public final class ApexCore extends AluminaPlugin {

    private ApexAPI api;

    @Override
    public void enable() {
        Locale.init(this);

        this.api = new ApexAPIProvider(this);

        handleCommands();
        handleListeners();
    }

    private void handleListeners() {
        registerListeners(
                new ApexProfileListener(this)
        );
    }

    @Override
    public void disable() {
        api.disable();
    }

    private void handleCommands() {
        // Register the /ignore command
        registerCommand(
                new CommandBuilder(new CommandIgnore())
                        .name("ignore")
                        .description("Add, remove, or list ignored players.")
                        .playerOnly()
                        .subcommands(
                                // /ignore add <player>
                                new CommandBuilder(new CmdAdd(api))
                                        .name("add")
                                        .description("Add a player to your ignore list.")
                                        .params("<player>")
                                        .playerOnly(),

                                // /ignore remove <player>
                                new CommandBuilder(new CmdRemove(api))
                                        .name("remove")
                                        .description("Remove a player from your ignore list.")
                                        .params("<player>")
                                        .playerOnly(),

                                // /ignore list
                                new CommandBuilder(new CmdList(api))
                                        .name("list")
                                        .description("List all players on your ignore list.")
                                        .playerOnly(),

                                // ignore clear
                                new CommandBuilder(new CmdClear(api))
                                        .name("clear")
                                        .description("Clear your ignore list.")
                                        .playerOnly()
                        )
        );
    }

    /**
     * Returns the API instance.
     *
     * @return the API instance
     * @throws NullPointerException If method is invoked before the plugin is enabled.
     */
    @Nullable
    public ApexAPI api() {
        return api;
    }
}
