package games.negative.apexcore;

import games.negative.alumina.AluminaPlugin;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.ApexPlaceholderManager;
import games.negative.apexcore.command.*;
import games.negative.apexcore.command.ignore.*;
import games.negative.apexcore.core.Locale;
import games.negative.apexcore.core.provider.ApexAPIProvider;
import games.negative.apexcore.listener.ApexChatListener;
import games.negative.apexcore.listener.ApexProfileListener;
import games.negative.apexcore.placeholder.UniqueIDPlaceholder;
import org.jetbrains.annotations.Nullable;

public final class ApexCore extends AluminaPlugin {

    private ApexAPI api;

    @Override
    public void enable() {
        Locale.init(this);

        this.api = new ApexAPIProvider(this);

        handlePlaceholders();
        handleCommands();
        handleListeners();
    }

    private void handlePlaceholders() {
        ApexPlaceholderManager manager = api.getPlaceholderManager();
        manager.registerPlaceholder("user-id", new UniqueIDPlaceholder(api));
    }

    private void handleListeners() {
        registerListeners(
                new ApexProfileListener(this),
                new ApexChatListener(api)
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
                                        .params("player")
                                        .playerOnly(),

                                // /ignore remove <player>
                                new CommandBuilder(new CmdRemove(api))
                                        .name("remove")
                                        .description("Remove a player from your ignore list.")
                                        .params("player")
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

        // Register the /message command
        registerCommand(new CommandBuilder(new CommandMessage(api))
                .name("message")
                .aliases("msg", "m", "tell", "whisper", "w")
                .description("Send a private message to a player.")
                .params("player", "message")
                .playerOnly()
        );

        // Register the /reply command
        registerCommand(new CommandBuilder(new CommandReply(api))
                .name("reply")
                .aliases("r")
                .description("Reply to a private message.")
                .params("message")
                .playerOnly()
        );

        // Register the /togglemessage command
        registerCommand(new CommandBuilder(new CommandToggleMessage(api))
                .name("togglemessage")
                .aliases("tm", "togglemsg", "tmsg", "togglepm", "tpm")
                .description("Toggle private messages.")
                .playerOnly()
        );

        // Register the /togglemessagesound command
        registerCommand(new CommandBuilder(new CommandToggleMessageSound(api))
                .name("togglemessagesound")
                .aliases("tms", "togglemsgsound", "tmsgs", "togglepmsound", "tpms", "togglesound")
                .description("Toggle private message sounds.")
                .playerOnly()
        );

        // Register the /seen command
        registerCommand(new CommandBuilder(new CommandSeen(api))
                .name("seen")
                .description("Check when a player was last online.")
                .params("player")
                .usage("/seen <player>")
                .playerOnly()
        );

        // Register /datejoin command
        registerCommand(new CommandBuilder(new CommandDateJoin(api))
                .name("datejoin")
                .description("Check when you first joined the server.")
                .playerOnly()
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
