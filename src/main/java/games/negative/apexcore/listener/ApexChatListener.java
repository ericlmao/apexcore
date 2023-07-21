package games.negative.apexcore.listener;

import com.google.common.collect.Lists;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.ApexPermission;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ApexChatListener implements Listener {

    private final ApexAPI api;

    public ApexChatListener(@NotNull ApexAPI api) {
        this.api = api;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        UUID uuid = sender.getUniqueId();

        List<Player> toRemove = Lists.newArrayList();
        Set<Player> recipients = event.getRecipients();

        for (Player recipient : recipients) {
            ApexPlayer user = api.getPlayer(recipient.getUniqueId());
            if (user == null) continue;

            if (!user.isIgnoring(uuid)) continue;
            if (sender.hasPermission(ApexPermission.IGNORE_BYPASS)) continue;

            toRemove.add(recipient);
        }

        toRemove.forEach(event.getRecipients()::remove);

        String message = event.getMessage();
        if (!message.startsWith(">")) return;
        
        event.setMessage(ChatColor.GREEN + message);
    }

}
