package games.negative.apexcore.ui;

import com.google.common.collect.Lists;
import games.negative.alumina.builder.ItemBuilder;
import games.negative.alumina.menu.MenuButton;
import games.negative.alumina.menu.PaginatedMenu;
import games.negative.alumina.util.IntList;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class IgnoreListMenu extends PaginatedMenu {

    private final ApexPlayer user;

    public IgnoreListMenu(@NotNull ApexPlayer user) {
        super("Your Ignored Players", 6);
        setCancelClicks(true);

        this.user = user;

        List<Integer> fillerSlots = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 45, 46, 47, 48, 49, 50, 51, 52, 53);
        fillerSlots.forEach(index -> addButton(MenuButton.builder().slot(index).item(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()).build()));

        setPaginatedSlots(IntList.getList(List.of("9-44")));

        List<OfflinePlayer> ignored = user.getIgnoredUsers().stream().map(Bukkit::getOfflinePlayer).toList();

        Collection<MenuButton> buttons = generatePaginatedButtons(ignored, player -> {
            ItemStack stack = new ItemBuilder(Material.PLAYER_HEAD)
                    .setSkullOwner(player.getPlayerProfile())
                    .setName("<yellow>" + player.getName())
                    .addLoreLine("<gray>Click to unignore this player.")
                    .build();

            return MenuButton.builder().item(stack).action(new RemoveIgnoreUserClickAction(player.getUniqueId())).build();
        });

        setPaginatedButtons(buttons);

        setPreviousPageButton(
                MenuButton.builder().item(new ItemBuilder(Material.ARROW)
                .setName("<red>Previous Page").build())
                .action((menuButton, player, inventoryClickEvent) -> changePage(player, page - 1)).build()
        );

        setNextPageButton(
                MenuButton.builder().item(new ItemBuilder(Material.ARROW)
                .setName("<green>Next Page").build())
                .action((menuButton, player, inventoryClickEvent) -> changePage(player, page + 1)).build()
        );

    }

    @RequiredArgsConstructor
    private class RemoveIgnoreUserClickAction implements MenuButton.ClickAction {
        private final UUID uuid;

        @Override
        public void onClick(@NotNull MenuButton button, @NotNull Player player, @NotNull InventoryClickEvent event) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(uuid);

            if (!user.isIgnoring(uuid)) {
                Locale.IGNORE_NOT_IGNORING.create().replace("%player%", target.getName()).send(player);
                return;
            }

            user.removeIgnoredUser(uuid);
            Locale.IGNORE_REMOVE_SINGLE.create().replace("%player%", target.getName()).send(player);

            player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
            player.closeInventory();
        }
    }

}
