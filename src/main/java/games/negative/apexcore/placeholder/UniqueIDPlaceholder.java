package games.negative.apexcore.placeholder;

import games.negative.alumina.util.NumberUtil;
import games.negative.apexcore.api.ApexAPI;
import games.negative.apexcore.api.model.ApexPlaceholder;
import games.negative.apexcore.api.model.ApexPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UniqueIDPlaceholder implements ApexPlaceholder {

    private final ApexAPI api;

    public UniqueIDPlaceholder(@NotNull ApexAPI api) {
        this.api = api;
    }

    @Override
    public @Nullable String handle(@NotNull Player player, @NotNull String[] params) {
        ApexPlayer user = api.getPlayer(player.getUniqueId());
        return (user != null ? NumberUtil.parse(user.getID()) : null);
    }
}
