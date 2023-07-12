package games.negative.apexcore.ui;

import games.negative.alumina.menu.ChestMenu;
import games.negative.alumina.menu.base.MenuItem;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlayer;
import games.negative.apexcore.core.Locale;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class IgnoreListMenu extends ChestMenu {

    private final ApexAPI api;
    private final int page;

    public IgnoreListMenu(@NotNull ApexAPI api, int page) {
        super("Your Ignored Players", 6);

        this.api = api;
        this.page = page;
    }

    @Override
    public void onFunctionClick(@NotNull Player player, @NotNull MenuItem item, @NotNull InventoryClickEvent event) {
        event.setCancelled(true);

        String key = item.key();
        if (key == null) return;

        switch (key.toLowerCase()) {
            case "next-page" -> handleNextPage(player);
            case "previous-page" -> handlePreviousPage(player);
        }


    }

    private void handlePreviousPage(@NotNull Player player) {
        new IgnoreListMenu(api, page - 1).open(player);
    }

    private void handleNextPage(@NotNull Player player) {
        new IgnoreListMenu(api, page + 1).open(player);
    }
}
