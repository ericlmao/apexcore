package games.negative.apexcore.ui;

import com.google.common.collect.Lists;
import games.negative.alumina.menu.ChestMenu;
import games.negative.alumina.menu.base.MenuItem;
import games.negative.alumina.menu.filler.FillerItem;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class IgnoreListMenu extends ChestMenu {

    private final ApexAPI api;
    private final int page;
    private final ApexPlayer user;

    public IgnoreListMenu(@NotNull ApexAPI api, @NotNull ApexPlayer user, int page) {
        super("Your Ignored Players", 6);

        this.api = api;
        this.page = page;
        this.user = user;

        List<Integer> fillerSlots = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53);
        fillerSlots.forEach(index -> setItem(index, FillerItem.BLACK));

        int limit = Math.abs(fillerSlots.size() - (9 * 6));

        List<UUID> ignored = user.getIgnoredUsers();
        List<UUID> sorted = ignored.stream().skip((long) (page - 1) * limit).limit(limit).toList();
        for (UUID uuid : sorted) {
            // todo Convert to ItemBuilder later
            ItemStack display = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta = display.getItemMeta();
            if (meta == null) continue;

            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

            meta.setDisplayName(ChatColor.YELLOW + player.getName());
            meta.setLore(List.of(ChatColor.GRAY + "Click to unignore this player."));

            if (meta instanceof SkullMeta skull)
                skull.setOwningPlayer(player);
        }
    }

    @Override
    public void onFunctionClick(@NotNull Player player, @NotNull MenuItem item, @NotNull InventoryClickEvent event) {
        event.setCancelled(true);

        String key = item.key();
        if (key == null) return;

        switch (key.toLowerCase()) {
            case "next-page" -> handleNextPage(player);
            case "previous-page" -> handlePreviousPage(player);
            case "head" -> handleHead(player, item);
        }


    }

    private void handleHead(@NotNull Player player, @NotNull MenuItem item) {
        ItemStack itemStack = item.item();
        if (itemStack.getType() != Material.PLAYER_HEAD) return;
        if (!(itemStack.getItemMeta() instanceof SkullMeta meta)) return;

        OfflinePlayer target = meta.getOwningPlayer();
        if (target == null) {
            Locale.GENERIC_PROFILE_ERROR_OTHER.send(player);
            return;
        }

        UUID uuid = target.getUniqueId();
        if (!user.isIgnoring(uuid)) {
            Locale.IGNORE_NOT_IGNORING.replace("%player%", target.getName()).send(player);
            return;
        }

        user.removeIgnoredUser(uuid);
        Locale.IGNORE_REMOVE_SINGLE.replace("%player%", target.getName()).send(player);

        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
        player.closeInventory();
    }

    private void handlePreviousPage(@NotNull Player player) {
        new IgnoreListMenu(api, user, page - 1).open(player);
    }

    private void handleNextPage(@NotNull Player player) {
        new IgnoreListMenu(api, user, page + 1).open(player);
    }
}
