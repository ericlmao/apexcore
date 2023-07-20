package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandToggleMessageSound implements Command {

    private final ApexAPI api;

    public CommandToggleMessageSound(@NotNull ApexAPI api) {
        this.api = api;
    }

    @Override
    public void execute(@NotNull Context context) {
        Player player = context.getPlayer();
        assert player != null;

        ApexPlayer profile = api.getPlayer(player.getUniqueId());
        if (profile == null) {
            Locale.GENERIC_PROFILE_ERROR.send(player);
            return;
        }

        // declares the new "state" variable as the opposite of the current state
        boolean state = !profile.isMessageSound();
        profile.setMessageSound(state);

        // send the appropriate message
        Locale message = state ? Locale.MESSAGE_TOGGLE_SOUND_ENABLED : Locale.MESSAGE_TOGGLE_SOUND_DISABLED;
        message.send(player);
    }

}
