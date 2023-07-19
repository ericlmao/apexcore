package games.negative.apexcore.listener;

import com.google.common.collect.Lists;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.ApexPermission;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class ApexChatListener implements Listener {

    private final ApexAPI api;

    public ApexChatListener(@NotNull ApexAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        ApexPlayer user = api.getPlayer(sender.getUniqueId());
        if (user == null) return;

        List<Player> toRemove = Lists.newArrayList();

        Set<Player> recipients = event.getRecipients();
        for (Player recipient : recipients) {
            if (!user.isIgnoring(recipient.getUniqueId())) continue;
            if (recipient.hasPermission(ApexPermission.IGNORE_BYPASS)) continue;

            toRemove.add(recipient);
        }

        toRemove.forEach(event.getRecipients()::remove);
    }

}
