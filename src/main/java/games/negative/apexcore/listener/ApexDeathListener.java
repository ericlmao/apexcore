package games.negative.apexcore.listener;

import games.negative.alumina.builder.ItemBuilder;
import games.negative.apexcore.ApexCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ApexDeathListener implements Listener {

    private final NamespacedKey killerKey;

    public ApexDeathListener(@NotNull ApexCore plugin) {
        this.killerKey = new NamespacedKey(plugin, "killer");
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer == null) return;

        ItemStack stack = new ItemBuilder(Material.PLAYER_HEAD)
                .setName("<yellow>" + victim.getName() + "'s Head")
                .addLoreLine("<gray>Killed by <red>" + killer.getName())
                .setSkullOwner(victim.getPlayerProfile())
                .applyPersistentData(data -> data.set(killerKey, PersistentDataType.STRING, killer.getUniqueId().toString()))
                .build();

        event.getDrops().add(stack);
    }
}
