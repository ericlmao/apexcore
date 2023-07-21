package games.negative.apexcore.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

public class ApexDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        if (victim.getKiller() == null) return;

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        if (meta == null) return;

        meta.setDisplayName(ChatColor.YELLOW + victim.getName() + "'s Head");
        meta.setOwningPlayer(victim);
        skull.setItemMeta(meta);

        event.getDrops().add(skull);
    }
}
