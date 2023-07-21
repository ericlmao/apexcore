package games.negative.apexcore.core.provider;

import com.google.common.collect.Maps;
import games.negative.apexcore.ApexCore;
import games.negative.apexcore.api.ApexPlaceholderManager;
import games.negative.apexcore.api.model.ApexPlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ApexPlaceholderManagerProvider extends PlaceholderExpansion implements ApexPlaceholderManager {

    private final ApexCore plugin;
    private final Map<String, ApexPlaceholder> placeholders;

    public ApexPlaceholderManagerProvider(@NotNull ApexCore plugin) {
        this.plugin = plugin;
        this.placeholders = Maps.newHashMap();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "core";
    }

    @Override
    public @NotNull String getAuthor() {
        List<String> authors = plugin.getDescription().getAuthors();
        if (authors.size() > 1) {
            StringBuilder builder = new StringBuilder();
            for (String author : authors) {
                builder.append(author).append(", ");
            }
            return builder.substring(0, builder.length() - 2);
        }

        return authors.get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(@Nullable Player player, @NotNull String params) {
        if (player == null) return null;

        String[] split = params.split("_");
        if (split.length == 0) return null;

        ApexPlaceholder placeholder = getPlaceholder(split[0].toLowerCase());
        if (placeholder == null) return null;

        String[] args = Arrays.copyOfRange(split, 1, split.length);

        return placeholder.handle(player, args);
    }

    @Override
    public void registerPlaceholder(@NotNull String identifier, @NotNull ApexPlaceholder placeholder) {
        String id = identifier.toLowerCase();
        this.placeholders.remove(id);
        this.placeholders.put(id, placeholder);
    }

    @Override
    public void unregisterPlaceholder(@NotNull String identifier) {
        this.placeholders.remove(identifier.toLowerCase());
    }

    @Override
    public boolean isPlaceholderRegistered(@NotNull String identifier) {
        return this.placeholders.containsKey(identifier.toLowerCase());
    }

    @Override
    public @Nullable ApexPlaceholder getPlaceholder(@NotNull String identifier) {
        return this.placeholders.getOrDefault(identifier.toLowerCase(), null);
    }

    @Override
    public @NotNull Collection<ApexPlaceholder> getRegisteredPlaceholders() {
        return Collections.unmodifiableCollection(this.placeholders.values());
    }
}
