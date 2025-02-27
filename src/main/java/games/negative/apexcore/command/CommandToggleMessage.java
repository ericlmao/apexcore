package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.CommandContext;
import games.negative.alumina.command.builder.CommandBuilder;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandToggleMessage extends Command {

    private final ApexAPI api;

    public CommandToggleMessage(@NotNull ApexAPI api) {
        super(CommandBuilder.builder()
                .name("togglemessage")
                .aliases("tm", "togglemsg", "tmsg", "togglepm", "tpm")
                .description("Toggles whether or not you can receive messages.")
                .playerOnly(true));

        this.api = api;
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        Player player = context.player().orElseThrow();

        ApexPlayer profile = api.getPlayer(player.getUniqueId());
        if (profile == null) {
            Locale.GENERIC_PROFILE_ERROR.create().send(player);
            return;
        }

        // declares the new "state" variable as the opposite of the current state
        boolean state = !profile.isMessageable();
        profile.setMessageable(state);

        // send the appropriate message
        Locale message = state ? Locale.MESSAGE_TOGGLE_ENABLED : Locale.MESSAGE_TOGGLE_DISABLED;
        message.create().send(player);
    }

}
