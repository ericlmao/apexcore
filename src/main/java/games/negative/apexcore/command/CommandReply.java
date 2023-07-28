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

import java.util.UUID;

public class CommandReply implements Command {

    private final ApexAPI api;

    public CommandReply(@NotNull ApexAPI api) {
        this.api = api;
    }

    @Override
    public void execute(@NotNull Context context) {
        Player player = context.getPlayer();
        assert player != null;

        UUID uuid = player.getUniqueId();

        ApexPlayer profile = api.getPlayer(uuid);
        if (profile == null) {
            Locale.GENERIC_PROFILE_ERROR.send(player);
            return;
        }

        if (!api.hasConversation(uuid)) {
            Locale.REPLY_CANNOT_SEND.send(player);
            return;
        }

        Conversation conversation = api.getConversation(uuid);
        if (conversation == null) return;

        UUID recipient = conversation.recipient();
        Player target = Bukkit.getPlayer(recipient);
        if (target == null) {
            Locale.GENERIC_PLAYER_NOT_FOUND
                    .replace("%player%", Bukkit.getOfflinePlayer(recipient).getName())
                    .send(player);
            return;
        }

        ApexPlayer user = api.getPlayer(recipient);
        if (user == null) {
            Locale.GENERIC_PROFILE_ERROR_OTHER.send(player);
            return;
        }

        String[] args = context.args();

        String message = TextUtil.combine(args);
        if (message.isEmpty()) {
            Locale.MESSAGE_NO_MESSAGE.send(player);
            return;
        }

        // SENDER is ignoring RECEIVER
        if (profile.isIgnoring(target.getUniqueId())) {
            Locale.MESSAGE_CANNOT_SEND_IGNORING.replace("%player%", target.getName()).send(player);
            api.removeConversation(uuid);
            return;
        }

        // RECEIVER is ignoring SENDER
        if (user.isIgnoring(uuid)) {
            Locale.MESSAGE_CANNOT_SEND_IGNORED.replace("%player%", target.getName()).send(player);
            api.removeConversation(uuid);
            return;
        }

        // RECEIVER has messages disabled and SENDER does not have bypass permission
        if (!user.isMessageable() && !player.hasPermission(ApexPermission.MESSAGE_BYPASS)) {
            Locale.MESSAGE_CANNOT_SEND_DISABLED.replace("%player%", target.getName()).send(player);
            api.removeConversation(uuid);
            return;
        }

        message = message.replaceAll("&([0-9a-fk-or])", "");

        String username = user.getNameColor() + target.getName();

        Locale.MESSAGE_SENDER
                .replace("%player%", username)
                .replace("%message%", message)
                .send(player);

        Locale.MESSAGE_RECEIVER
                .replace("%player%", profile.getNameColor() + player.getName())
                .replace("%message%", message)
                .send(target);

        if (user.isMessageSound()) {
            target.playSound(target.getLocation(), user.getMessageSound(), 1, 1);
        }

        api.updateConversation(uuid, recipient);
        api.updateConversation(recipient, uuid);
    }

}
