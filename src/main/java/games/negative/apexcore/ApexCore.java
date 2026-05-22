package games.negative.apexcore;

import games.negative.alumina.AluminaPlugin;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.ApexPlaceholderManager;
import games.negative.apexcore.command.*;
import games.negative.apexcore.command.ignore.CommandIgnore;
import games.negative.apexcore.core.Locale;
import games.negative.apexcore.core.Placeholder;
import games.negative.apexcore.core.provider.ApexAPIProvider;
import games.negative.apexcore.listener.ApexChatListener;
import games.negative.apexcore.listener.ApexDeathListener;
import games.negative.apexcore.listener.ApexProfileListener;
import games.negative.apexcore.placeholder.UniqueIDPlaceholder;
import games.negative.apexcore.task.TicksPerSecondTask;
import org.jetbrains.annotations.Nullable;

public final class ApexCore extends AluminaPlugin {

    private ApexAPI api;
    private long start;
    private TicksPerSecondTask tpsHandler;

    @Override
    public void load() {

    }

    @Override
    public void enable() {
        Locale.init(this);
        Placeholder.init(this);

        this.api = new ApexAPIProvider(this);

        handlePlaceholders();
        handleCommands();
        handleListeners();

        this.start = System.currentTimeMillis();
        this.tpsHandler = new TicksPerSecondTask();
        this.tpsHandler.runTaskTimer(this, 0, 1);
    }

    private void handlePlaceholders() {
        ApexPlaceholderManager manager = api.getPlaceholderManager();
        manager.registerPlaceholder("user-id", new UniqueIDPlaceholder(api));
    }

    private void handleListeners() {
        registerListeners(
                new ApexProfileListener(this),
                new ApexChatListener(api),
                new ApexDeathListener(this)
        );
    }

    @Override
    public void disable() {
        api.disable();
    }

    private void handleCommands() {
        // Register the /ignore command
        registerCommand(new CommandIgnore(api));

        // Register the /message command
        registerCommand(new CommandMessage(api));

        // Register the /reply command
        registerCommand(new CommandReply(api));

        // Register the /togglemessage command
        registerCommand(new CommandToggleMessage(api));

        // Register the /togglemessagesound command
        registerCommand(new CommandToggleMessageSound(api));

        // Register the /seen command
        registerCommand(new CommandSeen(api));

        // Register /datejoin command
        registerCommand(new CommandDateJoin(api));

        // Register /info command
        registerCommand(new CommandInfo(this));
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

    /**
     * Returns the time the server started in milliseconds.
     * @return the time the server started in milliseconds
     */
    public long getStart() {
        return start;
    }

    /**
     * Returns the TicksPerSecondTask instance.
     * @return the TicksPerSecondTask instance
     */
    public TicksPerSecondTask getTpsHandler() {
        return tpsHandler;
    }
}
