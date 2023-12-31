package games.negative.apexcore.command;

import games.negative.alumina.command.Command;
import games.negative.alumina.command.Context;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.api.model.Conversation;
import games.negative.apexcore.core.ApexPermission;
import games.negative.apexcore.core.Locale;
import games.negative.apexcore.core.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.UUID;

public class CommandMessage implements Command {

    private final ApexAPI api;

    public CommandMessage(@NotNull ApexAPI api) {
        this.api = api;
    }

    @Override
    public void execute(@NotNull Context context) {
        Player sender = context.getPlayer();
        assert sender != null;

        UUID uuid = sender.getUniqueId();
        ApexPlayer profile = api.getPlayer(uuid);
        if (profile == null) {
            Locale.GENERIC_PROFILE_ERROR.send(sender);
            return;
        }

        String[] args = context.args();

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            Locale.GENERIC_PLAYER_NOT_FOUND.replace("%player%", args[0]).send(sender);
            return;
        }

        UUID recipientUUID = target.getUniqueId();
        ApexPlayer recipient = api.getPlayer(recipientUUID);
        if (recipient == null) {
            Locale.GENERIC_PLAYER_NOT_FOUND.replace("%player%", args[0]).send(sender);
            return;
        }

        String[] messageRaw = Arrays.copyOfRange(args, 1, args.length);
        String message = TextUtil.combine(messageRaw);

        if (message.isEmpty()) {
            Locale.MESSAGE_NO_MESSAGE.send(sender);
            return;
        }

        // SENDER is ignoring RECEIVER
        if (profile.isIgnoring(recipientUUID)) {
            Locale.MESSAGE_CANNOT_SEND_IGNORING.replace("%player%", target.getName()).send(sender);
            return;
        }

        // RECEIVER is ignoring SENDER
        if (recipient.isIgnoring(uuid)) {
            Locale.MESSAGE_CANNOT_SEND_IGNORED.replace("%player%", target.getName()).send(sender);
            return;
        }

        // RECEIVER has messages disabled and SENDER does not have bypass permission
        if (!recipient.isMessageable() && !sender.hasPermission(ApexPermission.MESSAGE_BYPASS)) {
            Locale.MESSAGE_CANNOT_SEND_DISABLED.replace("%player%", target.getName()).send(sender);
            return;
        }

        Locale.MESSAGE_SENDER.replace("%player%", target.getName()).replace("%message%", message).send(sender);
        Locale.MESSAGE_RECEIVER.replace("%player%", sender.getName()).replace("%message%", message).send(target);

        if (recipient.isMessageSound()) {
            target.playSound(target.getLocation(), recipient.getMessageSound(), 1, 1);
        }

        api.updateConversation(uuid, recipientUUID);
        api.updateConversation(recipientUUID, uuid);
    }
}
